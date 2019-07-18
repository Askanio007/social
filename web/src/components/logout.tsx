import React from 'react';
import UserService from '../service/UserService';
import {Redirect} from 'react-router-dom';

export default function Logout() {
    UserService.cleanLocalStorage();
    return <Redirect to={"/login"} />;
}
