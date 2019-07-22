import React, {Component} from 'react';
import {FormattedMessage} from 'react-intl';
import '../../css/wall.css';
import WallService, {PublicMessage, RecipientType} from '../../service/WallService';
import Photo from './photo';
import {Link} from 'react-router-dom';
import UserService from '../../service/UserService';
import {Pagination} from './pagination';

interface WallInterface {
    wall: any[]
    countRecord: number
    currentPage: number
    publicMessage: PublicMessage
}

interface WallProps {
    receiptId: number
    recipientType: RecipientType
}

class Wall extends Component<WallProps, WallInterface> {

    state: WallInterface = {
        wall: [],
        countRecord: 0,
        currentPage: 0,
        publicMessage: {
            message: "",
            recipientId: this.props.receiptId,
            recipientType: this.props.recipientType,
            senderId: UserService.getRootUserId()
        }
    };

    componentDidMount() {
        this.updateWall(0);
    }

    updateWall = (page:number) => {
        WallService.find(this.props.receiptId, this.props.recipientType, page, (res:any) => {
            let resp = res.data;
            if (resp.success === true) {
                this.setState({
                    wall: resp.data.content,
                    countRecord: resp.data.totalElements,
                    currentPage: page
                });
            }
        });
    };

    sendMessage = () => {
        console.log(this.state.publicMessage.message);
        if (this.state.publicMessage.message !== "") {
            WallService.sendMessage(this.state.publicMessage, this.handleResponseAfterMessage);
        }
    };

    handleResponseAfterMessage = (res:any) => {
        console.log(res);
        if (res.data.success === true) {
            let state = this.state;
            state.wall.unshift(res.data.data);
            state.publicMessage.message = "";
            this.setState(state);
        }
    };

    handleMessage = (event: React.ChangeEvent<HTMLTextAreaElement>) => {
        let state = this.state;
        state.publicMessage.message = event.currentTarget.value;
        this.setState(state);
    };

    WallRecord = (value:any) => {
        let record = value.record;
        return (
            <tr className="wall-record">
                <td>
                    <Photo stylePhoto="wall-photo-block" link={"/user/" + record.sender.id} photoId={record.sender.imageId} />
                </td>
                <td className="wall-user-info">
                    <div className="wall-record-name">
                        <div><Link to={'/user/' + record.sender.id} className="custom-link">{record.sender.fullName}</Link></div>
                        <div>{record.createDate}</div>
                    </div>
                    <div className="wall-record-message">{record.message}</div>
                </td>
            </tr>
        );
    };

    render() {
        let message = "";
        if (this.state.publicMessage) {
            message = this.state.publicMessage.message;
        }
        let records;
        let pagination;
        if (this.state.wall) {
            records = this.state.wall.map((record:any) =>
                <this.WallRecord key={record.id} record={record}/>
            );
            pagination = <Pagination currentPage={this.state.currentPage} countRecord={this.state.countRecord} handlePage={this.updateWall} />
        }
        return (
            <div>
                <h3><FormattedMessage id="common.wall" /></h3>
                <div className = "form-group">
                    <label htmlFor = "formControlTextarea"><FormattedMessage id="wall.message.enter"/></label>
                    <textarea name="message" value={message} className="form-control rounded-0" id="formControlTextarea" rows={3} onChange={this.handleMessage} />
                </div>
                <button onClick={this.sendMessage} type="button" className="btn btn-secondary btn-custom wall-btn"><FormattedMessage id="wall.message.send"/></button>
                <table className="widthMax">
                    <tbody>{records}</tbody>
                </table>
                {pagination}
            </div>

        )
    }
}

export default Wall;
