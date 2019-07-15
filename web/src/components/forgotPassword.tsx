import React from 'react';
import {FormattedMessage} from 'react-intl';
import {Link} from 'react-router-dom';
import RestorePasswordService from '../service/RestorePasswordService';

interface ForgotPasswordState {
    email: string
    errors: any[]
    isSuccess:boolean
}
class ForgotPassword extends React.Component<{}, ForgotPasswordState> {

    state: ForgotPasswordState = {
        email: "",
        errors: [],
        isSuccess: false
    };

    restorePassword = () => {
        RestorePasswordService.sendRestoreMail(this.state.email, (res:any) => {
            this.setState({
                isSuccess: res.data.success,
                errors: res.data.errors
            })
        })
    };

    handleChange = (event: React.FormEvent<HTMLInputElement>) => {
        this.setState({
            email: event.currentTarget.value,
            errors: this.state.errors
        });
    };

    render() {

        let errors;
        if (this.state.errors) {
            errors = this.state.errors.map((code) =>
                <div className="alert alert-danger"><FormattedMessage id={code}/></div>
            );
        }

        let success;
        if (this.state.isSuccess) {
            success = <div className="alert alert-success">
                <FormattedMessage id="common.restore.info" />
            </div>
        }
        return (
            <div className="container main-container">
                <h3 className="center"><FormattedMessage id="common.restore.password" /></h3>
                <form>
                    <div className="form-group">
                        <label htmlFor="inputEmail"><FormattedMessage id="common.email" /></label>
                        <input name="email" value={this.state.email} type="text" className="form-control" id="inputEmail" onChange={this.handleChange}/>
                    </div>
                    {errors}
                    {success}
                    <button type="button" onClick={this.restorePassword} className="btn btn-secondary btn-custom btn-margin"><FormattedMessage id="common.restore.password" /></button>
                    <div className="widthMax center">
                        <Link to={'/login'} className="custom-link center"><FormattedMessage id="common.login" /></Link>
                    </div>
                </form>
            </div>

    );
    }

}

export default ForgotPassword;
