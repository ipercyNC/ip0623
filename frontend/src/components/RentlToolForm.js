import React from 'react';
import axios from 'axios';
import Paper from '@mui/material/Paper';
import Box from '@mui/material/Box';
import FormControl from '@mui/material/FormControl';
import FormHelperText from '@mui/material/FormHelperText';
import InputLabel from '@mui/material/InputLabel';
import OutlinedInput from '@mui/material/OutlinedInput';
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs';
import { LocalizationProvider } from '@mui/x-date-pickers/LocalizationProvider';
import { DatePicker } from '@mui/x-date-pickers/DatePicker';
import Button from '@mui/material/Button';
import moment from 'moment';
import Typography from '@mui/material/Typography';
import { Divider } from '@mui/material';
import { makeStyles } from '@mui/styles';
import dayjs from 'dayjs';
import RentalAgreement from './RentalAgreement';

export default class RentToolForm extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            toolCode: "", checkoutDate: dayjs(Date.now()), rentalDays: 0, percentDiscount: 0,
            rentalAgreement: "", error: false, errorMessage: {}
        };
    }
    handleDateChange = (newValue) => {
        // Catch if the valid is invalid - otherwise this will generate runtime errors
        let date = moment(newValue.$d)
        if (!date.isValid()) {
            let tempErrorMessage = this.state.errorMessage
            tempErrorMessage.checkoutDate = "Enter Valid Date"
            this.setState({
                error: true,
                errorMessage: tempErrorMessage
            });
        }
        else {
            let tempErrorMessage = this.state.errorMessage
            if (tempErrorMessage.hasOwnProperty("checkoutDate")) {
                delete tempErrorMessage["checkoutDate"]
            }
            this.setState({
                checkoutDate: date.format("yyyy-MM-DD"),
                errorMessage: tempErrorMessage
            });
            console.log(this.state)
        }


    }
    handleSubmit = (event) => {
        event.preventDefault();
        let isError = false;
        if (this.state.toolCode.length !== 4) {
            isError = true;
            let tempErrorMessage = this.state.errorMessage
            tempErrorMessage.toolCode = "Enter 4 Character Code"
            this.setState({
                error: true,
                errorMessage: tempErrorMessage
            });
        }
        let rentalDaysParsed = parseInt(this.state.rentalDays)
        if (rentalDaysParsed === NaN || rentalDaysParsed < 1) {
            isError = true;
            let tempErrorMessage = this.state.errorMessage
            tempErrorMessage.rentalDays = "Enter 1 Or More Days"
            this.setState({
                error: true,
                errorMessage: tempErrorMessage
            });
        }

        let percentParsed = parseInt(this.state.percentDiscount)
        console.log("percent", percentParsed)
        if (percentParsed === NaN || percentParsed < 0 || percentParsed > 100) {
            isError = true;
            let tempErrorMessage = this.state.errorMessage
            tempErrorMessage.percentDiscount = "Enter 0-100 Percent"
            this.setState({
                error: true,
                errorMessage: tempErrorMessage
            });
            console.log(this.state)
        }
        if (!isError) {
            this.setState({
                error: false,
                errorMessage: {}
            })
            const json = JSON.stringify({
                "code": this.state.toolCode,
                "startDate": this.state.checkoutDate,
                "days": this.state.rentalDays,
                "discount": this.state.percentDiscount
            });
            console.log(axios.post('/api/rentTool', json, {
                // Overwrite Axios's automatically set Content-Type
                headers: {
                    'Content-Type': 'application/json'
                }
            }).then(response => {
                console.log(response);
                this.setState({ rentalAgreement: response.data })
            }).catch(error => {
                console.log(error);
            })
            )
        }
    }
    render() {
        return (
            <>
                <Divider />
                <Typography display="flex"
                    justifyContent="center"
                    alignItems="center" variant="h4" component="div" sx={{ flexGrow: 1 }}>
                    Tool Checkout:
                </Typography>
                <item>
                    <Box
                        component="form"
                        sx={{
                            '& > :not(style)': { m: 1 },
                        }}
                        noValidate
                        autoComplete="off"
                        display="flex"
                        justifyContent="center"
                        alignItems="center"
                        onSubmit={this.handleSubmit}
                    >

                        <FormControl>
                            <InputLabel htmlFor="component-outlined">Tool Code</InputLabel>
                            <OutlinedInput
                                error={!!this.state.errorMessage.toolCode}
                                id="component-outlined"
                                defaultValue=""
                                label="Tool Code"
                                value={this.state.toolCode}
                                onChange={e => this.setState({ toolCode: e.target.value })}

                            />
                            <FormHelperText id="tool-code-helper" sx={{
                                position: 'absolute',
                                bottom: '-1rem'
                            }}
                                error={!!this.state.errorMessage.toolCode}>{this.state.errorMessage.toolCode}
                            </FormHelperText>
                        </FormControl>
                        <LocalizationProvider dateAdapter={AdapterDayjs}>
                            <DatePicker
                                label="Checkout Date"
                                value={this.state.checkoutDate}
                            />
                        </LocalizationProvider>
                        <FormControl>
                            <InputLabel htmlFor="component-outlined">Rental Days</InputLabel>
                            <OutlinedInput
                                error={!!this.state.errorMessage.rentalDays}
                                id="component-outlined"
                                label="Rental Days"
                                value={this.state.rentalDays}
                                onChange={e => this.setState({ rentalDays: e.target.value })}
                            />
                            <FormHelperText id="rental-days-helper" sx={{
                                position: 'absolute',
                                bottom: '-1rem'
                            }}
                                error={!!this.state.errorMessage.rentalDays}>{this.state.errorMessage.rentalDays}
                            </FormHelperText>
                        </FormControl>
                        <FormControl>
                            <InputLabel htmlFor="component-outlined">Percent Discount</InputLabel>
                            <OutlinedInput
                                error={!!this.state.errorMessage.percentDiscount}
                                id="component-outlined"
                                label="Percent Discount %"
                                value={this.state.percentDiscount}
                                onChange={e => this.setState({ percentDiscount: e.target.value })}
                            />
                            <FormHelperText id="rental-days-helper" sx={{
                                position: 'absolute',
                                bottom: '-1rem'
                            }}
                                error={!!this.state.errorMessage.percentDiscount}>{this.state.errorMessage.percentDiscount}
                            </FormHelperText>
                        </FormControl>
                        <Button variant="contained" type="submit"
                            style={{ maxWidth: '120px', maxHeight: '55px', minWidth: '120px', minHeight: '55px' }}
                        >Rent Tool</Button>
                    </Box>
                    {this.state.rentalAgreement &&
                        <RentalAgreement rentalAgreement={this.state.rentalAgreement} />
                    }
                </item>
            </>
        )
    }
}
