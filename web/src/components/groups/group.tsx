import React, {Component} from 'react';
import {FormattedMessage} from 'react-intl';
import MainMenu from '../templates/menu';
import Wall from '../templates/wall';
import UserBlock from '../templates/userBlock';
import {GroupRelation} from '../../service/GroupService';
import {RecipientType} from '../../service/WallService';
import {withRouter} from 'react-router';
import {apiImages} from '../../index';
import {EnterGroupBtn, ExitGroupBtn} from '../templates/buttons/groupButtons';
import {connect} from 'react-redux';
import {exitGroup, getGroup, joinGroup} from '../../reducers/groups';
import {IStore} from '../../store';

class Group extends Component<any, any> {

    componentDidMount(): void {
        let groupId:number = this.props.match.params.groupId;
        const { dispatch } = this.props;
        dispatch(getGroup(groupId));
    }

    leave = async () => {
        const { dispatch, group } = this.props;
        await dispatch(exitGroup(group.id));
        await dispatch(getGroup(group.id));
    };

     join = async () => {
        const { dispatch, group } = this.props;
        await dispatch(joinGroup(group.id));
        await dispatch(getGroup(group.id));
    };

    Buttons = () => {
        const { relation, group } = this.props;
        switch (relation) {
            case GroupRelation.NOT_PARTICIPANT: return (<EnterGroupBtn action={this.join} />);
            case GroupRelation.PARTICIPANT: return (<ExitGroupBtn action={this.leave} />);
            case GroupRelation.ADMIN: return (<button type="button" onClick={() => this.props.history.push('/editGroup/' + group.id)}
                                                      className="btn btn-secondary btn-custom"><FormattedMessage id={"common.edit"} /></button>);
            default: return null;
        }
    };

    render() {
        let { group, participantCount } = this.props;
        if (!group) {
            return null
        }
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

const mapStateToProps = (state: IStore) => ({
    group: state.groups.get.group,
    participantCount: state.groups.get.countParticipant,
    relation:state.groups.get.groupRelation
});

export default connect(mapStateToProps)(withRouter(Group));
