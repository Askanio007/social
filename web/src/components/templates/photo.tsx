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
        const imageStyle = {
            background: 'url(data:image/gif;base64,' + this.props.photoHashCode +')' // 40% 30% no-repeat;
        };

        return (
            <div className={this.props.stylePhoto} style={imageStyle}>
                <Link to={this.props.link} />
            </div>
        );
    }
}

export default Photo;
