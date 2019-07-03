import React, {Component} from 'react';
import {Link} from 'react-router-dom';
import '../../css/wall.css';

interface PhotoProps {
    link: string,
    photoHashCode: string,
    stylePhoto: string
}

class Photo extends Component<PhotoProps, any> {

    constructor(props: PhotoProps, context: any) {
        super(props, context);
        this.props = props;
    }

    props: PhotoProps = {
        link: "",
        photoHashCode: "",
        stylePhoto: ""
    };

    render() {
        return (
            <Link to={this.props.link}><img className={this.props.stylePhoto} src={'data:image/gif;base64,' + this.props.photoHashCode} /></Link>
        );
    }
}

export default Photo;
