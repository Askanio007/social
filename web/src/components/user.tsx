import React, {Component} from 'react';
import {FormattedMessage} from 'react-intl';
import MainMenu from './templates/menu';
import Wall from './templates/wall';
import FriendsBlock from './templates/friendsBlock';
import GroupBlock from './templates/groupBlock';
import UserService from '../service/UserService';
import {AddFriendBtn, EditBtn, RemoveFriendBtn, RequestFriendBtn, SendMessageBtn} from './templates/buttons';
import '../css/user.css';
import {FriendshipRequest} from '../service/FriendService';

interface UserState {
    loading?: boolean,
    user?: any
    friendCount: number,
    groupCount: number,
    relation: string
}

class User extends Component<any, UserState> {

    state: UserState = {
        loading: true,
        user: null,
        friendCount: 0,
        groupCount: 0,
        relation: ""
    };

    componentDidMount() {
        let user:any;
        let friendCount:number;
        let groupCount:number;
        let relation:string;
        const rootUserId = UserService.getRootUserId();
        let userId:number = this.props.match.params.userId ? this.props.match.params.userId : rootUserId;
        UserService.find(userId).then((res:any) => {
            if (res.data.success === true) {
                user = res.data.data;
            }
            return UserService.countFriends(userId)
        }).then((res:any) => {
            if (res.data.success === true) {
                friendCount = res.data.data;
            }
            return UserService.countGroups(userId)
        }).then((res:any) => {
            if (res.data.success === true) {
                groupCount = res.data.data;
            }
            return UserService.getRelation(rootUserId, userId)
        }).then((res:any) => {
            if (res.data.success === true) {
                relation = res.data.data;
            }
        }).then(() => {
            this.setState({
                user: user,
                groupCount: groupCount,
                friendCount: friendCount,
                loading: false,
                relation: relation
            })
        })
    }

    MyPageButton = () => {
        return (
            <div>
                <EditBtn />
            </div>
        )
    };

    FriendPageButton = () => {
        return (
            <div>
                <SendMessageBtn />
                <RemoveFriendBtn userId={this.state.user.id} />
            </div>
        )
    };

    RequestFriendPageButton = () => {
        return (
            <div>
                <SendMessageBtn />
                <RequestFriendBtn />
            </div>
        )
    };

    NotFriendPageButton = () => {
        let request:FriendshipRequest = {
            fromUserId: UserService.getRootUserId(),
            toUserId: this.state.user.id
        };
        return (
            <div>
                <SendMessageBtn />
                <AddFriendBtn request={request} />
            </div>
        )
    };

    render() {
        if (this.state.loading === true) {
            return ("")
        }
        return (
            <div className="container">
                <div className="row">
                    <MainMenu />
                    <div className="col-sm-3 middle-block">
                        <div className="blocks">
                            <img className="userPhoto" src={"data:image/JPEG;base64," + this.state.user.details.image64code} />
                        </div>
                        {this.state.relation === "ME" ? <this.MyPageButton /> : null}
                        {this.state.relation === "FRIEND" ? <this.FriendPageButton /> : null}
                        {this.state.relation === "REQUEST_FRIEND" ? <this.RequestFriendPageButton /> : null}
                        {this.state.relation === "NOT_FRIEND" ? <this.NotFriendPageButton /> : null}
                        <FriendsBlock friends={this.state.user.friends} count={this.state.friendCount}/>
                        <GroupBlock groups={this.state.user.groups} count={this.state.groupCount} />
                    </div>

                    <div className="col-sm-6">
                        <h3>{this.state.user.fullName}</h3>
                        <table className="widthMax">
                            <tbody>
                                <tr>
                                    <td className="width40"><FormattedMessage id='common.city' />:</td>
                                    <td className="width60">{this.state.user.details.city}</td>
                                </tr>
                                <tr>
                                    <td><FormattedMessage id='common.sex' />:</td>
                                    <td><FormattedMessage id={'common.' + this.state.user.details.sex} /></td>
                                </tr>
                                <tr>
                                    <td><FormattedMessage id='common.birthday' />:</td>
                                    <td>{this.state.user.details.birthdayView}</td>
                                </tr>
                            </tbody>
                        </table>

                        <Wall receiptId={this.state.user.id} isUser={true} />
                    </div>
                </div>
            </div>
        )
    }
}

export default User;
