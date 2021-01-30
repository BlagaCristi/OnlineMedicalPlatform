export const MedicationDoctorCss = (theme) => ({
    root: {
        width: '100%',
        marginTop: theme.spacing(3),
    },
    paper: {
        width: '100%',
    },
    table: {
        padding: theme.spacing(1),
    },
    tableWrapper: {
        overflowX: 'auto' as 'auto',
    },
    textField: {
        marginLeft: theme.spacing(1),
        marginRight: theme.spacing(1),
        width: '100%'
    },
    gridItem: {
        width: '30%',
        padding: theme.spacing(1),
    },
    textFieldGridItem: {
        width: '75%',
    },
    button: {
        padding: theme.spacing(1),
        margin: theme.spacing(1)
    },
    textFieldGridItemSelect: {
        paddingTop: theme.spacing(2),
        width: '75%',
    }
});