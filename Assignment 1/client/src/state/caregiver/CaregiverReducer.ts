import {Reducer} from "redux";
import {CaregiverState, initialState} from "./CaregiverState";
import {CaregiverActionTypes} from "./CaregiverActions";

export const CaregiverReducer: Reducer<CaregiverState> = (state = initialState, action) => {
    switch (action.type) {
        case CaregiverActionTypes.updateCaregiverId: {
            let updatedState = Object.assign({}, state,);

            updatedState.caregiverId = action.payload.caregiverId;

            return updatedState;
        }
        case CaregiverActionTypes.updatePatientCaregiverList: {
            let updatedState = Object.assign({}, state,);

            updatedState.patientCaregiverList = action.payload.patientCaregiverList;

            return updatedState;
        }
        case CaregiverActionTypes.updateSelectedPatientId: {
            let updatedState = Object.assign({}, state,);

            updatedState.selectedPatientId = action.payload.selectedPatientId;

            return updatedState;
        }
        case CaregiverActionTypes.updateSelectedMedicationPlanId: {
            let updatedState = Object.assign({}, state,);

            updatedState.selectedMedicationPlanId = action.payload.selectedMedicationPlanId;

            return updatedState;
        }
        default:
            return state;
    }
};