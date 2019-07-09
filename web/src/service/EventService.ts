import {api} from '../index';
import ApiClient from './ApiClient';
import UserService from './UserService';

class EventService {

    public find(page:number, callback:any):void {
        ApiClient.get(api + '/' + UserService.getRootUserId() + '/events?page=' + page, callback);
    }

}
export default new EventService();
