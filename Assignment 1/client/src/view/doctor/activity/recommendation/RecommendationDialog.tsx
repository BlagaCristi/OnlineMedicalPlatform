import * as React from "react";
import withStyles from "@material-ui/core/styles/withStyles";
import {Dialog, DialogTitle, TextareaAutosize} from "@material-ui/core";
import Typography from "@material-ui/core/Typography";
import {PlaylistPlay} from "@material-ui/icons";
import Button from "@material-ui/core/Button";
import {RecommendationDialogCss} from "./RecommendationDialogCss";
import {setPatientRecommendation} from "../../../../state/doctor/DoctorService";

interface RecommendationDialogProps {
    classes: any;
    open: boolean;
    onClose: () => void;
    patientId: number | undefined;
}

const RecommendationDialog: React.FunctionComponent<RecommendationDialogProps> = ({classes, onClose, open, patientId}: RecommendationDialogProps) => {

    const [recommendation, setRecommendation] = React.useState("");

    return (
        <Dialog
            aria-labelledby="simple-dialog-title"
            open={open}
            onClose={() => onClose()}
        >
            <DialogTitle
                id="simple-dialog-title"
            >
                <Typography
                    variant="h6"
                    align="center">
                    Add recommendation
                </Typography>
            </DialogTitle>
            <TextareaAutosize aria-label="empty textarea" placeholder=""
                              onChange={event => setRecommendation(event.target.value)}
            />
            <Button
                variant="contained"
                color="primary"
                className={classes.button}
                startIcon={<PlaylistPlay/>}
                onClick={() => {
                    setPatientRecommendation(patientId, recommendation);
                    onClose();
                }}
            >
                Add
            </Button>
        </Dialog>
    );
};

export default withStyles(RecommendationDialogCss)(RecommendationDialog);