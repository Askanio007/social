import React, {Component} from 'react';
import {FormattedMessage} from 'react-intl';
import Photo from './photo';
import {Link} from 'react-router-dom';

interface GroupBlockProps {
    groups: any;
    count: number;
}

class GroupBlock extends Component<GroupBlockProps, any> {

    constructor(props: GroupBlockProps, context: any) {
        super(props, context);
        this.props = props;
    }

    props: GroupBlockProps = {
        groups: [],
        count: 0
    };

    Group = (value:any) => {
        let group = value.group;
        return (
            <tr>
                <td><Photo stylePhoto="mini-image-circle" link={"/group/" + group.id} photoHashCode={group.image64code} /></td>
                <td className="widthMax center">
                    <Link to={"/group/" + group.id} className="custom-link"><p>{group.fullName}</p></Link>
                </td>
            </tr>
        )
    };

    render() {
        const groupsBlocks = this.props.groups.map((group:any) =>
                <this.Group key={group.id} group={group} />
            );
        return (
            <div className="blocks">
                <div className="blocksTitle"><FormattedMessage id='common.groups' /> ({this.props.count})</div>
                <table>
                    <tbody>
                        {groupsBlocks}
                    </tbody>
                </table>
            </div>
        )
    }
}

export default GroupBlock;
