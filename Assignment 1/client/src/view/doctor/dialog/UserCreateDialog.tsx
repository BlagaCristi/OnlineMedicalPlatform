import * as React from "react";
import withStyles from "@material-ui/core/styles/withStyles";
import {UserCreateDialogCss} from "./UserCreateDialogCss";
import {Dialog, DialogTitle, FormControl, Grid, InputLabel, MenuItem, Select} from "@material-ui/core";
import Typography from "@material-ui/core/Typography";
import TextField from "@material-ui/core/TextField";
import {dateValidator, validateEmail} from "../../../util/Validator";
import Button from "@material-ui/core/Button";
import {UserDoctorView} from "../../../state/doctor/DoctorState";
import {DoctorActionCreators} from "../../../state/doctor/DoctorActions";
import {RootState} from "../../../state/RootState";
import {connect} from "react-redux";
import {createUser} from "../../../state/doctor/DoctorService";
import {ErrorUser} from "../../../state/user/UserState";
import {UserActionCreators} from "../../../state/user/UserActions";

interface UserCreateDialogProps {
    classes: any;
    open: boolean;
    onClose: () => void;
    updateUserList: (userList: Array<UserDoctorView>) => void;
    updateSelectedUserId: (selectedUserId: number | undefined) => void;
    updateError: (error: ErrorUser) => void;
}

const UserCreateDialog: React.FunctionComponent<UserCreateDialogProps> = ({updateError, classes, open, onClose, updateUserList, updateSelectedUserId}: UserCreateDialogProps) => {

    const [values, setValues] = React.useState({
        username: '',
        name: '',
        birthDate: new Date().toISOString().split("T")[0],
        gender: '',
        address: '',
        role: '',
        email: '',
        password: '',
        medicalRecord: '',
        caregiverId: ''
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
                    Create user
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
                        label="Username"
                        className={classes.textField}
                        value={values.username}
                        onChange={handleChange('username')}
                        margin="normal"
                        error={values.username === ''}
                    />
                </Grid>
                <Grid
                    className={classes.textFieldGridItem}
                    item>
                    <TextField
                        id="standard-name"
                        label="Password"
                        className={classes.textField}
                        value={values.password}
                        onChange={handleChange('password')}
                        type="password"
                        margin="normal"
                        error={values.password === ''}
                    />
                </Grid>
                <Grid
                    className={classes.textFieldGridItem}
                    item>
                    <FormControl className={classes.textField}>
                        <InputLabel htmlFor="gender-simple">Gender</InputLabel>
                        <Select
                            value={values.gender}
                            onChange={handleChange('gender')}
                            inputProps={{
                                name: 'gender',
                                id: 'gender-simple',
                            }}
                            error={values.gender === ''}
                        >
                            <MenuItem value={"MALE"}>Male</MenuItem>
                            <MenuItem value={"FEMALE"}>Female</MenuItem>
                        </Select>
                    </FormControl>
                </Grid>
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
                        label="Address"
                        className={classes.textField}
                        value={values.address}
                        onChange={handleChange('address')}
                        margin="normal"
                        error={values.address === ''}
                    />
                </Grid>
                <Grid
                    className={classes.textFieldGridItem}
                    item>
                    <TextField
                        id="standard-name"
                        label="Email"
                        className={classes.textField}
                        value={values.email}
                        onChange={handleChange('email')}
                        margin="normal"
                        error={!validateEmail(values.email)}
                    />
                </Grid>
                <Grid
                    className={classes.textFieldGridItem}
                    item>
                    <TextField
                        id="date"
                        label="BirthDate"
                        type="date"
                        defaultValue={values.birthDate}
                        className={classes.textField}
                        error={!dateValidator(values.birthDate)}
                        onChange={handleChange('birthDate')}
                    />
                </Grid>
                <Grid
                    className={classes.textFieldGridItem}
                    item>
                    <TextField
                        id="standard-name"
                        label="Medical record"
                        className={classes.textField}
                        value={values.medicalRecord}
                        onChange={handleChange('medicalRecord')}
                        margin="normal"
                        disabled={values.role !== 'PATIENT'}
                    />
                </Grid>
                <Grid
                    className={classes.textFieldGridItem}
                    item>
                    <TextField
                        id="standard-name"
                        label="Caregiver Id"
                        className={classes.textField}
                        value={values.caregiverId}
                        onChange={handleChange('caregiverId')}
                        margin="normal"
                        disabled={values.role !== 'PATIENT'}
                        error={values.role === 'PATIENT' && values.caregiverId === ''}
                    />
                </Grid>
                <Grid
                    className={classes.textFieldGridItem}
                    item>
                    <FormControl className={classes.textField}>
                        <InputLabel htmlFor="role-simple">Role</InputLabel>
                        <Select
                            value={values.role}
                            onChange={handleChange('role')}
                            inputProps={{
                                name: 'role',
                                id: 'role-simple',
                            }}
                            error={values.role === ''}
                        >
                            <MenuItem value={"ADMIN"}>Admin</MenuItem>
                            <MenuItem value={"DOCTOR"}>Doctor</MenuItem>
                            <MenuItem value={"CAREGIVER"}>Caregiver</MenuItem>
                            <MenuItem value={"PATIENT"}>Patient</MenuItem>
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
                            values.username === '' ||
                            values.password === '' ||
                            values.address === '' ||
                            values.role === '' ||
                            values.gender === '' ||
                            values.name === '' ||
                            !validateEmail(values.email) ||
                            !dateValidator(values.birthDate) ||
                            (values.role === 'PATIENT' && values.caregiverId === '')
                        }
                        onClick={() => {
                            updateSelectedUserId(undefined);
                            createUser(values.username, values.password, values.role, values.gender, values.address, values.email, values.birthDate, values.name, values.medicalRecord, values.caregiverId, updateUserList, updateError);
                            setValues({
                                username: '',
                                name: '',
                                birthDate: new Date().toISOString().split("T")[0],
                                gender: '',
                                address: '',
                                role: '',
                                email: '',
                                password: '',
                                medicalRecord: '',
                                caregiverId: ''
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
        updateUserList: (userList: Array<UserDoctorView>) => {
            dispatch(DoctorActionCreators.updateUserList({
                userList: userList
            }))
        },
        updateSelectedUserId: (selectedUserId: number | undefined) => {
            dispatch(DoctorActionCreators.updateSelectedUserId({
                selectedUserId: selectedUserId
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

export default connect(mapStateToProps, mapDispatchToProps)(withStyles(UserCreateDialogCss)(UserCreateDialog));