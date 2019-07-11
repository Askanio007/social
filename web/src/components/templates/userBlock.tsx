import React, {Component} from 'react';
import {FormattedMessage} from 'react-intl';
import Photo from './photo';
import {Link} from 'react-router-dom';
import '../../css/friendsBlock.css';

interface FriendsBlockProps {
    friends: any
    count: number
    title: string
}

class UserBlock extends Component<FriendsBlockProps, any> {


    constructor(props: FriendsBlockProps, context: any) {
        super(props, context);
        this.props = props;
    }

    props: FriendsBlockProps = {
        friends: [],
        count: 0,
        title:""
    };

    Friend = (value:any) => {
        let friend = value.friend;
        return (
            <div className="friends-block">
                <Photo stylePhoto="mini-image-block" link={"/user/" + friend.id} photoHashCode={friend.image64code} />
                <Link to={"/user/" + friend.id} className="custom-link"><p className="friends-name">{friend.shortName}</p></Link>
            </div>
        )
    };

    render() {
        const friendsBlocks = this.props.friends.map((friend:any) =>
            <this.Friend key={friend.id} friend={friend} />
        );
        return (
            <div className="blocks">
                <div className="blocksTitle">
                    <FormattedMessage id={this.props.title} /> ({this.props.count})
                </div>
                <div className="left">
                    {friendsBlocks}
                </div>
            </div>
        )
    }
}

export default UserBlock;