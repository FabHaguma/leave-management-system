import React, { createContext, useState, useEffect, useCallback } from 'react';
import { useNavigate } from 'react-router-dom';

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
    { id: '1', level: 1, username: 'staff', email: 'staff@example.com', password: 'staff-word', role: 'STAFF', name: 'Fab staff' },
    { id: '2', level: 2, username: 'manager', email: 'manager@example.com', password: 'manager-word', role: 'MANAGER', name: 'Fab Manager' },
    { id: '3', level: 3, username: 'admin', email: 'admin@example.com', password: 'admin-word', role: 'ADMIN', name: 'Fab Admin' },
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
        const response = await fetch('http://localhost:8080/api/users/login', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
          },
          body: JSON.stringify({ email, password }),
        });
        
        if (!response.ok) {
          throw new Error('Login failed'); // Or handle different error codes
        }
        
        const userData = await response.json();
        // console.log('Login - Response: ' + JSON.stringify(userData)); // Debugging line
        // Simulate a successful login

        // Simulate finding the user in the dummy array:
        // const foundUser = DUMMY_USERS.find(u => u.email === email && u.password === password);
        // const foundUser =  user;


        if (userData) {
            console.log('Login - User found:'+ JSON.stringify(userData)); // Debugging line
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
        const response = await fetch('http://localhost:8080/api/users/register', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                name,
                email,
                password,
                profilePicture
            }),
        });

        let userData = null;
        if (!response.ok) {
            console.log('Registration - User Not Found'); // Debugging line
            // data = DUMMY_USERS[2];
            throw new Error('Registration failed'); // Handle different error codes
        }
        else{
            userData = await response.json();
            console.log('Registration - User Found: ' + JSON.stringify(userData)); // Debugging line
        }
        
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
