import React, {Component} from 'react';
import {FormattedMessage} from 'react-intl';
import MainMenu from './templates/menu';
import Wall from './templates/wall';
import FriendsBlock from './templates/friendsBlock';
import GroupBlock from './templates/groupBlock';
import UserService from '../service/UserService';
import {AddFriendBtn, RemoveFriendBtn, RequestFriendBtn, SendMessageBtn} from './templates/buttons';
import '../css/user.css';
import {FriendshipRequest} from '../service/FriendService';
import {withRouter} from 'react-router';
import {RecipientType} from '../service/WallService';

export enum UserRelation {
    ME = "ME",
    FRIEND = "FRIEND",
    REQUEST_FRIEND = "REQUEST_FRIEND",
    NOT_FRIEND = "NOT_FRIEND"
}
interface UserState {
    loading?: boolean,
    user?: any
    friendCount: number,
    groupCount: number,
    relation: UserRelation
}

class User extends Component<any, UserState> {

    state: UserState = {
        loading: true,
        user: null,
        friendCount: 0,
        groupCount: 0,
        relation: UserRelation.ME
    };

    componentDidMount() {
        let user:any;
        let friendCount:number;
        let groupCount:number;
        let relation:UserRelation;
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
            return UserService.getRelation(rootUserId)
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

    updateRelation = (promise:Promise<any>, relation?:UserRelation) => {
        promise.then((res:any) => {
            if (res.data.success === true) {
                if (relation) {
                    let state = this.state;
                    state.relation = relation;
                    this.setState(state);
                }
            }
        })
    };

    redirectToEditProfile = () => {
        this.props.history.push('/profile')
    };

    MyPageButton = () => {
        return (<div><button type="button" className="btn btn-secondary btn-custom btn-margin" onClick={this.redirectToEditProfile} ><FormattedMessage id='common.edit' /></button></div>);
    };

    FriendPageButton = () => {
        return (
            <div>
                <SendMessageBtn />
                <RemoveFriendBtn id={this.state.user.id} callback={this.updateRelation} />
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
                <AddFriendBtn request={request} callback={this.updateRelation} />
            </div>
        )
    };

    ButtonSet = () => {
        switch (this.state.relation) {
            case UserRelation.ME: return (<this.MyPageButton />);
            case UserRelation.FRIEND: return (<this.FriendPageButton />);
            case UserRelation.NOT_FRIEND: return (<this.NotFriendPageButton />);
            case UserRelation.REQUEST_FRIEND: return (<this.RequestFriendPageButton />)
        }
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
                        <this.ButtonSet />
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
                                <tr>
                                    <td><FormattedMessage id='profile.country' />:</td>
                                    <td>{this.state.user.details.country}</td>
                                </tr>
                                <tr>
                                    <td><FormattedMessage id='profile.city' />:</td>
                                    <td>{this.state.user.details.city}</td>
                                </tr>
                                <tr>
                                    <td><FormattedMessage id='profile.phone' />:</td>
                                    <td>{this.state.user.details.phone}</td>
                                </tr>
                                <tr>
                                    <td><FormattedMessage id='profile.about' />:</td>
                                    <td>{this.state.user.details.about}</td>
                                </tr>
                            </tbody>
                        </table>

                        <Wall receiptId={this.state.user.id} recipientType={RecipientType.USER} />
                    </div>
                </div>
            </div>
        )
    }
}

export default withRouter(User);
