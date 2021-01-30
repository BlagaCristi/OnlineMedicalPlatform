import React from 'react';
import {ErrorUser, UserState} from "./state/user/UserState";
import {UserActionCreators} from "./state/user/UserActions";
import {RootState} from "./state/RootState";
import {connect} from "react-redux";
import withStyles from "@material-ui/core/styles/withStyles";
import {AppCss} from "./AppCss";
import {CAREGIVER_ROLE, DOCTOR_ROLE, PATIENT_ROLE, TOKEN} from "./util/GlobalConstants";
import {api} from "./util/Api";
import {handleLogout, initPostLoginBehavior} from "./util/LoginUtil";
import {ConnectedRouter} from "connected-react-router";
import {Paper} from "@material-ui/core";
import Tabs from "@material-ui/core/Tabs";
import {Link, Route, Switch} from 'react-router-dom'
import Login from "./view/login/Login";
import Tab from "@material-ui/core/Tab";
import {Favorite, List, PowerSettingsNew, SettingsOverscan} from "@material-ui/icons";
import Button from "@material-ui/core/Button";
import PrivateRoute from "./view/route/PrivateRoute";
import AdminOverview from "./view/doctor/DoctorOverview";
import {MedicationDoctorView, UserDoctorView} from "./state/doctor/DoctorState";
import {DoctorActionCreators} from "./state/doctor/DoctorActions";
import MedicationDoctor from "./view/doctor/medication/MedicationDoctor";
import CaregiverOverview from "./view/caregiver/CaregiverOverview";
import {CaregiverActionCreators} from "./state/caregiver/CaregiverActions";
import {PatientCaregiver} from "./state/caregiver/CaregiverState";
import PatientOverview from "./view/patient/PatientOverview";
import {PatientActionCreators} from "./state/patient/PatientActions";
import {MedicationPacientPlan, PatientDetails} from "./state/patient/PatientState";
import PatientDetailsPage from "./view/patient/details/PatientDetailsPage";
import ErrorDialog from "./view/error/ErrorDialog";


interface AppProps {
    history: any,
    user: UserState,
    addUser: (username: string, role: string, isLoggedIn: boolean) => void;
    updateSelectedTab: (selectedTab: number) => void;
    updateUserList: (userList: Array<UserDoctorView>) => void;
    updateMedicationList: (medicationList: Array<MedicationDoctorView>) => void;
    updateCaregiverId: (caregiverId: number) => void;
    updatePatientCaregiverList: (patientCaregiverList: PatientCaregiver[]) => void;
    updateSelectedMedicationPlanId: (selectedMedicationPlanId: number | undefined) => void;
    updateMedicationPlanList: (medicationPlanList: MedicationPacientPlan[]) => void;
    updatePatientDetails: (patientDetails: PatientDetails) => void;
    updatePatientId: (patientId: number) => void;
    updateError: (error: ErrorUser) => void;
}

class App extends React.Component<AppProps> {

    componentDidMount(): void {
        let token = localStorage.getItem(TOKEN);
        api.setHeader("Authorization", "Basic " + token)
        api.get("/login")
            .then(response => {
                initPostLoginBehavior(response, this.props.addUser, this.props.history, this.props.updateUserList, this.props.updateMedicationList, this.props.updateCaregiverId, this.props.updatePatientCaregiverList, this.props.updatePatientDetails, this.props.updatePatientId, this.props.updateSelectedMedicationPlanId, this.props.updateMedicationPlanList, this.props.updateError);
            })
            .catch(() => this.props.history.push("/login"));
    }

    handleChange = (event: React.ChangeEvent<{}>, newTab: string) => {
        this.props.updateSelectedTab(parseInt(newTab));
    };

