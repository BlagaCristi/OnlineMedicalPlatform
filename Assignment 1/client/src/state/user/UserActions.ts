import {Action} from "redux";
import {ErrorUser} from "./UserState";

export enum UserActionTypes {
    addUser = 'USER/ADD_USER',
    updateSelectedTab = "USER/UPDATE_SELECTED_TAB",
    updateError = "USER/UPDATE_ERROR"
}

export interface AddUserAction extends Action<UserActionTypes.addUser> {
    payload: {
        username: string,
        role: string,
        isLoggedIn: boolean
    }
}

export interface UpdateSelectedTabAction extends Action<UserActionTypes.updateSelectedTab> {
    payload: {
        selectedTab: number
    }
}

export interface UpdateErrorAction extends Action<UserActionTypes.updateError> {
    payload: {
        error: ErrorUser
    }
}

export const UserActionCreators = {
    addUser: (payload: AddUserAction['payload']): AddUserAction => ({
        type: UserActionTypes.addUser,
        payload
    }),
    updateSelectedTab: (payload: UpdateSelectedTabAction['payload']): UpdateSelectedTabAction => ({
        type: UserActionTypes.updateSelectedTab,
        payload
    }),
    updateError: (payload: UpdateErrorAction['payload']): UpdateErrorAction => ({
        type: UserActionTypes.updateError,
        payload
    }),
};