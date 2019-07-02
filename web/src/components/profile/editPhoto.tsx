import React, {ChangeEvent, Component} from 'react';
import {FormattedMessage} from 'react-intl';
import UserService from '../../service/UserService';

interface EditPhotoState {
    currentPhoto: string
}
class EditPhoto extends Component<any, EditPhotoState> {

    state: EditPhotoState = {
        currentPhoto: ""
    };

    componentDidMount(): void {
        UserService.getRootUser((res:any) => {
            if (res.data.success === true) {
                this.setState({currentPhoto: res.data.data.details.image64code});
            }
        })
    }

    handleUpload = (event: ChangeEvent<HTMLInputElement>) => {
        if (event.target.files && event.target.files[0]) {
            UserService.savePhoto(event.target.files[0], (res:any) => {
                if (res.data.success === true) {
                    this.setState({currentPhoto: res.data.data});
                }
            })
        }
    };

    render() {
        return (
            <div>
                <h3><FormattedMessage id="profile.photo.title" /></h3>
                <form>
                    <div className="custom-file">
                        <input type="file" className="custom-file-input" id="customFile" file-upload="myFile" onChange={this.handleUpload}/>
                        <label className="custom-file-label" htmlFor="customFile"><FormattedMessage id="common.choose.file" /></label>
                    </div>
                </form>
                <br />
                <img src={"data:image/JPEG;base64," + this.state.currentPhoto} />
            </div>
        );
    }
}

export default EditPhoto;
