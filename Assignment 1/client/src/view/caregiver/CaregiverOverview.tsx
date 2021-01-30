import * as React from "react";
import {RootState} from "../../state/RootState";
import {connect} from "react-redux";
import {Grid, TableHead, withStyles} from "@material-ui/core";
import {CaregiverOverviewCss} from "./CaregiverOverviewCss";
import Paper from "@material-ui/core/Paper";
import Typography from "@material-ui/core/Typography";
import Table from "@material-ui/core/Table";
import TableRow from "@material-ui/core/TableRow";
import TableCell from "@material-ui/core/TableCell";
import TableBody from "@material-ui/core/TableBody";
import Checkbox from "@material-ui/core/Checkbox";
import TablePagination from "@material-ui/core/TablePagination";
import {
    MedicationPacientCaregiver,
    MedicationPacientCaregiverPlan,
    PatientCaregiver
} from "../../state/caregiver/CaregiverState";
import {CaregiverActionCreators} from "../../state/caregiver/CaregiverActions";

interface CaregiverOverviewProps {
    classes: any;
    caregiverId: number;
    patientCaregiverList: PatientCaregiver[],
    selectedPatientId: number | undefined,
    updateSelectedPatientId: (selectedPatientId: number | undefined) => void;
    updatePatientCaregiverList: (patientCaregiverList: PatientCaregiver[]) => void;
    updateSelectedMedicationPlanId: (selectedMedicationPlanId: number | undefined) => void;
    selectedMedicationPlanId: number | undefined;
}


