import React, {Component} from 'react';
import {FormattedMessage} from 'react-intl';
import {Link} from 'react-router-dom';
import MainMenu from './templates/menu';
import Photo from './templates/photo';
import EventService from '../service/EventService';
import '../css/wall.css';
import {Pagination} from './templates/pagination';

interface EventsState {
    events: any[]
    countRecord: number
    currentPage: number
}

class Events extends Component<{}, EventsState> {

    state: EventsState = {
        events: [],
        countRecord: 0,
        currentPage: 0
    };

    componentDidMount() {
        this.updateList(0);
    }

    updateList = (page:number) => {
        EventService.find(page, (res:any) => {
            if (res.data.success === true) {
                this.setState({
                    events: res.data.data.content,
                    countRecord: res.data.data.totalElements,
                    currentPage: page
                })
            }
        })
    };

    Event = (value:any) => {
        let event = value.event;
        return (
            <tr className="event">
                <td className="vertical-top">
                    <Photo link={'/user/' + event.user.id} photoId={event.user.imageId} stylePhoto="wall-photo-block"/>
                </td>
                <td className="vertical-top">
                    <div><p>{event.dateView}</p></div>
                    <div className="event-message">
                        <p>{event.user.fullName} <FormattedMessage id={event.description} /> <Link to={'/user/' + event.targetActionId} className="custom-link">{event.targetActionName}</Link></p>
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
        let pagination;
        if (this.state.events.length > 0) {
            pagination = <Pagination currentPage={this.state.currentPage} countRecord={this.state.countRecord} handlePage={this.updateList} />
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
                        {pagination}
                    </div>
                </div>

            </div>
        )
    }
}

export default Events;
