export interface MedicationPacient {
    name: string,
    sideEffect: string,
    dosage: string
}

export interface MedicationPacientPlan {
    startDate: string,
    endDate: string,
    interval: string,
    hourInDay: string,
    doctorId: number,
    doctorName: string,
    id: number,
    medicationList: MedicationPacient[]
}

export interface PatientDetails {
    medicalRecord: string,
    username: string,
    name: string,
    birthDate: string,
    gender: string,
    address: string,
    email: string,
    caregiverId: number
}

export interface PatientState {
    patientDetails: PatientDetails,
    medicationPlanList: MedicationPacientPlan[],
    selectedMedicationPlanId: number | undefined,
    patientId: number
}

export const initialState: PatientState = {
    medicationPlanList: [],
    patientDetails: {
        medicalRecord: "",
        username: "",
        name: "",
        birthDate: "",
        gender: "",
        address: "",
        email: "",
        caregiverId: 0
    },
    patientId: 0,
    selectedMedicationPlanId: undefined
};