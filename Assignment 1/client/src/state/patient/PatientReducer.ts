import {Reducer} from "redux";
import {initialState, PatientState} from "./PatientState";
import {PatientActionTypes} from "./PatientActions";

export const PatientReducer: Reducer<PatientState> = (state = initialState, action) => {
    switch (action.type) {
        case PatientActionTypes.updatePatientDetails: {
            let updatedState = Object.assign({}, state,);

            updatedState.patientDetails = action.payload.patientDetails;

            return updatedState;
        }
        case PatientActionTypes.updateMedicationPlanList: {
            let updatedState = Object.assign({}, state,);

            updatedState.medicationPlanList = action.payload.medicationPlanList;

            return updatedState;
        }
        case PatientActionTypes.updatePatientId: {
            let updatedState = Object.assign({}, state,);

            updatedState.patientId = action.payload.patientId;

            return updatedState;
        }
        case PatientActionTypes.updateSelectedMedicationPlanId: {
            let updatedState = Object.assign({}, state,);

            updatedState.selectedMedicationPlanId = action.payload.selectedMedicationPlanId;

            return updatedState;
        }
        default:
            return state;
    }
};