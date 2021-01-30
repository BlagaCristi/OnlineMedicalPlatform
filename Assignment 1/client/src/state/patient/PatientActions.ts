import {Action} from "redux";
import {MedicationPacientPlan, PatientDetails} from "./PatientState";

export enum PatientActionTypes {
    updatePatientDetails = "PATIENT/UPDATE_PATIENT_DETAILS",
    updateMedicationPlanList = "PATIENT/UPDATE_MEDICATION_PLAN_LIST",
    updateSelectedMedicationPlanId = "PATIENT/UPDATE_SELECTED_MEDICATION_PLAN_ID",
    updatePatientId = "PATIENT/UPDATE_PATIENT_ID"
}

export interface UpdatePatientDetailsAction extends Action<PatientActionTypes.updatePatientDetails> {
    payload: {
        patientDetails: PatientDetails
    }
}

export interface UpdateMedicationPlanListAction extends Action<PatientActionTypes.updateMedicationPlanList> {
    payload: {
        medicationPlanList: MedicationPacientPlan[]
    }
}

export interface UpdateSelectedMedicationPlanIdAction extends Action<PatientActionTypes.updateSelectedMedicationPlanId> {
    payload: {
        selectedMedicationPlanId: number | undefined
    }
}

export interface UpdatePatientIdAction extends Action<PatientActionTypes.updatePatientId> {
    payload: {
        patientId: number
    }
}

export const PatientActionCreators = {
    updatePatientDetails: (payload: UpdatePatientDetailsAction['payload']): UpdatePatientDetailsAction => ({
        type: PatientActionTypes.updatePatientDetails,
        payload
    }),
    updateMedicationPlanList: (payload: UpdateMedicationPlanListAction['payload']): UpdateMedicationPlanListAction => ({
        type: PatientActionTypes.updateMedicationPlanList,
        payload
    }),
    updateSelectedMedicationPlanId: (payload: UpdateSelectedMedicationPlanIdAction['payload']): UpdateSelectedMedicationPlanIdAction => ({
        type: PatientActionTypes.updateSelectedMedicationPlanId,
        payload
    }),
    updatePatientId: (payload: UpdatePatientIdAction['payload']): UpdatePatientIdAction => ({
        type: PatientActionTypes.updatePatientId,
        payload
    }),
};