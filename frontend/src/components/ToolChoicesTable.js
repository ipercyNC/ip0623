/*
 * ToolChoicesTable.js
 * 7/10/2023
 * Ian Percy
 * 
 * 
 * Component for displaying the ToolChoices table
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

export default class ToolChoicesTable extends React.Component {
    render() {
        return (
            <>
              <Typography variant="h6" component="div" sx={{ flexGrow: 1 }}>
                Tool Choices
              </Typography>
              <item>
                {
                  this.props.toolChoices.length > 0 &&
                  <TableContainer component={Paper}>
                    <Table aria-label="simple table">
                      <TableHead sx={{ backgroundColor: "lightblue" }}>
                        <TableRow >
                          <TableCell>Code</TableCell>
                          <TableCell>Tool Brand</TableCell>
                          <TableCell>Tool Type</TableCell>
                        </TableRow>
                      </TableHead>
                      <TableBody>
                        {/* Print out ToolChoices info */}
                        {this.props.toolChoices.map((toolChoice) => (
                          <TableRow key={toolChoice.code}>
                            <TableCell component="th" scope="toolchoice" align="right">{toolChoice.code}</TableCell>
                            <TableCell align="right">{toolChoice.toolBrand.name}</TableCell>
                            <TableCell align="right">{toolChoice.toolType.name}</TableCell>

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
