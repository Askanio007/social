import axios, {AxiosResponse} from 'axios';
import {api} from '../api';

class WallService {

    async find(receiptId:number, isUser:boolean): Promise<AxiosResponse<any>> {
        return axios.get(api + '/message/public/' + receiptId + '/' + isUser);
    }

    async sendMessage(message:PublicMessage): Promise<AxiosResponse<any>> {
        return axios.post(api + '/message/public/save', message);
    }
}

export default new WallService();
export interface PublicMessage {
    message: string,
    recipientId: number,
    recipientUser: boolean,
    senderId: number
}