const CaregiverOverview: React.FunctionComponent<CaregiverOverviewProps> = ({selectedMedicationPlanId, updateSelectedMedicationPlanId, caregiverId, classes, patientCaregiverList, selectedPatientId, updatePatientCaregiverList, updateSelectedPatientId}: CaregiverOverviewProps) => {
    const [rowsPerPage, setRowsPerPage] = React.useState(10);
    const [rowsPerPageMedicationPlan, setRowsPerPageMedicationPlan] = React.useState(10);
    const [rowsPerPageMedication, setRowsPerPageMedication] = React.useState(10);
    const [page, setPage] = React.useState(0);
    const [pageMedicationPlan, setPageMedicationPlan] = React.useState(0);
    const [pageMedication, setPageMedication] = React.useState(0);

    const handleChangePage = (event, newPage) => {
        setPage(newPage);
    };

    const handleChangePageMedicationPlan = (event, newPage) => {
        setPageMedicationPlan(newPage);
    };

    const handleChangePageMedication = (event, newPage) => {
        setPageMedication(newPage);
    };

    const handleChangeRowsPerPage = event => {
        setRowsPerPage(+event.target.value);
        setPage(0);
    };

    const handleChangeRowsPerPageMedicationPlan = event => {
        setRowsPerPageMedicationPlan(+event.target.value);
        setPageMedicationPlan(0);
    };

    const handleChangeRowsPerPageMedication = event => {
        setRowsPerPageMedication(+event.target.value);
        setPageMedication(0);
    };

    const handleClick = (patientCaregiver: PatientCaregiver) => {
        if (selectedPatientId && selectedPatientId === patientCaregiver.patientId) {
            updateSelectedPatientId(undefined);
            updateSelectedMedicationPlanId(undefined);
        } else {
            updateSelectedPatientId(patientCaregiver.patientId);
            updateSelectedMedicationPlanId(undefined);
        }
    };

    const handleClickMedicationPlan = (medicationPlan: MedicationPacientCaregiverPlan) => {
        if (selectedMedicationPlanId && selectedMedicationPlanId === medicationPlan.id) {
            updateSelectedMedicationPlanId(undefined);
        } else {
            updateSelectedMedicationPlanId(medicationPlan.id);
        }
    };

    let selectedMedicationPlanList: MedicationPacientCaregiverPlan[] | undefined =
        selectedPatientId ?
            patientCaregiverList.filter(patient => patient.patientId === selectedPatientId)[0].medicationPlanList :
            undefined;

    let selectedMedicationList: MedicationPacientCaregiver[] | undefined =
        selectedMedicationPlanId && selectedMedicationPlanList ?
            selectedMedicationPlanList.filter(medicationPlan => medicationPlan.id === selectedMedicationPlanId)[0].medicationList :
            undefined;

    return (
        <Grid
            direction="column"
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
                                Patient list
                            </Typography>
                            <Table
                                aria-labelledby="Users"
                                size={'medium'}
                            >
                                <TableHead>
                                    <TableRow>
                                        <TableCell align="center">Selected</TableCell>
                                        <TableCell align="center">User id</TableCell>
                                        <TableCell align="center">Patient id</TableCell>
                                        <TableCell align="center">Username</TableCell>
                                        <TableCell align="center">Name</TableCell>
                                        <TableCell align="center">Address</TableCell>
                                        <TableCell align="center">Birth date</TableCell>
                                        <TableCell align="center">Gender</TableCell>
                                        <TableCell align="center">Email</TableCell>
                                        <TableCell align="center">Medical record</TableCell>
                                        <TableCell align="center">Doctor recommendation</TableCell>
                                    </TableRow>
                                </TableHead>
                                <TableBody>
                                    {patientCaregiverList
                                        .slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage)
                                        .map((patientCaregiver) => {
                                            const isItemSelected: boolean = selectedPatientId !== undefined && patientCaregiver.patientId === selectedPatientId;
                                            return (
                                                <TableRow
                                                    hover
                                                    onClick={event => handleClick(patientCaregiver)}
                                                    role="checkbox"
                                                    aria-checked={isItemSelected}
                                                    tabIndex={-1}
                                                    key={patientCaregiver.patientId}
                                                    selected={isItemSelected}
                                                >
                                                    <TableCell padding="checkbox">
                                                        <Checkbox
                                                            checked={isItemSelected}
                                                            inputProps={{'aria-labelledby': "" + patientCaregiver.patientId}}
                                                        />
                                                    </TableCell>
                                                    <TableCell align="center">{patientCaregiver.userId}</TableCell>
                                                    <TableCell align="center">{patientCaregiver.patientId}</TableCell>
                                                    <TableCell align="center">{patientCaregiver.username}</TableCell>
                                                    <TableCell align="center">{patientCaregiver.name}</TableCell>
                                                    <TableCell align="center">{patientCaregiver.address}</TableCell>
                                                    <TableCell align="center">{patientCaregiver.birthDate}</TableCell>
                                                    <TableCell align="center">{patientCaregiver.gender}</TableCell>
                                                    <TableCell align="center">{patientCaregiver.email}</TableCell>
                                                    <TableCell
                                                        align="center">{patientCaregiver.medicalRecord}</TableCell>
                                                    <TableCell
                                                        align="center">{patientCaregiver.recommendation}</TableCell>
                                                </TableRow>
                                            );
                                        })}
                                </TableBody>
                            </Table>
                            <TablePagination
                                rowsPerPageOptions={[5, 10, 25]}
                                component="div"
                                count={patientCaregiverList.length}
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
                </Grid>
            </Grid>
            <Grid
                direction="row"
                justify="center"
                alignItems="center"
                container
            >
                {
                    selectedMedicationPlanList &&
                    <Grid
                        className={classes.medGridItem}
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
                                        Medication plan list
                                    </Typography>
                                    <Table
                                        aria-labelledby="Users"
                                        size={'medium'}
                                    >
                                        <TableHead>
                                            <TableRow>
                                                <TableCell align="center">Selected</TableCell>
                                                <TableCell align="center">Start date</TableCell>
                                                <TableCell align="center">End date</TableCell>
                                                <TableCell align="center">Interval</TableCell>
                                                <TableCell align="center">Time in day</TableCell>
                                                <TableCell align="center">Doctor id</TableCell>
                                                <TableCell align="center">Doctor name</TableCell>
                                            </TableRow>
                                        </TableHead>
                                        <TableBody>
                                            {selectedMedicationPlanList
                                                .slice(pageMedicationPlan * rowsPerPageMedicationPlan, rowsPerPageMedicationPlan * rowsPerPageMedicationPlan + rowsPerPageMedicationPlan)
                                                .map((medicationPlan) => {
                                                    const isItemSelected: boolean = selectedMedicationPlanId !== undefined && medicationPlan.id === selectedMedicationPlanId;
                                                    return (
                                                        <TableRow
                                                            hover
                                                            onClick={event => handleClickMedicationPlan(medicationPlan)}
                                                            role="checkbox"
                                                            aria-checked={isItemSelected}
                                                            tabIndex={-1}
                                                            key={medicationPlan.id}
                                                            selected={isItemSelected}
                                                        >
                                                            <TableCell padding="checkbox">
                                                                <Checkbox
                                                                    checked={isItemSelected}
                                                                    inputProps={{'aria-labelledby': "" + medicationPlan.id}}
                                                                />
                                                            </TableCell>
                                                            <TableCell
                                                                align="center">{medicationPlan.startDate}</TableCell>
                                                            <TableCell
                                                                align="center">{medicationPlan.endDate}</TableCell>
                                                            <TableCell
                                                                align="center">{medicationPlan.interval}</TableCell>
                                                            <TableCell
                                                                align="center">{Math.floor(Number(medicationPlan.hourInDay) / 60) + ":" + Number(medicationPlan.hourInDay) % 60}</TableCell>
                                                            <TableCell
                                                                align="center">{medicationPlan.doctorId}</TableCell>
                                                            <TableCell
                                                                align="center">{medicationPlan.doctorName}</TableCell>
                                                        </TableRow>
                                                    );
                                                })}
                                        </TableBody>
                                    </Table>
                                    <TablePagination
                                        rowsPerPageOptions={[5, 10, 25]}
                                        component="div"
                                        count={selectedMedicationPlanList.length}
                                        rowsPerPage={rowsPerPageMedicationPlan}
                                        page={pageMedicationPlan}
                                        backIconButtonProps={{
                                            'aria-label': 'previous page',
                                        }}
                                        nextIconButtonProps={{
                                            'aria-label': 'next page',
                                        }}
                                        onChangePage={handleChangePageMedicationPlan}
                                        onChangeRowsPerPage={handleChangeRowsPerPageMedicationPlan}
                                    />
                                </Paper>
                            </Grid>
                        </Grid>
                    </Grid>
                }
                {
                    selectedMedicationList &&
                    <Grid
                        className={classes.medGridItem}
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
                                        Medication list
                                    </Typography>
                                    <Table
                                        aria-labelledby="Users"
                                        size={'medium'}
                                    >
                                        <TableHead>
                                            <TableRow>
                                                <TableCell align="center">Name</TableCell>
                                                <TableCell align="center">Dosage</TableCell>
                                                <TableCell align="center">Side effect</TableCell>
                                            </TableRow>
                                        </TableHead>
                                        <TableBody>
                                            {selectedMedicationList
                                                .slice(pageMedication * rowsPerPageMedication, rowsPerPageMedication * rowsPerPageMedication + rowsPerPageMedication)
                                                .map((medication) => {
                                                    return (
                                                        <TableRow
                                                            hover
                                                            role="checkbox"
                                                            tabIndex={-1}
                                                            key={medication.name}
                                                        >
                                                            <TableCell align="center">{medication.name}</TableCell>
                                                            <TableCell align="center">{medication.dosage}</TableCell>
                                                            <TableCell
                                                                align="center">{medication.sideEffect}</TableCell>
                                                        </TableRow>
                                                    );
                                                })}
                                        </TableBody>
                                    </Table>
                                    <TablePagination
                                        rowsPerPageOptions={[5, 10, 25]}
                                        component="div"
                                        count={selectedMedicationList.length}
                                        rowsPerPage={rowsPerPageMedication}
                                        page={pageMedication}
                                        backIconButtonProps={{
                                            'aria-label': 'previous page',
                                        }}
                                        nextIconButtonProps={{
                                            'aria-label': 'next page',
                                        }}
                                        onChangePage={handleChangePageMedication}
                                        onChangeRowsPerPage={handleChangeRowsPerPageMedication}
                                    />
                                </Paper>
                            </Grid>
                        </Grid>
                    </Grid>
                }
            </Grid>
        </Grid>
    );
};


// @ts-ignore
const mapDispatchToProps = dispatch => {
    return {
        updatePatientCaregiverList: (patientCaregiverList: PatientCaregiver[]) => {
            dispatch(CaregiverActionCreators.updatePatientCaregiverList({
                patientCaregiverList: patientCaregiverList
            }))
        },
        updateSelectedPatientId: (selectedPatientId: number | undefined) => {
            dispatch(CaregiverActionCreators.updateSelectedPatientId({
                selectedPatientId: selectedPatientId
            }))
        },
        updateSelectedMedicationPlanId: (selectedMedicationPlanId: number | undefined) => {
            dispatch(CaregiverActionCreators.updateSelectedMedicationPlanId({
                selectedMedicationPlanId: selectedMedicationPlanId
            }))
        }
    }
};

const mapStateToProps = (state: RootState) => {
    return {
        caregiverId: state.app.caregiver.caregiverId,
        patientCaregiverList: state.app.caregiver.patientCaregiverList,
        selectedPatientId: state.app.caregiver.selectedPatientId,
        selectedMedicationPlanId: state.app.caregiver.selectedMedicationPlanId
    };
};

export default connect(mapStateToProps, mapDispatchToProps)(withStyles(CaregiverOverviewCss)(CaregiverOverview));