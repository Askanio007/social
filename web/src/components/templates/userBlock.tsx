import React from 'react';
import {FormattedMessage} from 'react-intl';
import Photo from './photo';
import {Link} from 'react-router-dom';
import '../../css/friendsBlock.css';

interface FriendsBlockProps {
    friends: any[]
    count: number
    title: string
}

const UserBlock = (props:FriendsBlockProps) => (
    <div className="blocks">
        <div className="blocksTitle">
            <FormattedMessage id={props.title} /> ({props.count})
        </div>
        <div className="left">
            {props.friends.map((friend:any) => {
                return (
                    <div className="friends-block" key={friend.id}>
                        <Photo stylePhoto="mini-image-block" link={"/user/" + friend.id} photoId={friend.imageId} />
                        <Link to={"/user/" + friend.id} className="custom-link"><p className="friends-name">{friend.shortName}</p></Link>
                    </div>
                )
            })}
        </div>
    </div>
);

export default UserBlock;
