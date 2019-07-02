import axios, {AxiosInstance, AxiosRequestConfig} from 'axios';

class ApiClient {

    static ax:AxiosInstance = axios.create();

    public static async get(url:string, callback?:any): Promise<any> {
        this.addHeaders();
        return this.ax.get(url)
            .then(callback)
            .catch((err:any) => {
            console.log("failed api response:" + err);
        })
    }

    public static async post(url:string, data?:any, config?:AxiosRequestConfig, callback?:any): Promise<any> {
        this.addHeaders();
        return this.ax.post(url, data, config).then(callback,(res:any) => {

        });
    }

    private static addHeaders() {
        this.ax.defaults.headers.common['Authorization'] = this.getToken();
    }

    public static setToken(token:string) {
        localStorage.setItem("token", token);
    }

    public static getToken():string | null {
        return localStorage.getItem("token");
    }
}

export default ApiClient;
