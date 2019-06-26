import React, {Component} from 'react';
import {FormattedMessage} from 'react-intl';
import GroupService from '../../service/GroupService';
import FriendService, {FriendshipRequest} from '../../service/FriendService';

class AddFriendBtn extends Component<{request:FriendshipRequest}> {

    add = () => {
        FriendService.sendFriendRequest(this.props.request)
    };

    render() {
        return (<button type="button" className="btn btn-secondary btn-custom btn-margin" onClick={this.add}><FormattedMessage id='user.friends.add' /></button>);
    }
}

class RemoveFriendBtn extends Component<{userId:number}, any> {

    remove = () => {
        FriendService.remove(this.props.userId);
    };

    render() {
        return (<button type="button" className="btn btn-secondary btn-custom btn-margin" onClick={this.remove}><FormattedMessage id='user.friends.remove' /></button>);
    }
}

class SendMessageBtn extends Component {
    render() {
        return (<button type="button" className="btn btn-secondary btn-custom"><FormattedMessage id='user.message.send' /></button>);
    }
}

class RequestFriendBtn extends Component {
    render() {
        return (<button type="button" className="btn btn-secondary btn-custom btn-margin" disabled={true} ><FormattedMessage id='user.friends.request.send' /></button>);
    }
}

class EditBtn extends Component {
    render() {
        return (<button type="button" className="btn btn-secondary btn-custom btn-margin"><FormattedMessage id='common.edit' /></button>);
    }
}

class AcceptRequestBtn extends Component {
    render() {
        return (<button type="button" className="btn btn-secondary btn-custom btn-margin"><FormattedMessage id='friends.accept' /></button>);
    }
}

class DeclineRequestBtn extends Component {
    render() {
        return (<button type="button" className="btn btn-secondary btn-custom btn-margin"><FormattedMessage id='friends.decline' /></button>);
    }
}

class ExitGroupBtn extends Component<{groupId:number}, any> {

    exit = () => {
        GroupService.exit(this.props.groupId);
    };

    render() {
        return (<button type="button" className="btn btn-secondary btn-custom" onClick={this.exit}><FormattedMessage id="groups.exit" /></button>)
    }
}

class EnterGroupBtn extends Component<{groupId:number}, any> {

    join = () => {
        GroupService.join(this.props.groupId);
    };

    render() {
        return (<button type="button" className="btn btn-secondary btn-custom" onClick={this.join}><FormattedMessage id="groups.enter" /></button>)
    }
}



export {AddFriendBtn, RemoveFriendBtn, SendMessageBtn, RequestFriendBtn, EditBtn, DeclineRequestBtn, ExitGroupBtn, EnterGroupBtn, AcceptRequestBtn};
