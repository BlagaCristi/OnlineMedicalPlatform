import * as React from "react";
import withStyles from "@material-ui/core/styles/withStyles";
import {MedicationIntakeDialogCss} from "./MedicationIntakeDialogCss";
import {MedicationIntakePatient} from "../../../state/doctor/DoctorState";
import {Dialog, DialogTitle, List, ListItem, ListItemIcon, ListItemText} from "@material-ui/core";
import Typography from "@material-ui/core/Typography";
import {ArrowUpward, Check, ErrorOutline} from "@material-ui/icons";

interface MedicationIntakeDialogProps {
    classes: any;
    open: boolean;
    onClose: () => void;
    patientId: number | undefined;
    medicationIntakeArray: MedicationIntakePatient[]
}

const MedicationIntakeDialog: React.FunctionComponent<MedicationIntakeDialogProps> = ({classes, onClose, open, patientId, medicationIntakeArray}: MedicationIntakeDialogProps) => {

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
                    Medication intake
                </Typography>
                <List component="nav" aria-label="main mailbox folders"
                      className={classes.dialog}
                >
                    {
                        medicationIntakeArray.map(medicationIntake => {
                            return (
                                <ListItem
                                    button
                                    className={classes.dialog}
                                >
                                    <ListItemIcon>
                                        {
                                            medicationIntake.isTaken == "true" ?
                                                <Check/>
                                                : <ErrorOutline/>
                                        }
                                    </ListItemIcon>
                                    <ListItemText
                                        className={classes.dialog}
                                        primary={
                                            "Medication " + medicationIntake.medicationName
                                        }
                                        secondary={
                                            medicationIntake.isTaken == "true" ? " Taken" : " Not taken"
                                                + " in " + medicationIntake.intakeDate.split(" ")[0]
                                        }
                                    />
                                </ListItem>
                            )
                        })
                    }
                </List>

            </DialogTitle>
        </Dialog>
    );
};

export default withStyles(MedicationIntakeDialogCss)(MedicationIntakeDialog);