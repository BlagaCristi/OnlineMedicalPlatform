import {api} from "../../util/Api";
import {
    ACTIVITY_PATH,
    CAREGIVER_PATH,
    DOCTOR_PATH,
    MED_INTAKE_PATH,
    MEDICATION_PATH,
    MEDICATION_PLAN_DRUGS_PATH,
    MEDICATION_PLAN_PATH,
    PATIENT_PATH,
    RECOMMENDATION_PATH,
    USER_PATH,
    USER_ROLE_PATH
} from "../../util/GlobalConstants";
import {MedicationDoctorView, UserDoctorView} from "./DoctorState";
import {getGenderEnumPos, getRoleId} from "../../util/Converters";

export const getUserList = (dispatchAction, updateError) => {
    let userList: Array<UserDoctorView> = [];
    api.get(USER_PATH + "/all")
        .then(response => {
            response.data.forEach(user => {
                let adminUserView: UserDoctorView = {
                    caregiverId: 0, concreteUserid: 0, medicalRecord: "",
                    id: 0,
                    address: "",
                    birthDate: new Date(),
                    gender: "",
                    password: "",
                    personName: "",
                    roleName: "",
                    roleId: 0,
                    username: "",
                    email: ""
                };
                adminUserView.address = user.address;
                adminUserView.birthDate = new Date(user.birthDate);
                adminUserView.gender = user.gender;
                adminUserView.id = user.id;
                adminUserView.password = user.password;
                adminUserView.personName = user.personName;
                adminUserView.username = user.username;
                adminUserView.roleId = user.roleId;
                adminUserView.email = user.email;
                userList.push(adminUserView);
            });
            userList.forEach(adminUser => {
                api.get(USER_ROLE_PATH + "?id=" + adminUser.roleId)
                    .then(response => {
                        adminUser.roleName = "ROLE_" + response.data["roleName"];
                        if (adminUser.roleName === "ROLE_DOCTOR") {
                            api.get(DOCTOR_PATH + "/user?userId=" + adminUser.id)
                                .then(response => {
                                    adminUser.concreteUserid = response.data.id
                                })
                        }
                        if (adminUser.roleName === "ROLE_CAREGIVER") {
                            api.get(CAREGIVER_PATH + "/user?userId=" + adminUser.id)
                                .then(response => {
                                    adminUser.concreteUserid = response.data.id
                                })
                        }
                        if (adminUser.roleName === "ROLE_PATIENT") {
                            api.get(PATIENT_PATH + "/user?userId=" + adminUser.id)
                                .then(response => {
                                    adminUser.concreteUserid = response.data.id;
                                    adminUser.caregiverId = response.data.caregiverId;
                                    adminUser.medicalRecord = response.data.medicalRecord;
                                })
                        }
                    })
                    .catch(error => {
                        updateError({
                            open: true,
                            status: error.response.status,
                            message: error.response.data.message
                        })
                    })
            });
            dispatchAction(userList);
        })
        .catch(error => {
            updateError({
                open: true,
                status: error.response.status,
                message: error.response.data.message
            })
        })
};

export const createUser = (username, password, role, gender, address, email, birthDate, name, medicalRecord, caregiverId, dispatchAction, updateError) => {
    api.post(USER_PATH, {
        username: username,
        password: password,
        roleId: getRoleId(role),
        gender: getGenderEnumPos(gender),
        address: address,
        email: email,
        birthDate: birthDate,
        personName: name
    }).then(response => {
        let userId = response.data.id;
        if (role === 'DOCTOR') {
            api.post(DOCTOR_PATH, {
                userDto: {
                    id: userId
                }
            }).then(response => {
                getUserList(dispatchAction, updateError);
            })
                .catch(error => {
                    updateError({
                        open: true,
                        status: error.response.status,
                        message: error.response.data.message
                    })
                })
        }
        if (role === 'CAREGIVER') {
            api.post(CAREGIVER_PATH, {
                userDto: {
                    id: userId
                }
            }).then(response => {
                getUserList(dispatchAction, updateError);
            })
                .catch(error => {
                    updateError({
                        open: true,
                        status: error.response.status,
                        message: error.response.data.message
                    })
                })
        }
        if (role === 'PATIENT') {
            api.post(PATIENT_PATH, {
                userDto: {
                    id: userId
                },
                medicalRecord: medicalRecord,
                caregiverId: caregiverId
            }).then(response => {
                getUserList(dispatchAction, updateError);
            })
                .catch(error => {
                    updateError({
                        open: true,
                        status: error.response.status,
                        message: error.response.data.message
                    })
                })
        }
    })
};

