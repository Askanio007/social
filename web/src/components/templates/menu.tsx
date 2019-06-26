import React, {Component} from 'react';
import {FormattedMessage} from 'react-intl';
import {Link} from 'react-router-dom';

class MainMenu extends Component {

    render() {
        return (
            <div className="col-sm-2">
                <div className="nav flex-column nav-pills" id="v-pills-tab" aria-orientation="vertical">
                    <Link to={'/me'} className="custom-link nav-link"><FormattedMessage id="menu.home" /></Link>
                    <Link to={'/events'} className="custom-link nav-link"><FormattedMessage id="menu.news" /></Link>
                    <Link to={'/friends'} className="custom-link nav-link"><FormattedMessage id="menu.friends" /></Link>
                    <Link to={'/dialogs'} className="custom-link nav-link"><FormattedMessage id="menu.messages" /></Link>
                    <Link to={'/groups'} className="custom-link nav-link"><FormattedMessage id="menu.groups" /></Link>
                </div>
            </div>
        )
    }

}

export default MainMenu;
