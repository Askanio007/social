import React, {Component} from 'react';
import FriendService from '../../service/FriendService';
import Photo from '../templates/photo';
import '../../css/friends.css';
import '../../css/wall.css';
import {Link, withRouter} from 'react-router-dom';
import {RemoveFriendBtn, SendMessageBtn} from '../templates/buttons';
import UserService from '../../service/UserService';

interface FriendsListState {
    friends: any[]
}
class FriendsList extends Component<any, FriendsListState> {

    state: FriendsListState = {
        friends: []
    };

    componentDidMount(): void {
        FriendService.findToUser(UserService.getRootUserId(),(res:any) => {
            if (res.data.success === true) {
                this.setState({
                    friends: res.data.data
                })
            }
        })
    }

    updateState = (promise:Promise<any>) => {
        promise.then((res:any) => {
            if (res.data.success === true) {
                this.componentDidMount();
            }
        })
    };

    ListFriends = (value:any) => {

        const friend = value.friend;
        return (
            <tr>
                <td className="vertical-top">
                    <Photo link={"/user/" + friend.id} photoHashCode={friend.details.miniImage64code} stylePhoto="photo-friend"/>
                </td>
                <td className="vertical-top">
                    <div className="wall-record-name">
                        <h4><Link to={'/user/' + friend.id} className="custom-link">{friend.fullName}</Link></h4>
                    </div>
                    <SendMessageBtn friendId={friend.id} history={this.props.history} />
                    <RemoveFriendBtn id={friend.id} callback={this.updateState} />
                </td>
            </tr>
        );
    };

    render() {
        const friends = this.state.friends.map((friend:any) =>
            <this.ListFriends key={friend.id} friend={friend} />
        );
        return (
            <table className="widthMax">
               <tbody>{friends}</tbody>
            </table>
        );
    }
}

export default withRouter(FriendsList);
