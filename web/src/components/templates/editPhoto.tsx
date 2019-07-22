import React, {ChangeEvent, Component} from 'react';
import {FormattedMessage} from 'react-intl';
import AvatarEditor from 'react-avatar-editor';
import Photo from '../templates/photo';
import {apiImages} from '../../index';
import ImageService from '../../service/ImageService';

interface EditPhotoState {
    currentPhoto: number
    currentPhotoBase64:string
    sizePhoto: number
}
interface EditPhotoProps {
    currentPhoto: number
    sizePhoto: number
    id:number
    savePhotoFunc:any
    saveMiniPhotoFunc:any
}
export default class EditPhoto extends Component<EditPhotoProps, EditPhotoState> {

    state: EditPhotoState = {
        currentPhoto: this.props.currentPhoto,
        sizePhoto: this.props.sizePhoto,
        currentPhotoBase64: ""
    };


    componentDidMount(): void {
        this.updateImageBase64();
    }

    updateImageBase64 = () => {
        ImageService.getImageBase64Encode(this.state.currentPhoto, (res:any) => {
            this.setState({
                currentPhoto: this.state.currentPhoto,
                sizePhoto: this.state.sizePhoto,
                currentPhotoBase64: res.data
            })
        })
    };

    editor:any = null;

    handleUpload = (event: ChangeEvent<HTMLInputElement>) => {
        if (event.target.files && event.target.files[0]) {
            this.props.savePhotoFunc(this.props.id, event.target.files[0], (res:any) => {
                if (res.data.success === true) {
                    this.setState({
                        currentPhoto: res.data.data,
                        sizePhoto: 0
                    });
                    this.updateImageBase64();
                }
            });
        }
    };

    handleMini = () => {
        fetch(this.editor.getImageScaledToCanvas().toDataURL())
            .then(res => res.blob())
            .then(blob => {
                this.props.saveMiniPhotoFunc(this.props.id, blob, (res:any) => {
                    let state = this.state;
                    state.sizePhoto = res.data.data;
                    this.setState(state);
                });
            });

    };

    setEditorRef = (editor:any) => this.editor = editor;

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
                <div className="row">
                    <div className="col-sm-3 middle-block">
                        <div className="menu-margin"><img className="userPhoto" src={apiImages + this.state.currentPhoto} /></div>
                        <div className="menu-margin"><Photo link={'#'} photoId={this.state.sizePhoto} stylePhoto="wall-photo-block"/></div>
                        <button type="button" className="btn btn-secondary btn-custom btn-margin" onClick={this.handleMini}><FormattedMessage id="common.photo.mini.save" /></button>
                    </div>

                    <div className="col-sm-6">
                        <AvatarEditor
                            image={'data:image/gif;base64,' + this.state.currentPhotoBase64}
                            ref={this.setEditorRef}
                            border={50}
                            scale={1.2}
                            color={[255, 255, 255, 0.6]}
                        />
                    </div>
                </div>
            </div>
        );
    }
}
