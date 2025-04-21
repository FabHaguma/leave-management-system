// src/services/authService.js
import { apiRequest } from './apiConfig';

// Function to authenticate user
export const login = async (email, password) => {
  try {
    const data = await apiRequest('/auth/login', {
      method: 'POST',
      body: JSON.stringify({ email, password }),
    });
    
    // Store token in localStorage
    if (data.token) {
      localStorage.setItem('token', data.token);
    }
    
    return data;
  } catch (error) {
    console.error('Login failed:', error);
    throw error;
  }
};

// Function to get current user profile
export const getProfile = async () => {
  try {
    return await apiRequest('/auth/me', {
      method: 'GET',
    });
  } catch (error) {
    console.error('Failed to fetch user profile:', error);
    throw error;
  }
};

// Function to logout user
export const logout = () => {
  localStorage.removeItem('token');
  // You can also call an endpoint to invalidate the token on the server
  // return apiRequest('/auth/logout', { method: 'POST' });
};

// Function to check if token is valid
export const validateToken = async () => {
  try {
    const response = await apiRequest('/auth/validate', {
      method: 'GET',
    });
    return response;
  } catch (error) {
    console.error('Token validation failed:', error);
    throw error;
  }
};