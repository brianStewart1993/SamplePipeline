import React, {Component} from 'react';
import Button from '@material-ui/core/Button';
import Input from '@material-ui/core/Input';
import InputLabel from '@material-ui/core/InputLabel';
import TextField from '@material-ui/core/TextField';
import Card from '@material-ui/core/Card';
import CardActions from '@material-ui/core/CardActions';
import CardContent from '@material-ui/core/CardContent';
import Grid from '@material-ui/core/Grid';
import Typography from '@material-ui/core/Typography';
import './SignUp.css';
import axios from 'axios';

class LoginForm extends React.Component{
  url = "http://10.150.59.6:8080/";
  constructor(props){
    super(props);
    this.handleUsernameChange = this.handleUsernameChange.bind(this);
    this.handlePasswordChange = this.handlePasswordChange.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);

    this.state = {
      username: "",
      password: "",
      isValid: true,
      isPasswordLengthValid: true,
      passwordErrorMessage: "Password must be atleast 8 characters long.",
      errorMessage: "",
      loginSuccess: false,
      loginAttempt: 0,
      successMessage: ""
    };


  }

  handleSubmit(e){
    e.preventDefault();
    const self = this;
    const loginAttempt = self.state.loginAttempt;
    if(this.validate()){
      console.log(this.state);
      /*axios.post(this.url+'users/', {
        username: this.state.username,
        password: this.state.password
      })
      .then(function (response) {
        console.log(response);
      })
      .catch(function (error) {
        console.log(error);
      });*/

      axios.post(this.url+'users/addCustom', {

          username: this.state.username,
          password: this.state.password

      })
      .then(function (response) {

        console.log(response);
        if(response.data == "Username or Password Incorrect!"){

          self.setState({
            loginAttempt: loginAttempt+1,
            loginSuccess: false
          });
        }else{
          self.setState({
            successMessage: response.data,
            loginSuccess: true
          });

          window.location="/";
        }
      })
      .catch(function (error) {
        console.log(error.response);
        self.setState({
          isValid: false,
          loginAttempt: loginAttempt+1,
          errorMessage: error.response.data
        });
      });
    }else{

    }

  };

  checkLoginFields(){
    if(this.state.username && this.state.password){
      this.setState({
        isValid : true
      })
    }
  }

  handleUsernameChange(e){
    console.log(e.target.value);
    this.setState({
      username: e.target.value
    });
    this.checkLoginFields();

  };

  handlePasswordChange(e){
    console.log(e.target.value);
    const password =  e.target.value;


    if(password.length<8){
      this.setState({
        password: password
      });
    }else{
      this.setState({
        password: password,
        isPasswordLengthValid: true
      });
    }

    this.checkLoginFields();


  };



  validate(){
    if(!this.state.username || !this.state.password){
      this.setState({
        isValid: false,
        errorMessage: "User Name and Password is required"
      });
      return false;
    }else if(this.state.password && this.state.password.length<8){
      this.setState({
        errorMessage: "User Name and Password is required",
        isPasswordLengthValid: false
      });
      return false;
    }
    this.setState({
      isValid: true,
      isPasswordLengthValid: true
    });
    return true;
  }

  render(){
    return (

        <div id="login-form">
          <form onSubmit={this.handleSubmit}>
            <Card>
              <CardContent>

                <Typography variant="display1" gutterBottom align="center">
                  Welcome to Cash Secured
                </Typography>

                <Typography variant="subheading" type="password" gutterBottom align="center">
                  Please enter your user name and password
                </Typography>

                {this.state.isValid ? "" : <ErrorMessage message={this.state.errorMessage}/>}
                {this.state.loginSuccess ? <SuccessMessage message={this.state.successMessage}/> : ""}

                <TextField
                  id="user-name"
                  label="User Name"

                  value={this.state.name}
                  onChange={this.handleUsernameChange}
                  margin="normal"
                />

                <br/>


                <TextField
                  id="password"
                  label="Password"
                  type="password"
                  value={this.state.password}
                  onChange={this.handlePasswordChange}
                  margin="normal"
                />

                {this.state.isPasswordLengthValid ? "" : <ErrorMessage message={this.state.passwordErrorMessage}/>}
              </CardContent>

              <CardActions>
                <Button type="submit" variant="raised" color="primary">
                  Sign Up
                </Button>

              </CardActions>
          </Card>


        </form>
      </div>
    );
  }
}

function ErrorMessage(props){
  return (<Typography variant="body1" color="error">{props.message}</Typography>);
}

function SuccessMessage(props){
  return (<Typography variant="body1" color="primary">{props.message}</Typography>);
}

export default LoginForm;
