import {api} from '../index';
import ApiClient from './ApiClient';

class WallService {

    public find(receiptId:number, recipientType:RecipientType, page:number, callback:any):Promise<any> {
        return ApiClient.get(api + '/messages/public/' + receiptId + '/' + recipientType + '?page=' + page, callback);
    }

    public sendMessage(message:PublicMessage, callback:any):void {
        ApiClient.put(api + '/messages/public', message, {}, callback);
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
