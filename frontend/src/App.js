/*
 * App.js
 * 7/10/2023
 * Ian Percy
 * 
 * 
 * Main view for the application. Entry point and renderer 
 */
import axios from 'axios';
import Box from '@mui/material/Box';
import Grid from '@mui/material/Grid';
import AppBar from '@mui/material/AppBar';
import Toolbar from '@mui/material/Toolbar';
import IconButton from '@mui/material/IconButton';
import Typography from '@mui/material/Typography';
import ToolTypeTable from './components/ToolTypeTable';
import ToolBrandTable from './components/ToolBrandTable';
import ToolChoicesTable from './components/ToolChoicesTable';
import ToolChargesTable from './components/ToolChargesTable';
import RentToolForm from './components/RentlToolForm';

const React = require('react');

export default class App extends React.Component {
  constructor(props) {
    super(props);
    // Save all of the necesssary information for the application
    this.state = {
      toolTypes: [], toolBrands: [], toolChoices: [], toolCharges: [],
      toolCode: "", checkoutDate: undefined, rentalDays: 0, percentDiscount: 0,
      rentalAgreement: "", error: false, errorMessage: {}
    };
  }

  // Call all of the tables we need from the backend
  componentDidMount() {
    axios.get('/api/toolType').then(response => {
      this.setState({ toolTypes: response.data });
    }).catch(error => console.log("Error getting ToolType table", error));
    axios.get('/api/toolBrand').then(response => {
      this.setState({ toolBrands: response.data });
    }).catch(error => console.log("Error getting ToolBrand table", error));
    axios.get('/api/toolChoices').then(response => {
      this.setState({ toolChoices: response.data });
    }).catch(error => console.log("Error getting ToolChoices table", error));
    axios.get('/api/toolCharges').then(response => {
      this.setState({ toolCharges: response.data });
    }).catch(error => console.log("Error getting ToolCharges table", error));
  }

  render() {
    return (
      <>
        <Box sx={{ flexGrow: 1 }}>
          <AppBar position="static">
            <Toolbar>
              <IconButton
                size="large"
                edge="start"
                color="inherit"
                aria-label="menu"
                sx={{ mr: 2 }}
              >
                {/* <MenuIcon /> */}
              </IconButton>
              <Typography variant="h6" component="div" sx={{ flexGrow: 1 }}>
                Toolywood Rentals
              </Typography>
            </Toolbar>
          </AppBar>
          <Grid container spacing={4} padding={2}>
            {/* Print out ToolTypes info */}
            <Grid item xs={2}>
              <ToolTypeTable toolTypes={this.state.toolTypes} />
            </Grid>
            {/* Print out ToolBrands info */}
            <Grid item xs={2}>
              <ToolBrandTable toolBrands={this.state.toolBrands} />
            </Grid>
            {/* Display ToolChoices info*/}
            <Grid item xs={3}>
              <ToolChoicesTable toolChoices={this.state.toolChoices} />
            </Grid>
            {/* Display ToolCharges info */}
            <Grid item xs={5}>
              <ToolChargesTable toolCharges={this.state.toolCharges} />
            </Grid>
            {/* Display tool rental form */}

            <Grid item xs={12}   >
              <RentToolForm />
            </Grid>
          </Grid>
        </Box >

      </>

    )
  }
}



