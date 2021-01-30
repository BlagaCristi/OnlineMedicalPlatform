import {UserState} from "./user/UserState";
import {DoctorState} from "./doctor/DoctorState";
import {CaregiverState} from "./caregiver/CaregiverState";
import {PatientState} from "./patient/PatientState";

export interface GlobalState {
    user: UserState,
    doctor: DoctorState,
    caregiver: CaregiverState,
    patient: PatientState
};