import { ApiClient } from "./ApiClient";
import { USER_ENDPOINT } from "../config/apiEndPoints";

export const getUsers = async () => {
    try {
        const response = await ApiClient.get(USER_ENDPOINT + "/findall")
        return { data: response.data, error: null };
    } catch (error) {
        return { data: null, error: error.response };
    }
}