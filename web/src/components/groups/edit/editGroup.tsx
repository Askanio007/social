import React, {Component} from 'react';
import MainMenu from '../../templates/menu';
import Tabs from '../../templates/tabs';
import {FormattedMessage} from 'react-intl';
import GroupService from '../../../service/GroupService';
import EditData from './editData';
import EditPhoto from '../../templates/editPhoto';
import {withRouter} from 'react-router';

interface EditGroupState {
    groupId: number
    group:any
    isLoading:boolean

}
class EditGroup extends Component<any, EditGroupState> {

    state: EditGroupState = {
        groupId: this.props.match.params.groupId,
        group: null,
        isLoading: true
    };


    componentDidMount(): void {
        GroupService.find(this.state.groupId, (res:any) => {
            if (res.data.success === true) {
                this.setState({
                    group: res.data.data,
                    isLoading: false
                })
            }
        })
    }

    render() {
        if (this.state.isLoading) {
            return null
        }
        return(
            <div className="container">
                <div className="row">
                    <MainMenu />
                    <Tabs>
                        <EditData group={this.state.group} history={this.props.history}><FormattedMessage id="groups.edit.menu.detail" /></EditData>
                        <EditPhoto id={this.state.group.id}
                                   currentPhoto={this.state.group.avatar64code}
                                   sizePhoto={this.state.group.miniAvatar64code}
                                   saveMiniPhotoFunc={GroupService.saveMiniPhoto}
                                   savePhotoFunc={GroupService.savePhoto}>
                            <FormattedMessage id="groups.edit.menu.photo" /></EditPhoto>
                    </Tabs>
                </div>
            </div>
        );
    }
}

export default withRouter(EditGroup);
