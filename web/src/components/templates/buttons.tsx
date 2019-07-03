import React, {Component} from 'react';
import {FormattedMessage} from 'react-intl';
import GroupService from '../../service/GroupService';
import FriendService, {FriendshipRequest} from '../../service/FriendService';
import {UserRelation} from '../user';

interface GeneralBtnProps {
    id:number
    callback:(promise:Promise<any>, userRelation?:UserRelation) => void
}

interface AddFriendProps {
    request:FriendshipRequest
    callback:(promise:Promise<any>, userRelation?:UserRelation) => void
}
class AddFriendBtn extends Component<AddFriendProps, any> {

    add = () => {
        this.props.callback(FriendService.sendFriendRequest(this.props.request), UserRelation.REQUEST_FRIEND);
    };

    render() {
        return (<button type="button" className="btn btn-secondary btn-custom btn-margin" onClick={this.add}><FormattedMessage id='user.friends.add' /></button>);
    }
}

class RemoveFriendBtn extends Component<GeneralBtnProps, any> {

    remove = () => {
        this.props.callback(FriendService.remove(this.props.id), UserRelation.NOT_FRIEND);
    };

    render() {
        return (<button type="button" className="btn btn-secondary btn-custom btn-margin" onClick={this.remove}><FormattedMessage id='user.friends.remove' /></button>);
    }
}

class SendMessageBtn extends Component<any, any> {
    render() {
        return (<button type="button" className="btn btn-secondary btn-custom" onClick={() => {this.props.history.push('/dialog')}} ><FormattedMessage id='user.message.send' /></button>);
    }
}

class RequestFriendBtn extends Component {
    render() {
        return (<button type="button" className="btn btn-secondary btn-custom btn-margin" disabled={true} ><FormattedMessage id='user.friends.request.send' /></button>);
    }
}

class AcceptRequestBtn extends Component<GeneralBtnProps, any> {

    accept = () => {
        this.props.callback(FriendService.acceptRequest(this.props.id), UserRelation.FRIEND);
    };

    render() {
        return (<button type="button" className="btn btn-secondary btn-custom btn-margin" onClick={this.accept}><FormattedMessage id='friends.accept' /></button>);
    }
}

class DeclineRequestBtn extends Component<GeneralBtnProps, any> {

    decline = () => {
        this.props.callback(FriendService.declineRequest(this.props.id), UserRelation.NOT_FRIEND);
    };

    render() {
        return (<button type="button" className="btn btn-secondary btn-custom btn-margin" onClick={this.decline}><FormattedMessage id='friends.decline' /></button>);
    }
}

class ExitGroupBtn extends Component<GeneralBtnProps, any> {

    exit = () => {
        this.props.callback(GroupService.exit(this.props.id));
    };

    render() {
        return (<button type="button" className="btn btn-secondary btn-custom" onClick={this.exit}><FormattedMessage id="groups.exit" /></button>)
    }
}

class EnterGroupBtn extends Component<GeneralBtnProps, any> {

    join = () => {
        this.props.callback(GroupService.join(this.props.id));
    };

    render() {
        return (<button type="button" className="btn btn-secondary btn-custom" onClick={this.join}><FormattedMessage id="groups.enter" /></button>)
    }
}



export {AddFriendBtn, RemoveFriendBtn, SendMessageBtn, RequestFriendBtn, DeclineRequestBtn, ExitGroupBtn, EnterGroupBtn, AcceptRequestBtn};
