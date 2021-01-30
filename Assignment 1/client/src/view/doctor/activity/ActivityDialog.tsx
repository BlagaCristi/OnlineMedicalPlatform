import * as React from "react";
import withStyles from "@material-ui/core/styles/withStyles";
import {PatientActivity} from "../../../state/doctor/DoctorState";
import {Dialog, DialogTitle, List, ListItem, ListItemIcon, ListItemText, Paper} from "@material-ui/core";
import Typography from "@material-ui/core/Typography";
import {ActivityDialogCss} from "./ActivityDialogCss";
import {VictoryPie, VictorySharedEvents} from 'victory';
import {ErrorOutline, PlaylistPlay} from "@material-ui/icons";
import Button from "@material-ui/core/Button";
import RecommendationDialog from "./recommendation/RecommendationDialog";

interface ActivityDialogProps {
    classes: any;
    open: boolean;
    onClose: () => void;
    patientId: number | undefined;
    activityArray: PatientActivity[]
}

interface IDataItem {
    month: string,
    sale: number,
    total: number,
}

const ActivityDialog: React.FunctionComponent<ActivityDialogProps> = ({classes, onClose, open, patientId, activityArray}: ActivityDialogProps) => {

    const [openRec, setOpen] = React.useState(false);

    let activites = {};
    let shouldAddRecommendation = false;
    activityArray.forEach(activity => {
        activites[activity.activity] = 0
    });

    activityArray.forEach(activity => {
        activites[activity.activity] +=
            (Date.parse(activity.endTime) - Date.parse(activity.startTime)) / 60000;
        if (activity.isNormal === 'false') {
            shouldAddRecommendation = true;
        }
    });

    let activityData: any = [];
    let labels: any = [];

    for (let key in activites) {
        labels.push(key);
        activityData.push(
            {
                'x': key,
                'y': activites[key]
            }
        )
    }

    console.log(activityData);

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
                    Activity report
                </Typography>
            </DialogTitle>
            <svg viewBox="0 0 450 350"
                 className={classes.dialog}
            >
                <VictorySharedEvents
                    className={classes.dialog}
                    events={[{
                        childName: ["pie", "bar"],
                        target: "data",
                        eventHandlers: {
                            onMouseOver: () => {
                                return [{
                                    childName: ["pie", "bar"],
                                    mutation: (props) => {
                                        return {
                                            style: Object.assign({}, props.style, {fill: "tomato"})
                                        };
                                    }
                                }];
                            },
                            onMouseOut: () => {
                                return [{
                                    childName: ["pie", "bar"],
                                    mutation: () => {
                                        return null;
                                    }
                                }];
                            }
                        }
                    }]}
                >
                    {/*<g transform={"translate(150, 50)"}>*/}
                    {/*    <VictoryBar name="bar"*/}
                    {/*                width={300}*/}
                    {/*                standalone={false}*/}
                    {/*                style={{*/}
                    {/*                    data: {width: 20},*/}
                    {/*                    labels: {fontSize: 10}*/}
                    {/*                }}*/}
                    {/*                data={activityData}*/}
                    {/*                labels={labels}*/}
                    {/*                labelComponent={<VictoryLabel y={290}/>}*/}
                    {/*    />*/}
                    {/*</g>*/}
                    <g transform={"translate(0, -50)"}>
                        <VictoryPie name="pie"
                                    width={400}
                                    standalone={false}
                                    style={{labels: {fontSize: 10, padding: 10}}}
                                    data={activityData}
                        />
                    </g>
                </VictorySharedEvents>
            </svg>
            <Paper style={{maxHeight: 200, overflow: 'auto'}}>
                <List component="nav" aria-label="main mailbox folders"
                      className={classes.dialog}
                >
                    {
                        activityArray.map(activity => {
                            if (activity.isNormal !== 'true') {
                                return (
                                    <ListItem
                                        button
                                        className={classes.dialog}
                                    >
                                        <ListItemIcon>
                                            <ErrorOutline/>
                                        </ListItemIcon>
                                        <ListItemText
                                            className={classes.dialog}
                                            primary={
                                                "Activity:  " + activity.activity
                                            }
                                            secondary={
                                                "Activity starting at: " + activity.startTime + "and ending at: " + activity.endTime
                                            }
                                        />
                                    </ListItem>
                                )
                            } else {
                                return null;
                            }
                        })
                    }
                </List>
            </Paper>
            <Button
                variant="contained"
                color="primary"
                className={classes.button}
                startIcon={<PlaylistPlay/>}
                disabled={!shouldAddRecommendation}
                onClick={() => {
                    setOpen(true);
                }}
            >
                Add recommendation
            </Button>
            <RecommendationDialog
                open={openRec}
                onClose={() => setOpen(false)}
                patientId={patientId}
            />
        </Dialog>
    );
};

export default withStyles(ActivityDialogCss)(ActivityDialog);