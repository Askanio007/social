import axios, {AxiosResponse} from 'axios';
import {api} from '../index';
import UserService from './UserService';

class FriendService {

    async findToUser(userId:any): Promise<AxiosResponse<any>> {
        return axios.get(api + '/' + userId + '/friends');
    }

    async findRequestsToUser(userId:any): Promise<AxiosResponse<any>> {
        return axios.get(api + '/' + userId + '/friends/request');
    }

    async countFriends(userId:number): Promise<AxiosResponse<any>> {
        return axios.get(api + '/' + userId + '/friends/count');
    }

    async searchUser(userName:string) {
        return axios.get(api + '/user/search?userName=' + userName + '&rootUserId=' + UserService.getRootUserId());
    }

    async acceptRequest(friendshipRequestId:number) {
        return axios.post(api + '/' +  UserService.getRootUserId() + '/friends/request/accept/' + friendshipRequestId);
    }

    async declineRequest(friendshipRequestId:number) {
        return axios.post(api + '/' +  UserService.getRootUserId() + '/friends/request/decline/' + friendshipRequestId);
    }

    async remove(userId:number) {
        return axios.post(api + '/' + UserService.getRootUserId() + '/friends/remove/' + userId);
    }

    async sendFriendRequest(friendshipRequest:FriendshipRequest) {
        return axios.post(api + '/' +  friendshipRequest.fromUserId + '/friends/request/add', friendshipRequest);
    }
}

export default new FriendService();
export interface FriendshipRequest {
    fromUserId:number
    toUserId:number
}
