import * as React from "react";
import {RootState} from "../../state/RootState";
import {connect} from "react-redux";
import {Grid, TableHead, withStyles} from "@material-ui/core";
import {PatientOverviewCss} from "./PatientOverviewCss";
import {PatientActionCreators} from "../../state/patient/PatientActions";
import {MedicationPacientPlan} from "../../state/patient/PatientState";
import Paper from "@material-ui/core/Paper";
import Typography from "@material-ui/core/Typography";
import Table from "@material-ui/core/Table";
import TableRow from "@material-ui/core/TableRow";
import TableCell from "@material-ui/core/TableCell";
import TableBody from "@material-ui/core/TableBody";
import Checkbox from "@material-ui/core/Checkbox";
import TablePagination from "@material-ui/core/TablePagination";

interface PatientOverviewProps {
    classes: any;
    medicationPlanList: MedicationPacientPlan[];
    selectedMedicationPlanId: number | undefined;
    updateSelectedMedicationPlanId: (selectedMedicationPlanId: number | undefined) => void;
}

const PatientOverview: React.FunctionComponent<PatientOverviewProps> = ({classes, medicationPlanList, selectedMedicationPlanId, updateSelectedMedicationPlanId}: PatientOverviewProps) => {
    const [rowsPerPage, setRowsPerPage] = React.useState(10);
    const [rowsPerPageMedication, setRowsPerPageMedication] = React.useState(10);
    const [page, setPage] = React.useState(0);
    const [pageMedication, setPageMedication] = React.useState(0);

    const handleChangePage = (event, newPage) => {
        setPage(newPage);
    };

    const handleChangePageMedication = (event, newPage) => {
        setPageMedication(newPage);
    };

    const handleChangeRowsPerPage = event => {
        setRowsPerPage(+event.target.value);
        setPage(0);
    };

    const handleChangeRowsPerPageMedication = event => {
        setRowsPerPageMedication(+event.target.value);
        setPageMedication(0);
    };

    const handleClick = (medicationPlan: MedicationPacientPlan) => {
        if (selectedMedicationPlanId && selectedMedicationPlanId === medicationPlan.id) {
            updateSelectedMedicationPlanId(undefined);
        } else {
            updateSelectedMedicationPlanId(medicationPlan.id);
        }
    };

    let selectedMedicationPlan: MedicationPacientPlan | undefined = selectedMedicationPlanId ?
        medicationPlanList.filter(medicationPlan => medicationPlan.id === selectedMedicationPlanId)[0]
        : undefined;

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
                <Paper
                    className={classes.paper}
                    elevation={12}
                >
                    <Typography
                        className={classes.table}
                        variant="h6"
                        align="center">
                        Medication plans
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
                                <TableCell align="center">Hour in day</TableCell>
                                <TableCell align="center">Doctor id</TableCell>
                                <TableCell align="center">Doctor name</TableCell>
                            </TableRow>
                        </TableHead>
                        <TableBody>
                            {medicationPlanList
                                .slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage)
                                .map((medicationPlan) => {
                                    const isItemSelected: boolean = selectedMedicationPlanId !== undefined && medicationPlan.id === selectedMedicationPlanId;
                                    return (
                                        <TableRow
                                            hover
                                            onClick={event => handleClick(medicationPlan)}
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
                                            <TableCell align="center">{medicationPlan.startDate}</TableCell>
                                            <TableCell align="center">{medicationPlan.endDate}</TableCell>
                                            <TableCell align="center">{medicationPlan.interval}</TableCell>
                                            <TableCell
                                                align="center">{Math.floor(Number(medicationPlan.hourInDay) / 60) + ":" + Number(medicationPlan.hourInDay) % 60}</TableCell>
                                            <TableCell align="center">{medicationPlan.doctorId}</TableCell>
                                            <TableCell align="center">{medicationPlan.doctorName}</TableCell>
                                        </TableRow>
                                    );
                                })}
                        </TableBody>
                    </Table>
                    <TablePagination
                        rowsPerPageOptions={[5, 10, 25]}
                        component="div"
                        count={medicationPlanList.length}
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
            {
                selectedMedicationPlan &&
                <Grid
                    className={classes.gridItem}
                    item>
                    <Paper
                        className={classes.paper}
                        elevation={12}
                    >
                        <Typography
                            className={classes.table}
                            variant="h6"
                            align="center">
                            Medication
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
                                {selectedMedicationPlan.medicationList
                                    .slice(pageMedication * rowsPerPageMedication, pageMedication * rowsPerPageMedication + rowsPerPageMedication)
                                    .map((medication) => {
                                        return (
                                            <TableRow
                                                hover
                                                tabIndex={-1}
                                                key={medication.name}
                                            >
                                                <TableCell align="center">{medication.name}</TableCell>
                                                <TableCell align="center">{medication.dosage}</TableCell>
                                                <TableCell align="center">{medication.sideEffect}</TableCell>
                                            </TableRow>
                                        );
                                    })}
                            </TableBody>
                        </Table>
                        <TablePagination
                            rowsPerPageOptions={[5, 10, 25]}
                            component="div"
                            count={selectedMedicationPlan.medicationList.length}
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
            }
        </Grid>
    );
};


// @ts-ignore
const mapDispatchToProps = dispatch => {
    return {
        updateSelectedMedicationPlanId: (selectedMedicationPlanId: number | undefined) => {
            dispatch(PatientActionCreators.updateSelectedMedicationPlanId({
                selectedMedicationPlanId: selectedMedicationPlanId
            }))
        },
    }
};

const mapStateToProps = (state: RootState) => {
    return {
        medicationPlanList: state.app.patient.medicationPlanList,
        selectedMedicationPlanId: state.app.patient.selectedMedicationPlanId
    };
};

export default connect(mapStateToProps, mapDispatchToProps)(withStyles(PatientOverviewCss)(PatientOverview));