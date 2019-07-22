import React, {Component} from 'react';
import MainMenu from '../templates/menu';
import Tabs from '../templates/tabs';
import {FormattedMessage} from 'react-intl';
import EditDetails from './editDetails';
import UserService from '../../service/UserService';
import EditPhoto from '../templates/editPhoto';

interface EditProfileState {
    user:any
    isLoading:boolean

}
export default class Profile extends Component<any, EditProfileState> {

    state: EditProfileState = {
        user: null,
        isLoading: true
    }

    componentDidMount(): void {
        UserService.getRootUser((res:any) => {
            if (res.data.success === true) {
                this.setState({
                    user: res.data.data,
                    isLoading: false
                });
            }
        })
    }

    render() {
        if (this.state.isLoading) {
            return null
        }
        const { user } = this.state;

        return (
            <div className="container">
                <div className="row">
                    <MainMenu />
                    <Tabs>
                        <EditDetails><FormattedMessage id="profile.menu.detail" /></EditDetails>
                        <EditPhoto sizePhoto={user.details.miniImageId}
                                   currentPhoto={user.details.imageId}
                                   id={UserService.getRootUserId()}
                                   savePhotoFunc={UserService.savePhoto}
                                   saveMiniPhotoFunc={UserService.saveMiniPhoto}><FormattedMessage id="profile.menu.photo" /></EditPhoto>
                    </Tabs>
                </div>
            </div>
        );
    }

}
