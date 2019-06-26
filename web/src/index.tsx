import React from 'react';
import ReactDOM from 'react-dom';
import './css/index.css';
import {BrowserRouter, Route, Switch} from 'react-router-dom';
import {addLocaleData, IntlProvider} from 'react-intl';
import {getLocalizationMessage} from './api';
import Login from './components/login';
import Registration from './components/registration';
import User from './components/user';
import Events from './components/events';
import Friends from './components/friends/friends';
import Groups from './components/groups/groups';
import CreateGroup from './components/groups/createGroup';
import Group from './components/groups/group';

let elLocaleData = require('react-intl/locale-data/ru');
addLocaleData(elLocaleData);

let i18nConfig = {
    locale: 'ru',
    messages: {}
};

fetch(getLocalizationMessage + 'ru')
    .then(response => response.json())
    .then(data => {
        i18nConfig.messages = data;
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
                    </Switch>
                </BrowserRouter>
            </IntlProvider>, document.getElementById('root'));
    });

//serviceWorker.unregister();
