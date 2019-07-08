import React, {Component} from 'react';
import {FormattedMessage} from 'react-intl';
import MainMenu from '../templates/menu';
import Tabs from '../templates/tabs';
import FriendsList from './friendsList';
import FriendsRequest from './friendsRequest';
import FriendsSearch from './friendsSearch';

class Friends extends Component<any, any> {

    render() {
        return (
            <div className="container">
                <div className="row">
                    <MainMenu />
                    <Tabs>
                        <FriendsList><FormattedMessage id="friends.menu.my" /></FriendsList>
                        <FriendsRequest><FormattedMessage id="friends.menu.request" /></FriendsRequest>
                        <FriendsSearch><FormattedMessage id="friends.menu.search" /></FriendsSearch>
                    </Tabs>
                </div>
            </div>
        );
    }
}

export default Friends;
