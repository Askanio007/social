import React from 'react';
import {FormattedMessage} from 'react-intl';
import {Link} from 'react-router-dom';
import UserService from '../service/UserService';
import RestorePasswordService from '../service/RestorePasswordService';
import Errors from './templates/errors';

interface RestorePasswordState {
    userId: number
    password?: string
    confirmPassword?: string
    errors?: any[]
    notification?: string
    isAccess?:boolean
}
class RestorePassword extends React.Component<any, RestorePasswordState> {

    state: RestorePasswordState = {
        userId: 0,
        password: "",
        confirmPassword: "",
        errors: [],
        isAccess: false
    };


    componentDidMount(): void {
        UserService.getUserByRestoreToken(this.props.match.params.token, (res:any) => {
            if (res.data.success === true) {
                this.setState({
                    isAccess:true,
                    userId: res.data.data.id})
            } else {
                this.setState({
                    isAccess:false,
                    errors: res.data.errors})
            }
        });
    }

    validate = () => {
        let errors = [];
        if (this.state.password !== this.state.confirmPassword) {
            errors.push("restore.password.not.confirm");
        }
        if (errors.length > 0) {
            this.setState({
                //isAccess: true,
                errors: errors});
            return false;
        }
        return true;
    };

    restorePassword = () => {
        if (this.state.password && this.state.confirmPassword && this.validate()) {
            RestorePasswordService.changePassword(this.state.password, this.state.userId, (res:any) => {
                if (res.data.success === true) {
                    this.setState({
                        notification: "restore.password.success"
                    })
                } else {
                    this.setState({
                        errors: res.data.errors
                    })
                }
            })
        }
    };

    handleChange = (event: React.FormEvent<HTMLInputElement>) => {
        this.setState({
            userId: this.state.userId,
            [event.currentTarget.name]: event.currentTarget.value
        });
    };

    render() {
        let restoreBlock;
        const { notification, password, confirmPassword, isAccess, errors } = this.state;

        if (notification) {
            restoreBlock = <div className="alert alert-success"><FormattedMessage id={notification} /></div>
        } else if (isAccess) {
            restoreBlock =
                <form>
                    <div className="form-group">
                        <label htmlFor="inputPassword"><FormattedMessage id="common.password" /></label>
                        <input name="password" value={password} type="password" className="form-control" id="inputPassword" onChange={this.handleChange}/>
                    </div>
                    <div className="form-group">
                        <label htmlFor="inputPassword"><FormattedMessage id="common.password" /></label>
                        <input name="confirmPassword" value={confirmPassword} type="password" className="form-control" id="inputPassword" onChange={this.handleChange}/>
                    </div>
                    <Errors errors={errors}/>
                    <button type="button" onClick={this.restorePassword} className="btn btn-secondary btn-custom btn-margin"><FormattedMessage id="common.restore.password" /></button>
                </form>
        } else {
            restoreBlock = <Errors errors={errors}/>;
        }

        return (
            <div className="container main-container">
                <h3 className="center"><FormattedMessage id="common.restore.password" /></h3>
                {restoreBlock}
                <div className="widthMax center">
                    <Link to={'/login'} className="custom-link center"><FormattedMessage id="common.login" /></Link>
                </div>
            </div>

    );
    }

}

export default RestorePassword;
