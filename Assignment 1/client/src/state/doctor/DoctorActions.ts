import {Action} from "redux";
import {MedicationDoctorView, UserDoctorView} from "./DoctorState";

export enum DoctorActionTypes {
    updateUserList = "DOCTOR/UPDATE_USER_LIST",
    updateSelectedUserId = "DOCTOR/UPDATE_SELECTED_USER_ID",
    updateSelectedMedicationId = "DOCTOR/UPDATE_SELECTED_MEDICATION_ID",
    updateMedicationList = "DOCTOR/IPDATE_MEDICATION_LIST"
}

export interface UpdateUserListAction extends Action<DoctorActionTypes.updateUserList> {
    payload: {
        userList: Array<UserDoctorView>
    }
}

export interface UpdateMedicationListAction extends Action<DoctorActionTypes.updateMedicationList> {
    payload: {
        medicationList: Array<MedicationDoctorView>
    }
}

export interface UpdateSelectedUserIdAction extends Action<DoctorActionTypes.updateSelectedUserId> {
    payload: {
        selectedUserId: number | undefined
    }
}

export interface UpdateSelectedMedicationIdAction extends Action<DoctorActionTypes.updateSelectedMedicationId> {
    payload: {
        selectedMedicationId: number | undefined
    }
}

export const DoctorActionCreators = {
    updateUserList: (payload: UpdateUserListAction['payload']): UpdateUserListAction => ({
        type: DoctorActionTypes.updateUserList,
        payload
    }),
    updateMedicationList: (payload: UpdateMedicationListAction['payload']): UpdateMedicationListAction => ({
        type: DoctorActionTypes.updateMedicationList,
        payload
    }),
    updateSelectedUserId: (payload: UpdateSelectedUserIdAction['payload']): UpdateSelectedUserIdAction => ({
        type: DoctorActionTypes.updateSelectedUserId,
        payload
    }),
    updateSelectedMedicationId: (payload: UpdateSelectedMedicationIdAction['payload']): UpdateSelectedMedicationIdAction => ({
        type: DoctorActionTypes.updateSelectedMedicationId,
        payload
    }),
};