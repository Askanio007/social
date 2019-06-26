import React, {Component} from 'react';
import {FormattedMessage} from 'react-intl';
import {Link} from 'react-router-dom';
import MainMenu from './templates/menu';
import Photo from './templates/photo';
import EventService from '../service/EventService';
import '../css/wall.css';

interface EventsState {
    events: any[]
}

class Events extends Component<{}, EventsState> {

    state: EventsState = {
        events: []
    };

    componentDidMount() {
        let rootUserId = localStorage.getItem("rootUserId");
        EventService.find(rootUserId).then((res:any) => {
            if (res.data.success === true) {
                this.setState({
                    events: res.data.data
                })
            }
        })
    }

    Event = (value:any) => {
        let event = value.event;
        return (
            <tr className="event">
                <td className="vertical-top">
                    <Photo link={'/user/' + event.userId} photoHashCode={event.userAvatar64code} stylePhoto="wall-photo-block"/>
                </td>
                <td className="vertical-top">
                    <div><p>{event.dateView}</p></div>
                    <div className="event-message">
                        <p>{event.userName} <FormattedMessage id={event.description} /> <Link to={'/user/' + event.targetActionId} className="custom-link">{event.targetActionName}</Link></p>
                    </div>
                </td>
            </tr>
        )
    };


    render() {
        let events;
        if (this.state.events) {
            events = this.state.events.map((event:any) =>
                <this.Event key={event.id} event={event}/>
            );
        }
        return (
            <div className="container">
                <div className="row">
                    <MainMenu />
                    <div className="col-sm-6">
                        <ul className="nav nav-tabs">
                            <li className="nav-item">
                                <Link className="nav-link active" to={'/events'}><FormattedMessage id="event.title" /></Link>
                            </li>
                        </ul>
                        <br />
                        <table className="widthMax">
                            <tbody>
                                {events}
                            </tbody>

                        </table>
                    </div>
                </div>

            </div>
        )
    }
}

export default Events;
