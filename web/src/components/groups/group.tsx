import React, {Component} from 'react';
import {FormattedMessage} from 'react-intl';
import MainMenu from '../templates/menu';
import Wall from '../templates/wall';
import UserBlock from '../templates/userBlock';
import GroupService, {GroupRelation} from '../../service/GroupService';
import {RecipientType} from '../../service/WallService';
import {withRouter} from 'react-router';
import {apiImages} from '../../index';
import {EnterGroupBtn, ExitGroupBtn} from '../templates/buttons/groupButtons';

interface GroupState {
    group:any
    participantCount: number
    relation:GroupRelation
    isLoading: boolean
}
class Group extends Component<any, GroupState> {

    state: GroupState = {
        group: null,
        participantCount: 0,
        isLoading: true,
        relation:GroupRelation.NOT_PARTICIPANT
    };

    componentDidMount(): void {
        let group:any;
        let participantCount = 0;
        let relation:GroupRelation;
        let groupId:number = this.props.match.params.groupId;
        GroupService.find(groupId, (res:any) => {
            if (res.data.success === true) {
                group = res.data.data;
            }
        }).then(() => {
            return GroupService.countParticipant(groupId,(res:any) => {
                if (res.data.success === true) {
                    participantCount = res.data.data;
                }
            })
        }).then(() => {
            return GroupService.groupRelation(groupId,(res:any) => {
                if (res.data.success === true) {
                    relation = res.data.data;
                }
            })
        }).then(() => {
            this.setState({
                group: group,
                participantCount: participantCount,
                isLoading: false,
                relation: relation
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

    Buttons = () => {
        switch (this.state.relation) {
            case GroupRelation.NOT_PARTICIPANT: return (<EnterGroupBtn id={this.state.group.id} callback={this.updateState} />);
            case GroupRelation.PARTICIPANT: return (<ExitGroupBtn id={this.state.group.id} callback={this.updateState} />);
            case GroupRelation.ADMIN: return (<button type="button" onClick={() => this.props.history.push('/editGroup/' + this.state.group.id)}
                                                      className="btn btn-secondary btn-custom"><FormattedMessage id={"common.edit"} /></button>);
        }
    };

    render() {
        if (this.state.isLoading) {
            return ("");
        }

        let { group, participantCount } = this.state;
        return (
            <div className="container">
                <div className="row">
                    <MainMenu />

                    <div className="col-sm-6">
                        <h3>{group.name}</h3>
                        <p>{group.description}</p>
                        <Wall receiptId={group.id} recipientType={RecipientType.GROUP}/>
                    </div>
                    <div className="col-sm-3">
                        <div className="blocks">
                            <img className="userPhoto" src={apiImages + group.imageId} />
                        </div>
                        <this.Buttons />
                        <UserBlock friends={group.participant} count={participantCount} title="groups.participant"/>
                    </div>
                </div>
            </div>

    );
    }

}

export default withRouter(Group);
