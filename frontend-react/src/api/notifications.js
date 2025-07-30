import { ApiClient } from "./ApiClient";
import { NOTIFICATION_ENDPOINT } from "../config/apiEndPoints";

export const getNotifications = async (
    userId, channelId, seen,
    dateStart, dateEnd, limit, offset,
    sortColumn, sortOrder, message, enabled
) => {

    const params = {
        userId,
        channelId,
        seen,
        start: dateStart !== '' ? dateStart : null,
        end: dateEnd !== '' ? dateEnd : null,
        limit,
        offset,
        sortColumn,
        sortOrder,
        messageBody: message,
        enabled
    }

    return await ApiClient.get(NOTIFICATION_ENDPOINT + "/findall", {
        params: params
    }).then(response => {
        return { data: response.data.data, error: null };
    }).catch(error => {
        return { data: [], error: error.response };
    })
}

export const getCount = async (
    userId, channelId, seen,
    dateStart, dateEnd, sortColumn, sortOrder, message, enabled
) => {
    const params = {
        userId,
        channelId,
        seen,
        start: dateStart !== '' ? dateStart : null,
        end: dateEnd !== '' ? dateEnd : null,
        sortColumn,
        sortOrder,
        messageBody: message,
        enabled
    }

    return await ApiClient.get(NOTIFICATION_ENDPOINT + "/count", {
        params: params
    }).then(response => {
        // console.log("Total items: ", response.data);
        return { data: response.data, error: null };
    }).catch(error => {
        return { data: 0, error: error.response };
    })
}

export const checkNotification = async (userId, notificationId) => {

    return await ApiClient.post(NOTIFICATION_ENDPOINT + "/checknotification", {
        userDto: {
            id: userId
        },
        notificationId: notificationId
    }).then((response) => {
        return { data: response.data, error: null };
    }).catch(err => {
        return { data: null, error: err.response };
    })
}