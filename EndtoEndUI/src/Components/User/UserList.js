import React, {Component} from 'react';
import PropTypes from 'prop-types';
import { withStyles } from '@material-ui/core/styles';
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';
import Paper from '@material-ui/core/Paper';
import Icon from '@material-ui/core/Icon';
import IconButton from '@material-ui/core/IconButton';
import DeleteIcon from '@material-ui/icons/Delete';
import EditIcon from '@material-ui/icons/Edit';
import AddShoppingCartIcon from '@material-ui/icons/AddShoppingCart';
import PhotoCamera from '@material-ui/icons/PhotoCamera';
import axios from 'axios';
import './UserList.css';
import { BrowserRouter as Router, Route, Link } from "react-router-dom";
import Snackbar from '@material-ui/core/Snackbar';
import CloseIcon from '@material-ui/icons/Close';
import Button from '@material-ui/core/Button';
import Dialog from '@material-ui/core/Dialog';
import DialogActions from '@material-ui/core/DialogActions';
import DialogContent from '@material-ui/core/DialogContent';
import DialogContentText from '@material-ui/core/DialogContentText';
import DialogTitle from '@material-ui/core/DialogTitle';


let id = 0;
function createData(name, calories, fat, carbs, protein) {
  id += 1;
  return { id, name, calories, fat, carbs, protein };
}

const data = [
  createData('Frozen yoghurt', 159, 6.0, 24, 4.0),
  createData('Ice cream sandwich', 237, 9.0, 37, 4.3),
  createData('Eclair', 262, 16.0, 24, 6.0),
  createData('Cupcake', 305, 3.7, 67, 4.3),
  createData('Gingerbread', 356, 16.0, 49, 3.9),
];

const url = "http://10.150.59.6:8080/";

class UserList extends React.Component {

  constructor(props){
    super(props);
    /*this.handleUsernameChange = this.handleUsernameChange.bind(this);
    this.handlePasswordChange = this.handlePasswordChange.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);*/
    this.handleDelete = this.handleDelete.bind(this);
    this.confirmDelete = this.confirmDelete.bind(this);
    this.handleClose = this.handleClose.bind(this);
    this.handleConfrimDeleteClose = this.handleConfrimDeleteClose.bind(this);

    this.state = {
      username: "",
      password: "",
      users: [],
      open: false,
      openDeleteConfirm: false,
      prevDeletedUser: "",
      idForDelete: 0,
      usernameForDelete: 0
    };


  }

  componentDidMount(){
    this.reloadUsers();

  }

  reloadUsers(){
    const self = this;
    axios.get(url+'users/', {

    })
    .then(function (response) {
      console.log(response);
      const userData = response.data;
      self.setState({
        users: userData
      });
    })
    .catch(function (error) {
      console.log(error);
    });

  }

  handleClose(e){
    this.setState({
      open: false
    });
  }

  handleConfrimDeleteClose(e){
    this.setState({
      openDeleteConfirm: false
    });
  }

  confirmDelete(id,username){
    this.setState({
      openDeleteConfirm: true,
      idForDelete: id,
      usernameForDelete: username
    });

  }


  handleDelete(e){
    const self = this;
    const uid = this.state.idForDelete;
    const username = this.state.usernameForDelete;
    axios.delete(url+'users/delete/'+uid, {

    })
    .then(function (response) {
      console.log(response);
      self.reloadUsers();
      self.setState({
        open: true,
        openDeleteConfirm: false,
        prevDeletedUser: username
      });

    })
    .catch(function (error) {
      console.log(error);
      self.setState({
        openDeleteConfirm: false,
      });
    });

  }



  render(){
    return (
    <div id="user-list">
      <Paper >
        <Table >
          <TableHead>
            <TableRow className="user-header">
              <TableCell>ID </TableCell>
              <TableCell numeric>User Name</TableCell>
              <TableCell numeric>Password</TableCell>
              <TableCell numeric></TableCell>
              <TableCell numeric></TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {this.state.users.map(n => {
              return (
                <TableRow  key={n.id}>
                  <TableCell >
                    {n.id}
                  </TableCell>
                  <TableCell >
                    {n.username}
                  </TableCell>
                  <TableCell >{n.password}</TableCell>
                  <TableCell>
                    <IconButton  aria-label="Edit"  color="primary">
                      <Link to={"/edit-user/"+n.id}>
                        <EditIcon />
                      </Link>
                    </IconButton>
                  </TableCell>
                  <TableCell>
                    <IconButton onClick={(e) => this.confirmDelete(n.id,n.username)} aria-label="Delete"  color="primary">
                      <DeleteIcon />
                    </IconButton>
                  </TableCell>
                </TableRow>
              );
            })}
          </TableBody>
        </Table>
      </Paper>

      <Dialog
          open={this.state.openDeleteConfirm}
          onClose={this.handleConfrimDeleteClose}
          aria-labelledby="alert-dialog-title"
          aria-describedby="alert-dialog-description"
        >
          <DialogTitle id="alert-dialog-title">{"Delete this user?"}</DialogTitle>
          <DialogContent>
            <DialogContentText id="alert-dialog-description">
              This user will be permanently deleted.
            </DialogContentText>
          </DialogContent>
          <DialogActions>
            <Button onClick={this.handleConfrimDeleteClose} color="primary">
              No
            </Button>
            <Button onClick={this.handleDelete} color="primary" autoFocus>
              Yes
            </Button>
          </DialogActions>
        </Dialog>

      <Snackbar
          anchorOrigin={{
            vertical: 'bottom',
            horizontal: 'left',
          }}
          open={this.state.open}
          autoHideDuration={6000}
          onClose={this.handleClose}
          ContentProps={{
            'aria-describedby': 'message-id',
          }}
          message={<span id="message-id">User {this.state.prevDeletedUser} deleted</span>}

        />
    </div>
  );
  }
}

export default UserList;
