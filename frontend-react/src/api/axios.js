import axios from 'axios';

const apiClient = axios.create({
    baseURL: process.env.REACT_APP_API_BASE_URL || 'http://localhost:8080/api',
    timeout: 10000,
    headers: {
        'Content-Type': 'application/json',
    },
});

export const postRequest = async ({ url, params, body }) => {
  const response = await apiClient.post(url, {
    params: params,
    data: body,
  });

  return response.data;
};

export const getRequest = async ({ url, params }) => {
  const response = await apiClient.get(url, {
    params: params,
  });

  return response.data;
};

// // Optional: Add a request interceptor
// apiClient.interceptors.request.use(
//     config => {
//         // Example: Attach token if available
//         const token = localStorage.getItem('token');
//         if (token) {
//             config.headers.Authorization = `Bearer ${token}`;
//         }
//         return config;
//     },
//     error => Promise.reject(error)
// );

// // Optional: Add a response interceptor
// apiClient.interceptors.response.use(
//     response => response,
//     error => {
//         // Handle errors globally
//         return Promise.reject(error);
//     }
// );

export default apiClient;