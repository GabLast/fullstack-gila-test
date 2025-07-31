import { ApiClient } from "./ApiClient";
import { CHANNEL_ENDPOINT } from "../config/apiEndPoints";

export const getChannels = async () => {
    try {
        const response = await ApiClient.get(CHANNEL_ENDPOINT + "/findall")
        return { data: response.data, error: null };
    } catch (error) {
        return { data: null, error: error.response };
    }
}