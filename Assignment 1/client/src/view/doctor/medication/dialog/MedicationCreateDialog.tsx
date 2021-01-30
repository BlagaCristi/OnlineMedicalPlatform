import * as React from "react";
import withStyles from "@material-ui/core/styles/withStyles";
import {Dialog, DialogTitle, Grid} from "@material-ui/core";
import Typography from "@material-ui/core/Typography";
import TextField from "@material-ui/core/TextField";
import Button from "@material-ui/core/Button";
import {connect} from "react-redux";
import {MedicationDoctorView} from "../../../../state/doctor/DoctorState";
import {MedicationCreateDialogCss} from "./MedicationCreateDialogCss";
import {RootState} from "../../../../state/RootState";
import {DoctorActionCreators} from "../../../../state/doctor/DoctorActions";
import {createMedication, createUser} from "../../../../state/doctor/DoctorService";
import {ErrorUser} from "../../../../state/user/UserState";
import {UserActionCreators} from "../../../../state/user/UserActions";

interface MedicationCreateDialogProps {
    classes: any;
    open: boolean;
    onClose: () => void;
    updateMedicationList: (medicationList: Array<MedicationDoctorView>) => void;
    updateSelectedMedicationId: (selectedMedicationId: number | undefined) => void;
    updateError: (error: ErrorUser) => void;
}

const MedicationCreateDialog: React.FunctionComponent<MedicationCreateDialogProps> = ({updateError, classes, open, onClose, updateMedicationList, updateSelectedMedicationId}: MedicationCreateDialogProps) => {

    const [values, setValues] = React.useState({
        name: '',
        dosage: '',
        sideEffect: ''
    });

    const handleChange = name => event => {
        setValues({...values, [name]: event.target.value});
    };


    return (
        <Dialog
            aria-labelledby="simple-dialog-title"
            open={open}
            onClose={() => onClose()}
            className={classes.dialog}
        >
            <DialogTitle
                id="simple-dialog-title"
            >
                <Typography
                    variant="h6"
                    align="center">
                    Create medication
                </Typography>
            </DialogTitle>
            <Grid
                direction="column"
                justify="center"
                alignItems="center"
                container>
                <Grid
                    className={classes.textFieldGridItem}
                    item>
                    <TextField
                        id="standard-name"
                        label="Name"
                        className={classes.textField}
                        value={values.name}
                        onChange={handleChange('name')}
                        margin="normal"
                        error={values.name === ''}
                    />
                </Grid>
                <Grid
                    className={classes.textFieldGridItem}
                    item>
                    <TextField
                        id="standard-name"
                        label="Side effect"
                        className={classes.textField}
                        value={values.sideEffect}
                        onChange={handleChange('sideEffect')}
                        margin="normal"
                        error={values.sideEffect === ''}
                    />
                </Grid>
                <Grid
                    className={classes.textFieldGridItem}
                    item>
                    <TextField
                        id="standard-name"
                        label="Dosage"
                        className={classes.textField}
                        value={values.dosage}
                        onChange={handleChange('dosage')}
                        margin="normal"
                        error={values.dosage === ''}
                    />
                </Grid>
                <Grid
                    item
                >
                    <Button
                        variant="contained"
                        color="primary"
                        className={classes.button}
                        disabled={
                            values.dosage === '' ||
                            values.name === '' ||
                            values.sideEffect === ''
                        }
                        onClick={() => {
                            updateSelectedMedicationId(undefined);
                            createMedication(values.name, values.dosage, values.sideEffect, updateMedicationList, updateError);
                            setValues({
                                name: '',
                                dosage: '',
                                sideEffect: ''
                            });
                            onClose();
                        }}
                    >
                        Create
                    </Button>
                </Grid>
            </Grid>
        </Dialog>
    );
};

const mapDispatchToProps = dispatch => {
    return {
        updateMedicationList: (medicationList: Array<MedicationDoctorView>) => {
            dispatch(DoctorActionCreators.updateMedicationList({
                medicationList: medicationList
            }))
        },
        updateSelectedMedicationId: (selectedMedicationId: number | undefined) => {
            dispatch(DoctorActionCreators.updateSelectedMedicationId({
                selectedMedicationId: selectedMedicationId
            }))
        },
        updateError: (error: ErrorUser) => {
            dispatch(UserActionCreators.updateError({
                error: error
            }))
        }
    }
};

const mapStateToProps = (state: RootState) => {
    return {};
};

export default connect(mapStateToProps, mapDispatchToProps)(withStyles(MedicationCreateDialogCss)(MedicationCreateDialog));