import {RouterState} from "connected-react-router";
import {GlobalState} from "./GlobalState";

export interface RootState {
    app: GlobalState;
    router: RouterState;
}