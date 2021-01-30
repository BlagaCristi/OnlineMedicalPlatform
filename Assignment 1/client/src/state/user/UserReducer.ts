import {Reducer} from "redux";
import {initialState, UserState} from "./UserState";
import {UserActionTypes} from "./UserActions";

export const UserReducer: Reducer<UserState> = (state = initialState, action) => {
    switch (action.type) {
        case UserActionTypes.addUser: {
            let updatedState = Object.assign({}, state,);
            updatedState.role = action.payload.role;
            updatedState.username = action.payload.username;
            updatedState.isLoggedIn = action.payload.isLoggedIn;

            return updatedState;
        }
        case UserActionTypes.updateSelectedTab: {
            let updatedState = Object.assign({}, state,);
            updatedState.selectedTab = action.payload.selectedTab;

            return updatedState;
        }
        case UserActionTypes.updateError: {
            let updatedState = Object.assign({}, state,);
            updatedState.error = action.payload.error;

            return updatedState;
        }
        default:
            return state;
    }
};