import React, {Component} from 'react';
import {FormattedMessage} from 'react-intl';
import GroupService, {Group} from '../../service/GroupService';
import MainMenu from '../templates/menu';
import UserService from '../../service/UserService';
import {withRouter} from 'react-router-dom';

interface CreateGroupState {
    group: Group
    errors: string[]

}
class CreateGroup extends Component<any, CreateGroupState> {

    state: CreateGroupState = {
        group: {
            name: "",
            description: ""
        },
        errors: []
    };

    handleChangeName = (event: React.FormEvent<HTMLInputElement>) => {
        this.setState({
            group: {
                name: event.currentTarget.value,
                description: this.state.group.description
            }
        });
    };

    handleChangeDescription = (event: React.FormEvent<HTMLInputElement>) => {
        this.setState({
            group: {
                name: this.state.group.name,
                description: event.currentTarget.value
            }
        });
    };

    create = () => {
        GroupService.create(UserService.getRootUserId(), this.state.group, (res:any) => {
            if (res.data.success === true) {
                this.props.history.push('/group/' + res.data.data.id);
            }
        })
    };


    render() {
        const errors = this.state.errors.map((error:string) =>
            <div className="alert alert-danger">
                <div><FormattedMessage id={error} /></div>
            </div>
        );
        return (
            <div className="container">
                <div className="row">
                    <MainMenu />

                    <div className="col-sm-6">
                        <h3><FormattedMessage id="groups.create" /></h3>
                        <form>
                            <div className="form-group">
                                <label htmlFor="inputName"><FormattedMessage id="groups.name" /></label>
                                <input name="name" value={this.state.group.name} className="form-control" id="inputName" onChange={this.handleChangeName}/>
                            </div>
                            <div className="form-group">
                                <label htmlFor="inputDescription"><FormattedMessage id="groups.description" /></label>
                                <input name="description" value={this.state.group.description} className="form-control" id="inputDescription" onChange={this.handleChangeDescription}/>
                            </div>
                            {errors}
                            <button type="button" onClick={this.create} className="btn btn-secondary btn-custom"><FormattedMessage id='common.create'/></button>
                        </form>
                    </div>
                </div>
            </div>
        );
    }
}
export default withRouter(CreateGroup);
