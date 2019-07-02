import React, {Component} from 'react';
import {FormattedMessage} from 'react-intl';
import UserService, {Gender, Profile} from '../../service/UserService';
import {withRouter} from 'react-router-dom';

interface EditDetailsState {
    name?: string
    surname?: string
    sex?: Gender
    city?: string
    country?: string
    birthday?: string
    phone?: string
    about?: string
    errors: string[]
}
class EditDetails extends Component<any, EditDetailsState> {

    state: EditDetailsState = {
        name: "",
        surname: "",
        sex: Gender.MALE,
        city: "",
        country: "",
        birthday: "",
        phone: "",
        about: "",
        errors: []

    };


    componentDidMount(): void {
        UserService.getRootUser((res:any) => {
            if (res.data.success === true) {
                let data = res.data.data;
                this.setState({
                    name: data.name,
                    surname: data.surname,
                    sex: data.details.sex,
                    city: data.details.city,
                    country: data.details.country,
                    birthday: data.details.birthdayInputView,
                    phone: data.details.phone,
                    about: data.details.about,
                    errors: []
                });
            }
        })
    }

    updateState = (name:string, value:string) => {
        this.setState({
            [name]: value,
            errors: this.state.errors
        })
    };

    handleInputChange = (event: React.FormEvent<HTMLInputElement>) => {
        this.updateState(event.currentTarget.name, event.currentTarget.value)
    };

    handleTextAreaChange = (event: React.FormEvent<HTMLTextAreaElement>) => {
        this.updateState(event.currentTarget.name, event.currentTarget.value)
    };

    editProfile = () => {
        let profile:Profile = {
            id: UserService.getRootUserId(),
            name: this.state.name,
            surname: this.state.surname,
            sex: this.state.sex,
            city: this.state.city,
            country: this.state.country,
            birthday: this.state.birthday ? new Date(this.state.birthday) : null,
            phone: this.state.phone,
            about: this.state.about,
        };
        UserService.saveProfile(profile, (res:any) => {
            this.props.history.push('/me');
        })
    };

    render() {
        let errors = this.state.errors.map((code) =>
            <div className="alert alert-danger">
                <div><FormattedMessage id={code} /></div>
            </div>
        );
        return (
            <div>
                <h3><FormattedMessage id="profile.detail.title" /></h3>
                <form>
                    <div className="form-group">
                        <label htmlFor="inputName"><FormattedMessage id="profile.name" /></label>
                        <input name="name" value={this.state.name} className="form-control" id="inputName" onChange={this.handleInputChange}/>
                    </div>
                    <div className="form-group">
                        <label htmlFor="inputSurname"><FormattedMessage id="profile.surname" /></label>
                        <input name="surname" value={this.state.surname} className="form-control" id="inputSurname" onChange={this.handleInputChange}/>
                    </div>
                    <fieldset className="form-group">
                        <div className="row">
                            <legend className="col-form-label col-sm-2 pt-0"><FormattedMessage id="common.sex" /></legend>
                            <div className="col-sm-10">
                                <div className="form-check">
                                    <input className="form-check-input"
                                           type="radio" name="sex" id="gridRadios1"
                                           value={Gender.FEMALE}
                                           checked={this.state.sex == Gender.FEMALE}
                                           onChange={this.handleInputChange} />
                                        <label className="form-check-label" htmlFor="gridRadios1">
                                            <FormattedMessage id="common.FEMALE" />
                                        </label>
                                </div>
                                <div className="form-check">
                                    <input className="form-check-input"
                                           type="radio" name="sex" id="gridRadios2"
                                           value={Gender.MALE}
                                           checked={this.state.sex == Gender.MALE}
                                           onChange={this.handleInputChange}/>
                                        <label className="form-check-label" htmlFor="gridRadios2">
                                            <FormattedMessage id="common.MALE" />
                                        </label>
                                </div>
                            </div>
                        </div>
                    </fieldset>
                    <div className="form-group">
                        <label htmlFor="inputCountry"><FormattedMessage id="profile.country" /></label>
                        <input name="country" value={this.state.country} className="form-control" id="inputCountry" onChange={this.handleInputChange}/>
                    </div>
                    <div className="form-group">
                        <label htmlFor="inputCity"><FormattedMessage id="profile.city" /></label>
                        <input name="city" value={this.state.city} className="form-control" id="inputCity" onChange={this.handleInputChange}/>
                    </div>
                    <div className="form-group">
                        <label htmlFor="inputAge"><FormattedMessage id="profile.birthday" /></label>
                        <input type="date" name="birthday" value={this.state.birthday} className="form-control" id="inputAge" onChange={this.handleInputChange}/>
                    </div>
                    <div className="form-group">
                        <label htmlFor="inputPhone"><FormattedMessage id="profile.phone" /></label>
                        <input name="phone" value={this.state.phone} className="form-control" id="inputPhone" onChange={this.handleInputChange}/>
                    </div>
                    <div className="form-group">
                        <label htmlFor="inputAbout"><FormattedMessage id="profile.about" /></label>
                        <textarea name="about" value={this.state.about} className="form-control rounded-0" id="inputAbout" rows={3} onChange={this.handleTextAreaChange} />
                    </div>
                    {errors}
                    <button onClick={this.editProfile} type="button" className="btn btn-secondary btn-custom"><FormattedMessage id="common.save" /></button>
                </form>
            </div>
        );
    }
}

export default withRouter(EditDetails);
