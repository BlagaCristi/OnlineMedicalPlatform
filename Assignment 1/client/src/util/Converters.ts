export const getRoleId = (role) => {
    if (role === 'CAREGIVER')
        return 104;
    if (role === 'PATIENT')
        return 103;
    if (role === 'DOCTOR')
        return 102;
};

export const getGenderEnumPos = (gender) => {
    if (gender === 'MALE')
        return 0;
    else
        return 1;
};