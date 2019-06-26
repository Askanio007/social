import axios from 'axios';
import {api} from '../api';

class EventService {

    async find(rootUserId:any) {
        return axios.get(api + '/' + rootUserId + '/events');
    }

}
export default new EventService();
