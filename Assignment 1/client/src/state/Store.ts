import {applyMiddleware, combineReducers, compose, createStore} from "redux";
import {connectRouter, routerMiddleware} from "connected-react-router";
import {globalAppReducer} from "./GlobalAppReducer";
import { createBrowserHistory } from "history"

export const configureStore = () => {
    // @ts-ignore
    const composeEnhancers = window['__REDUX_DEVTOOLS_EXTENSION_COMPOSE__'] as typeof compose || compose;

    const history = createBrowserHistory();
    const reduxMiddleware = [routerMiddleware(history)];

    const store = createStore(
        combineReducers({
            app: globalAppReducer,
            router: connectRouter(history)
        }),
        composeEnhancers(applyMiddleware(...reduxMiddleware))
    );
    return {history, store};
};