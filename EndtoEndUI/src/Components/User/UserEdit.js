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
import './User.css';
import axios from 'axios';
import { BrowserRouter as Router, Route, Link, Redirect } from "react-router-dom";

class UserEdit extends React.Component{
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
      updateSuccess: false,
      attempt: 0
    };


  }

  componentDidMount(){
    console.log(this.props);
    const uid = this.props.match.params.id;
    const self = this;
    axios.get(this.url+'users/'+uid,{})
    .then(function (response) {
      const attempt = self.state.attempt;
      console.log(response);
      if(response.data == "Username or Password Incorrect!"){


      }else{
        self.setState({
          username: response.data.username,
          password: response.data.password
        });

      }
    })
    .catch(function (error) {
      console.log(error);
    });


  }

  handleSubmit(e){
    e.preventDefault();
    const uid = this.props.match.params.id;
    const self = this;
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

      axios.put(this.url+'users/update', {
        id: uid,
        username: this.state.username,
        password: this.state.password
      })
      .then(function (response) {
        const attempt = self.state.attempt;
        console.log(response);
        if(response.data == "Username or Password Incorrect!"){

          self.setState({
            attempt: attempt+1,
            updateSuccess: false
          });
        }else{
          self.setState({
            attempt: attempt+1,
            updateSuccess: true
          });

          window.location="/user-list";
        }
      })
      .catch(function (error) {
        console.log(error.response);
        if(error.response.status == 500){
          self.setState({
            isValid: false,
            errorMessage: "Username already taken"
          });

        }
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
                {this.state.updateSuccess ? <SuccessMessage message="Update Successful"/> : ""}
                {!this.state.updateSuccess && this.state.attempt>0 ? <ErrorMessage message="Error Updating User"/> : ""}

                <TextField
                  id="user-name"
                  label="User Name"

                  value={this.state.username}
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
                  Update
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

export default UserEdit;
