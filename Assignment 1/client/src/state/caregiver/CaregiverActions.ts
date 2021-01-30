import {Action} from "redux";
import {PatientCaregiver} from "./CaregiverState";

export enum CaregiverActionTypes {
    updateCaregiverId = "CAREGIVER/UPDATE_CAREGIVER_ID",
    updatePatientCaregiverList = "CAREGIVER/UPDATE_PATIENT_CAREGIVER_LIST",
    updateSelectedPatientId = "CAREGIVER/UPDATE_SELECTED_PATIENT_ID",
    updateSelectedMedicationPlanId = "CAREGIVER/UPDATE_SELECTED_MEDICATION_PLAN_ID",
}

export interface UpdateCaregiverIdAction extends Action<CaregiverActionTypes.updateCaregiverId> {
    payload: {
        caregiverId: number
    }
}

export interface UpdatePatientCaregiverListAction extends Action<CaregiverActionTypes.updatePatientCaregiverList> {
    payload: {
        patientCaregiverList: PatientCaregiver[]
    }
}

export interface UpdateSelectedPatientId extends Action<CaregiverActionTypes.updateSelectedPatientId> {
    payload: {
        selectedPatientId: number | undefined
    }
}

export interface UpdateSelectedMedicationPlanId extends Action<CaregiverActionTypes.updateSelectedMedicationPlanId> {
    payload: {
        selectedMedicationPlanId: number | undefined
    }
}

export const CaregiverActionCreators = {
    updateCaregiverId: (payload: UpdateCaregiverIdAction['payload']): UpdateCaregiverIdAction => ({
        type: CaregiverActionTypes.updateCaregiverId,
        payload
    }),
    updatePatientCaregiverList: (payload: UpdatePatientCaregiverListAction['payload']): UpdatePatientCaregiverListAction => ({
        type: CaregiverActionTypes.updatePatientCaregiverList,
        payload
    }),
    updateSelectedPatientId: (payload: UpdateSelectedPatientId['payload']): UpdateSelectedPatientId => ({
        type: CaregiverActionTypes.updateSelectedPatientId,
        payload
    }),
    updateSelectedMedicationPlanId: (payload: UpdateSelectedMedicationPlanId['payload']): UpdateSelectedMedicationPlanId => ({
        type: CaregiverActionTypes.updateSelectedMedicationPlanId,
        payload
    }),
};