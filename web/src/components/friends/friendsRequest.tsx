import React, {Component} from 'react';
import FriendService from '../../service/FriendService';
import '../../css/friends.css';
import '../../css/wall.css';
import {AcceptRequestBtn, DeclineRequestBtn} from '../templates/buttons';
import UserService from '../../service/UserService';
import {Pagination} from '../templates/pagination';
import FriendBlockList from './components/friendListBlock';

interface FriendsRequestState {
    requests: any[]
    countRecord:number
    currentPage:number
}
class FriendsRequest extends Component<any, FriendsRequestState> {

    state: FriendsRequestState = {
        requests: [],
        countRecord: 0,
        currentPage: 0
    };

    componentDidMount(): void {
        this.updateList(0);
    }

    updateList = (page:number) => {
        FriendService.findRequestsToUser(UserService.getRootUserId(), page, (res:any) => {
            if (res.data.success === true) {
                this.setState({
                    requests: res.data.data.content,
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

    RequestsFriends = (value:any) => {
        const request = value.request;
        return (
            <FriendBlockList
                fullName={request.fromUserName}
                avatar={request.fromUserAvatar64code}
                id={request.fromUserId}
                buttons={(
                    <div>
                        <AcceptRequestBtn id={request.id} callback={this.updateState} />
                        <DeclineRequestBtn id={request.id} callback={this.updateState} />
                    </div>
                )}
            />
        );
    };

    render() {
        const friends = this.state.requests.map((request:any) =>
            <this.RequestsFriends key={request.id} request={request} />
            );
        let pagination;
        if (this.state.requests.length > 0) {
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

export default FriendsRequest;
