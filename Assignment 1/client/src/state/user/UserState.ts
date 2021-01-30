export interface ErrorUser {
    status: string,
    message: string,
    open: boolean
}

export interface UserState {
    username: string,
    role: string | undefined,
    isLoggedIn: boolean,
    selectedTab: number,
    error: ErrorUser
}

export const initialState: UserState = {
    error: {
        status: "",
        message: "",
        open: false
    },
    username: "",
    role: undefined,
    isLoggedIn: false,
    selectedTab: 0
};