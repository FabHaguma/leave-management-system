import React, { useState, useContext } from 'react';
import { FaMicrosoft } from 'react-icons/fa'; // Import the icon
import { AuthContext } from '../context/AuthContext'; 
import '../styles/LoginPage.css'; // Import your CSS styles

function LoginPage() {
    const [username, setUsername] = useState('');
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [confirmPassword, setConfirmPassword] = useState('');
    const [role, setRole] = useState('employee');
    const [isRegistering, setIsRegistering] = useState(false);
    const [error, setError] = useState(''); // To display errors
    const { login, loading } = useContext(AuthContext); // Use the context

    const handleSubmit = async (e) => {
        e.preventDefault(); // Prevent the default form submission

        setError(''); // Clear any previous errors
        if (isRegistering) {
            if (!username || !email || !password || !confirmPassword) {
                setError('Please fill in all fields');
                return;
            }
            if (password !== confirmPassword) {
                setError('Passwords do not match');
                return;
            }
            try {
                // Replace this with your actual registration API call
                // const response = await fetch('/api/users/register', {
                //     method: 'POST',
                //     headers: {
                //         'Content-Type': 'application/json',
                //     },
                //     body: JSON.stringify({
                //         username,
                //         email,
                //         password,
                //         role,
                //     }),
                // });
    
                // if (!response.ok) {
                //     const errorData = await response.json();
                //     throw new Error(errorData.message || 'Registration failed');
                // }
    
                // alert('Registration successful! You can now log in.');
                // toggleMode(); // Switch to login mode after successful registration
            } catch (error) {
                setError(error.message); // Display error message from the API
            }
        } else {
            if (!email || !password) {
                setError('Please enter both email and password');
                return;
            }
            try {
                await login(email, password); // Call the login function from the context
                // The login function in AuthProvider will handle redirection on success
            } catch (error) {
                setError(error.message); // Display error message from login
            }
        }
    };

    if (loading) {
        return <div>Loading...</div>; // Show a loading indicator during login
    }

    const toggleMode = () => {
        setIsRegistering(!isRegistering);
        setError(''); // Clear errors when switching modes
        // Reset fields when switching modes for clarity
        setEmail('');
        setPassword('');
        setUsername('');
        setConfirmPassword('');
        setRole('employee');
    };

    const handleOAuthLogin = () => {
        // Placeholder for Microsoft OAuth logic
        alert('Redirecting to Microsoft OAuth...');
        // Implement actual OAuth flow here
    };

    return (
        <div className="loginContainer"> {/* Added loginContainer class */}
            <h2 id="form-title">{isRegistering ? 'Register' : 'Login'}</h2>
            <form id="auth-form" onSubmit={handleSubmit}>
                {isRegistering && (
                    <div id="username-field"> {/* Added username field */}
                        <label htmlFor="username">Username:</label>
                        <input
                            type="text"
                            id="username"
                            placeholder="Enter your username"
                            value={username}
                            onChange={(e) => setUsername(e.target.value)}
                            required={isRegistering} // Required only when registering
                            disabled={loading}
                        />
                    </div>
                )}
                <div> {/* Wrapped in div for consistency */}
                    <label htmlFor="email">Email:</label>
                    <input
                        type="email"
                        id="email"
                        placeholder="Enter your email"
                        value={email}
                        onChange={(e) => setEmail(e.target.value)}
                        required
                        disabled={loading}
                    />
                </div>
                <div> {/* Wrapped in div */}
                    <label htmlFor="password">Password:</label>
                    <input
                        type="password"
                        id="password"
                        placeholder="Enter your password"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                        required
                        disabled={loading}
                    />
                </div>
                {isRegistering && (
                    <> {/* Use Fragment */}
                        <div id="confirm-password-field"> {/* Added confirm password field */}
                            <label htmlFor="confirm-password">Confirm Password:</label>
                            <input
                                type="password"
                                id="confirm-password"
                                placeholder="Confirm your password"
                                value={confirmPassword}
                                onChange={(e) => setConfirmPassword(e.target.value)}
                                required
                                disabled={loading}
                            />
                        </div>
                        <div id="role-field"> {/* Added role field */}
                            <label htmlFor="role">Role:</label>
                            <select
                                id="role"
                                value={role}
                                onChange={(e) => setRole(e.target.value)}
                                disabled={loading}
                            >
                                <option value="employee">Employee</option>
                                <option value="manager">Manager</option>
                                <option value="admin">Admin</option>
                            </select>
                        </div>
                    </>
                )}
                {error && <p style={{ color: 'red', textAlign: 'center', marginBottom: '10px' }}>{error}</p>} {/* Centered error */}
                <button type="submit" id="submit-btn" disabled={loading}>
                    {loading ? (isRegistering ? 'Registering...' : 'Logging in...') : isRegistering ? 'Register' : 'Login'}
                </button>
                {/* Added Microsoft OAuth Button */}
                <button type="button" className="oauth-btn" id="oauth-btn" onClick={handleOAuthLogin} disabled={loading}>
                    <FaMicrosoft style={{ marginRight: '8px' }} /> {/* Use icon component */}
                    {isRegistering ? 'Register with Microsoft' : 'Login with Microsoft'}
                </button>
            </form>
            <div className="toggle"> {/* Added toggle div */}
                <p id="toggle-text">
                    {isRegistering ? 'Already have an account? ' : "Don't have an account? "}
                    <button type="button" id="toggle-btn" onClick={toggleMode} disabled={loading}>
                        {isRegistering ? 'Login' : 'Register'}
                    </button>
                </p>
            </div>
        </div>
    );
}

export default LoginPage;