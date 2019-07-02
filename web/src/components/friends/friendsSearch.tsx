import React, {Component} from 'react';
import {FormattedMessage} from 'react-intl';
import Photo from '../templates/photo';
import {Link} from 'react-router-dom';
import {AddFriendBtn, SendMessageBtn} from '../templates/buttons';
import FriendService, {FriendshipRequest} from '../../service/FriendService';
import UserService from '../../service/UserService';

interface FriendsSearchState {
    friends: any[]
    searchText: string

}
class FriendsSearch extends Component<any, FriendsSearchState> {

    state: FriendsSearchState = {
        friends:[],
        searchText: ""
    };

    updateRelation = (promise:Promise<any>) => {
        promise.then((res:any) => {
            if (res.data.success === true) {
                this.forceUpdate();
            }
        })
    };

    FoundedUser = (value:any) => {
        const user = value.user;
        const request:FriendshipRequest = {
            fromUserId: UserService.getRootUserId(),
            toUserId: user.id
        };
        return (
            <tr>
                <td className="vertical-top">
                    <Photo link={'/user/' + user.id} photoHashCode={user.details.image64code} stylePhoto="photo-friend"/>
                </td>
                <td className="vertical-top">
                    <div className="wall-record-name">
                        <h4><Link to={'/user/' + user.id} className="custom-link">{user.fullName}</Link></h4>
                    </div>
                    <SendMessageBtn />
                    <AddFriendBtn request={request} callback={this.updateRelation}/>
                </td>
            </tr>
        );
    };

    handleSearchText = (event: React.ChangeEvent<HTMLInputElement>) => {
        let state = this.state;
        state.searchText = event.currentTarget.value;
        this.setState(state);
    };

    search = () => {
        FriendService.searchUser(this.state.searchText,(res:any) => {
            if (res.data.success === true) {
                let state = this.state;
                state.friends = res.data.data;
                this.setState(state);
            }
        })

    };

    render() {
        let foundUser = this.state.friends.map((user:any) =>
            <this.FoundedUser key={user.id} user={user} />
        );
        return (
            <div>
                <div className="input-group mb-3">
                    <div className="input-group-prepend">
                        <span className="input-group-text" id="basic-addon1"><i className="fas fa-search"></i></span>
                    </div>
                    <input type="text" name="searchText" value={this.state.searchText} className="form-control" onChange={this.handleSearchText} />
                    <div className="input-group-append">
                        <button type="button" className="btn btn-secondary btn-custom-style" onClick={this.search}><FormattedMessage id="common.search" /></button>
                    </div>
                </div>
                <table className="widthMax">
                    <tbody>
                        {foundUser}
                    </tbody>
                </table>
            </div>
        );
    }
}

export default FriendsSearch;
