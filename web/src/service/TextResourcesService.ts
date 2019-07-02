import axios, {AxiosResponse} from 'axios';
import {api} from '../index';

class TextResourcesService {

    async getAllTextResources(): Promise<AxiosResponse<any>> {
        return axios.get(api + "/resources?lang=ru");
    }

}

export default new TextResourcesService();
