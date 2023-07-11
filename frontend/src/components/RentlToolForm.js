/*
 * RentToolForm.js
 * 7/10/2023
 * Ian Percy
 * 
 * Form for allows users to input tool rental options. Calls backend to rent tool.
 */
import React from 'react';
import axios from 'axios';
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
import dayjs from 'dayjs';
import RentalAgreement from './RentalAgreement';

export default class RentToolForm extends React.Component {

    constructor(props) {
        super(props);
        // Set the proper variables needed for the rental api call
        this.state = {
            toolCode: "", checkoutDate: undefined, rentalDays: 1, percentDiscount: 0,
            rentalAgreement: "", error: false, errorMessage: {}
        };
    }

    /*
    * Handle simple form validation and calling backend with the rental details
    *
    * @param {event} event
    */
    handleSubmit = (event) => {
        event.preventDefault();
        let isError = false;
        // Catch if ToolCode isn't proper format (4 characters)
        if (this.state.toolCode.length !== 4) {
            isError = true;
            let tempErrorMessage = this.state.errorMessage
            tempErrorMessage.toolCode = "Enter 4 Character Code"
            // Set ToolCode error message
            this.setState({
                error: true,
                errorMessage: tempErrorMessage
            });
        }

        //Catch if rental days isn't valid (must be 1 or more days)
        let rentalDaysParsed = parseInt(this.state.rentalDays)
        if (rentalDaysParsed === NaN || rentalDaysParsed < 1) {
            isError = true;
            let tempErrorMessage = this.state.errorMessage
            tempErrorMessage.rentalDays = "Enter 1 Or More Days"
            // Set RentalDays error message
            this.setState({
                error: true,
                errorMessage: tempErrorMessage
            });
        }

        //Catch if percent discount isn't valid (must be 0 - 100 percent)
        let percentParsed = parseInt(this.state.percentDiscount)
        if (percentParsed === NaN || percentParsed < 0 || percentParsed > 100) {
            isError = true;
            let tempErrorMessage = this.state.errorMessage
            tempErrorMessage.percentDiscount = "Enter 0-100 Percent"
            // Set PercentDiscount error message
            this.setState({
                error: true,
                errorMessage: tempErrorMessage
            });
        } 
        

        // If the form validation did not trigger an error, call the backend
        if (!isError) {
            this.setState({
                error: false,
                errorMessage: {}, 
                rentalAgreement: ""
            })
            // Create JSON object for api
            const json = JSON.stringify({
                "code": this.state.toolCode,
                "startDate": moment(this.state.checkoutDate.$d).format("YYYY-MM-DD"),
                "days": this.state.rentalDays,
                "discount": this.state.percentDiscount
            });
            axios.post('/api/rentTool', json, {
                // Set content type to JSON
                headers: {
                    'Content-Type': 'application/json'
                }
            }).then(response => {
                // Save response into RentalAgreement to be displayed
                console.log(response);
                this.setState({ rentalAgreement: response.data })
            }).catch(error => {
                // Catch error and display alert
                console.log(error);
                setTimeout(() => alert(error.response.data), 1000);
            })

        }
    }
    render() {
        return (
            <>
                <Divider />
                {/* Header for form */}
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
                        {/* Tool Code input */}
                        <FormControl>
                            <InputLabel htmlFor="toolcode-component">Tool Code</InputLabel>
                            <OutlinedInput
                                error={!!this.state.errorMessage.toolCode}
                                id="toolcode-component"
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
                        {/* Checkout Date input */}
                        <LocalizationProvider dateAdapter={AdapterDayjs}>
                            <DatePicker
                                label="Checkout Date"
                                adapterLocale="en-us"
                                value={this.state.checkoutDate}
                                onChange={e => this.setState({checkoutDate: e})}
                            />
                        </LocalizationProvider>
                        {/* Rental Days input */}
                        <FormControl>
                            <InputLabel htmlFor="rentaldays-component">Rental Days</InputLabel>
                            <OutlinedInput
                                error={!!this.state.errorMessage.rentalDays}
                                id="rentaldays-component"
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
                        {/* PercentDiscount input */}
                        <FormControl>
                            <InputLabel htmlFor="discount-component">Percent Discount</InputLabel>
                            <OutlinedInput
                                error={!!this.state.errorMessage.percentDiscount}
                                id="discount-component"
                                label="Percent Discount %"
                                value={this.state.percentDiscount}
                                onChange={e => this.setState({ percentDiscount: e.target.value })}
                            />
                            <FormHelperText id="percent-discount-helper" sx={{
                                position: 'absolute',
                                bottom: '-1rem'
                            }}
                                error={!!this.state.errorMessage.percentDiscount}>{this.state.errorMessage.percentDiscount}
                            </FormHelperText>
                        </FormControl>
                        {/* Submit button */}
                        <Button variant="contained" type="submit"
                            style={{ maxWidth: '120px', maxHeight: '55px', minWidth: '120px', minHeight: '55px' }}
                        >Rent Tool</Button>
                    </Box>
                    {/* Display RentalAgreement */}
                    {this.state.rentalAgreement &&
                        <RentalAgreement rentalAgreement={this.state.rentalAgreement} />
                    }
                </item>
            </>
        )
    }
}
