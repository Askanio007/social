import React from 'react';
import {FormattedMessage} from 'react-intl';

interface ErrorProps {
    errors?:string[]
}
const Errors = (props:ErrorProps) => {
    if (props.errors && props.errors.length !== 0) {
        return (
            <div className="alert alert-danger">
                {props.errors.map((code:any) =>
                    <div><FormattedMessage id={code} /></div>
                )}
            </div>
        )
    } else {
        return null;
    }
};

export default Errors;
