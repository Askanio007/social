import React, {Component} from 'react';
import FriendService from '../../service/FriendService';
import Photo from '../templates/photo';
import '../../css/friends.css';
import '../../css/wall.css';
import {Link} from 'react-router-dom';
import {RemoveFriendBtn, SendMessageBtn} from '../templates/buttons';

interface FriendsListState {
    friends: any[]
}
class FriendsList extends Component<{}, FriendsListState> {

    state: FriendsListState = {
        friends: []
    };

    componentDidMount(): void {
        let rootUserId = localStorage.getItem("rootUserId");
        FriendService.findToUser(rootUserId).then((res:any) => {
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
                    <Photo link={"/user/" + friend.id} photoHashCode={friend.details.image64code} stylePhoto="photo-friend"/>
                </td>
                <td className="vertical-top">
                    <div className="wall-record-name">
                        <h4><Link to={'/user/' + friend.id} className="custom-link">{friend.fullName}</Link></h4>
                    </div>
                    <SendMessageBtn />
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

export default FriendsList;
