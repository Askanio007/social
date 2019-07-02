import {api} from '../index';
import ApiClient from './ApiClient';

class TextResourcesService {

    public static getAllTextResources(callback:any): void {
        ApiClient.get(api + "/resources?lang=ru", callback);
    }
}

export default TextResourcesService;
