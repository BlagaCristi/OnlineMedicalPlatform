import React from 'react';
import {Route} from "react-router-dom";
import {getOverviewForRole} from "../../util/LoginUtil";


const PrivateRoute = ({isAuthenticated, role, correctRole, history, render, ...rest}) => (
    <Route
        {...rest}
        render={
            props => {
                if (isAuthenticated) {
                    if (role) {
                        if (role === correctRole) {
                            return render(props);
                        } else {
                            history.push(getOverviewForRole(role));
                        }
                    } else {
                        history.push("/login");
                    }
                } else {
                    history.push("/login");
                }
            }
        }
    />
);

export default PrivateRoute