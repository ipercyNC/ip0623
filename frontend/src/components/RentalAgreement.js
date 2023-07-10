/*
 * RentalAgreement.js
 * 7/10/2023
 * Ian Percy
 * 
 * 
 * Component for displaying the Rental Agreement
 */
import React from 'react';
import Typography from '@mui/material/Typography';
import Paper from '@mui/material/Paper';
import { Divider } from '@mui/material';
import Box from '@mui/material/Box';
import Grid from '@mui/material/Grid';

export default class RentalAgreement extends React.Component {
    constructor(props) {
        super(props);
        // Set the variable for the rentalAgreement - split into an array
        this.state = {
            rentalAgreement: props.rentalAgreement.split("\n")
        };
    }
    render() {
        return (
            <>
                <Paper elevation={3} sx={{ padding: 1 }}>
                    <Box>
                        <Grid container spacing={2}>
                            <Grid item xs={12}>
                                <Typography display="inline" variant="h5" sx={{
                                    color: "#4585d9",
                                    fontWeight: "bold"
                                }}>
                                    Rental Agreement
                                    <Divider />
                                </Typography>
                            </Grid>

                            {this.state.rentalAgreement && this.state.rentalAgreement.map((line, i) => {
                                let lineSplit = line.split(":")
                                return (
                                    <>
                                        {/* Label for line */}
                                        <Grid key={i} xs={3} sx={{ height: "40px" }}>
                                            <Typography display="inline" variant="h6" sx={{
                                                color: "#4585d9",
                                                fontWeight: "bold",
                                                width: "130px"
                                            }}>
                                                {lineSplit[0]}:
                                            </Typography>
                                        </Grid>
                                        {/* Value for line */}
                                        <Grid item xs={9} sx={{ height: "40px" }}>
                                            <Typography display="inline" variant="subtitle1">
                                                {lineSplit[1]}
                                            </Typography>
                                            <br />
                                        </Grid>

                                    </>)
                            })}
                        </Grid>
                    </Box>
                </Paper>
            </>
        )
    }
}
