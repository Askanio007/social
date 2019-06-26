import React, {Component} from 'react';
import {FormattedMessage} from 'react-intl';
import {Link} from 'react-router-dom';
import GroupService from '../../service/GroupService';
import Photo from '../templates/photo';
import {EnterGroupBtn} from '../templates/buttons';

interface GroupsSearchState {
    groups: any[]
    searchText: string
}
class GroupsSearch extends Component<{}, GroupsSearchState> {

    state: GroupsSearchState = {
        groups: [],
        searchText: ""
    };

    handleSearchText = (event: React.ChangeEvent<HTMLInputElement>) => {
        let state = this.state;
        state.searchText = event.currentTarget.value;
        this.setState(state);
    };

    search = () => {
        GroupService.search(this.state.searchText).then((res:any) => {
            if (res.data.success === true) {
                let state = this.state;
                state.groups = res.data.data;
                this.setState(state);
            }
        })

    };

    enterGroup = (promise:Promise<any>) => {
        promise.then((res:any) => {
            if (res.data.success === true) {
                this.forceUpdate();
            }
        });
    };

    ListGroups = (value:any) => {
        let group = value.group;
        return (
            <tr>
                <td className="vertical-top">
                    <Photo link={'/group/' + group.id} photoHashCode={group.avatar64code} stylePhoto={"photo-friend"} />
                </td>
                <td className="vertical-top">
                    <div className="wall-record-name">
                        <h4>
                            <Link className="custom-link" to={'/group/' + group.id}>{group.name}</Link>
                        </h4>
                    </div>
                    <EnterGroupBtn groupId={group.id} callback={this.enterGroup}/>
                </td>
            </tr>
        );
    };

    render() {
        const groups = this.state.groups.map((group:any) => <this.ListGroups key={group.id} group={group}/>);
        return (
            <div>
                <div className="input-group mb-3">
                    <div className="input-group-prepend">
                        <span className="input-group-text" id="basic-addon1"><i className="fas fa-search"></i></span>
                    </div>
                    <input type="text" name="searchText" value={this.state.searchText} className="form-control" onChange={this.handleSearchText} />
                    <div className="input-group-append">
                        <button type="button" className="btn btn-secondary btn-custom-style" onClick={this.search}><FormattedMessage id="common.search" /></button>
                    </div>
                </div>
                <table className="widthMax">
                    <tbody>
                        {groups}
                    </tbody>
                </table>
            </div>
    );
    }
}

export default GroupsSearch;