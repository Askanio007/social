import axios from 'axios';
import {api} from '../index';

class EventService {

    async find(rootUserId:any) {
        return axios.get(api + '/' + rootUserId + '/events');
    }

}
export default new EventService();
