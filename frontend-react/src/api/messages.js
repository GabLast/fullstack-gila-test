import { ApiClient } from "./ApiClient";
import { MESSAGE_ENDPOINT } from "../config/apiEndPoints";

export const postMessage = async (category, message) => {
    try {
        const response = await ApiClient.post(MESSAGE_ENDPOINT, {
            category: category,
            message: message
        })
        
        return { data: response.data, error: null };
    } catch (error) {
        return { data: null, error: error.response };
    }
}

export const getMessage = async (id) => {
    try {
        const response = await ApiClient.get(MESSAGE_ENDPOINT, {
            params: {
                id: id
            }
        })
        
        return { data: response.data, error: null };
    } catch (error) {
        return { data: null, error: error.response };
    }
}