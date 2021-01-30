import Axios, {AxiosInstance} from 'axios';
import {API_BASE_PATH, BASE_PATH} from "./GlobalConstants";

class Api {
    private axiosInstance: AxiosInstance;

    constructor() {
        this.axiosInstance = Api.initialize();
    }

    private static initialize() {
        return Axios.create({
            baseURL: BASE_PATH + API_BASE_PATH,
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
            }
        });
    }

    public reset() {
        this.axiosInstance = Api.initialize();
    }

    public get(path: string) {
        return this.axiosInstance.get(path);
    }

    public put<T>(path: string, payload: T) {
        return this.axiosInstance.put(path, payload);
    }

    public post<T>(path: string, payload: T) {
        return this.axiosInstance.post(path, payload);
    }

    public delete(path: string) {
        return this.axiosInstance.delete(path);
    }

    public setHeader(headerName: string, headerValue: string) {
        this.axiosInstance.defaults.headers[headerName] = headerValue;
    }
}

export const api = new Api();