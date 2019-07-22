import React, {Component} from 'react';
import {Link} from 'react-router-dom';
import '../../css/wall.css';
import {apiImages} from '../../index';

interface PhotoProps {
    link: string,
    photoId: number,
    stylePhoto: string
}

class Photo extends Component<PhotoProps, any> {

    constructor(props: PhotoProps, context: any) {
        super(props, context);
        this.props = props;
    }

    props: PhotoProps = {
        link: "",
        photoId: 0,
        stylePhoto: ""
    };

    render() {
        return (
            <Link to={this.props.link}><img className={this.props.stylePhoto} src={apiImages + this.props.photoId} /></Link>
        );
    }
}

export default Photo;
