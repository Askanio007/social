import React, {Component} from 'react';
import {FormattedMessage} from 'react-intl';
import {Link} from 'react-router-dom';
import FriendService from '../../service/FriendService';
import UserService from '../../service/UserService';
import DialogService from '../../service/DialogService';

interface MenuState {
    messageNotificationCount: number
    friendRequestNotificationCount: number
}
class MainMenu extends Component<any, MenuState> {

    state : MenuState = {
        messageNotificationCount: 0,
        friendRequestNotificationCount: 0
    };

    componentDidMount(): void {
        let messageCount:number = 0;
        let friendRequest:number = 0;
        let rootUserId:number = UserService.getRootUserId();
        FriendService.findCountRequestToUser(rootUserId, (res:any) => {
            if (res.data.success === true) {
                friendRequest = res.data.data;
            }
        }).then(() => {
            return DialogService.findCountNotReadMessage(rootUserId, (res:any) => {
                if (res.data.success === true) {
                    messageCount = res.data.data;
                }
            })
        }).then(() => {
            this.setState({
                messageNotificationCount: messageCount,
                friendRequestNotificationCount: friendRequest
            })
        })
    }

    Notification = (count:number) => {
        if (count === 0) {
            return null;
        }
        return (
            <strong>[{count}]</strong>
        );
    };

    render() {
        let requestNotification = this.Notification(this.state.friendRequestNotificationCount);
        let messageNotification = this.Notification(this.state.messageNotificationCount);
        return (
            <div className="col-sm-2 none-padding-lef-right">
                <div className="nav flex-column nav-pills" id="v-pills-tab" aria-orientation="vertical">
                    <Link to={'/me'} className="custom-link nav-link"><FormattedMessage id="menu.home" /></Link>
                    <Link to={'/events'} className="custom-link nav-link"><FormattedMessage id="menu.news" /></Link>
                    <Link to={'/friends'} className="custom-link nav-link"><FormattedMessage id="menu.friends" />{requestNotification}</Link>
                    <Link to={'/dialogs'} className="custom-link nav-link"><FormattedMessage id="menu.messages" />{messageNotification}</Link>
                    <Link to={'/groups'} className="custom-link nav-link"><FormattedMessage id="menu.groups" /></Link>
                    <Link to={'/logout'} className="custom-link nav-link"><FormattedMessage id="menu.exit" /></Link>
                </div>
            </div>
        )
    }

}

export default MainMenu;
