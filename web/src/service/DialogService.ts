import {api} from '../index';
import UserService from './UserService';
import ApiClient from './ApiClient';

class DialogService {

    public find(page:number, callback:any): void {
        ApiClient.get(api + '/' + UserService.getRootUserId() + '/dialog?page=' + page, callback);
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

    public findCountNotReadMessage(userId:number, callback:any): Promise<any> {
        return ApiClient.get(api + '/' + userId + '/dialog/messages/unread/count', callback)
    }
}
export default new DialogService();
