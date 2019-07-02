import axios, {AxiosResponse} from 'axios';
import {api} from '../index';

class WallService {

    async find(receiptId:number, recipientType:RecipientType): Promise<AxiosResponse<any>> {
        return axios.get(api + '/message/public/' + receiptId + '/' + recipientType);
    }

    async sendMessage(message:PublicMessage): Promise<AxiosResponse<any>> {
        return axios.post(api + '/message/public/save', message);
    }
}

export default new WallService();
export interface PublicMessage {
    message: string,
    recipientId: number,
    recipientType: RecipientType,
    senderId: number
}
export enum RecipientType {
    USER = "USER",
    GROUP = "GROUP"
}
