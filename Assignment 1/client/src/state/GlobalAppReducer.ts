import {combineReducers} from "redux";
import {UserReducer} from "./user/UserReducer";
import {DoctorReducer} from "./doctor/DoctorReducer";
import {CaregiverReducer} from "./caregiver/CaregiverReducer";
import {PatientReducer} from "./patient/PatientReducer";

export const globalAppReducer = combineReducers({
    user: UserReducer,
    doctor: DoctorReducer,
    caregiver: CaregiverReducer,
    patient: PatientReducer
});