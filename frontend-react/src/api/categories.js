import { ApiClient } from "./ApiClient";
import { CATEGORY_ENDPOINT } from "../config/apiEndPoints";

export const getCategories = async () => {
    try {
        const response = await ApiClient.get(CATEGORY_ENDPOINT + "/findall")
        return { data: response.data, error: null };
    } catch (error) {
        return { data: null, error: error.response };
    }
}