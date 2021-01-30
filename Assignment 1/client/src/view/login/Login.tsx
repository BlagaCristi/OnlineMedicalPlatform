import {Paper} from "@material-ui/core";
import * as React from "react";
import Grid from "@material-ui/core/Grid";
import Typography from "@material-ui/core/Typography";
import withStyles from "@material-ui/core/styles/withStyles";
import {LoginCss} from "./LoginCss";
import TextField from "@material-ui/core/TextField";
import Button from "@material-ui/core/Button";
import {api} from "../../util/Api";
import {TOKEN} from "../../util/GlobalConstants";

interface LoginProps {
    classes: any;
}

const Login: React.FunctionComponent<LoginProps> = ({classes}: LoginProps) => {

    const [values, setValues] = React.useState({
        username: '',
        password: ''
    });

    const handleChange = name => event => {
        setValues({...values, [name]: event.target.value});
    };

    return (
        <Paper
            className={classes.paper}
        >
            <Grid
                container
                direction="column"
                justify="center"
                alignItems="center"
            >
                <Grid
                    item>
                    <Typography variant="h5" gutterBottom>
                        Please log in
                    </Typography>
                </Grid>
                <Grid
                    item
                >
                    <TextField
                        id="standard-name"
                        label="Username"
                        className={classes.textField}
                        value={values.username}
                        onChange={handleChange('username')}
                        margin="normal"
                    />
                </Grid>
                <Grid
                    item
                >
                    <TextField
                        id="standard-password-input"
                        label="Password"
                        className={classes.textField}
                        value={values.password}
                        type="password"
                        onChange={handleChange('password')}
                        margin="normal"
                    />
                </Grid>
                <Grid
                    item
                >
                    <Button
                        variant="contained"
                        color="primary"
                        className={classes.button}
                        onClick={() => {
                            api.setHeader("Authorization", "Basic " + btoa(values.username + ":" + values.password));
                            api.get("/login")
                                .then(response => {
                                    localStorage.setItem(TOKEN, btoa(values.username + ":" + values.password));
                                    window.location.reload();
                                })
                        }}
                    >
                        Log in
                    </Button>
                </Grid>
            </Grid>
        </Paper>
    );
};

export default withStyles(LoginCss)(Login);