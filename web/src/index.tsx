import React from 'react';
import ReactDOM from 'react-dom';
import './css/index.css';
import {BrowserRouter, Route, Switch} from 'react-router-dom';
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

let tempApi = process.env.REACT_APP_LOCAL_API_URL;
if (process.env.REACT_APP_PRODUCTION_API_URL) {
    tempApi = process.env.REACT_APP_PRODUCTION_API_URL
}
export const api = tempApi;


let elLocaleData = require('react-intl/locale-data/ru');
addLocaleData(elLocaleData);

let i18nConfig = {
    locale: 'ru',
    messages: {}
};

TextResourcesService.getAllTextResources().then((res:any) => {
    i18nConfig.messages = res.data;
    ReactDOM.render(
        <IntlProvider locale={i18nConfig.locale} messages={i18nConfig.messages}>
            <BrowserRouter>
                <Switch>
                    <Route exact path='/' component={Login} />
                    <Route path='/login' component={Login}/>
                    <Route path='/registration' component={Registration}/>
                    <Route path='/me' component={User}/>
                    <Route path='/user/:userId' component={User}/>
                    <Route path='/events' component={Events} />
                    <Route path='/friends' component={Friends} />
                    <Route path='/groups' component={Groups} />
                    <Route path='/createGroup' component={CreateGroup} />
                    <Route path='/group/:groupId' component={Group} />
                    <Route path='/profile' component={Profile} />
                    <Route path='/dialogs' component={Dialogs} />
                    <Route path='/dialog/:dialogId' component={Dialog} />
                </Switch>
            </BrowserRouter>
        </IntlProvider>, document.getElementById('root'));
});
