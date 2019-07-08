import React, {Component} from 'react';
import FriendService from '../../service/FriendService';
import Photo from '../templates/photo';
import '../../css/friends.css';
import '../../css/wall.css';
import {Link} from 'react-router-dom';
import {AcceptRequestBtn, DeclineRequestBtn} from '../templates/buttons';
import UserService from '../../service/UserService';

interface FriendsRequestState {
    requests: any[]
}
class FriendsRequest extends Component<any, FriendsRequestState> {

    state: FriendsRequestState = {
        requests: []
    };

    componentDidMount(): void {
        FriendService.findRequestsToUser(UserService.getRootUserId(), (res:any) => {
            if (res.data.success === true) {
                this.setState({
                    requests: res.data.data
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

    RequestsFriends = (value:any) => {

        const request = value.request;
        return (
            <tr>
                <td className="vertical-top">
                    <Photo link={"/user/" + request.fromUserId} photoHashCode={request.fromUserAvatar64code} stylePhoto="photo-friend"/>
                </td>
                <td className="vertical-top">
                    <div className="wall-record-name">
                        <h4>
                            <Link to={'/user/' + request.fromUserId} className="custom-link">{request.fromUserName}</Link>
                        </h4>
                    </div>
                    <AcceptRequestBtn id={request.id} callback={this.updateState} />
                    <DeclineRequestBtn id={request.id} callback={this.updateState} />
                </td>
            </tr>
        );
    };

    render() {
        const friends = this.state.requests.map((request:any) =>
            <this.RequestsFriends key={request.id} request={request} />
    );
        return (
            <table className="widthMax">
                <tbody>{friends}</tbody>
            </table>
        );
    }
}

export default FriendsRequest;
