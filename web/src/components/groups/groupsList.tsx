import React, {Component} from 'react';
import {FormattedMessage} from 'react-intl';
import {Link, withRouter} from 'react-router-dom';
import Photo from '../templates/photo';
import {Pagination} from '../templates/pagination';
import {connect} from 'react-redux';
import {IStore} from '../../store';
import {exitGroup, getList} from '../../reducers/groups';
import {ExitGroupBtn} from '../templates/buttons/groupButtons';

class GroupsList extends Component<any, any> {

    componentDidMount(): void {
        this.updateList(0);
    }

    updateList = (page:number) => {
        const { dispatch } = this.props;
        dispatch(getList(page));
    };

    ListGroups = (value:any) => {
        const { dispatch } = this.props;
        let group = value.group;
        return (
            <tr>
                <td className="vertical-top">
                    <Photo link={'/group/' + group.id} photoId={group.miniImageId} stylePhoto={"photo-friend"} />
                </td>
                <td className="vertical-top">
                    <div className="wall-record-name">
                        <h4>
                            <Link className="custom-link" to={'/group/' + group.id}>{group.name}</Link>
                        </h4>
                    </div>
                    <ExitGroupBtn action={() => dispatch(exitGroup(group.id))} />
                </td>
            </tr>
        );
    };

    render() {
        const { groups, countRecord, currentPage, history } = this.props;
        if (!groups || countRecord === 0) {
            return null;
        }
        const groupsView = groups.map((group:any) => <this.ListGroups key={group.id} group={group}/>);
        let pagination;
        if (countRecord > 0) {
            pagination = <Pagination currentPage={currentPage} countRecord={countRecord} handlePage={this.updateList} />
        }
        return (
            <div>
                <button type="button" className="btn btn-secondary btn-custom" onClick={() => history.push('/createGroup')}><FormattedMessage id="groups.create" /></button>
                <table className="widthMax">
                    <tbody>
                        {groupsView}
                    </tbody>
                </table>
                {pagination}
            </div>
        );
    }
}

const mapStateToProps = (state: IStore) => ({
    groups: state.groups.getList.groups,
    countRecord:state.groups.getList.countRecord,
    currentPage:state.groups.getList.currentPage
});

export default connect(mapStateToProps)(withRouter(GroupsList));
