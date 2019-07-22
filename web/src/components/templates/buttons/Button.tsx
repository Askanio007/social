import React from 'react';
import {FormattedMessage} from 'react-intl';
import {UserRelation} from '../../user';

interface ButtonProps {
    titleCode:string,
    onClick?:any
    disabled?:boolean
}
export interface GeneralBtnProps {
    id:number
    callback:(promise:Promise<any>, userRelation?:UserRelation) => void
}
const Button = (props:ButtonProps) => (
    <button type="button"
            className="btn btn-secondary btn-custom btn-margin"
            onClick={props.onClick}
            disabled={props.disabled}>
        <FormattedMessage id={props.titleCode} />
    </button>
);

Button.defaultProps = {
    titleCode:"",
    onClick:() => {},
    disabled:false
};

export default Button;
