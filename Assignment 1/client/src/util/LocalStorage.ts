import {ROLE, USERNAME} from "./GlobalConstants";

class LocalStorage {
    constructor() {
        const username = localStorage.getItem(USERNAME);
        if (username === null)
            localStorage.setItem(USERNAME, "");

        const role = localStorage.getItem(ROLE);
        if (role === null)
            localStorage.setItem(ROLE, "");
    }

    public setItem(key: string, value: string) {
        localStorage.setItem(key, value);
    }

    public getItem(key: string) {
        const item = localStorage.getItem(key);
        if (item === null)
            return "";
        return item;
    }
}

export const localStorageObject = new LocalStorage();