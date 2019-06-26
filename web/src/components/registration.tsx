import React from 'react';
import {FormattedMessage} from 'react-intl';
import {Link, Redirect} from 'react-router-dom';
import {registration} from '../api';
import axios from 'axios';
import UserService from '../service/UserService';

interface RegistrationState {
    name?: string;
    surname?: string,
    sex?: string,
    email?: string;
    password?: string;
    errors?: string[];
    isLogged?: boolean
}

class Registration extends React.Component<{}, RegistrationState> {

    state: RegistrationState = {
        name: "",
        surname: "",
        sex: "MALE",
        email: "",
        password: "",
        errors: [],
        isLogged: false
    };

    handleChange = (event: React.FormEvent<HTMLInputElement>) => {
        this.setState({
            [event.currentTarget.name]: event.currentTarget.value
        });
    };

    registration = () => {
        var registrationModel = {
            name: this.state.name,
            surname: this.state.surname,
            sex: this.state.sex,
            email: this.state.email,
            password: this.state.password,
        };

        axios.post(registration, registrationModel).then( res => {
            if (res.data.success === true) {
                UserService.setRootUserId(res.data.data.id);
            } else {
                this.setState({
                    errors: res.data.errors
                })
            }
        })
    };

    render() {
        if (this.state.isLogged === true) {
            return <Redirect to={'/me'} />
        }
        let errors;
        let viewErrors;
        if (this.state.errors && this.state.errors.length !== 0) {
            errors = this.state.errors.map((code) =>
                <div><FormattedMessage id={code} /></div>
            );
            viewErrors = (
                <div className="alert alert-danger">
                    {errors}
                </div>
            )
        }
        return (
            <div className="container main-container">
                <h3 className="center"><FormattedMessage id="common.welcome.message" /></h3>
                <form>
                    <div className="form-group">
                        <label htmlFor="inputName"><FormattedMessage id="common.name" /></label>
                        <input name="name" value={this.state.name} type="text" className="form-control" id="inputName" onChange={this.handleChange} />
                    </div>
                    <div className="form-group">
                        <label htmlFor="inputSurname"><FormattedMessage id="common.surname" /></label>
                        <input name="surname" value={this.state.surname} type="text" className="form-control" id="inputSurname" onChange={this.handleChange} />
                    </div>
                    <fieldset className="form-group">
                        <div className="row">
                            <legend className="col-form-label col-sm-2 pt-0"><FormattedMessage id="common.sex" /></legend>
                            <div className="col-sm-10">
                                <div className="form-check">
                                    <input className="form-check-input" type="radio" name="sex"  id="gridRadios1" value="FEMALE" onChange={this.handleChange} />
                                    <label className="form-check-label" htmlFor="gridRadios1"><FormattedMessage id="common.FEMALE" /></label>
                                </div>
                                <div className="form-check">
                                    <input className="form-check-input" type="radio" name="sex" id="gridRadios2" value="MALE" onChange={this.handleChange} checked />
                                    <label className="form-check-label" htmlFor="gridRadios2"><FormattedMessage id="common.MALE" /></label>
                                </div>
                            </div>
                        </div>
                    </fieldset>
                    <div className="form-group">
                        <label htmlFor="inputEmail"><FormattedMessage id="common.email" /></label>
                        <input name="email" value={this.state.email}  type="email" className="form-control" id="inputEmail" onChange={this.handleChange} />
                        <small id="emailHelp" className="form-text text-muted"><FormattedMessage id="common.email.notice" /></small>
                    </div>
                    <div className="form-group">
                        <label htmlFor="inputPassword"><FormattedMessage id="common.password" /></label>
                        <input name="password" value={this.state.password} type="password" className="form-control" id="inputPassword" onChange={this.handleChange}/>
                    </div>
                    {viewErrors}
                    <button type="button" onClick={this.registration} className="btn btn-primary btn-custom"><FormattedMessage id="common.registration" /></button>
                    <div className="widthMax center">
                        <Link to={'/login'} className="custom-link center"><FormattedMessage id="common.login" /></Link>
                    </div>
                </form>
            </div>

    );
    }

}

export default Registration;