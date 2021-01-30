export interface MedicationPacientCaregiver {
    name: string,
    sideEffect: string,
    dosage: string
}

export interface MedicationPacientCaregiverPlan {
    startDate: string,
    endDate: string,
    interval: string,
    hourInDay: string,
    doctorId: number,
    doctorName: string,
    id: number,
    medicationList: MedicationPacientCaregiver[]
}

export interface PatientCaregiver {
    medicalRecord: string,
    address: string,
    birthDate: string,
    email: string,
    gender: string,
    userId: number,
    patientId: number,
    name: string,
    username: string,
    recommendation: string,
    medicationPlanList: MedicationPacientCaregiverPlan[]
}

export interface CaregiverState {
    caregiverId: number,
    patientCaregiverList: PatientCaregiver[],
    selectedPatientId: number | undefined,
    selectedMedicationPlanId: number | undefined
}

export const initialState: CaregiverState = {
    caregiverId: 0,
    patientCaregiverList: [],
    selectedPatientId: undefined,
    selectedMedicationPlanId: undefined
};