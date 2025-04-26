-- Initial schema setup for Leave Management System

-- Create Roles Table
CREATE TABLE roles (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE
);

-- Create Leave Types Table
CREATE TABLE leave_types (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE,
    default_days INTEGER DEFAULT 0
);

-- Create Users Table
CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    name VARCHAR(255),
    password VARCHAR(255),
    profile_picture VARCHAR(512) -- URL might be long
);

-- Create User Roles Join Table (Many-to-Many)
CREATE TABLE user_roles (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    role_name VARCHAR(255) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE CASCADE
);

-- Create Leave Balances Table
CREATE TABLE leave_balances (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    leave_type_id BIGINT NOT NULL,
    entitlement DOUBLE PRECISION DEFAULT 0.0,
    used DOUBLE PRECISION DEFAULT 0.0,
    remaining DOUBLE PRECISION DEFAULT 0.0,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (leave_type_id) REFERENCES leave_types(id) ON DELETE CASCADE,
    UNIQUE (user_id, leave_type_id) -- Ensure one balance entry per user per leave type
);

-- Create Leave Requests Table
CREATE TABLE leave_requests (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    leave_type_id BIGINT NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    reason TEXT,
    comment TEXT,
    status VARCHAR(50), -- e.g., PENDING, APPROVED, REJECTED
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (leave_type_id) REFERENCES leave_types(id) ON DELETE CASCADE -- Cascade if type is deleted? Or RESTRICT? Cascade for now.
);

-- Create Documents Table
CREATE TABLE documents (
    id BIGSERIAL PRIMARY KEY,
    file_name VARCHAR(255),
    file_url VARCHAR(512), -- URL might be long
    leave_request_id BIGINT NOT NULL,
    FOREIGN KEY (leave_request_id) REFERENCES leave_requests(id) ON DELETE CASCADE
);

-- Create Notifications Table
CREATE TABLE notifications (
    id BIGSERIAL PRIMARY KEY,
    recipient_id BIGINT NOT NULL,
    message TEXT,
    type VARCHAR(100), -- e.g., LEAVE_SUBMITTED, APPROVED, REJECTED
    read BOOLEAN DEFAULT FALSE,
    timestamp TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (recipient_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Seed initial roles
INSERT INTO roles (name) VALUES ('STAFF'), ('MANAGER'), ('ADMIN')
ON CONFLICT (name) DO NOTHING; -- Avoid errors if run again

-- Seed initial leave types (optional, add more as needed)
INSERT INTO leave_types (name) VALUES ('Annual Leave'), ('Sick Leave'), ('Compassionate Leave'), ('Maternity Leave'), ('Unpaid Leave')
ON CONFLICT (name) DO NOTHING;

-- Add Indexes for commonly queried columns (optional but good practice)
CREATE INDEX idx_leave_requests_user_id ON leave_requests(user_id);
CREATE INDEX idx_leave_requests_status ON leave_requests(status);
CREATE INDEX idx_leave_balances_user_id ON leave_balances(user_id);
CREATE INDEX idx_notifications_recipient_id ON notifications(recipient_id);