import * as React from "react";
import withStyles from "@material-ui/core/styles/withStyles";
import {DoctorOverviewCss} from "./DoctorOverviewCss";
import {UserDoctorView} from "../../state/doctor/DoctorState";
import {DoctorActionCreators} from "../../state/doctor/DoctorActions";
import {RootState} from "../../state/RootState";
import {connect} from "react-redux";
import {FormControl, Grid, InputLabel, MenuItem, Select, TableHead} from "@material-ui/core";
import Paper from "@material-ui/core/Paper";
import Typography from "@material-ui/core/Typography";
import Table from "@material-ui/core/Table";
import TableBody from "@material-ui/core/TableBody";
import TableRow from "@material-ui/core/TableRow";
import TableCell from "@material-ui/core/TableCell";
import Checkbox from "@material-ui/core/Checkbox";
import TablePagination from "@material-ui/core/TablePagination";
import TextField from "@material-ui/core/TextField";
import {dateValidator, validateEmail} from "../../util/Validator";
import Button from "@material-ui/core/Button";
import DeleteIcon from '@material-ui/icons/Delete';
import {ArrowUpward, Done, PlaylistPlay} from "@material-ui/icons";
import UserCreateDialog from "./dialog/UserCreateDialog";
import {deleteUser, getMedIntakeForPatient, getPatientActivity, updateUser} from "../../state/doctor/DoctorService";
import MedicationPlanCreateDialog from "./plan/MedicationPlanCreateDialog";
import {ErrorUser} from "../../state/user/UserState";
import {UserActionCreators} from "../../state/user/UserActions";
import MedicationIntakeDialog from "./medication-intake/MedicationIntakeDialog";
import ActivityDialog from "./activity/ActivityDialog";

interface DoctorOverviewProps {
    classes: any;
    userList: Array<UserDoctorView>;
    updateSelectedUserId: (selectedUserId: number | undefined) => void;
    selectedUserId: number | undefined;
    updateUserList: (userList: Array<UserDoctorView>) => void;
    updateError: (error: ErrorUser) => void;
}


