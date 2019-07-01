import axios from 'axios';
import {api} from '../api';
import UserService from './UserService';

class DialogService {

    async find() {
        return axios.get(api + '/' + UserService.getRootUserId() + '/dialog');
    }

    async findMessages(dialogId:number) {
        return axios.get(api + '/' + UserService.getRootUserId() + '/dialog/' + dialogId + '/message');
    }

    async saveMessage(message:string, dialogId:number) {
        let params = {
            senderId: UserService.getRootUserId(),
            message: message,
            dialogId: dialogId
        };
        return axios.post(api + '/dialog/message/save', params);
    }
}
export default new DialogService();
