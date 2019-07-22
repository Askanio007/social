import {api} from '../index';
import ApiClient from './ApiClient';

class ImageService {

    public getImageBase64Encode(imageId:number, callback:any) :void {
        console.log(imageId);
        ApiClient.get(api + "/images/" + imageId + '/base64', callback);
    }

}

export default new ImageService();
