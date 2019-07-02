import axios, {AxiosResponse} from 'axios';
import {api} from '../index';
import UserService from './UserService';

class GroupService {
    async findListByUserId(rootUserId:number): Promise<AxiosResponse<any>> {
        return axios.get(api + '/' + rootUserId + '/groups');
    }

    async create(rootUserId:number, newGroup:Group): Promise<AxiosResponse<any>> {
        return axios.post(api + '/' + rootUserId + '/groups/create', newGroup);
    }

    async find(groupId:number): Promise<AxiosResponse<any>> {
        return axios.get(api + '/group/' + groupId);
    }

    public async countParticipant(groupId:number): Promise<AxiosResponse<any>> {
        return axios.get( api + '/group/' + groupId + '/count');
    }

    async isUserInGroup(userId:number, groupId:number): Promise<AxiosResponse<any>> {
        return axios.get(api + '/' + userId + '/groups/existence/' + groupId);
    }

    async join(groupId:number): Promise<AxiosResponse<any>> {
        return axios.post(api + '/' + UserService.getRootUserId() + '/groups/' + groupId + '/join');
    }

    async search(groupName:string): Promise<AxiosResponse<any>> {
        return axios.get(api + '/group/search?groupName=' + groupName);
    }

    async exit(groupId:number): Promise<AxiosResponse<any>> {
        return axios.post(api + '/' + UserService.getRootUserId() + '/groups/' + groupId + '/exit');
    }
}

export default new GroupService();
export interface Group {
    name: string,
    description: string
}
