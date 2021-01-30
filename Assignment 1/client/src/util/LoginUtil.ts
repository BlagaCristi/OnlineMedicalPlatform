import {CAREGIVER_ROLE, DOCTOR_ROLE, PATIENT_ROLE} from "./GlobalConstants";
import {getMedicationList, getUserList} from "../state/doctor/DoctorService";
import {initCaregiver} from "../state/caregiver/CaregiverService";
import {initPatient} from "../state/patient/PatientService";

export const initPostLoginBehavior = (response, addUser, history, updateUserList, updateMedicationList, updateCaregiverId, updatePatientCaregiverList, updatePatientDetails, updatePatientId, updateSelectedMedicationPlanId, updateMedicationPlanList, updateError): void => {
    let username = response.data.name;
    let role: string = response.data.principal.authorities[0].authority;
    let authenticated: boolean = response.data.authenticated;

    addUser(username, role, authenticated);

    if (role === DOCTOR_ROLE) {
        getUserList(updateUserList, updateError);
        getMedicationList(updateMedicationList, updateError);
    }
    if (role === CAREGIVER_ROLE) {
        initCaregiver(username, updateCaregiverId, updatePatientCaregiverList, updateError);
    }
    if (role === PATIENT_ROLE) {
        initPatient(username, updatePatientId, updatePatientDetails, updateMedicationPlanList, updateError);
    }

    history.push(getOverviewForRole(role));
};

export const handleLogout = (addUser, history) => {
    localStorage.clear();
    addUser("", undefined, false);
    history.push("/login");
};

export const getOverviewForRole = (role) => {
    if (role === "ROLE_DOCTOR") {
        return "/doctor-overview";
    }
    if (role === "ROLE_CAREGIVER") {
        return "/caregiver-overview";
    }
    if (role === "ROLE_PATIENT") {
        return "/patient-overview";
    }
    return "/login";
};