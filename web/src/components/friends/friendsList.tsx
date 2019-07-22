import React, {Component} from 'react';
import FriendService from '../../service/FriendService';
import '../../css/friends.css';
import '../../css/wall.css';
import {withRouter} from 'react-router-dom';
import {RemoveFriendBtn, SendMessageBtn} from '../templates/buttons';
import UserService from '../../service/UserService';
import {Pagination} from '../templates/pagination';
import FriendBlockList from './components/friendListBlock';

interface FriendsListState {
    friends: any[]
    countRecord:number
    currentPage:number
}
class FriendsList extends Component<any, FriendsListState> {

    state: FriendsListState = {
        friends: [],
        countRecord: 0,
        currentPage: 0
    };

    componentDidMount(): void {
        this.updateList(0);
    }

    updateList = (page:number) => {
        FriendService.findToUser(UserService.getRootUserId(), page,(res:any) => {
            if (res.data.success === true) {
                this.setState({
                    friends: res.data.data.content,
                    countRecord: res.data.data.totalElements,
                    currentPage: page
                })
            }
        })
    };

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
            <FriendBlockList
                fullName={friend.fullName}
                avatarId={friend.details.miniImageId}
                id={friend.id}
                buttons={(
                    <div>
                        <SendMessageBtn friendId={friend.id} history={this.props.history} />
                        <RemoveFriendBtn id={friend.id} callback={this.updateState} />
                    </div>
                )}
            />
        );
    };

    render() {
        const friends = this.state.friends.map((friend:any) =>
            <this.ListFriends key={friend.id} friend={friend} />
        );
        let pagination;
        if (this.state.friends.length > 0) {
            pagination = <Pagination currentPage={this.state.currentPage} countRecord={this.state.countRecord} handlePage={this.updateList} />
        }
        return (
            <table className="widthMax">
               <tbody>{friends}</tbody>
                {pagination}
            </table>
        );
    }
}

export default withRouter(FriendsList);
