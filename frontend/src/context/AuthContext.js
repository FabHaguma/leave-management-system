import React, { createContext, useState, useContext, useEffect } from "react";
import * as authService from "../services/authService";

const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
  const [user, setUser] = useState(null); // { name, email, role }
  // Initialize token from localStorage
  const [token, setToken] = useState(() => localStorage.getItem("token"));
  // Loading state for initial auth check
  const [loading, setLoading] = useState(true);


  const login = async (email, password) => {
    setLoading(true); // Start loading before API call
    try {
      const data = await authService.login(email, password);

      // Check if the necessary data is present in the API response
      if (data && data.user && data.token) {
        setUser(data.user); // { name, email, role }
        setToken(data.token);
        setLoading(false); // Stop loading on success
        return true; // Indicate successful login and state update
      } else {
        // API response was successful but didn't contain expected data
        console.error("Login succeeded but response data is missing user or token:", data);
        logout(); // Clear any potentially partial state
        setLoading(false);
        return false; // Indicate failure
      }
    } catch (error) {
      console.error("Login failed:", error);
      logout(); // Clear state on failure
      setLoading(false); // Stop loading on error
      return false;
    }
  };
  

  const logout = () => {
    authService.logout(); // Call the service function to remove token
    setUser(null);
    setToken(null);
  };

  // Effect to validate token and potentially fetch user profile on initial load
  useEffect(() => {
    const validateTokenAndFetchUser = async () => {
      if (token) {
        try {
          // Try to get the user profile using the stored token
          const profile = await authService.getProfile();
          setUser(profile); // Set user data from API response
        } catch (error) {
          console.error("Token validation/User fetch failed:", error);
          logout(); // Invalid token or error fetching user, log out
        }
      }
      setLoading(false); // Finished initial auth check
    };
    validateTokenAndFetchUser();
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []); // Run only once on mount

  return (
    // Provide loading state as well
    <AuthContext.Provider value={{ user, token, login, logout, loading }}>
      {/* Don't render children until initial auth check is done */}
      {!loading && children}
    </AuthContext.Provider>
  );
};

export const useAuth = () => useContext(AuthContext);