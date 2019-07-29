import React from 'react';
import {FormattedMessage} from 'react-intl';

interface GroupButtonsProps {
    action: any
}

const ExitGroupBtn = ({ action }:GroupButtonsProps) => (
    <button type="button" className="btn btn-secondary btn-custom" onClick={action}><FormattedMessage id="groups.exit" /></button>
);

const EnterGroupBtn = ({ action }:GroupButtonsProps) => (
    <button type="button" className="btn btn-secondary btn-custom" onClick={action}><FormattedMessage id="groups.enter" /></button>
);

export {EnterGroupBtn, ExitGroupBtn}
