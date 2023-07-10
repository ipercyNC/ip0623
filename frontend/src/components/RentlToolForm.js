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

export default class RentToolForm extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            toolTypes: [], toolBrands: [], toolChoices: [], toolCharges: [],
            toolCode: "", checkoutDate: undefined, rentalDays: 0, percentDiscount: 0,
            rentalAgreement: "", error: false, errorMessage: {}
        };
    }
    handleDateChange = (newValue) => {
        // Catch if the valid is invalid - otherwise this will generate runtime errors
        let date = moment(newValue.$d)
        if (!date.isValid()) {
            this.setState({ checkoutDate: undefined });
        }
        else {
            this.setState({ checkoutDate: date.format("yyyy-MM-DD") });
        }


    }
    handleSubmit = (event) => {
        event.preventDefault();
        console.log("toolCode", this.state.toolCode)
        console.log("checkoutDate", this.state.checkoutDate)
        console.log("rentalDays", this.state.rentalDays)
        console.log("discount", this.state.percentDiscount)
        let isError = false;
        console.log(this.state.toolCode.length)
        if (this.state.toolCode.length !== 4) {
            isError = true;
            this.setState({
                error: true,
                errorMessage: { toolCode: "Enter ToolCode that is 4 letters long" }
            });
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
                            <FormHelperText id="tool-code-helper"
                                error={!!this.state.errorMessage.toolCode}>{this.state.errorMessage.toolCode}
                            </FormHelperText>
                        </FormControl>
                        <LocalizationProvider dateAdapter={AdapterDayjs}>
                            <DatePicker
                                label="Checkout Date"
                                defaultValue={undefined}
                                value={this.state.checkoutDate || undefined}
                                onChange={this.handleDateChange} />
                        </LocalizationProvider>
                        <FormControl>
                            <InputLabel htmlFor="component-outlined">Rental Days</InputLabel>
                            <OutlinedInput
                                id="component-outlined"
                                label="Rental Days"
                                value={this.state.rentalDays}
                                onChange={e => this.setState({ rentalDays: e.target.value })}
                            />
                        </FormControl>
                        <FormControl>
                            <InputLabel htmlFor="component-outlined">Percent Discount</InputLabel>
                            <OutlinedInput
                                id="component-outlined"
                                label="Percent Discount"
                                value={this.state.percentDiscount}
                                onChange={e => this.setState({ percentDiscount: e.target.value })}
                                helperText={
                                    this.state.errorMessage.zipCode &&
                                    this.state.errorMessage.zipCode
                                }
                            />
                        </FormControl>
                        <Button variant="contained" type="submit"
                            style={{ maxWidth: '120px', maxHeight: '55px', minWidth: '120px', minHeight: '55px' }}
                        >Rent Tool</Button>
                    </Box>
                    {this.state.rentalAgreement &&
                        <Paper elevation={3}>
                            <p style={{ whiteSpace: "pre-line" }}>
                                {this.state.rentalAgreement}
                            </p>
                        </Paper>
                    }
                </item>
            </>
        )
    }
}
