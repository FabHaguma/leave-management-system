import React, { createContext, useState, useEffect, useCallback } from 'react';
import { useNavigate } from 'react-router-dom';
const { sendRequest } = require('../utils/urlBuilder');

// Create the authentication context
const AuthContext = createContext({
    user: null,
    register: async () => { },
    login: async () => { },
    logout: () => { },
    isAuthenticated: false,
    loading: true, // Add a loading state
});

// Dummy user data for demonstration purposes
const DUMMY_USERS = [
    { id: 1, name: 'Fab staff', email: 'staff@example.com', role: 'STAFF', avatar: '../assets/default-avatar.png' },
    { id: 2, name: 'Fab Manager', email: 'manager@example.com', role: 'MANAGER', avatar: '../assets/default-avatar.png' },
    { id: 3, name: 'Fab Admin', email: 'admin@example.com', role: 'ADMIN', avatar: '../assets/default-avatar.png' },
];

const setUserAccessLevel = (user) => {
    // Set user access level based on role
    if (user.role === 'STAFF') {
        user.level = 1;
    } else if (user.role === 'MANAGER') {
        user.level = 2;
    } else if (user.role === 'ADMIN') {
        user.level = 3;
    } else {
        user.level = 0; // Default level for unknown roles
    }
}

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

        const response = await sendRequest('users/login', 'POST', { email, password });

        // let response = null;
        // if(email.includes('staff')) response = JSON.stringify(DUMMY_USERS[0]);
        // else if(email.includes('manager')) response = JSON.stringify(DUMMY_USERS[1]);
        // else if(email.includes('admin')) response = JSON.stringify(DUMMY_USERS[2]);
        
        
        // console.log('Login - Response: ' + JSON.stringify(userData)); // Debugging line
        // Simulate a successful login

        // const userData = JSON.parse(response); 
        const userData = response;
        console.log(`[AuthContext - login] userData: ${JSON.stringify(userData)}`); // Debugging line
        
        if (userData) {
            // console.log('Login - User found:'+ JSON.stringify(userData)); // Debugging line
            // Simulate a successful login
            setLoading(false); // Set loading to false after checking
            setUserAccessLevel(userData); // Set user access level based on role
            setUser(userData);
            setIsAuthenticated(true);
            localStorage.setItem('user', JSON.stringify(userData)); // Persist user data
            navigate('/dashboard'); // Redirect on successful login
            return; // Important: Exit the function after successful login
        }
        else {
            console.log('Login - User Not Found'); // Debugging line
            throw new Error('Invalid credentials-FE');
        }

    }, [navigate]);

    const register = useCallback(async (name, email, password, profilePicture) => {

        profilePicture = profilePicture || '../assets/default-avatar.png'; // Default profile picture if not provided
        // Simulate an API call for registration
        const response = await sendRequest('users/register', 'POST', { name, email, password, profilePicture });
        const userData = response; // await response.json();
        console.log(`[AuthContext - register] userData: ${JSON.stringify(userData)}`); // Debugging line
        // console.log('Registration - User Found: ' + JSON.stringify(userData));

        setLoading(false); // Set loading to false after checking
        setUserAccessLevel(userData); // Set user access level based on role
        setUser(userData);
        setIsAuthenticated(true);
        localStorage.setItem('user', JSON.stringify(userData)); // Persist user data
        navigate('/dashboard'); // Redirect on successful registration
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
        register,
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
