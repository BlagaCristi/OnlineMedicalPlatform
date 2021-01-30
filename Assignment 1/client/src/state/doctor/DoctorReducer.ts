import {Reducer} from "redux";
import {DoctorState, initialState} from "./DoctorState";
import {DoctorActionTypes} from "./DoctorActions";

export const DoctorReducer: Reducer<DoctorState> = (state = initialState, action) => {
    switch (action.type) {
        case DoctorActionTypes.updateUserList: {
            let updatedState = Object.assign({}, state,);

            updatedState.userList = action.payload.userList;

            return updatedState;
        }
        case DoctorActionTypes.updateSelectedUserId: {
            let updatedState = Object.assign({}, state,);

            updatedState.selectedUserId = action.payload.selectedUserId;

            return updatedState;
        }
        case DoctorActionTypes.updateMedicationList: {
            let updatedState = Object.assign({}, state,);

            updatedState.medicationList = action.payload.medicationList;

            return updatedState;
        }
        case DoctorActionTypes.updateSelectedMedicationId: {
            let updatedState = Object.assign({}, state,);

            updatedState.selectedMedicationId = action.payload.selectedMedicationId;

            return updatedState;
        }
        default:
            return state;
    }
};