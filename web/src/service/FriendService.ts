import {api} from '../index';
import UserService from './UserService';
import ApiClient from './ApiClient';

class FriendService {

    public findToUser(userId:any, page:number, callback:any): void {
        ApiClient.get(api + '/' + userId + '/friends?page=' + page, callback);
    }

    public findCountRequestToUser(userId:any, callback:any): Promise<any> {
        return ApiClient.get(api + '/' + userId + '/friends/request/count', callback);
    }

    public findRequestsToUser(userId:any, page:number, callback:any): void {
        ApiClient.get(api + '/' + userId + '/friends/request?page=' + page, callback);
    }

    public searchUser(userName:string, callback:any):void {
        ApiClient.get(api + '/user/search?userName=' + userName + '&rootUserId=' + UserService.getRootUserId(), callback);
    }

    async acceptRequest(friendshipRequestId:number) {
        return ApiClient.post(api + '/' +  UserService.getRootUserId() + '/friends/request/' + friendshipRequestId + '/accept/');
    }

    async declineRequest(friendshipRequestId:number) {
        return ApiClient.delete(api + '/' +  UserService.getRootUserId() + '/friends/request/' + friendshipRequestId);
    }

    async remove(userId:number) {
        return ApiClient.delete(api + '/' + UserService.getRootUserId() + '/friends/' + userId);
    }

    async sendFriendRequest(friendshipRequest:FriendshipRequest) {
        return ApiClient.put(api + '/' +  friendshipRequest.fromUserId + '/friends/request', friendshipRequest);
    }
}

export default new FriendService();
export interface FriendshipRequest {
    fromUserId:number
    toUserId:number
}
