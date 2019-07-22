import React from 'react';
import GroupService from '../../../service/GroupService';
import {FormattedMessage} from 'react-intl';
import {GeneralBtnProps} from './Button';

const ExitGroupBtn = (props:GeneralBtnProps) => (
    <button type="button" className="btn btn-secondary btn-custom" onClick={() => props.callback(GroupService.exit(props.id))}><FormattedMessage id="groups.exit" /></button>
);

const EnterGroupBtn = (props:GeneralBtnProps) => (
    <button type="button" className="btn btn-secondary btn-custom" onClick={() => props.callback(GroupService.join(props.id))}><FormattedMessage id="groups.enter" /></button>
);

export {EnterGroupBtn, ExitGroupBtn}
