import {AxiosResponse} from 'axios';
import {api} from '../index';
import ApiClient from './ApiClient';

class UserService {

    public async find(userId:number, callback:any): Promise<any> {
       return ApiClient.get( api + '/users/' + userId, callback);
    }

    public getRootUser(callback:any):void {
        ApiClient.get( api + '/users/' + this.getRootUserId(), callback);
    }

    public async getRelation(userId:number, callback?:any): Promise<AxiosResponse<any>> {
        return ApiClient.get( api + '/' + this.getRootUserId() + '/friends/relation/' + userId, callback);
    }

    public getUserByRestoreToken(token:string, callback:any): void {
        ApiClient.get(api + "/forgot-password?token=" + token, callback);
    }

    public async countGroups(userId:number, callback?:any): Promise<AxiosResponse<any>> {
        return ApiClient.get( api + '/' + userId + '/groups/count', callback);
    }

    public async countFriends(userId:number, callback?:any): Promise<AxiosResponse<any>> {
        return ApiClient.get( api + '/' + userId + '/friends/count', callback);
    }

    public saveProfile(profile:Profile, callback:any): void {
        ApiClient.post( api + '/' + profile.id + '/profile', profile, {}, callback);
    }

    public async registration(registrationModel:any, callback:any) {
        ApiClient.put(api + "/registration", registrationModel, callback);
    }
    public async login(email:any, password:any, callback:any) {
        ApiClient.get(api + "/login?email=" + email + "&password=" + password, callback);
    }

    public savePhoto(userId:number, file:File, callback:any):void {
        let url = api + '/image/user/' + userId;
        var fileFormData = new FormData();
        fileFormData.append('file', file);
        var options = {
            headers: {'Content-Type': undefined}
        };
        ApiClient.put(url, fileFormData, options, callback)
    }

    public saveMiniPhoto(userId:number, blob:Blob, callback:any):void {
        let url = api + '/image/user/' + userId + '/mini';
        var fileFormData = new FormData();
        fileFormData.append('file', new File([blob], ""));
        var options = {
            headers: {'Content-Type': undefined}
        };
        ApiClient.put(url, fileFormData, options, callback)

    }

    public setRootUserId(rootUserId:number) {
        localStorage.setItem("rootUserId", String(rootUserId));
    }

    public cleanLocalStorage() {
        localStorage.setItem("rootUserId", "");
        localStorage.setItem("token", "");
    }

    public getRootUserId():number {
        let rootUserIdStr = localStorage.getItem("rootUserId");
        return rootUserIdStr != null ? +rootUserIdStr : 0;
    }

}

export default new UserService();

export interface Profile {
    id: number
    name?: string
    surname?: string
    sex?: Gender
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
