import React, {Component} from 'react';
import {FormattedMessage} from 'react-intl';
import MainMenu from '../templates/menu';
import Wall from '../templates/wall';
import FriendsBlock from '../templates/friendsBlock';
import GroupService from '../../service/GroupService';
import {EnterGroupBtn, ExitGroupBtn} from '../templates/buttons';

interface GroupState {
    group:any
    participantCount: number
    isLoading: boolean
}
export default class Group extends Component<any, GroupState> {

    state: GroupState = {
        group: null,
        participantCount: 0,
        isLoading: true
    };

    componentDidMount(): void {
        let group:any;
        let participantCount = 0;
        let groupId:number = this.props.match.params.groupId;
        GroupService.find(groupId).then((res:any) => {
            if (res.data.success === true) {
                group = res.data.data;
            }
            return GroupService.countParticipant(groupId)
        }).then((res:any) => {
            if (res.data.success === true) {
                participantCount = res.data.data;
            }
        }).then(() => {
            this.setState({
                group: group,
                participantCount: participantCount,
                isLoading: false
            })
        })
    }

    updateState = (promise:Promise<any>) => {
        promise.then((res:any) => {
            if (res.data.success) {
                this.componentDidMount();
            }
        });
    };

    render() {
        if (this.state.isLoading) {
            return ("");
        }
        return (
            <div className="container">
                <div className="row">
                    <MainMenu />

                    <div className="col-sm-6">
                        <h3>{this.state.group.name}</h3>
                        <p>{this.state.group.description}</p>
                        <Wall receiptId={this.state.group.id} isUser={false}/>
                    </div>
                    <div className="col-sm-3">
                        <div className="blocks">
                            <img className="userPhoto" src={"data:image/JPEG;base64," + this.state.group.avatar64code} />
                        </div>
                        <button ng-show="isAdmin()" ui-sref="groupEdit({groupId: group.id})" type="button"
                                className="btn btn-secondary btn-custom"><FormattedMessage id={"common.edit"} /></button>
                        <EnterGroupBtn id={this.state.group.id} callback={this.updateState} />
                        <ExitGroupBtn id={this.state.group.id} callback={this.updateState} />
                        <FriendsBlock friends={this.state.group.participant} count={this.state.participantCount}/>
                    </div>
                </div>
            </div>

    );
    }

}
