import axios, {AxiosResponse} from 'axios';
import {api} from '../api';

class UserService {

    public async find(userId:number): Promise<AxiosResponse<any>> {
       return axios.get( api + '/user/' + userId);
    }

    public async getRelation(rootUserId:number, userId:number): Promise<AxiosResponse<any>> {
        return axios.get( api + '/' + rootUserId + '/friends/relation/' + userId);
    }

    public async countGroups(userId:number): Promise<AxiosResponse<any>> {
        return axios.get( api + '/' + userId + '/groups/count');
    }
    public async countFriends(userId:number): Promise<AxiosResponse<any>> {
        return axios.get( api + '/' + userId + '/friends/count');
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
