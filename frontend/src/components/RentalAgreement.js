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
                <Paper elevation={3} sx={{
                    padding: 1, width: "30vw",
                    transform: "translate(100%, 2%)",
                    height: "40vh"
                }}>
                    <Box >
                        <Grid container spacing={2} sx={{
                            padding: 2, display: "flex",
                            justifyContent: "center",
                            alignItems: "center",
                        }}>
                            <Grid item xs={12}>
                                <Typography variant="h5" sx={{
                                    color: "#1976D2",
                                    fontWeight: "bold",
                                    display: "flex",
                                    justifyContent: "center",
                                    alignItems: "center", height: "25px"
                                }}>
                                    Rental Agreement
                                </Typography>
                                <Divider />
                            </Grid>

                            {this.state.rentalAgreement && this.state.rentalAgreement.map((line, i) => {
                                let lineSplit = line.split(":")
                                return (
                                    <>
                                        {/* Label + Value for line */}
                                        <Grid key={i} item xs={12} height="26px">
                                            <Typography display="inline" variant="h6" sx={{
                                                color: "#1976D2",
                                                fontWeight: "bold"
                                            }}>
                                                {lineSplit[0]}:
                                            </Typography>
                                            <Typography display="inline" variant="subtitle1">
                                                {lineSplit[1]}
                                            </Typography>
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
