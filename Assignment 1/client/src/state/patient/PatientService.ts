import {api} from "../../util/Api";
import {
    DOCTOR_PATH,
    MEDICATION_PATH,
    MEDICATION_PLAN_DRUGS_PATH,
    MEDICATION_PLAN_PATH,
    PATIENT_PATH,
    USER_PATH
} from "../../util/GlobalConstants";
import {MedicationPacient, MedicationPacientPlan, PatientDetails} from "./PatientState";

export const initPatient = (username, updatePatientId, updatePatientDetails, updateMedicationPlanList, updateError) => {
    api.get(USER_PATH + "/username?username=" + username)
        .then(response => {
            let userId = response.data.id;
            api.get(PATIENT_PATH + "/user?userId=" + userId)
                .then(response => {
                    updatePatientId(response.data.id);
                    let patientDetails: PatientDetails = {
                        address: "",
                        birthDate: "",
                        caregiverId: 0,
                        email: "",
                        gender: "",
                        medicalRecord: "",
                        name: "",
                        username: ""
                    };
                    patientDetails.address = response.data.userDto.address;
                    patientDetails.birthDate = response.data.userDto.birthDate;
                    patientDetails.email = response.data.userDto.email;
                    patientDetails.gender = response.data.userDto.gender;
                    patientDetails.name = response.data.userDto.personName;
                    patientDetails.username = response.data.userDto.username;
                    patientDetails.medicalRecord = response.data.medicalRecord;
                    patientDetails.caregiverId = response.data.caregiverId;
                    updatePatientDetails(patientDetails);

                    getPatientMedicationPlan(response.data.id, updateMedicationPlanList, updateError)
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

export const getPatientMedicationPlan = (patientId, updateMedicationPlanList, updateError) => {
    api.get(MEDICATION_PLAN_PATH + "/patient?patientId=" + patientId)
        .then(response => {
            let medicationPlanList: MedicationPacientPlan[] = [];
            response.data.forEach(medicationPlan => {
                let medicationPatientPlan: MedicationPacientPlan = {
                    id: 0,
                    doctorId: 0,
                    doctorName: "",
                    endDate: "",
                    interval: "",
                    hourInDay: "",
                    medicationList: [],
                    startDate: ""
                };
                medicationPatientPlan.doctorId = medicationPlan.doctorId;
                medicationPatientPlan.endDate = medicationPlan.endDate;
                medicationPatientPlan.interval = medicationPlan.interval;
                medicationPatientPlan.startDate = medicationPlan.startDate;
                medicationPatientPlan.hourInDay = medicationPlan.hourInDay;
                medicationPatientPlan.id = medicationPlan.id;
                api.get(DOCTOR_PATH + "?id=" + medicationPlan.doctorId)
                    .then(response => {
                        medicationPatientPlan.doctorName = response.data.userDto.personName;
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
                                    let medication: MedicationPacient = {dosage: "", name: "", sideEffect: ""};
                                    medication.dosage = response.data.dosage;
                                    medication.name = response.data.name;
                                    medication.sideEffect = response.data.sideEffect;
                                    medicationPatientPlan.medicationList.push(medication)
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
                medicationPlanList.push(medicationPatientPlan);
            });
            updateMedicationPlanList(medicationPlanList);
        })
        .catch(error => {
            updateError({
                open: true,
                status: error.response.status,
                message: error.response.data.message
            })
        })
};