import {AxiosResponse} from 'axios';
import {api} from '../index';
import UserService from './UserService';
import ApiClient from './ApiClient';

class GroupService {
    public findListByUserId(rootUserId:number, callback:any): void {
        ApiClient.get(api + '/' + rootUserId + '/groups', callback);
    }

    public create(rootUserId:number, newGroup:Group, callback:any): void {
        ApiClient.post(api + '/' + rootUserId + '/groups/create', newGroup, {}, callback);
    }

    public async find(groupId:number, callback:any): Promise<any> {
        return ApiClient.get(api + '/group/' + groupId, callback);
    }

    public async countParticipant(groupId:number, callback:any): Promise<any> {
        return ApiClient.get( api + '/group/' + groupId + '/count', callback);
    }

    async isUserInGroup(userId:number, groupId:number): Promise<AxiosResponse<any>> {
        return ApiClient.get(api + '/' + userId + '/groups/existence/' + groupId);
    }

    async join(groupId:number): Promise<AxiosResponse<any>> {
        return ApiClient.post(api + '/' + UserService.getRootUserId() + '/groups/' + groupId + '/join');
    }

    public search(groupName:string, callback:any): void {
        ApiClient.get(api + '/group/search?groupName=' + groupName, callback);
    }

    async exit(groupId:number): Promise<AxiosResponse<any>> {
        return ApiClient.post(api + '/' + UserService.getRootUserId() + '/groups/' + groupId + '/exit');
    }
}

export default new GroupService();
export interface Group {
    name: string,
    description: string
}
