import React, { createContext, useState, useEffect, useCallback } from 'react';
import { useNavigate } from 'react-router-dom';

// Create the authentication context
const AuthContext = createContext({
    user: null,
    login: async () => { },
    logout: () => { },
    isAuthenticated: false,
    loading: true, // Add a loading state
});

// Dummy user data for demonstration purposes
const DUMMY_USERS = [
    { id: '1', level: 1, username: 'staff', email: 'staff@ist.com', password: 'staff-word', role: 'staff', name: 'Fabrice Haguma - s' },
    { id: '2', level: 2, username: 'manager', email: 'manager@ist.com', password: 'manager-word', role: 'manager', name: 'Fabrice Haguma - m' },
    { id: '3', level: 3, username: 'admin', email: 'admin@ist.com', password: 'admin-word', role: 'admin', name: 'Fabrice Haguma - a' },
];

const AuthProvider = ({ children }) => {
    const [user, setUser] = useState(null);
    const [isAuthenticated, setIsAuthenticated] = useState(false);
    const [loading, setLoading] = useState(true); // Initialize loading state
    const navigate = useNavigate();

    // Check for existing session on initial load
    useEffect(() => {
        const storedUser = localStorage.getItem('user');
        if (storedUser) {
            try {
                const parsedUser = JSON.parse(storedUser);
                setUser(parsedUser);
                setIsAuthenticated(true);
            } catch (error) {
                console.error("Error parsing user from localStorage", error);
                localStorage.removeItem('user'); // Clear corrupted data
            }
        }
        setLoading(false); // Set loading to false after checking
    }, []);

    // Login function (simulated with dummy users)
    const login = useCallback(async (email, password) => {
        // Simulate an API call (replace with your actual API call)
        // For example using fetch:
        // const response = await fetch('/api/users/login', {
        //   method: 'POST',
        //   headers: {
        //     'Content-Type': 'application/json',
        //   },
        //   body: JSON.stringify({ email, password }),
        // });
        
        // if (!response.ok) {
        //   throw new Error('Login failed'); // Or handle different error codes
        // }
        
        // const data = await response.json();
        // const user = data.user; // Adjust based on your API response

        // Simulate finding the user in the dummy array:
        const foundUser = DUMMY_USERS.find(u => u.email === email && u.password === password);
        // const foundUser =  user;


        if (foundUser) {
            // Simulate a successful login
            setUser(foundUser);
            setIsAuthenticated(true);
            localStorage.setItem('user', JSON.stringify(foundUser)); // Persist user data
            navigate('/dashboard'); // Redirect on successful login
            return; // Important: Exit the function after successful login
        }
        else {
             throw new Error('Invalid credentials');
        }

        // --- REAL DATABASE AUTHENTICATION (Commented Out) ---
        // try {
        //   const response = await fetch('/api/users/register', { // Replace '/api/login'
        //     method: 'POST',
        //     headers: {
        //       'Content-Type': 'application/json',
        //     },
        //     body: JSON.stringify({ email, password }),
        //   });

        //   if (!response.ok) {
        //     // Handle HTTP errors (e.g., 401 Unauthorized, 500 Internal Server Error)
        //     if (response.status === 401) {
        //       throw new Error('Invalid credentials');
        //     } else {
        //       throw new Error(`Login failed: ${response.status}`);
        //     }
        //   }

        //   const data = await response.json();

        //   if (data && data.user) {
        //     const { user } = data;
        //     setUser(user);
        //     setIsAuthenticated(true);
        //     localStorage.setItem('user', JSON.stringify(user));
        //     navigate('/dashboard'); // Redirect
        //   } else {
        //      throw new Error('Invalid response from server');
        //   }

        // } catch (error) {
        //   // Handle login errors (e.g., network issues, server errors, invalid credentials)
        //   console.error('Login error:', error);
        //   throw error; // Re-throw to be caught by the component
        // }
    }, [navigate]);

    // Logout function
    const logout = useCallback(() => {
        setUser(null);
        setIsAuthenticated(false);
        localStorage.removeItem('user'); // Clear user data on logout
        navigate('/login');
    }, [navigate]);

    const contextValue = {
        user,
        login,
        logout,
        isAuthenticated,
        loading,
    };

    return (
        <AuthContext.Provider value={contextValue}>
            {!loading && children} {/* Render children when not loading */}
        </AuthContext.Provider>
    );
};

export { AuthContext, AuthProvider };
const AuthModule = { AuthContext, AuthProvider };
export default AuthModule;
