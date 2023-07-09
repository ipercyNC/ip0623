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
import FilledInput from '@mui/material/FilledInput';
import FormControl from '@mui/material/FormControl';
import FormHelperText from '@mui/material/FormHelperText';
import Input from '@mui/material/Input';
import InputLabel from '@mui/material/InputLabel';
import OutlinedInput from '@mui/material/OutlinedInput';
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs';
import { LocalizationProvider } from '@mui/x-date-pickers/LocalizationProvider';
import { DatePicker } from '@mui/x-date-pickers/DatePicker';
import { DateField } from '@mui/x-date-pickers/DateField';
import Button from '@mui/material/Button';
import moment from 'moment';

const React = require('react');
const ReactDOM = require('react-dom');


export default class App extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      toolTypes: [], toolBrands: [], toolChoices: [], toolCharges: [],
      toolCode: "", checkoutDate: "", rentalDays: 0, percentDiscount: 0,
      rentalAgreement: ""
    };
  }

  componentDidMount() {
    axios.get('/api/toolType').then(response => {
      this.setState({ toolTypes: response.data });
    });
    axios.get('/api/toolBrand').then(response => {
      this.setState({ toolBrands: response.data });
    });
    axios.get('/api/toolChoices').then(response => {
      this.setState({ toolChoices: response.data });
    });
    axios.get('/api/toolCharges').then(response => {
      this.setState({ toolCharges: response.data });
    });
  }
  handleDateChange = (newValue) => {
    let formatString = newValue.$y + "-" + String((parseInt(newValue.$M) + 1)).padStart(2, "0") + "-" + String(newValue.$D).padStart(2, "0");
    this.setState({ checkoutDate: formatString });
  }
  handleSubmit = (event) => {
    event.preventDefault();
    console.log("toolCode", this.state.toolCode)
    console.log("checkoutDate", this.state.checkoutDate)
    console.log("rentalDays", this.state.rentalDays)
    console.log("discount", this.state.percentDiscount)
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
    })
    )
  }
  render() {
    return (
      <>
        <Box sx={{ flexGrow: 1 }}>
          <Grid container spacing={4}>
            <Grid item xs={2}>
              <item>
                {
                  this.state.toolTypes.length > 0 &&
                  <TableContainer component={Paper}>
                    <Table aria-label="simple table">
                      <TableHead sx={{ backgroundColor: "lightblue" }}>
                        <TableRow >
                          <TableCell>Name</TableCell>
                        </TableRow>
                      </TableHead>
                      <TableBody>
                        {this.state.toolTypes.map((toolType) => (
                          <TableRow>
                            <TableCell component="th" scope="toolType" align="right">{toolType.name}</TableCell>
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
                  this.state.toolBrands.length > 0 &&
                  <TableContainer component={Paper}>
                    <Table aria-label="simple table">
                      <TableHead sx={{ backgroundColor: "lightblue" }}>
                        <TableRow >
                          <TableCell>Name</TableCell>
                        </TableRow>
                      </TableHead>
                      <TableBody>
                        {this.state.toolBrands.map((toolBrand) => (
                          <TableRow>
                            <TableCell component="th" scope="toolBrand" align="right">{toolBrand.name}</TableCell>
                          </TableRow>
                        ))}
                      </TableBody>
                    </Table>
                  </TableContainer>
                }
              </item>
            </Grid>
            <Grid item xs={3}>
              <item>
                {
                  this.state.toolChoices.length > 0 &&
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
                        {this.state.toolChoices.map((toolChoice) => (
                          <TableRow>
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
            </Grid>
            <Grid item xs={5}>
              <item>
                {
                  this.state.toolCharges.length > 0 &&
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
                        {this.state.toolCharges.map((toolCharge) => (
                          <TableRow>
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
            </Grid>
            <Grid item xs={12}>
              <item>
                <Box
                  component="form"
                  sx={{
                    '& > :not(style)': { m: 1 },
                  }}
                  noValidate
                  autoComplete="off"
                  onSubmit={this.handleSubmit}
                >
                  <FormControl>
                    <InputLabel htmlFor="component-outlined">Tool Code</InputLabel>
                    <OutlinedInput
                      id="component-outlined"
                      defaultValue=""
                      label="Tool Code"
                      value={this.state.toolCode}
                      onChange={e => this.setState({ toolCode: e.target.value })}
                    />
                  </FormControl>
                  <LocalizationProvider dateAdapter={AdapterDayjs}>
                    <DatePicker
                      label="Checkout Date"
                      defaultValue=""
                      value={this.state.checkoutDate}
                      onChange={this.handleDateChange} />
                  </LocalizationProvider>
                  <FormControl>
                    <InputLabel htmlFor="component-outlined">Rental Days</InputLabel>
                    <OutlinedInput
                      id="component-outlined"
                      defaultValue=""
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
                    />
                  </FormControl>
                  <Button variant="contained" type="submit"
                    style={{ maxWidth: '120px', maxHeight: '55px', minWidth: '120px', minHeight: '55px' }}
                  >Rent Tool</Button>
                </Box>
              </item>
            </Grid>
            <Grid item xs={12}>
              {this.state.rentalAgreement &&
                <Paper elevation={3}>
                  <p style={{whiteSpace: "pre-line"}}>
                    {this.state.rentalAgreement}
                  </p>
                </Paper>
              }
            </Grid>
          </Grid>
        </Box >

      </>

    )
  }
}



