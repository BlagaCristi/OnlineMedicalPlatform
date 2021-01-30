import * as React from "react";
import {Dialog, DialogTitle, Grid, withStyles} from "@material-ui/core";
import Typography from "@material-ui/core/Typography";
import {ErrorDialogCss} from "./ErrorDialogCss";

interface ErrorDialogProps {
    classes: any;
    open: boolean;
    onClose: () => void;
    status: string;
    message: string;
}

const ErrorDialog: React.FunctionComponent<ErrorDialogProps> = ({classes, open, onClose, status, message}: ErrorDialogProps) => {

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
                    Error with status: {status}
                </Typography>
            </DialogTitle>
            <Grid
                direction="column"
                justify="center"
                alignItems="center"
                container>
                <Grid
                    className={classes.dialog}
                    item>
                    <Typography variant="subtitle1" gutterBottom>
                        {message}
                    </Typography>
                </Grid>
            </Grid>
        </Dialog>
    );
};

export default withStyles(ErrorDialogCss)(ErrorDialog);