export const updateUser = (username, gender, address, email, birthDate, name, id, concreteId, medicalRecord, role, dispatchAction, updateError) => {
    api.put(USER_PATH, {
        username: username,
        gender: getGenderEnumPos(gender),
        address: address,
        email: email,
        birthDate: birthDate,
        personName: name,
        id: id
    }).then((response) => {
        if (role === 'ROLE_PATIENT') {
            api.put(PATIENT_PATH, {
                medicalRecord: medicalRecord,
                id: concreteId
            }).then(() => {
                getUserList(dispatchAction, updateError);

            })
                .catch(error => {
                    updateError({
                        open: true,
                        status: error.response.status,
                        message: error.response.data.message
                    })
                })
        } else {
            getUserList(dispatchAction, updateError);
        }
    })
        .catch(error => {
            updateError({
                open: true,
                status: error.response.status,
                message: error.response.data.message
            })
        })

};

export const deleteUser = (userId, dispatchAction, updateError) => {
    api.delete(USER_PATH + "?id=" + userId)
        .then(() => {
            getUserList(dispatchAction, updateError);
        })
        .catch(error => {
            updateError({
                open: true,
                status: error.response.status,
                message: error.response.data.message
            })
        })
};

export const getMedicationList = (dispatchAction, updateError) => {
    api.get(MEDICATION_PATH + "/all")
        .then(response => {
            let medicationList: MedicationDoctorView[] = [];
            response.data.forEach(medication => {
                let medicationDoctorView: MedicationDoctorView = {dosage: "", id: 0, name: "", sideEffect: ""}
                medicationDoctorView.dosage = medication.dosage;
                medicationDoctorView.id = medication.id;
                medicationDoctorView.name = medication.name;
                medicationDoctorView.sideEffect = medication.sideEffect;
                medicationList.push(medicationDoctorView);
            });
            dispatchAction(medicationList);
        })
        .catch(error => {
            updateError({
                open: true,
                status: error.response.status,
                message: error.response.data.message
            })
        })
};

export const createMedication = (name, dosage, sideEffect, dispatchAction, updateError) => {
    api.post(MEDICATION_PATH, {
        name: name,
        dosage: dosage,
        sideEffect: sideEffect
    }).then(() => {
        getMedicationList(dispatchAction, updateError);
    })
        .catch(error => {
            updateError({
                open: true,
                status: error.response.status,
                message: error.response.data.message
            })
        })
};

export const updateMedication = (id, name, dosage, sideEffect, dispatchAction, updateError) => {
    api.put(MEDICATION_PATH, {
        name: name,
        dosage: dosage,
        sideEffect: sideEffect,
        id: id
    }).then(() => {
        getMedicationList(dispatchAction, updateError);
    })
        .catch(error => {
            updateError({
                open: true,
                status: error.response.status,
                message: error.response.data.message
            })
        })
};

export const deleteMedication = (id, dispatchAction, updateError) => {
    api.delete(MEDICATION_PATH + "?id=" + id)
        .then(() => {
            getMedicationList(dispatchAction, updateError);
        })
        .catch(error => {
            updateError({
                open: true,
                status: error.response.status,
                message: error.response.data.message
            })
        })
};

export const createMedicationPlan = (patientId, doctorId, interval, hour, minute, startDate, endDate, medicationIdList, updateError) => {

    let hourInDay: number = Number(hour) * 60 + Number(minute);

    api.post(MEDICATION_PLAN_PATH, {
        startDate: startDate,
        endDate: endDate,
        interval: interval,
        hourInDay: hourInDay,
        doctorId: doctorId,
        patientId: patientId
    })
        .then(response => {
            let medicationPlanId = response.data.id;
            medicationIdList.forEach(medicationId => {
                api.post(MEDICATION_PLAN_DRUGS_PATH, {
                    medicationPlanId: medicationPlanId,
                    medicationId: medicationId
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

export const getMedIntakeForPatient = (patientId, setMedicationIntakeArray) => {
    api.get(PATIENT_PATH + MED_INTAKE_PATH + "?patientId=" + patientId)
        .then(response => {
            setMedicationIntakeArray(response.data);
        });
};

export const getPatientActivity = (patientId, setPatientActivityArray) => {
    api.get(PATIENT_PATH + ACTIVITY_PATH + "?patientId=" + patientId)
        .then(response => {
            setPatientActivityArray(response.data);
        });
};

export const setPatientRecommendation = (patientId, recommendation) => {
    api.put(PATIENT_PATH + RECOMMENDATION_PATH + "?patientId=" + patientId, {
        "recommendation": recommendation
    })
};