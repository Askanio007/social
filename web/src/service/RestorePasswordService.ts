import ApiClient from './ApiClient';
import {api} from '../index';

class RestorePasswordService {

    public sendRestoreMail(email:string, callback:any): void {
        ApiClient.post( api + '/forgot-password',{email: email}, {}, callback);
    }

    public changePassword(password:string, userId:number, callback:any): void {
        ApiClient.post( api + '/change-password',{id: userId, password: password}, {}, callback);
    }

}
export default new RestorePasswordService();
