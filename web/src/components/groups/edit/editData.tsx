import React, {Component} from 'react';
import {FormattedMessage} from 'react-intl';
import GroupService, {Group} from '../../../service/GroupService';
import Errors from '../../templates/errors';

interface EditGroupDataState {
    id:number
    name:string,
    description:string,
    errors:string[]
}
interface EditGroupDataProps {
    group:any
    history:any
}
export default class EditData extends Component<EditGroupDataProps, EditGroupDataState> {

    state: EditGroupDataState = {
        name: this.props.group.name,
        description: this.props.group.description,
        id: this.props.group.id,
        errors: []
    };

    handleInputChangeName = (event: React.FormEvent<HTMLInputElement>) => {
        this.setState({
            name: event.currentTarget.value,
            description: this.state.description,
            errors: this.state.errors,
            id: this.state.id
        })
    };

    handleInputChangeDescription = (event: React.FormEvent<HTMLInputElement>) => {
        this.setState({
            name: this.state.name,
            description: event.currentTarget.value,
            errors: this.state.errors,
            id: this.state.id
        })
    };

    saveChanges = () => {
        let group:Group = {
            id: this.state.id,
            name: this.state.name,
            description: this.state.description
        };
        GroupService.edit(group, (res:any) => {
            if (res.data.success === true) {
                this.props.history.push('/group/' + res.data.data.id)
            }
        })
    };

    render() {
        const {name, description, errors} = this.state;
        return(
            <div>
                <h3><FormattedMessage id="groups.edit.title" /></h3>
                <form>
                    <div className="form-group">
                        <label htmlFor="inputName"><FormattedMessage id="groups.name" /></label>
                        <input name="name" value={name} className="form-control" id="inputName" onChange={this.handleInputChangeName} />
                    </div>
                    <div className="form-group">
                        <label htmlFor="inputDescription"><FormattedMessage id="groups.description" /></label>
                        <input name="description" value={description} className="form-control" id="inputDescription" onChange={this.handleInputChangeDescription} />
                    </div>
                    <Errors errors={errors} />
                    <button type="button" onClick={this.saveChanges} className="btn btn-secondary btn-custom"><FormattedMessage id="common.edit" /></button>
                </form>
            </div>
        );
    }
}
