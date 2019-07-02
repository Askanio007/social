import {api} from '../index';
import ApiClient from './ApiClient';

class WallService {

    public find(receiptId:number, recipientType:RecipientType, callback:any):void {
        ApiClient.get(api + '/message/public/' + receiptId + '/' + recipientType, callback);
    }

    public sendMessage(message:PublicMessage, callback:any):void {
        ApiClient.post(api + '/message/public/save', message, {}, callback);
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
