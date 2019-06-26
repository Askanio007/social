import React, {Component} from 'react';
import FriendService from '../../service/FriendService';
import Photo from '../templates/photo';
import '../../css/friends.css';
import '../../css/wall.css';
import {Link} from 'react-router-dom';
import {AcceptRequestBtn, DeclineRequestBtn} from '../templates/buttons';

interface FriendsRequestState {
    requests: any[]
}
class FriendsRequest extends Component<{}, FriendsRequestState> {

    state: FriendsRequestState = {
        requests: []
    };

    componentDidMount(): void {
        let rootUserId = localStorage.getItem("rootUserId");
        FriendService.findRequestsToUser(rootUserId).then((res:any) => {
            if (res.data.success === true) {
                this.setState({
                    requests: res.data.data
                })
            }
        })
    }

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
                    <AcceptRequestBtn />
                    <DeclineRequestBtn />
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
