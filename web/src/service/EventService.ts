import {api} from '../index';
import ApiClient from './ApiClient';

class EventService {

    public find(rootUserId:any, callback:any):void {
        ApiClient.get(api + '/' + rootUserId + '/events', callback);
    }

}
export default new EventService();
