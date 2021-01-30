import * as SockJS from 'sockjs-client';
import Stomp from "stompjs";
import {
    API_BASE_PATH,
    BASE_PATH,
    CAREGIVER_TOPIC,
    PATIENT_ACTIVITY_ENDPOINT,
    TOPIC,
    WS_BASE_PATH
} from "../util/GlobalConstants";

export const initPatientActivityWebsocket = (caregiverId, updateError) => {
    let socket = new SockJS(BASE_PATH + API_BASE_PATH + WS_BASE_PATH + PATIENT_ACTIVITY_ENDPOINT);
    let stompClientUpdates = Stomp.over(socket);
    stompClientUpdates.debug = () => {
    };
    stompClientUpdates.connect({}, function () {
        stompClientUpdates.subscribe(TOPIC + CAREGIVER_TOPIC + "-" + caregiverId, function (data) {
            updateError({
                open: true,
                status: "Patient problem!",
                message: data.body
            });
        });
    });
};