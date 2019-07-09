import React, {Component} from 'react';
import {FormattedMessage} from 'react-intl';
import {Link, withRouter} from 'react-router-dom';
import GroupService from '../../service/GroupService';
import UserService from '../../service/UserService';
import Photo from '../templates/photo';
import {ExitGroupBtn} from '../templates/buttons';
import {Pagination} from '../templates/pagination';

interface GroupsListState {
    groups: any[]
    countRecord:number
    currentPage:number
}
class GroupsList extends Component<any, GroupsListState> {

    state: GroupsListState = {
        groups: [],
        countRecord:0,
        currentPage:0
    };

    componentDidMount(): void {
        this.updateList(0);
    }

    updateList = (page:number) => {
        GroupService.findListByUserId(UserService.getRootUserId(), page, (res:any) => {
            if (res.data.success === true) {
                this.setState({
                    groups: res.data.data.content,
                    countRecord: res.data.data.totalElements,
                    currentPage: page
                })
            }
        })
    };

    updateState = (promise:Promise<any>) => {
        promise.then((res:any) => {
            if (res.data.success) {
                this.componentDidMount();
            }
        });
    };

    ListGroups = (value:any) => {
        let group = value.group;
        return (
            <tr>
                <td className="vertical-top">
                    <Photo link={'/group/' + group.id} photoHashCode={group.miniAvatar64code} stylePhoto={"photo-friend"} />
                </td>
                <td className="vertical-top">
                    <div className="wall-record-name">
                        <h4>
                            <Link className="custom-link" to={'/group/' + group.id}>{group.name}</Link>
                        </h4>
                    </div>
                    <ExitGroupBtn id={group.id} callback={this.updateState} />
                </td>
            </tr>
        );
    };

    createGroupRedirect = () => {
        this.props.history.push('/createGroup')
    };

    render() {
        const groups = this.state.groups.map((group:any) => <this.ListGroups key={group.id} group={group}/>);
        let pagination;
        if (this.state.groups.length > 0) {
            pagination = <Pagination currentPage={this.state.currentPage} countRecord={this.state.countRecord} handlePage={this.updateList} />
        }
        return (
            <div>
                <button type="button" className="btn btn-secondary btn-custom" onClick={this.createGroupRedirect}><FormattedMessage id="groups.create" /></button>
                <table className="widthMax">
                    <tbody>
                        {groups}
                    </tbody>
                </table>
                {pagination}
            </div>
        );
    }
}

export default withRouter(GroupsList);
