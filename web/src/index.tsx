import React from 'react';
import ReactDOM from 'react-dom';
import './css/index.css';
import {BrowserRouter, Redirect, Route, Switch} from 'react-router-dom';
import {addLocaleData, IntlProvider} from 'react-intl';
import Login from './components/login';
import Registration from './components/registration';
import User from './components/user';
import Events from './components/events';
import Friends from './components/friends/friends';
import Groups from './components/groups/groups';
import CreateGroup from './components/groups/createGroup';
import Group from './components/groups/group';
import Profile from './components/profile/profile';
import Dialogs from './components/dialogs/dialogs';
import Dialog from './components/dialogs/dialog';
import TextResourcesService from './service/TextResourcesService';
import EditGroup from './components/groups/edit/editGroup';
import ForgotPassword from './components/forgotPassword';
import RestorePassword from './components/restorePassword';
import Logout from './components/logout';
import UserService from './service/UserService';
import ApiClient from './service/ApiClient';

let tempApi = process.env.REACT_APP_LOCAL_API_URL;
if (process.env.REACT_APP_PRODUCTION_API_URL) {
    tempApi = process.env.REACT_APP_PRODUCTION_API_URL
}
export const api ='http://' + tempApi + '/api/v1';
export const websocket = 'ws://' + tempApi + '/dialog/websocket';


let elLocaleData = require('react-intl/locale-data/ru');
addLocaleData(elLocaleData);

let i18nConfig = {
    locale: 'ru',
    messages: {}
};

function isLogged() {
    return ApiClient.getToken() != null && ApiClient.getToken() !== "" && UserService.getRootUserId() !== 0;
}

function authCheck(element:React.ReactNode) {
    return isLogged() ? element : <Redirect to="/" />
}

TextResourcesService.getAllTextResources((res:any) => {
    i18nConfig.messages = res.data;
    ReactDOM.render(
        <IntlProvider locale={i18nConfig.locale} messages={i18nConfig.messages}>
            <BrowserRouter>
                <Switch>
                    <Route exact path='/' render={() => {return isLogged() ? <User/> : <Login />}}/>
                    <Route path='/login' render={() => {return isLogged() ? <User/> : <Login />}}/>
                    <Route path='/registration' render={() => {return isLogged() ? <User/> : <Registration />}}/>
                    <Route path='/me' render={() => {return authCheck(<User/>)}}/>
                    <Route path='/user/:userId' render={() => {return authCheck(<User/>)}}/>
                    <Route path='/events' render={() => {return authCheck(<Events/>)}}/>
                    <Route path='/friends' render={() => {return authCheck(<Friends/>)}}/>
                    <Route path='/groups' render={() => {return authCheck(<Groups/>)}}/>
                    <Route path='/createGroup' render={() => {return authCheck(<CreateGroup/>)}}/>
                    <Route path='/group/:groupId' render={() => {return authCheck(<Group/>)}}/>
                    <Route path='/profile' render={() => {return authCheck(<Profile/>)}}/>
                    <Route path='/dialogs' render={() => {return authCheck(<Dialogs/>)}}/>
                    <Route path='/dialog/:dialogId' render={() => {return authCheck(<Dialog/>)}}/>
                    <Route path='/editGroup/:groupId' render={() => {return authCheck(<EditGroup/>)}}/>
                    <Route path='/restore-password/:token' render={() => {return isLogged() ? <User/> : <RestorePassword />}}/>
                    <Route path='/forgot-password' render={() => {return isLogged() ? <User/> : <ForgotPassword />}}/>
                    <Route path='/logout' component={Logout} />
                </Switch>
            </BrowserRouter>
        </IntlProvider>, document.getElementById('root'));
});
