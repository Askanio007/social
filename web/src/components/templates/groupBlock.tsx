import React from 'react';
import {FormattedMessage} from 'react-intl';
import Photo from './photo';
import {Link} from 'react-router-dom';

interface GroupBlockProps {
    groups: any;
    count: number;
}

const GroupBlock = (props:GroupBlockProps) => (
    <div className="blocks">
        <div className="blocksTitle"><FormattedMessage id='common.groups' /> ({props.count})</div>
        <table>
            <tbody>
            {props.groups.map((group:any) => {
                return (
                    <tr key={group.id}>
                        <td><Photo stylePhoto="mini-image-circle" link={"/group/" + group.id} photoId={group.imageId} /></td>
                        <td className="widthMax center">
                            <Link to={"/group/" + group.id} className="custom-link"><p>{group.fullName}</p></Link>
                        </td>
                    </tr>
                );
            })}
            </tbody>
        </table>
    </div>
);

export default GroupBlock;
