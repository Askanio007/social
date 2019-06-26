import React from 'react';
import {Link, Redirect} from 'react-router-dom';
import {FormattedMessage} from 'react-intl';
import axios from 'axios';
import {login} from '../api';
import UserService from '../service/UserService';

interface LoginModel {
    email?: string;
    password?: string;
    errors?: string[];
    isLogged?: boolean;
}

class Login extends React.Component<any, LoginModel> {

    state: LoginModel = {
        email: "",
        password: "",
        errors: [],
        isLogged: false,
    };

    login = () => {
        axios.get(login + '?email=' + this.state.email + '&password=' + this.state.password).then( res => {
            if (res.data.success === true) {
                UserService.setRootUserId(res.data.data.id);
            }
            this.setState({
                isLogged: res.data.success,
                errors: res.data.errors
            });
        });

    };

    handleChange = (event: React.FormEvent<HTMLInputElement>) => {
        this.setState({
                [event.currentTarget.name]: event.currentTarget.value
        });
    };

    render() {
        if (this.state.isLogged === true) {
            return <Redirect to={'/me'} />
        }
        let errors;
        if (this.state.errors) {
            errors = this.state.errors.map((code) =>
                <div className="alert alert-danger">
                    <div><FormattedMessage id={code} /></div>
                </div>
            );
        }
        return (
            <div className="container main-container">
                <div className="row">
                    <div className="col align-self-center">
                        <h3 className="center"><FormattedMessage id="common.welcome.message" /></h3>
                        <form>
                            <div className="form-group">
                                <label htmlFor="inputEmail"><FormattedMessage id="common.email" /></label>
                                <input name="email" value={this.state.email} type="email" className="form-control" id="inputEmail" aria-describedby="emailHelp" onChange={this.handleChange}/>
                            </div>
                            <div className="form-group">
                                <label htmlFor="inputPassword"><FormattedMessage id="common.password" /></label>
                                <input name="password" value={this.state.password} type="password" className="form-control" id="inputPassword" onChange={this.handleChange}/>
                            </div>
                            {errors}
                            <button type="button" onClick={this.login} className="btn btn-primary btn-custom"><FormattedMessage id="common.login" /></button>
                            <div className="widthMax center">
                                <Link to={'/registration'} className="custom-link"><FormattedMessage id="common.registration" /></Link>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        );
    }

}

export default Login;
