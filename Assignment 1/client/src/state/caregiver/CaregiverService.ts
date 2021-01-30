import {api} from "../../util/Api";
import {
    CAREGIVER_PATH,
    DOCTOR_PATH,
    MEDICATION_PATH,
    MEDICATION_PLAN_DRUGS_PATH,
    MEDICATION_PLAN_PATH,
    PATIENT_PATH,
    RECOMMENDATION_PATH,
    USER_PATH
} from "../../util/GlobalConstants";
import {MedicationPacientCaregiver, MedicationPacientCaregiverPlan, PatientCaregiver} from "./CaregiverState";
import {initPatientActivityWebsocket} from "../../websocket/Websocket";

export const initCaregiver = (username, updateCaregiverId, updatePatientCaregiverList, updateError) => {
    api.get(USER_PATH + "/username?username=" + username)
        .then(response => {
            let userId = response.data.id;
            api.get(CAREGIVER_PATH + "/user?userId=" + userId)
                .then(response => {
                    updateCaregiverId(response.data.id);
                    initPatientActivityWebsocket(response.data.id, updateError);
                    getCaregiverPatients(response.data.id, updatePatientCaregiverList, updateError);
                })
                .catch(error => {
                    updateError({
                        open: true,
                        status: error.response.status,
                        message: error.response.data.message
                    })
                })
        })
        .catch(error => {
            updateError({
                open: true,
                status: error.response.status,
                message: error.response.data.message
            })
        })
};

export const getCaregiverPatients = (caregiverId, updatePatientCaregiverList, updateError) => {
    api.get(PATIENT_PATH + "/caregiver?caregiverId=" + caregiverId)
        .then(response => {
            let patientCaregiverList: PatientCaregiver[] = [];
            response.data.forEach(patient => {
                let patientCaregiver: PatientCaregiver = {
                    medicationPlanList: [],
                    address: "",
                    birthDate: "",
                    email: "",
                    gender: "",
                    medicalRecord: "",
                    name: "",
                    patientId: 0,
                    userId: 0,
                    username: "",
                    recommendation: ""
                };
                patientCaregiver.address = patient.userDto.address;
                patientCaregiver.birthDate = patient.userDto.birthDate;
                patientCaregiver.email = patient.userDto.email;
                patientCaregiver.gender = patient.userDto.gender;
                patientCaregiver.userId = patient.userDto.id;
                patientCaregiver.medicalRecord = patient.medicalRecord;
                patientCaregiver.name = patient.userDto.personName;
                patientCaregiver.username = patient.userDto.username;
                patientCaregiver.patientId = patient.id;

                api.get(PATIENT_PATH + RECOMMENDATION_PATH + "?patientId=" + patient.id)
                    .then(response => {
                        patientCaregiver.recommendation = response.data.recommendation;
                    });

                api.get(MEDICATION_PLAN_PATH + "/patient?patientId=" + patient.id)
                    .then(response => {
                        let medicationPlanList: MedicationPacientCaregiverPlan[] = [];
                        response.data.forEach(medicationPlan => {
                            let medicationPacientCaregiver: MedicationPacientCaregiverPlan = {
                                id: 0,
                                doctorId: 0,
                                doctorName: "",
                                endDate: "",
                                interval: "",
                                hourInDay: "",
                                medicationList: [],
                                startDate: ""
                            };
                            medicationPacientCaregiver.doctorId = medicationPlan.doctorId;
                            medicationPacientCaregiver.endDate = medicationPlan.endDate;
                            medicationPacientCaregiver.interval = medicationPlan.interval;
                            medicationPacientCaregiver.startDate = medicationPlan.startDate;
                            medicationPacientCaregiver.hourInDay = medicationPlan.hourInDay;
                            medicationPacientCaregiver.id = medicationPlan.id;
                            api.get(DOCTOR_PATH + "?id=" + medicationPlan.doctorId)
                                .then(response => {
                                    medicationPacientCaregiver.doctorName = response.data.userDto.personName;
                                })
                                .catch(error => {
                                    updateError({
                                        open: true,
                                        status: error.response.status,
                                        message: error.response.data.message
                                    })
                                })
                            api.get(MEDICATION_PLAN_DRUGS_PATH + "/medication-plan?medicationPlanId=" + medicationPlan.id)
                                .then(response => {
                                    response.data.forEach(medicationPlanDrug => {
                                        api.get(MEDICATION_PATH + "?id=" + medicationPlanDrug.medicationId)
                                            .then(response => {
                                                let medication: MedicationPacientCaregiver = {
                                                    dosage: "",
                                                    name: "",
                                                    sideEffect: ""
                                                };
                                                medication.dosage = response.data.dosage;
                                                medication.name = response.data.name;
                                                medication.sideEffect = response.data.sideEffect;
                                                medicationPacientCaregiver.medicationList.push(medication)
                                            })
                                            .catch(error => {
                                                updateError({
                                                    open: true,
                                                    status: error.response.status,
                                                    message: error.response.data.message
                                                })
                                            })
                                    })
                                })
                                .catch(error => {
                                    updateError({
                                        open: true,
                                        status: error.response.status,
                                        message: error.response.data.message
                                    })
                                })
                            medicationPlanList.push(medicationPacientCaregiver);
                        });
                        patientCaregiver.medicationPlanList = medicationPlanList;
                    })
                    .catch(error => {
                        updateError({
                            open: true,
                            status: error.response.status,
                            message: error.response.data.message
                        })
                    })
                patientCaregiverList.push(patientCaregiver);
            });
            updatePatientCaregiverList(patientCaregiverList);
        }).catch(error => {
        updateError({
            open: true,
            status: error.response.status,
            message: error.response.data.message
        })
    })
};