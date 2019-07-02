import axios, {AxiosResponse} from 'axios';
import {api} from '../index';

class UserService {

    public async find(userId:number): Promise<AxiosResponse<any>> {
       return axios.get( api + '/user/' + userId);
    }

    public async getRootUser(): Promise<AxiosResponse<any>> {
        return axios.get( api + '/user/' + this.getRootUserId());
    }

    public async getRelation(userId:number): Promise<AxiosResponse<any>> {
        return axios.get( api + '/' + this.getRootUserId() + '/friends/relation/' + userId);
    }

    public async countGroups(userId:number): Promise<AxiosResponse<any>> {
        return axios.get( api + '/' + userId + '/groups/count');
    }

    public async countFriends(userId:number): Promise<AxiosResponse<any>> {
        return axios.get( api + '/' + userId + '/friends/count');
    }

    public async saveProfile(profile:Profile): Promise<AxiosResponse<any>> {
        return axios.post( api + '/' + profile.id + '/profile', profile);
    }

    public async registration(registrationModel:any): Promise<AxiosResponse<any>> {
        return axios.post(api + "/registration", registrationModel);
    }
    public async login(email:any, password:any): Promise<AxiosResponse<any>> {
        return axios.get(api + "/login?email=" + email + "&password=" + password);
    }


    public async savePhoto(file:File): Promise<AxiosResponse<any>> {
        let url = api + '/image/user/' + this.getRootUserId() + '/upload';
        var fileFormData = new FormData();
        fileFormData.append('file', file);
        var options = {
            headers: {'Content-Type': undefined}
        };
        return axios.post(url, fileFormData, options)
    }

    public setRootUserId(rootUserId:number) {
        localStorage.setItem("rootUserId", String(rootUserId));
    }

    public getRootUserId():number {
        let rootUserIdStr = localStorage.getItem("rootUserId");
        return rootUserIdStr != null ? +rootUserIdStr : 0;
    }

}

export default new UserService();

export interface Profile {
    id: number
    name: string
    surname: string
    sex: Gender
    city?: string
    country?: string
    birthday?: Date | null
    phone?: string
    about?: string
}

export enum Gender {
    MALE = "MALE",
    FEMALE = "FEMALE"
}
