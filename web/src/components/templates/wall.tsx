import React, {Component} from 'react';
import {FormattedMessage} from 'react-intl';
import '../../css/wall.css';
import WallService, {PublicMessage} from '../../service/WallService';
import Photo from './photo';
import {Link} from 'react-router-dom';

interface WallInterface {
    wall?: any
    publicMessage: PublicMessage
}

interface WallProps {
    receiptId: number
    isUser: boolean
}

class Wall extends Component<WallProps, WallInterface> {

    constructor(props: WallProps, context: WallInterface) {
        super(props, context);
        this.props = props;
        this.state = context;
    }

    props: WallProps = {
        receiptId: 0,
        isUser: false
    };

    componentDidMount() {
        let publicMessage = {
            message: "",
            recipientId: this.props.receiptId,
            recipientUser: this.props.isUser,
            senderId: 20
        };

        WallService.find(this.props.receiptId, this.props.isUser).then((res: any) => {
            let responseData = res.data;
            if (responseData.success) {
                this.setState({
                    wall: responseData.data,
                    publicMessage: publicMessage
                });
            }
        })
    }

    sendMessage = () => {
        if (this.state.publicMessage.message !== "") {
            WallService.sendMessage(this.state.publicMessage).then((res:any) => {
                let state = this.state;
                state.wall.unshift(res.data.data);
                state.publicMessage.message = "";
                this.setState(state);
            });
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
                    <Photo stylePhoto="wall-photo-block" link={"/user" + record.senderId} photoHashCode={record.avatarSender} />
                </td>
                <td className="wall-user-info">
                    <div className="wall-record-name">
                        <div><Link to={'/user/' + record.senderId} className="custom-link">{record.sender}</Link></div>
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
        if (this.state.wall) {
            records = this.state.wall.map((record:any) =>
                <this.WallRecord key={record.id} record={record}/>
            );
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
            </div>

        )
    }
}

export default Wall;
