// src/config/msalConfig.js
export const msalConfig = {
    auth: {
      clientId: "YOUR_CLIENT_ID",
      authority: "https://login.microsoftonline.com/YOUR_TENANT_ID",
      redirectUri: "http://localhost:3000",
    },
    cache: {
      cacheLocation: "localStorage", // or "sessionStorage"
      storeAuthStateInCookie: false,
    },
  };
  
  export const loginRequest = {
    scopes: ["openid", "profile", "email"],
  };
  