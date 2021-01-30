export interface UserDoctorView {
    username: string,
    password: string,
    personName: string,
    birthDate: Date,
    gender: string,
    address: string,
    roleId: number,
    roleName: string,
    id: number,
    email: string,
    concreteUserid: number,
    caregiverId: number,
    medicalRecord: string
}

export interface MedicationDoctorView {
    id: number,
    sideEffect: string,
    dosage: string,
    name: string
}

export interface DoctorState {
    userList: Array<UserDoctorView>,
    selectedUserId: number | undefined,
    medicationList: MedicationDoctorView[],
    selectedMedicationId: number | undefined
}

export interface MedicationIntakePatient {
    id: number,
    medicationName: string,
    isTaken: string,
    patientId: number,
    intakeDate: string
}

export interface PatientActivity {
    activity: string,
    startTime: string,
    endTime: string,
    isNormal: string
}

export const initialState: DoctorState = {
    userList: [],
    selectedUserId: undefined,
    medicationList: [],
    selectedMedicationId: undefined
};