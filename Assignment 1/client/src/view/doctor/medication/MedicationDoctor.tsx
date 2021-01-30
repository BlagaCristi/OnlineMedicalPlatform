import {MedicationDoctorView} from "../../../state/doctor/DoctorState";
import * as React from "react";
import {RootState} from "../../../state/RootState";
import {connect} from "react-redux";
import {Grid, TableHead, withStyles} from "@material-ui/core";
import {MedicationDoctorCss} from "./MedicationDoctorCss";
import {DoctorActionCreators} from "../../../state/doctor/DoctorActions";
import Paper from "@material-ui/core/Paper";
import Typography from "@material-ui/core/Typography";
import Table from "@material-ui/core/Table";
import TableRow from "@material-ui/core/TableRow";
import TableCell from "@material-ui/core/TableCell";
import TableBody from "@material-ui/core/TableBody";
import Checkbox from "@material-ui/core/Checkbox";
import TablePagination from "@material-ui/core/TablePagination";
import Button from "@material-ui/core/Button";
import {ArrowUpward, Done} from "@material-ui/icons";
import TextField from "@material-ui/core/TextField";
import DeleteIcon from "@material-ui/core/SvgIcon/SvgIcon";
import MedicationCreateDialog from "./dialog/MedicationCreateDialog";
import {deleteMedication, updateMedication} from "../../../state/doctor/DoctorService";
import {ErrorUser} from "../../../state/user/UserState";
import {UserActionCreators} from "../../../state/user/UserActions";

interface MedicationDoctorProps {
    classes: any;
    medicationList: Array<MedicationDoctorView>;
    updateSelectedMedicationId: (selectedMedicationId: number | undefined) => void;
    selectedMedicationId: number | undefined;
    updateMedicationList: (medicationList: Array<MedicationDoctorView>) => void;
    updateError: (error: ErrorUser) => void;
}

const MedicationDoctor: React.FunctionComponent<MedicationDoctorProps> = ({updateError, classes, medicationList, selectedMedicationId, updateMedicationList, updateSelectedMedicationId}: MedicationDoctorProps) => {
    const [rowsPerPage, setRowsPerPage] = React.useState(10);
    const [page, setPage] = React.useState(0);
    const [open, setOpen] = React.useState(false);

    const handleChangePage = (event, newPage) => {
        setPage(newPage);
    };

    const handleChangeRowsPerPage = event => {
        setRowsPerPage(+event.target.value);
        setPage(0);
    };

    const handleClick = (medication: MedicationDoctorView) => {
        if (selectedMedicationId && selectedMedicationId === medication.id) {
            updateSelectedMedicationId(undefined);
        } else {
            updateSelectedMedicationId(medication.id);
            setValues({
                name: medication.name,
                sideEffect: medication.sideEffect,
                dosage: medication.dosage,
                id: "" + medication.id
            });
        }
    };

    let selectedMedication: MedicationDoctorView | undefined= selectedMedicationId !== undefined ?
        medicationList.filter(medication => medication.id === selectedMedicationId)[0]
        : undefined;

    const [values, setValues] = React.useState({
        name: selectedMedication ? selectedMedication.name : '',
        sideEffect: selectedMedication ? selectedMedication.sideEffect : '',
        dosage: selectedMedication ? selectedMedication.dosage : '',
        id: selectedMedication ? selectedMedication.id : ''
    });

    const handleChange = name => event => {
        setValues({...values, [name]: event.target.value});
    };

    const handleCloseDialog = () => {
        setOpen(false);
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
                                Medication
                            </Typography>
                            <Table
                                aria-labelledby="Users"
                                size={'medium'}
                            >
                                <TableHead>
                                    <TableRow>
                                        <TableCell align="center">Selected</TableCell>
                                        <TableCell align="center">Id</TableCell>
                                        <TableCell align="center">Name</TableCell>
                                        <TableCell align="center">Side effect</TableCell>
                                        <TableCell align="center">Dosage</TableCell>
                                    </TableRow>
                                </TableHead>
                                <TableBody>
                                    {medicationList
                                        .slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage)
                                        .map((medication) => {
                                            const isItemSelected: boolean = selectedMedicationId !== undefined && medication.id === selectedMedicationId;
                                            return (
                                                <TableRow
                                                    hover
                                                    onClick={event => handleClick(medication)}
                                                    role="checkbox"
                                                    aria-checked={isItemSelected}
                                                    tabIndex={-1}
                                                    key={medication.id}
                                                    selected={isItemSelected}
                                                >
                                                    <TableCell padding="checkbox">
                                                        <Checkbox
                                                            checked={isItemSelected}
                                                            inputProps={{'aria-labelledby': "" + medication.id}}
                                                        />
                                                    </TableCell>
                                                    <TableCell align="center">{medication.id}</TableCell>
                                                    <TableCell align="center">{medication.name}</TableCell>
                                                    <TableCell align="center">{medication.sideEffect}</TableCell>
                                                    <TableCell align="center">{medication.dosage}</TableCell>
                                                </TableRow>
                                            );
                                        })}
                                </TableBody>
                            </Table>
                            <TablePagination
                                rowsPerPageOptions={[5, 10, 25]}
                                component="div"
                                count={medicationList.length}
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
                            Create medication
                        </Button>
                    </Grid>
                </Grid>
            </Grid>
            {selectedMedicationId !== undefined && (
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
                                    Medication details
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
                                            deleteMedication(selectedMedicationId, updateMedicationList, updateError);
                                            updateSelectedMedicationId(undefined);
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
                                            values.name === '' ||
                                            values.sideEffect === '' ||
                                            values.dosage === ''
                                        }
                                        onClick={() => {
                                            updateMedication(values.id, values.name, values.dosage, values.sideEffect, updateMedicationList, updateError);
                                            updateSelectedMedicationId(undefined);
                                        }}
                                    >
                                        Update
                                    </Button>
                                </Grid>
                            </Grid>
                        </Grid>
                    </Paper>
                </Grid>
            )}
            <MedicationCreateDialog
                open={open}
                onClose={handleCloseDialog}
            />
        </Grid>
    )
};

// @ts-ignore
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
    return {
        medicationList: state.app.doctor.medicationList,
        selectedMedicationId: state.app.doctor.selectedMedicationId
    };
};

export default connect(mapStateToProps, mapDispatchToProps)(withStyles(MedicationDoctorCss)(MedicationDoctor));

