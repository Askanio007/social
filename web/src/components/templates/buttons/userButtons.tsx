import React from 'react';
import {FormattedMessage} from 'react-intl';
import FriendService, {FriendshipRequest} from '../../../service/FriendService';
import {UserRelation} from '../../user';
import DialogService from '../../../service/DialogService';
import Button, {GeneralBtnProps} from './Button';


interface SendMessageProps {
    friendId:number
    history:any
}
function SendMessageBtn (props:SendMessageProps) {

    function sendMessage () {
        DialogService.findDialogIdBy(props.friendId, (res:any) => {
            if (res.data.success === true) {
                props.history.push('/dialog/' + res.data.data.id);
            }
        });
    }

    return (<button type="button" className="btn btn-secondary btn-custom" onClick={sendMessage} ><FormattedMessage id='user.message.send' /></button>);
}

interface AddFriendProps {
    request:FriendshipRequest
    callback:(promise:Promise<any>, userRelation?:UserRelation) => void
}
const AddFriendBtn = (props:AddFriendProps) => (
    <Button titleCode={'user.friends.add'}
            onClick={() => props.callback(FriendService.sendFriendRequest(props.request), UserRelation.REQUEST_FRIEND)}
    />
);

const RemoveFriendBtn = (props:GeneralBtnProps) => (
    <Button titleCode={'user.friends.remove'}
            onClick={() => props.callback(FriendService.remove(props.id), UserRelation.NOT_FRIEND)}
    />
);

const RequestFriendBtn = () => (
    <Button titleCode={'user.friends.request.send'}
            disabled={true}
    />
);

const AcceptRequestBtn = (props:GeneralBtnProps) => (
    <Button titleCode={'friends.accept'}
            onClick={() => props.callback(FriendService.acceptRequest(props.id), UserRelation.FRIEND)}
    />
)

const DeclineRequestBtn = (props:GeneralBtnProps) => (
    <Button titleCode={'friends.decline'}
            onClick={() => props.callback(FriendService.declineRequest(props.id), UserRelation.NOT_FRIEND)}
    />
);

export {AddFriendBtn, RemoveFriendBtn, SendMessageBtn, RequestFriendBtn, DeclineRequestBtn, AcceptRequestBtn};
