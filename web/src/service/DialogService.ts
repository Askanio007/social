import {api} from '../index';
import UserService from './UserService';
import ApiClient from './ApiClient';

class DialogService {

    public find(callback:any): void {
        ApiClient.get(api + '/' + UserService.getRootUserId() + '/dialog', callback);
    }

    public findMessages(dialogId:number, callback:any): void {
        ApiClient.get(api + '/' + UserService.getRootUserId() + '/dialog/' + dialogId + '/message', callback);
    }

    public saveMessage(message:string, dialogId:number, callback:any): void {
        let params = {
            senderId: UserService.getRootUserId(),
            message: message,
            dialogId: dialogId
        };
        ApiClient.post(api + '/dialog/message/save', params, {}, callback);
    }

    public findDialogIdBy(friendId:number, callback:any): void {
        ApiClient.get(api + '/' + UserService.getRootUserId() + '/dialog/' + friendId, callback);
    }
}
export default new DialogService();
