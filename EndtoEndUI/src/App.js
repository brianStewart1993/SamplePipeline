import React, { Component } from 'react';
import logo from './logo.svg';
import './App.css';
import LoginForm from './Components/Login/Login';
import SignUp from './Components/SignUp/SignUp';
import UserList from './Components/User/UserList';
import UserEdit from './Components/User/UserEdit';
import AppBar from '@material-ui/core/AppBar';
import Toolbar from '@material-ui/core/Toolbar';
import Typography from '@material-ui/core/Typography';
import Button from '@material-ui/core/Button';
import { shallow } from 'enzyme';
import { BrowserRouter as Router, Route, Link } from "react-router-dom";

/*import { Router, Route, hashHistory } from 'react-router'*/

/*const RootStack = createStackNavigator({
  Home: {
    screen: App
  }
});*/

class App extends Component {




  constructor(props){
    super(props);
    /*this.handleUsernameChange = this.handleUsernameChange.bind(this);
    this.handlePasswordChange = this.handlePasswordChange.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);*/

    this.state = {
      username: "",
      password: "",
      users: []
    };


  }






  render() {
    /*return <Login />;*/
    return (
      <Router>

        <div id="app">
          <AppBar position="static" color="primary">
            <Toolbar>
              <Typography  variant="title" color="inherit">
                Cash Secured Portal
              </Typography>

                <Button color="inherit" className="logout-btn"  >
                  <Link to="/">
                    Logout
                  </Link>
                </Button>

            </Toolbar>
          </AppBar>
          <Route exact path="/" component={LoginForm} />
          <Route path="/sign-up" component={SignUp} />
          <Route path="/user-list" component={UserList} />
          <Route path="/edit-user/:id" component={UserEdit} />

        </div>
      </Router>
    );
  }


}


export default App;
