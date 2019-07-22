import React, {Component} from 'react';
import MainMenu from '../templates/menu';
import {FormattedMessage} from 'react-intl';
import DialogService from '../../service/DialogService';
import {Link} from 'react-router-dom';
import UserService from '../../service/UserService';
import {Pagination} from '../templates/pagination';
import {apiImages} from '../../index';

interface DialogListState {
    dialogs: any[]
    countRecord: number
    currentPage: number
}
export default class Dialogs extends Component<any, DialogListState> {

    state: DialogListState = {
        dialogs: [],
        countRecord: 0,
        currentPage: 0
    };

    componentDidMount(): void {
        this.updateList(0);
    }

    updateList = (page:number) => {
        DialogService.find(page,(res:any) => {
            if (res.data.success === true) {
                this.setState({
                    dialogs : res.data.data.content,
                    countRecord: res.data.data.totalElements,
                    currentPage: page
                });
            }
        })
    };

    DialogImage = (value:any) => {
        let user = value.user;
        let dialog = value.dialog;
        return (
            <td className="blocks">
                <Link to={'/dialog/' + dialog.id}>
                    <img width="70" height="70" src={apiImages + user.imageId} />
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
        let pagination;
        if (this.state.dialogs.length > 0) {
            pagination = <Pagination currentPage={this.state.currentPage} countRecord={this.state.countRecord} handlePage={this.updateList} />
        }
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
                            {pagination}
                        </div>
                    </div>
                </div>
            </div>

        );
    }

}
