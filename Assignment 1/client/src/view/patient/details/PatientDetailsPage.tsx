import {PatientDetails} from "../../../state/patient/PatientState";
import * as React from "react";
import {RootState} from "../../../state/RootState";
import {connect} from "react-redux";
import {Grid, Paper, Table, TableBody, TableCell, TableRow, Typography, withStyles} from "@material-ui/core";
import {PatientDetailsPageCss} from "./PatientDetailsPageCss";

interface PatientDetailsPageProps {
    classes: any;
    patientDetails: PatientDetails
}

const PatientDetailsPage: React.FunctionComponent<PatientDetailsPageProps> = ({classes, patientDetails}: PatientDetailsPageProps) => {
    return (
        <Grid
            direction="row"
            justify="center"
            alignItems="center"
            container
        >
            <Grid
                item
                className={classes.gridItem}
            >
                <Paper
                    elevation={12}
                >
                    <Table className={classes.table} size="small">
                        <TableBody>
                            <TableRow>
                                <TableCell component="th" scope="row">
                                    <Typography variant="button" display="block" gutterBottom align={"center"}>
                                        Name
                                    </Typography>
                                </TableCell>
                                <TableCell component="th" scope="row">
                                    <Typography variant="body2" gutterBottom align={"center"}>
                                        {patientDetails.name}
                                    </Typography>
                                </TableCell>
                            </TableRow>
                            <TableRow>
                                <TableCell component="th" scope="row">
                                    <Typography variant="button" display="block" gutterBottom align={"center"}>
                                        Username
                                    </Typography>
                                </TableCell>
                                <TableCell component="th" scope="row">
                                    <Typography variant="body2" gutterBottom align={"center"}>
                                        {patientDetails.username}
                                    </Typography>
                                </TableCell>
                            </TableRow>
                            <TableRow>
                                <TableCell component="th" scope="row">
                                    <Typography variant="button" display="block" gutterBottom align={"center"}>
                                        Email
                                    </Typography>
                                </TableCell>
                                <TableCell component="th" scope="row">
                                    <Typography variant="body2" gutterBottom align={"center"}>
                                        {patientDetails.email}
                                    </Typography>
                                </TableCell>
                            </TableRow>
                            <TableRow>
                                <TableCell component="th" scope="row">
                                    <Typography variant="button" display="block" gutterBottom align={"center"}>
                                        Medical record
                                    </Typography>
                                </TableCell>
                                <TableCell component="th" scope="row">
                                    <Typography variant="body2" gutterBottom align={"center"}>
                                        {patientDetails.medicalRecord}
                                    </Typography>
                                </TableCell>
                            </TableRow>
                            <TableRow>
                                <TableCell component="th" scope="row">
                                    <Typography variant="button" display="block" gutterBottom align={"center"}>
                                        Gender
                                    </Typography>
                                </TableCell>
                                <TableCell component="th" scope="row">
                                    <Typography variant="body2" gutterBottom align={"center"}>
                                        {patientDetails.gender}
                                    </Typography>
                                </TableCell>
                            </TableRow>
                            <TableRow>
                                <TableCell component="th" scope="row">
                                    <Typography variant="button" display="block" gutterBottom align={"center"}>
                                        Address
                                    </Typography>
                                </TableCell>
                                <TableCell component="th" scope="row">
                                    <Typography variant="body2" gutterBottom align={"center"}>
                                        {patientDetails.address}
                                    </Typography>
                                </TableCell>
                            </TableRow>
                            <TableRow>
                                <TableCell component="th" scope="row">
                                    <Typography variant="button" display="block" gutterBottom align={"center"}>
                                        Birth date
                                    </Typography>
                                </TableCell>
                                <TableCell component="th" scope="row">
                                    <Typography variant="body2" gutterBottom align={"center"}>
                                        {patientDetails.birthDate}
                                    </Typography>
                                </TableCell>
                            </TableRow>
                            <TableRow>
                                <TableCell component="th" scope="row">
                                    <Typography variant="button" display="block" gutterBottom align={"center"}>
                                        Caregiver id
                                    </Typography>
                                </TableCell>
                                <TableCell component="th" scope="row">
                                    <Typography variant="body2" gutterBottom align={"center"}>
                                        {patientDetails.caregiverId}
                                    </Typography>
                                </TableCell>
                            </TableRow>
                        </TableBody>
                    </Table>
                </Paper>
            </Grid>
        </Grid>
    )
};

// @ts-ignore
const mapDispatchToProps = dispatch => {
    return {}
};

const mapStateToProps = (state: RootState) => {
    return {
        patientDetails: state.app.patient.patientDetails
    };
};

export default connect(mapStateToProps, mapDispatchToProps)(withStyles(PatientDetailsPageCss)(PatientDetailsPage));