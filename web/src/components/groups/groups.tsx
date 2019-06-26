import React, {Component} from 'react';
import MainMenu from '../templates/menu';
import Tabs from '../templates/tabs';
import {FormattedMessage} from 'react-intl';
import GroupsList from './groupsList';
import GroupsSearch from './groupsSearch';

class Groups extends Component {

    render() {
        return (
            <div className="container">
                <div className="row">
                    <MainMenu />
                    <Tabs>
                        <GroupsList><FormattedMessage id="groups.my" /></GroupsList>
                        <GroupsSearch><FormattedMessage id="groups.search" /></GroupsSearch>
                    </Tabs>
                </div>
            </div>
        );
    }
}

export default Groups;
