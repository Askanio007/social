import React, {Component} from 'react';
import MainMenu from '../templates/menu';
import Tabs from '../templates/tabs';
import {FormattedMessage} from 'react-intl';
import EditDetails from './editDetails';
import EditPhoto from './editPhoto';

export default class Profile extends Component {

    render() {
        return (
            <div className="container">
                <div className="row">
                    <MainMenu />
                    <Tabs>
                        <EditDetails><FormattedMessage id="profile.menu.detail" /></EditDetails>
                        <EditPhoto><FormattedMessage id="profile.menu.photo" /></EditPhoto>
                    </Tabs>
                </div>
            </div>
        );
    }

}
