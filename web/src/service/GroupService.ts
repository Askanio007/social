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

    public async groupRelation(groupId:number, callback:any): Promise<any> {
        return ApiClient.get( api + '/group/' + groupId + '/relation/' + UserService.getRootUserId(), callback);
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

    public edit(group:Group, callback:any):void {
        ApiClient.post(api + "/group/" + group.id + "/edit", group, {}, callback);
    }

    public savePhoto(groupId:number, file:File, callback:any):void {
        let url = api + '/image/group/' + groupId + '/upload';
        console.log(url);
        var fileFormData = new FormData();
        fileFormData.append('file', file);
        var options = {
            headers: {'Content-Type': undefined}
        };
        ApiClient.post(url, fileFormData, options, callback)
    }

    public saveMiniPhoto(groupId:number, blob:Blob, callback:any):void {
        let url = api + '/image/group/' + groupId + '/mini/upload';
        var fileFormData = new FormData();
        fileFormData.append('file', new File([blob], ""));
        var options = {
            headers: {'Content-Type': undefined}
        };
        ApiClient.post(url, fileFormData, options, callback)

    }
}

export default new GroupService();
export interface Group {
    id?:number,
    name: string,
    description: string
}
export enum GroupRelation {
    ADMIN = "ADMIN",
    PARTICIPANT = "PARTICIPANT",
    NOT_PARTICIPANT = "NOT_PARTICIPANT"

}
