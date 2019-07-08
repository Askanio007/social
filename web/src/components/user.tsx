import React, {Component} from 'react';
import {FormattedMessage} from 'react-intl';
import MainMenu from './templates/menu';
import Wall from './templates/wall';
import UserBlock from './templates/userBlock';
import GroupBlock from './templates/groupBlock';
import UserService from '../service/UserService';
import {AddFriendBtn, RemoveFriendBtn, RequestFriendBtn, SendMessageBtn} from './templates/buttons';
import '../css/user.css';
import {FriendshipRequest} from '../service/FriendService';
import {withRouter} from 'react-router';
import {RecipientType} from '../service/WallService';
import DialogService from '../service/DialogService';

export enum UserRelation {
    ME = "ME",
    FRIEND = "FRIEND",
    REQUEST_FRIEND = "REQUEST_FRIEND",
    NOT_FRIEND = "NOT_FRIEND"
}
interface UserState {
    loading?: boolean,
    userId: number,
    user?: any
    friendCount: number,
    groupCount: number,
    relation: UserRelation
}

class User extends Component<any, UserState> {

    //userId:number = this.props.match.params.userId ? this.props.match.params.userId : this.rootUserId;
    user:any;
    friendCount:number = 0;
    groupCount:number = 0;
    relation:UserRelation = UserRelation.ME;

    state: UserState = {
        loading: true,
        userId: this.props.match.params.userId ? this.props.match.params.userId : UserService.getRootUserId(),
        user: null,
        friendCount: 0,
        groupCount: 0,
        relation: UserRelation.ME
    };

    setUser = (res:any) => {
        if (res.data.success === true) {
            this.user = res.data.data;
        }
        return UserService.countFriends(this.state.userId, this.setFriendsCount)
    };

    setFriendsCount = (res:any) => {
        if (res.data.success === true) {
            this.friendCount = res.data.data;
        }
        return UserService.countGroups(this.state.userId, this.setGroupCount)
    };

    setGroupCount = (res:any) => {
        if (res.data.success === true) {
            this.groupCount = res.data.data;
        }
        return UserService.getRelation(this.state.userId, this.setRelation)
    };

    setRelation = (res:any) => {
        if (res.data.success === true) {
            this.relation = res.data.data;
        }
    };

    componentWillReceiveProps(nextProps: Readonly<any>, nextContext: any): void {
        if (nextProps.match.params.userId !== this.props.match.params.userId) {
            this.setState({
                userId: nextProps.match.params.userId ? nextProps.match.params.userId : UserService.getRootUserId(),
                loading: true
            });
            this.forceUpdate(() => this.componentDidMount());
        }
    }

    componentDidMount() {
        UserService.find(this.state.userId, this.setUser).then(() => {
            this.setState({
                userId: this.state.userId,
                user: this.user,
                groupCount: this.groupCount,
                friendCount: this.friendCount,
                loading: false,
                relation: this.relation
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

    sendMessage = (friendId:number) => {
        DialogService.findDialogIdBy(friendId, (res:any) => {
            if (res.data.success === true) {
                this.props.history.push('/dialog/' + res.data.data);
            }
        })
    };

    FriendPageButton = () => {
        return (
            <div>
                <SendMessageBtn friendId={this.state.userId} history={this.props.history} />
                <RemoveFriendBtn id={this.state.user.id} callback={this.updateRelation} />
            </div>
        )
    };

    RequestFriendPageButton = () => {
        return (
            <div>
                <SendMessageBtn friendId={this.state.userId} history={this.props.history} />
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
                <SendMessageBtn friendId={this.state.userId} history={this.props.history} />
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
                        <UserBlock friends={this.state.user.friends} count={this.state.friendCount} title="common.friends"/>
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
