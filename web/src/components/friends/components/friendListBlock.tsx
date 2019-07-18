import Photo from '../../templates/photo';
import {Link} from 'react-router-dom';
import React from 'react';

interface FriendBlockListProps {
    buttons:any
    avatar:any
    id:any
    fullName:any

}
const FriendBlockList = (props: FriendBlockListProps) => {
    return (
        <tr>
            <td className="vertical-top photo-friend">
                <Photo link={"/user/" + props.id} photoHashCode={props.avatar} stylePhoto="photo-friend"/>
            </td>
            <td className="vertical-top">
                <div className="wall-record-name">
                    <h4 className="center"><Link to={'/user/' + props.id} className="custom-link">{props.fullName}</Link></h4>
                </div>
                {props.buttons}
            </td>
        </tr>
    );
};
export default FriendBlockList;
