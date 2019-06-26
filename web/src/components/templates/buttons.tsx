import React, {Component} from 'react';
import {FormattedMessage} from 'react-intl';
import GroupService from '../../service/GroupService';
import FriendService, {FriendshipRequest} from '../../service/FriendService';

interface AddFriendProps {
    request:FriendshipRequest
    callback:(userRelation:string) => void
}
class AddFriendBtn extends Component<AddFriendProps, any> {

    add = () => {
        FriendService.sendFriendRequest(this.props.request).then((res:any) => {
            if (res.data.success === true) {
                this.props.callback("REQUEST_FRIEND");
            }
        })
    };

    render() {
        return (<button type="button" className="btn btn-secondary btn-custom btn-margin" onClick={this.add}><FormattedMessage id='user.friends.add' /></button>);
    }
}

interface RemoveFriendProps {
    userId:number
    callback:(userRelation:string) => void
}
class RemoveFriendBtn extends Component<RemoveFriendProps, any> {

    remove = () => {
        FriendService.remove(this.props.userId).then((res:any) => {
            if (res.data.success === true) {
                this.props.callback("NOT_FRIEND");
            }
        });
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

interface EnterGroupProps {
    groupId:number
    callback:(promise:Promise<any>) => void
}
class ExitGroupBtn extends Component<EnterGroupProps, any> {

    exit = () => {
        this.props.callback(GroupService.exit(this.props.groupId));
    };

    render() {
        return (<button type="button" className="btn btn-secondary btn-custom" onClick={this.exit}><FormattedMessage id="groups.exit" /></button>)
    }
}

interface EnterGroupProps {
    groupId:number
    callback:(promise:Promise<any>) => void
}
class EnterGroupBtn extends Component<EnterGroupProps, any> {

    join = () => {
        this.props.callback(GroupService.join(this.props.groupId));
    };

    render() {
        return (<button type="button" className="btn btn-secondary btn-custom" onClick={this.join}><FormattedMessage id="groups.enter" /></button>)
    }
}



export {AddFriendBtn, RemoveFriendBtn, SendMessageBtn, RequestFriendBtn, EditBtn, DeclineRequestBtn, ExitGroupBtn, EnterGroupBtn, AcceptRequestBtn};
