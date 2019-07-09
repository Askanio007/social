import React, {Component} from 'react';
import UserService from '../service/UserService';
import {Redirect} from 'react-router-dom';

export default class Logout extends Component<any, any> {

    render() {
        UserService.cleanLocalStorage();
        return (
            <Redirect to={"/login"} />
        );
    }

}
