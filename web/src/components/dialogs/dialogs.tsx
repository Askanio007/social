import React, {Component} from 'react';
import MainMenu from '../templates/menu';
import {FormattedMessage} from 'react-intl';
import DialogService from '../../service/DialogService';
import {Link} from 'react-router-dom';
import UserService from '../../service/UserService';

interface DialogListState {
    dialogs: any[]
}
export default class Dialogs extends Component<any, DialogListState> {

    state: DialogListState = {
        dialogs: []
    };

    componentDidMount(): void {
        DialogService.find().then((res:any) => {
            if (res.data.success === true) {
                this.setState({dialogs : res.data.data});
            }
        })
    }

    DialogImage = (value:any) => {
        let user = value.user;
        let dialog = value.dialog;
        return (
            <td className="blocks">
                <Link to={'/dialog/' + dialog.id}>
                    <img width="70" height="70" src={'data:image/JPEG;base64,' + user.image64code} />
                </Link>
            </td>
        );
    };

    DialogLastMessage = (value:any) => {
        let user = value.user;
        let dialog = value.dialog;
        return (
            <td className="widthMax vertical-top">
                <div className="wall-record-name">
                    <Link to={'/dialog/' + dialog.id}>
                        <h5>{user.fullName}</h5>
                    </Link>
                </div>
                <div>{dialog.lastMessage}</div>
            </td>
        );
    };

    ListDialogs = (value:any) => {
        let dialogImage = value.users.map((user:any) => {
            if (user.id !== UserService.getRootUserId()) {
                return (<this.DialogImage key={user.id} user={user} dialog={value.dialog} />);
            } else {
                return null;
            }
        });
        let dialogLastMessage = value.users.map((user:any) => {
            if (user.id !== UserService.getRootUserId()) {
                return (<this.DialogLastMessage key={user.id} user={user} dialog={value.dialog} />);
            } else {
                return null;
            }
        });

        return (
            <tr className="blocks">
                {dialogImage}
                {dialogLastMessage}
            </tr>
        );
    };

    render() {
        return (
            <div className="container">
                <div className="row">
                    <MainMenu />
                    <div className="col-sm-6">
                        <ul className="nav nav-tabs menu-margin">
                            <li className="nav-item">
                                <Link to={'/dialogs'} className="nav-link active"><FormattedMessage id='dialogs.title' /></Link>
                            </li>
                            <li className="nav-item">
                                <a className="nav-link"><FormattedMessage id='dialogs.message' /></a>
                            </li>
                        </ul>
                        <div>
                            <table className="widthMax">
                                <tbody>
                                    {this.state.dialogs.map((dialog:any) => <this.ListDialogs  key={dialog.id} users={dialog.users} dialog={dialog} />)}
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>

        );
    }

}
