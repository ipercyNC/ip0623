/*
 * ToolBrandTable.js
 * 7/10/2023
 * Ian Percy
 * 
 * 
 * Component for displaying the ToolBrand table
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

export default class ToolBrandTable extends React.Component {
    render() {
        return (
            <>
                <Typography variant="h6" component="div" sx={{ flexGrow: 1 }}>
                    Tool Brands
                </Typography>
                <item>
                    {
                        this.props.toolBrands.length > 0 &&
                        <TableContainer component={Paper}>
                            <Table aria-label="simple table">
                                <TableHead sx={{ backgroundColor: "lightblue" }}>
                                    <TableRow>
                                        <TableCell>Name</TableCell>
                                    </TableRow>
                                </TableHead>
                                <TableBody>
                                    {/* Print out ToolBrand info */}
                                    {this.props.toolBrands.map((toolBrand) => (
                                        <TableRow key={toolBrand.name}>
                                            <TableCell component="th" scope="toolBrand" align="right">{toolBrand.name}</TableCell>
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