const DoctorOverview: React.FunctionComponent<DoctorOverviewProps> = ({classes, userList, selectedUserId, updateSelectedUserId, updateUserList, updateError}: DoctorOverviewProps) => {
    const [rowsPerPage, setRowsPerPage] = React.useState(10);
    const [page, setPage] = React.useState(0);
    const [open, setOpen] = React.useState(false);
    const [openMedicationPlanCreate, setOpenMedicationPlanCreate] = React.useState(false);
    const [openMedicationIntake, setOpenMedicationIntake] = React.useState(false);
    const [openPatientActivity, setOpenPatientActivity] = React.useState(false);
    const [patientId, setPatientId] = React.useState(0);
    const [medicationIntakeArray, setMedicationIntakeArray] = React.useState([]);
    const [patientActivityArray, setPatientActivityArray] = React.useState([]);

    const handleChangePage = (event, newPage) => {
        setPage(newPage);
    };

    const handleChangeRowsPerPage = event => {
        setRowsPerPage(+event.target.value);
        setPage(0);
    };

    const handleClick = (user: UserDoctorView) => {
        if (selectedUserId && selectedUserId === user.id) {
            updateSelectedUserId(undefined);
        } else {
            updateSelectedUserId(user.id);
            setPatientId(user.concreteUserid);
            setValues({
                username: user.username,
                name: user.personName,
                birthDate: user.birthDate.toISOString().split("T")[0],
                gender: user.gender,
                address: user.address,
                role: user.roleName,
                id: user.id,
                email: user.email,
                concreteId: "" + user.concreteUserid,
                caregiverId: "" + user.caregiverId,
                medicalRecord: user.medicalRecord
            });
        }
    };

    let selectedUser: UserDoctorView | undefined = selectedUserId !== undefined ?
        userList.filter(user => user.id === selectedUserId)[0]
        : undefined;

    const [values, setValues] = React.useState({
        username: selectedUser ? selectedUser.username : '',
        name: selectedUser ? selectedUser.personName : '',
        birthDate: selectedUser ? selectedUser.birthDate.toISOString().split("T")[0] : '',
        gender: selectedUser ? selectedUser.gender : '',
        address: selectedUser ? selectedUser.address : '',
        role: selectedUser ? selectedUser.roleName : '',
        id: selectedUser ? selectedUser.id : 0,
        email: selectedUser ? selectedUser.email : '',
        concreteId: selectedUser ? selectedUser.concreteUserid : '',
        caregiverId: selectedUser ? selectedUser.caregiverId : '',
        medicalRecord: selectedUser ? selectedUser.medicalRecord : ''
    });

    const handleChange = name => event => {
        setValues({...values, [name]: event.target.value});
    };

    const handleCloseDialog = () => {
        setOpen(false);
    };

    const handleCloseDialogMedicationPlan = () => {
        setOpenMedicationPlanCreate(false);
    };

    const handleCloseDialogMedicationIntake = () => {
        setOpenMedicationIntake(false);
    };

    const handleCloseDialogPatientActivity = () => {
        setOpenPatientActivity(false);
    };

    return (
        <Grid
            direction="row"
            justify="center"
            alignItems="center"
            container
        >
            <Grid
                className={classes.gridItem}
                item>
                <Grid
                    direction="column"
                    justify="center"
                    alignItems="center"
                    container>
                    <Grid
                        item>
                        <Paper
                            className={classes.paper}
                            elevation={12}
                        >
                            <Typography
                                className={classes.table}
                                variant="h6"
                                align="center">
                                Users
                            </Typography>
                            <Table
                                aria-labelledby="Users"
                                size={'medium'}
                            >
                                <TableHead>
                                    <TableRow>
                                        <TableCell align="center">Selected</TableCell>
                                        <TableCell align="center">Username</TableCell>
                                        <TableCell align="center">Name</TableCell>
                                        <TableCell align="center">Birth date</TableCell>
                                        <TableCell align="center">Gender</TableCell>
                                        <TableCell align="center">Address</TableCell>
                                        <TableCell align="center">Email</TableCell>
                                        <TableCell align="center">Role</TableCell>
                                    </TableRow>
                                </TableHead>
                                <TableBody>
                                    {userList
                                        .slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage)
                                        .map((user) => {
                                            const isItemSelected: boolean = selectedUserId !== undefined && user.id === selectedUserId;
                                            return (
                                                <TableRow
                                                    hover
                                                    onClick={event => handleClick(user)}
                                                    role="checkbox"
                                                    aria-checked={isItemSelected}
                                                    tabIndex={-1}
                                                    key={user.username}
                                                    selected={isItemSelected}
                                                >
                                                    <TableCell padding="checkbox">
                                                        <Checkbox
                                                            checked={isItemSelected}
                                                            inputProps={{'aria-labelledby': user.username}}
                                                        />
                                                    </TableCell>
                                                    <TableCell align="center">{user.username}</TableCell>
                                                    <TableCell align="center">{user.personName}</TableCell>
                                                    <TableCell
                                                        align="center">{user.birthDate.toISOString().split("T")[0]}</TableCell>
                                                    <TableCell align="center">{user.gender}</TableCell>
                                                    <TableCell align="center">{user.address}</TableCell>
                                                    <TableCell align="center">{user.email}</TableCell>
                                                    <TableCell align="center">{user.roleName.split("_")[1]}</TableCell>
                                                </TableRow>
                                            );
                                        })}
                                </TableBody>
                            </Table>
                            <TablePagination
                                rowsPerPageOptions={[5, 10, 25]}
                                component="div"
                                count={userList.length}
                                rowsPerPage={rowsPerPage}
                                page={page}
                                backIconButtonProps={{
                                    'aria-label': 'previous page',
                                }}
                                nextIconButtonProps={{
                                    'aria-label': 'next page',
                                }}
                                onChangePage={handleChangePage}
                                onChangeRowsPerPage={handleChangeRowsPerPage}
                            />
                        </Paper>
                    </Grid>
                    <Grid
                        item>
                        <Button
                            variant="contained"
                            color="primary"
                            className={classes.button}
                            startIcon={<Done/>}
                            onClick={() => setOpen(true)}
                        >
                            Create user
                        </Button>
                    </Grid>
                </Grid>
            </Grid>
            {selectedUserId !== undefined && (
                <Grid
                    className={classes.gridItem}
                    item>
                    <Paper
                        elevation={12}
                    >
                        <Grid
                            direction="column"
                            justify="center"
                            alignItems="center"
                            container>
                            <Grid
                                item
                            >
                                <Typography
                                    variant="h6"
                                    align="center">
                                    {
                                        values.role === "ROLE_PATIENT" ?
                                            "Patient details" :
                                            (values.role === "ROLE_DOCTOR" ?
                                                "Doctor details" :
                                                "Caregiver details")
                                    }
                                </Typography>
                            </Grid>
                            <Grid
                                className={classes.textFieldGridItem}
                                item>
                                <TextField
                                    id="standard-name"
                                    label="Id"
                                    className={classes.textField}
                                    value={values.id}
                                    margin="normal"
                                    disabled={true}
                                />
                            </Grid>
                            <Grid
                                className={classes.textFieldGridItem}
                                item>
                                <TextField
                                    id="standard-name"
                                    label="ConcreteId"
                                    className={classes.textField}
                                    value={values.concreteId}
                                    margin="normal"
                                    disabled={true}
                                />
                            </Grid>
                            {
                                values.role === "ROLE_PATIENT" &&
                                <Grid
                                    className={classes.textFieldGridItem}
                                    item>
                                    <TextField
                                        id="standard-name"
                                        label="Caregiver id"
                                        className={classes.textField}
                                        value={values.caregiverId}
                                        margin="normal"
                                        disabled={true}
                                    />
                                </Grid>
                            }
                            {
                                values.role === "ROLE_PATIENT" &&
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
                                    />
                                </Grid>
                            }
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
                                    value={values.birthDate}
                                    className={classes.textField}
                                    error={!dateValidator(values.birthDate)}
                                    onChange={handleChange('birthDate')}
                                />
                            </Grid>
                            <Grid
                                className={classes.textFieldGridItemSelect}
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
                                    label="Role"
                                    className={classes.textField}
                                    value={values.role.split("_")[1]}
                                    margin="normal"
                                    disabled={true}
                                />
                            </Grid>
                            <Grid
                                direction="row"
                                justify="center"
                                alignItems="center"
                                container>
                                <Grid
                                    item
                                >
                                    <Button
                                        variant="contained"
                                        color="primary"
                                        className={classes.button}
                                        startIcon={<DeleteIcon/>}
                                        onClick={() => {
                                            deleteUser(selectedUserId, updateUserList, updateError);
                                            updateSelectedUserId(undefined);
                                        }}
                                    >
                                        Delete
                                    </Button>
                                </Grid>
                                <Grid
                                    item
                                >
                                    <Button
                                        variant="contained"
                                        color="primary"
                                        className={classes.button}
                                        startIcon={<ArrowUpward/>}
                                        disabled={
                                            values.username === '' ||
                                            values.name === '' ||
                                            values.address === '' ||
                                            !validateEmail(values.email) ||
                                            !dateValidator(values.birthDate) ||
                                            values.gender === ''
                                        }
                                        onClick={() => {
                                            updateUser(values.username, values.gender, values.address, values.email, values.birthDate, values.name, values.id, values.concreteId, values.medicalRecord, values.role, updateUserList, updateError);
                                            updateSelectedUserId(undefined);
                                        }}
                                    >
                                        Update
                                    </Button>
                                </Grid>
                                {
                                    values.role === "ROLE_PATIENT" &&
                                    <Grid
                                        item
                                    >
                                        <Button
                                            variant="contained"
                                            color="primary"
                                            className={classes.button}
                                            startIcon={<ArrowUpward/>}
                                            onClick={() => {
                                                setOpenMedicationPlanCreate(true);
                                            }}
                                        >
                                            Create medPlan
                                        </Button>
                                    </Grid>
                                }
                                {
                                    values.role === "ROLE_PATIENT" &&
                                    <Grid
                                        item
                                    >
                                        <Button
                                            variant="contained"
                                            color="primary"
                                            className={classes.button}
                                            startIcon={<PlaylistPlay/>}
                                            onClick={() => {
                                                setOpenMedicationIntake(true);
                                                getMedIntakeForPatient(patientId, setMedicationIntakeArray);
                                            }}
                                        >
                                            Med Intake
                                        </Button>
                                    </Grid>
                                }
                                {
                                values.role === "ROLE_PATIENT" &&
                                <Grid
                                    item
                                >
                                    <Button
                                        variant="contained"
                                        color="primary"
                                        className={classes.button}
                                        startIcon={<PlaylistPlay/>}
                                        onClick={() => {
                                            setOpenPatientActivity(true);
                                            getPatientActivity(patientId, setPatientActivityArray);
                                        }}
                                    >
                                        Activity Report
                                    </Button>
                                </Grid>
                                }
                            </Grid>
                        </Grid>
                    </Paper>
                </Grid>
            )}
            <UserCreateDialog
                open={open}
                onClose={handleCloseDialog}
            />
            <MedicationPlanCreateDialog
                open={openMedicationPlanCreate}
                onClose={handleCloseDialogMedicationPlan}
            />
            <MedicationIntakeDialog
                open={openMedicationIntake}
                onClose={handleCloseDialogMedicationIntake}
                patientId={patientId}
                medicationIntakeArray={medicationIntakeArray}
            />
            <ActivityDialog
                open={openPatientActivity}
                onClose={handleCloseDialogPatientActivity}
                patientId={patientId}
                activityArray={patientActivityArray}
            />
        </Grid>
    )
};

// @ts-ignore
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
    return {
        userList: state.app.doctor.userList,
        selectedUserId: state.app.doctor.selectedUserId
    };
};

export default connect(mapStateToProps, mapDispatchToProps)(withStyles(DoctorOverviewCss)(DoctorOverview));
