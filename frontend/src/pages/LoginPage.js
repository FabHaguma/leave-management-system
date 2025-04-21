import React, { useState, useEffect } from 'react';
import { useAuth } from '../context/AuthContext'; // Updated from contexts to context
import { useNavigate, useLocation } from 'react-router-dom'; // Updated to v7

const LoginPage = () => {
  const { login, user } = useAuth();
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(false);

  const navigate = useNavigate(); // Updated from history to navigate
  const location = useLocation();
  const { from } = location.state || { from: { pathname: '/' } }; // Redirect path after login

  // Redirect if already logged in
  useEffect(() => {
    if (user) {
      navigate(from, { replace: true }); // Updated from history.replace
    }
  }, [user, navigate, from]); // Updated dependency array

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');
    setLoading(true);
    try {
      const success = await login(email, password);
      if (success) {
        navigate(from, { replace: true }); // Updated from history.replace
      } else {
        setError('Invalid email or password.');
      }
    } catch (err) {
      setError('Login failed. Please try again.');
      console.error(err);
    } finally {
      setLoading(false);
    }
  };

  // Don't render the form if already logged in (optional, depends on effect)
  if (user) return <div>Redirecting...</div>;

  return (
    <div>
      <h2>Login</h2>
      <form onSubmit={handleSubmit}>
        <div>
          <label htmlFor="email">Email:</label>
          <input
            type="email"
            id="email"
            placeholder="yourname@ist.com"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            required
            disabled={loading}
          />
        </div>
        <div>
          <label htmlFor="password">Password:</label>
          <input
            type="password"
            id="password"
            placeholder="Password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            required
            disabled={loading}
          />
        </div>
        {error && <p style={{ color: 'red' }}>{error}</p>}
        <button type="submit" disabled={loading}>
          {loading ? 'Logging in...' : 'Login'}
        </button>
      </form>
    </div>
  );
};

export default LoginPage;