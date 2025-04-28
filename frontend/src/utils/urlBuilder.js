// const { buildUrl, buildBody, sendRequest } = require('./utils/urlBuilder');
const BASE_URL = 'http://localhost:8080/api'; // Set your base URL here

// 1. Append endpoint to base URL
function buildUrl(endpoint) {
  const cleanEndpoint = endpoint.replace(/^\/+/, '');
  return `${BASE_URL}/${cleanEndpoint}`;
}

// 2. Build request body
function buildBody(data = {}) {
  return JSON.stringify(data);
}

// 3. Send request and receive response
async function sendRequest(endpoint, method = 'GET', body = null, headers = {}) {
  const url = buildUrl(endpoint);

  const options = {
    method,
    headers: {
      'Content-Type': 'application/json',
      ...headers,
    },
    ...(body && method !== 'GET' && method !== 'HEAD' && { body: buildBody(body) }),
  };

  try {
    const response = await fetch(url, options);

    if (!response.ok) {
      const errorBody = await response.text();
      throw new Error(`HTTP Error ${response.status}: ${errorBody}`);
    }

    console.log(`Request to ${url} completed with status: ${response.status}`); // Debugging line
    console.log(`[urlBuilder] Response: ${response}`); // Debugging line
    const responseData = await response.json();
    return responseData;
  } catch (error) {
    console.error(`Error in sendRequest: ${error.message}`);
    throw error; // Re-throw the error to allow further handling if needed
  }
}

module.exports = {
  buildUrl,
  buildBody,
  sendRequest,
};
