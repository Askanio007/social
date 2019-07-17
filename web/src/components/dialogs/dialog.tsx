import React, {Component} from 'react';
import MainMenu from '../templates/menu';
import {Link, withRouter} from 'react-router-dom';
import {FormattedMessage} from 'react-intl';
import DialogService from '../../service/DialogService';
import Photo from '../templates/photo';
import '../../css/dialog.css';
import {Stomp} from '@stomp/stompjs';
import UserService from '../../service/UserService';

interface DialogState {
    messages: any[],
    message: string,
    dialogId: number

}
class Dialog extends Component<any, DialogState> {

    state: DialogState = {
        messages: [],
        message: "",
        dialogId: 0
    };

    chat:any;
    stompClient:any;

    componentDidMount(): void {
        let socket = new WebSocket('ws://localhost:8080/dialog/websocket');
        this.stompClient = Stomp.over(socket);
        this.stompClient.connect({}, (frame:any) => {
            this.stompClient.subscribe('/dialog/message',  (response:any) => {
                let res = JSON.parse(response.body);
                if (res.success === true) {
                    let state = this.state;
                    state.messages.push(res.data);
                    state.message = "";
                    this.setState(state);
                    this.scrollToBottom();
                }
                console.log("Hello " + JSON.parse(response.body).data.message);
            });
        });
        let dialogId = this.props.match.params.dialogId;
        DialogService.findMessages(dialogId, (res:any) => {
            if (res.data.success === true) {
                this.setState({
                    messages: res.data.data,
                    message: "",
                    dialogId: dialogId
                });
                this.scrollToBottom();
            }
        });
    }

    ListMessages = (value:any) => {
        let message = value.message;
        return (
            <tr>
                <td className="vertical-top">
                    <Photo link={"/user/" + message.senderId} photoHashCode={message.image64encode} stylePhoto="mini-image-circle"/>
                </td>
                <td className="vertical-top widthMax">
                    <div className="wall-record-name">
                        <div>{message.senderName}</div>
                        <div>{message.createDateView}</div>
                    </div>
                    <div>{message.message}</div>
                </td>
            </tr>
        );
    };

    handleMessage = (event: React.ChangeEvent<HTMLTextAreaElement>) => {
        let state = this.state;
        state.message = event.currentTarget.value;
        this.setState(state);
    };

    sendMessageSock = () => {
        let params = {
            senderId: UserService.getRootUserId(),
            message: this.state.message,
            dialogId: this.state.dialogId
        };
        this.stompClient.send("/app/send", {}, JSON.stringify(params));
    };

    scrollToBottom() {
        this.chat.scrollTop = this.chat.scrollHeight;
    }


    render() {
        return(
            <div className="container">
                <div className="row">
                    <MainMenu />
                    <div className="col-sm-6">
                        <ul className="nav nav-tabs menu-margin">
                            <li className="nav-item">
                                <Link to={'/dialogs'} className="nav-link"><FormattedMessage id='dialogs.title' /></Link>
                            </li>
                            <li className="nav-item">
                                <a className="nav-link active"><FormattedMessage id='dialogs.message' /></a>
                            </li>
                        </ul>
                        <div>
                            <div className="chat-scroll" ref={(el) => { this.chat = el; }}>
                                <table className="widthMax">
                                    <tbody>
                                    {this.state.messages.map((message:any) => <this.ListMessages  key={message.id} message={message} />)}
                                    </tbody>
                                </table>
                            </div>
                            <div className="form-group">
                                <textarea name="textMessage" value={this.state.message} className="form-control rounded-0" id="formControlTextarea" rows={3} onChange={this.handleMessage} />
                            </div>
                            <button onClick={this.sendMessageSock} type="button" className="btn btn-secondary btn-custom"><FormattedMessage id='wall.message.send' /></button>
                        </div>
                    </div>
                </div>
            </div>
        );
    }
}

export default withRouter(Dialog);