    render() {
        return (
            <ConnectedRouter history={this.props.history}>
                <div>
                    <Paper square>
                        <Tabs
                            value={this.props.user.selectedTab}
                            variant="standard"
                            indicatorColor="secondary"
                            textColor="secondary"
                            onChange={this.handleChange}
                        >
                            {this.props.user.role && this.props.user.role === DOCTOR_ROLE &&
                            <Tab
                                icon={<SettingsOverscan/>}
                                label="Doctor Overview"
                                component={Link}
                                to="/doctor-overview"/>
                            }
                            {this.props.user.role && this.props.user.role === DOCTOR_ROLE &&
                            <Tab
                                icon={<Favorite/>}
                                label="Medication"
                                component={Link}
                                to="/doctor/medication"/>
                            }
                            {this.props.user.role && this.props.user.role === CAREGIVER_ROLE &&
                            <Tab
                                icon={<SettingsOverscan/>}
                                label="Caregiver Overview"
                                component={Link}
                                to="/caregiver-overview"/>
                            }
                            {this.props.user.role && this.props.user.role === PATIENT_ROLE &&
                            <Tab
                                icon={<SettingsOverscan/>}
                                label="Patient Overview"
                                component={Link}
                                to="/patient-overview"/>
                            }
                            {this.props.user.role && this.props.user.role === PATIENT_ROLE &&
                            <Tab
                                icon={<List/>}
                                label="Patient Details"
                                component={Link}
                                to="/patient/details"/>
                            }
                            {this.props.user.isLoggedIn && (
                                <Button onClick={() => {
                                    handleLogout(this.props.addUser, this.props.history);
                                }
                                }>
                                    <PowerSettingsNew/>
                                    Logout
                                </Button>
                            )}
                        </Tabs>
                    </Paper>
                </div>
                <Switch>
                    <PrivateRoute exact path="/doctor-overview"
                                  isAuthenticated={this.props.user.isLoggedIn}
                                  role={this.props.user.role}
                                  correctRole={DOCTOR_ROLE}
                                  history={this.props.history}
                                  render={(props) => {
                                      return (<AdminOverview {...props} history={this.props.history}/>)
                                  }}/>
                    <PrivateRoute exact path="/doctor/medication"
                                  isAuthenticated={this.props.user.isLoggedIn}
                                  role={this.props.user.role}
                                  correctRole={DOCTOR_ROLE}
                                  history={this.props.history}
                                  render={(props) => {
                                      return (<MedicationDoctor {...props} history={this.props.history}/>)
                                  }}/>
                    <PrivateRoute exact path="/caregiver-overview"
                                  isAuthenticated={this.props.user.isLoggedIn}
                                  role={this.props.user.role}
                                  correctRole={CAREGIVER_ROLE}
                                  history={this.props.history}
                                  render={(props) => {
                                      return (<CaregiverOverview {...props} history={this.props.history}/>)
                                  }}/>
                    <PrivateRoute exact path="/patient-overview"
                                  isAuthenticated={this.props.user.isLoggedIn}
                                  role={this.props.user.role}
                                  correctRole={PATIENT_ROLE}
                                  history={this.props.history}
                                  render={(props) => {
                                      return (<PatientOverview {...props} history={this.props.history}/>)
                                  }}/>
                    <PrivateRoute exact path="/patient/details"
                                  isAuthenticated={this.props.user.isLoggedIn}
                                  role={this.props.user.role}
                                  correctRole={PATIENT_ROLE}
                                  history={this.props.history}
                                  render={(props) => {
                                      return (<PatientDetailsPage {...props} history={this.props.history}/>)
                                  }}/>
                    <Route path="/login"
                           render={(props) => {
                               return (
                                   <Login/>
                               )
                           }}/>
                </Switch>
                <ErrorDialog
                    open={this.props.user.error.open}
                    message={this.props.user.error.message}
                    status={this.props.user.error.status}
                    onClose={() => this.props.updateError({
                        status: "",
                        message: "",
                        open: false
                    })}
                />
            </ConnectedRouter>
        )
    };
}

// @ts-ignore
const mapDispatchToProps = dispatch => {
    return {
        addUser: (username: string, role: string, isLoggedIn: boolean) => {
            dispatch(UserActionCreators.addUser({
                username: username,
                role: role,
                isLoggedIn: isLoggedIn
            }))
        },
        updateSelectedTab: (selectedTab: number) => {
            dispatch(UserActionCreators.updateSelectedTab({
                selectedTab: selectedTab
            }))
        },
        updateUserList: (userList: Array<UserDoctorView>) => {
            dispatch(DoctorActionCreators.updateUserList({
                userList: userList
            }))
        },
        updateMedicationList: (medicationList: Array<MedicationDoctorView>) => {
            dispatch(DoctorActionCreators.updateMedicationList({
                medicationList: medicationList
            }))
        },
        updateCaregiverId: (caregiverId: number) => {
            dispatch(CaregiverActionCreators.updateCaregiverId({
                caregiverId: caregiverId
            }))
        },
        updatePatientCaregiverList: (patientCaregiverList: PatientCaregiver[]) => {
            dispatch(CaregiverActionCreators.updatePatientCaregiverList({
                patientCaregiverList: patientCaregiverList
            }))
        },
        updateSelectedMedicationPlanId: (selectedMedicationPlanId: number | undefined) => {
            dispatch(PatientActionCreators.updateSelectedMedicationPlanId({
                selectedMedicationPlanId: selectedMedicationPlanId
            }))
        },
        updateMedicationPlanList: (medicationPlanList: MedicationPacientPlan[]) => {
            dispatch(PatientActionCreators.updateMedicationPlanList({
                medicationPlanList: medicationPlanList
            }))
        },
        updatePatientDetails: (patientDetails: PatientDetails) => {
            dispatch(PatientActionCreators.updatePatientDetails({
                patientDetails: patientDetails
            }))
        },
        updatePatientId: (patientId: number) => {
            dispatch(PatientActionCreators.updatePatientId({
                patientId: patientId
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
        user: state.app.user
    };
};

export default connect(mapStateToProps, mapDispatchToProps)(withStyles(AppCss)(App));
