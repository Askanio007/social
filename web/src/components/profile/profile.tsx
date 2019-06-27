import React, {Component} from 'react';
import MainMenu from '../templates/menu';
import Tabs from '../templates/tabs';
import {FormattedMessage} from 'react-intl';
import FriendsRequest from '../friends/friendsRequest';
import EditDetails from './editDetails';

export default class Profile extends Component {

    render() {
        return (
            <div className="container">
                <div className="row">
                    <MainMenu />
                    <Tabs>
                        <EditDetails><FormattedMessage id="profile.menu.detail" /></EditDetails>
                        <FriendsRequest><FormattedMessage id="profile.menu.photo" /></FriendsRequest>
                    </Tabs>
                </div>
            </div>
        );
    }

}
