import * as React from "react";
import withStyles from "@material-ui/core/styles/withStyles";
import {
    Checkbox,
    Dialog,
    DialogTitle,
    FormControl,
    Grid,
    Input,
    InputLabel,
    ListItemText,
    MenuItem,
    Select
} from "@material-ui/core";
import Typography from "@material-ui/core/Typography";
import TextField from "@material-ui/core/TextField";
import Button from "@material-ui/core/Button";
import {connect} from "react-redux";
import {RootState} from "../../../state/RootState";
import {MedicationPlanCreateDialogCss} from "./MedicationPlanCreateDialogCss";
import {MedicationDoctorView, UserDoctorView} from "../../../state/doctor/DoctorState";
import {dateValidator} from "../../../util/Validator";
import {createMedicationPlan} from "../../../state/doctor/DoctorService";
import {ErrorUser} from "../../../state/user/UserState";
import {UserActionCreators} from "../../../state/user/UserActions";

interface MedicationPlanCreateDialogProps {
    classes: any;
    open: boolean;
    onClose: () => void;
    selectedUserId: number | undefined;
    userList: UserDoctorView[];
    username: string;
    medicationList: MedicationDoctorView[];
    updateError: (error: ErrorUser) => void;
}


const MedicationPlanCreateDialog: React.FunctionComponent<MedicationPlanCreateDialogProps> = ({updateError, classes, open, onClose, selectedUserId, userList, username, medicationList}: MedicationPlanCreateDialogProps) => {

    let doctorId = open ? userList.filter(user => user.username === username)[0].concreteUserid : "";
    let doctorName = open ? userList.filter(user => user.username === username)[0].personName : "";
    let patientName = open ? userList.filter(user => user.id === selectedUserId)[0].personName : "";
    let patientId = open ? userList.filter(user => user.id === selectedUserId)[0].concreteUserid : "";

    const [values, setValues] = React.useState({
        startDate: new Date().toISOString().split("T")[0],
        endDate: new Date().toISOString().split("T")[0],
        interval: '',
        hour: '0',
        minute: '0',
        medications: []
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
                    Create medication plan
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
                        label="Patient id"
                        className={classes.textField}
                        value={patientId}
                        margin="normal"
                        disabled={true}
                    />
                </Grid>
                <Grid
                    className={classes.textFieldGridItem}
                    item>
                    <TextField
                        id="standard-name"
                        label="Patient name"
                        className={classes.textField}
                        value={patientName}
                        margin="normal"
                        disabled={true}
                    />
                </Grid>
                <Grid
                    className={classes.textFieldGridItem}
                    item>
                    <TextField
                        id="standard-name"
                        label="Doctor id"
                        className={classes.textField}
                        value={doctorId}
                        margin="normal"
                        disabled={true}
                    />
                </Grid>
                <Grid
                    className={classes.textFieldGridItem}
                    item>
                    <TextField
                        id="standard-name"
                        label="Doctor name"
                        className={classes.textField}
                        value={doctorName}
                        margin="normal"
                        disabled={true}
                    />
                </Grid>
                <Grid
                    className={classes.textFieldGridItem}
                    item>
                    <TextField
                        id="standard-name"
                        label="Interval"
                        className={classes.textField}
                        value={values.interval}
                        margin="normal"
                        onChange={handleChange('interval')}
                        error={values.interval === ''}
                    />
                </Grid>
                <Grid
                    className={classes.textFieldGridItem}
                    item>
                    <TextField
                        id="standard-name"
                        label="Hour"
                        className={classes.textField}
                        value={values.hour}
                        margin="normal"
                        onChange={handleChange('hour')}
                        error={values.hour.match(/^[0-9]+$/) == null}
                    />
                </Grid>
                <Grid
                    className={classes.textFieldGridItem}
                    item>
                    <TextField
                        id="standard-name"
                        label="Minute"
                        className={classes.textField}
                        value={values.minute}
                        margin="normal"
                        onChange={handleChange('minute')}
                        error={values.minute.match(/^[0-9]+$/) == null}
                    />
                </Grid>
                <Grid
                    className={classes.textFieldGridItem}
                    item>
                    <TextField
                        id="date"
                        label="Start date"
                        type="date"
                        value={values.startDate}
                        className={classes.textField}
                        error={!dateValidator(values.startDate)}
                        onChange={handleChange('startDate')}
                    />
                </Grid>
                <Grid
                    className={classes.textFieldGridItem}
                    item>
                    <TextField
                        id="date"
                        label="End date"
                        type="date"
                        value={values.endDate}
                        className={classes.textField}
                        error={!dateValidator(values.endDate)}
                        onChange={handleChange('endDate')}
                    />
                </Grid>
                <Grid
                    className={classes.textFieldGridItem}
                    item>
                    <FormControl
                        className={classes.formControl}
                    >
                        <InputLabel
                            htmlFor="select-multiple-checkbox"
                            className={classes.label}
                        >
                            Medications
                        </InputLabel>
                        <Select
                            multiple
                            value={values.medications}
                            className={classes.textField}
                            onChange={handleChange('medications')}
                            input={<Input id="select-multiple-checkbox"/>}
                            renderValue={selected => values.medications.join(', ')}
                            // MenuProps={MenuProps}
                        >
                            {medicationList.map(medication => (
                                <MenuItem key={medication.id} value={medication.name}>
                                    <Checkbox
                                        checked={values.medications.filter(id => id === medication.name).length > 0}/>
                                    <ListItemText primary={medication.name}/>
                                </MenuItem>
                            ))}
                        </Select>
                    </FormControl>
                </Grid>
                <Grid
                    item
                >
                    <Button
                        variant="contained"
                        color="primary"
                        className={classes.button}
                        disabled={
                            values.interval === '' ||
                            values.medications.length === 0 ||
                            values.hour.match(/^[0-9]+$/) == null ||
                            values.minute.match(/^[0-9]+$/) == null
                        }
                        onClick={() => {
                            createMedicationPlan(patientId, doctorId, values.interval, values.hour, values.minute, values.startDate, values.endDate,
                                values.medications.map(medication =>
                                    medicationList.filter(medicationListElem => medicationListElem.name === medication)[0].id), updateError);
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
        updateError: (error: ErrorUser) => {
            dispatch(UserActionCreators.updateError({
                error: error
            }))
        }
    }
};

const mapStateToProps = (state: RootState) => {
    return {
        selectedUserId: state.app.doctor.selectedUserId,
        userList: state.app.doctor.userList,
        username: state.app.user.username,
        medicationList: state.app.doctor.medicationList
    };
};

export default connect(mapStateToProps, mapDispatchToProps)(withStyles(MedicationPlanCreateDialogCss)(MedicationPlanCreateDialog));