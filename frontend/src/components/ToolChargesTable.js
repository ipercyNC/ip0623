/*
 * ToolChargesTable.js
 * 7/10/2023
 * Ian Percy
 * 
 * 
 * Component for displaying the ToolCharges table
 */
import React from 'react';
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import Typography from '@mui/material/Typography';
import Paper from '@mui/material/Paper';

export default class ToolChargesTable extends React.Component {
    render() {
        return (
            <>
              <Typography variant="h6" component="div" sx={{ flexGrow: 1 }}>
                Tool Charges
              </Typography>
              <item>
                {
                  this.props.toolCharges.length > 0 &&
                  <TableContainer component={Paper}>
                    <Table aria-label="simple table">
                      <TableHead sx={{ backgroundColor: "lightblue" }}>
                        <TableRow >
                          <TableCell>Tool Type</TableCell>
                          <TableCell>Daily Charge</TableCell>
                          <TableCell>Weekday Charge</TableCell>
                          <TableCell>Weekend Charge</TableCell>
                          <TableCell>Holiday Charge</TableCell>
                        </TableRow>
                      </TableHead>
                      <TableBody>
                        {/* Print out ToolCharges info */}
                        {this.props.toolCharges.map((toolCharge) => (
                          <TableRow key={toolCharge.toolType.name}>
                            <TableCell component="th" scope="toolCharge" align="right">{toolCharge.toolType.name}</TableCell>
                            <TableCell align="right">${toolCharge.dailyCharge}</TableCell>
                            <TableCell align="right">{toolCharge.weekdayCharge === 1 ? "Yes" : "No"}</TableCell>
                            <TableCell align="right">{toolCharge.weekendCharge === 1 ? "Yes" : "No"}</TableCell>
                            <TableCell align="right">{toolCharge.holidayCharge === 1 ? "Yes" : "No"}</TableCell>
                          </TableRow>
                        ))}
                      </TableBody>
                    </Table>
                  </TableContainer>
                }
              </item>
            </>
        )
    }
}
