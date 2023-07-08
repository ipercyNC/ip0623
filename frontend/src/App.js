import logo from './logo.svg';
import styles from './App.css';
import axios from 'axios';
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import Paper from '@mui/material/Paper';
import ToolType from './model_components/ToolType';
import Box from '@mui/material/Box';
import Grid from '@mui/material/Grid';

const React = require('react');
const ReactDOM = require('react-dom');


export default class App extends React.Component {
  constructor(props) {
    super(props);
    this.state = { toolTypes: [] };
  }

  componentDidMount() {
    axios.get('/api/toolType').then(response => {
      const toolTypes = response.data.map(toolType =>
        <ToolType key={toolType.id} toolType={toolType.name} />
      );
      this.setState({ toolTypes: toolTypes });
    });
  }

  render() {
    console.log(this.state.toolTypes)

    return (
      <>
        <Box sx={{ flexGrow: 1 }}>
          <Grid container spacing={12}>
            <Grid item xs={2}>
              <item>
                {
                  this.state.toolTypes.length > 0 &&
                  <TableContainer component={Paper} sx={{ width: 200 }}>
                    <Table sx={{ width: 200 }} aria-label="simple table">
                      <TableHead sx={{ backgroundColor: "lightblue" }}>
                        <TableRow >
                          <TableCell>Name</TableCell>
                        </TableRow>
                      </TableHead>
                      <TableBody>
                        {this.state.toolTypes.map((toolType) => (
                          <TableRow>
                            <TableCell component="th" scope="toolType">{toolType.props.toolType}</TableCell>
                          </TableRow>
                        ))}
                      </TableBody>
                    </Table>
                  </TableContainer>
                }
              </item>
            </Grid>
            <Grid item xs={2}>
              <item>
                {
                  this.state.toolTypes.length > 0 &&
                  <TableContainer component={Paper} sx={{ width: 200 }}>
                    <Table sx={{ width: 200 }} aria-label="simple table">
                      <TableHead sx={{ backgroundColor: "lightblue" }}>
                        <TableRow >
                          <TableCell>Name</TableCell>
                        </TableRow>
                      </TableHead>
                      <TableBody>
                        {this.state.toolTypes.map((toolType) => (
                          <TableRow>
                            <TableCell component="th" scope="toolType">{toolType.props.toolType}</TableCell>
                          </TableRow>
                        ))}
                      </TableBody>
                    </Table>
                  </TableContainer>
                }
              </item>
            </Grid>
          </Grid>
        </Box >

      </>

    )
  }
}



