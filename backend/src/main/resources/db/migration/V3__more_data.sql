-- Seed initial users
INSERT INTO users (email, name, password) VALUES
('user1@staff.com', 'User 1', 'password1'),
('user2@staff.com', 'User 2', 'password2'),
('user3@staff.com', 'User 3', 'password3'),
('user4@staff.com', 'User 4', 'password4'),
('user5@staff.com', 'User 5', 'password5'),
('user6@staff.com', 'User 6', 'password6'),
('user7@staff.com', 'User 7', 'password7'),
('user8@staff.com', 'User 8', 'password8'),
('user1@manager.com', 'Manager 1', 'password9'),
('user2@manager.com', 'Manager 2', 'password10');

-- Assign roles to users
INSERT INTO user_roles (user_id, role_id) VALUES
((SELECT id FROM users WHERE email = 'user1@staff.com'), (SELECT id FROM roles WHERE name = 'STAFF')),
((SELECT id FROM users WHERE email = 'user2@staff.com'), (SELECT id FROM roles WHERE name = 'STAFF')),
((SELECT id FROM users WHERE email = 'user3@staff.com'), (SELECT id FROM roles WHERE name = 'STAFF')),
((SELECT id FROM users WHERE email = 'user4@staff.com'), (SELECT id FROM roles WHERE name = 'STAFF')),
((SELECT id FROM users WHERE email = 'user5@staff.com'), (SELECT id FROM roles WHERE name = 'STAFF')),
((SELECT id FROM users WHERE email = 'user6@staff.com'), (SELECT id FROM roles WHERE name = 'STAFF')),
((SELECT id FROM users WHERE email = 'user7@staff.com'), (SELECT id FROM roles WHERE name = 'STAFF')),
((SELECT id FROM users WHERE email = 'user8@staff.com'), (SELECT id FROM roles WHERE name = 'STAFF')),
((SELECT id FROM users WHERE email = 'user1@manager.com'), (SELECT id FROM roles WHERE name = 'MANAGER')),
((SELECT id FROM users WHERE email = 'user2@manager.com'), (SELECT id FROM roles WHERE name = 'MANAGER'));

-- Seed additional leave requests
INSERT INTO leave_requests (user_id, leave_type_id, start_date, end_date, reason, status, has_documents) VALUES
((SELECT id FROM users WHERE email = 'user1@staff.com'), (SELECT id FROM leave_types WHERE name = 'Sick Leave'), '2025-05-01', '2025-05-10', 'Medical leave', 'PENDING', FALSE),
((SELECT id FROM users WHERE email = 'user2@staff.com'), (SELECT id FROM leave_types WHERE name = 'Annual Leave'), '2025-06-01', '2025-06-12', 'Vacation', 'APPROVED', FALSE),
((SELECT id FROM users WHERE email = 'user3@staff.com'), (SELECT id FROM leave_types WHERE name = 'Unpaid Leave'), '2025-07-01', '2025-07-08', 'Personal reasons', 'REJECTED', FALSE),
((SELECT id FROM users WHERE email = 'user4@staff.com'), (SELECT id FROM leave_types WHERE name = 'Sick Leave'), '2025-08-01', '2025-08-09', 'Medical leave', 'PENDING', FALSE),
((SELECT id FROM users WHERE email = 'user5@staff.com'), (SELECT id FROM leave_types WHERE name = 'Annual Leave'), '2025-09-01', '2025-09-10', 'Family time', 'APPROVED', FALSE),
((SELECT id FROM users WHERE email = 'user6@staff.com'), (SELECT id FROM leave_types WHERE name = 'Unpaid Leave'), '2025-10-01', '2025-10-07', 'Personal reasons', 'REJECTED', FALSE),
((SELECT id FROM users WHERE email = 'user7@staff.com'), (SELECT id FROM leave_types WHERE name = 'Sick Leave'), '2025-11-01', '2025-11-10', 'Medical leave', 'PENDING', FALSE),
((SELECT id FROM users WHERE email = 'user8@staff.com'), (SELECT id FROM leave_types WHERE name = 'Annual Leave'), '2025-12-01', '2025-12-14', 'Vacation', 'APPROVED', FALSE),
((SELECT id FROM users WHERE email = 'user1@manager.com'), (SELECT id FROM leave_types WHERE name = 'Unpaid Leave'), '2025-04-15', '2025-04-20', 'Personal reasons', 'REJECTED', FALSE),
((SELECT id FROM users WHERE email = 'user2@manager.com'), (SELECT id FROM leave_types WHERE name = 'Sick Leave'), '2025-03-01', '2025-03-10', 'Medical leave', 'APPROVED', FALSE);

-- Seed notifications for leave requests
INSERT INTO notifications (recipient_id, message, type, read, timestamp) VALUES
((SELECT id FROM users WHERE email = 'test@admin.com'), 'Leave request submitted by user1@staff.com.', 'LEAVE_SUBMITTED', FALSE, '2025-05-01T10:00:00Z'),
((SELECT id FROM users WHERE email = 'test@admin.com'), 'Leave request submitted by user2@staff.com.', 'LEAVE_SUBMITTED', FALSE, '2025-06-01T10:00:00Z'),
((SELECT id FROM users WHERE email = 'test@admin.com'), 'Leave request submitted by user3@staff.com.', 'LEAVE_SUBMITTED', FALSE, '2025-07-01T10:00:00Z'),
((SELECT id FROM users WHERE email = 'test@admin.com'), 'Leave request submitted by user4@staff.com.', 'LEAVE_SUBMITTED', FALSE, '2025-08-01T10:00:00Z'),
((SELECT id FROM users WHERE email = 'test@admin.com'), 'Leave request submitted by user5@staff.com.', 'LEAVE_SUBMITTED', FALSE, '2025-09-01T10:00:00Z'),
((SELECT id FROM users WHERE email = 'test@admin.com'), 'Leave request submitted by user6@staff.com.', 'LEAVE_SUBMITTED', FALSE, '2025-10-01T10:00:00Z'),
((SELECT id FROM users WHERE email = 'test@admin.com'), 'Leave request submitted by user7@staff.com.', 'LEAVE_SUBMITTED', FALSE, '2025-11-01T10:00:00Z'),
((SELECT id FROM users WHERE email = 'test@admin.com'), 'Leave request submitted by user8@staff.com.', 'LEAVE_SUBMITTED', FALSE, '2025-12-01T10:00:00Z'),
((SELECT id FROM users WHERE email = 'test@admin.com'), 'Leave request submitted by user1@manager.com.', 'LEAVE_SUBMITTED', FALSE, '2025-04-15T10:00:00Z'),
((SELECT id FROM users WHERE email = 'test@admin.com'), 'Leave request submitted by user2@manager.com.', 'LEAVE_SUBMITTED', FALSE, '2025-03-01T10:00:00Z');

-- Seed leave balances for users
INSERT INTO leave_balances (user_id, leave_type_id, entitlement, used, remaining) VALUES
((SELECT id FROM users WHERE email = 'user1@staff.com'), (SELECT id FROM leave_types WHERE name = 'Sick Leave'), 10, 0, 20),
((SELECT id FROM users WHERE email = 'user2@staff.com'), (SELECT id FROM leave_types WHERE name = 'Annual Leave'), 10, 12, 8),
((SELECT id FROM users WHERE email = 'user3@staff.com'), (SELECT id FROM leave_types WHERE name = 'Unpaid Leave'), 10, 0, 15),
((SELECT id FROM users WHERE email = 'user4@staff.com'), (SELECT id FROM leave_types WHERE name = 'Sick Leave'), 10, 0, 20),
((SELECT id FROM users WHERE email = 'user5@staff.com'), (SELECT id FROM leave_types WHERE name = 'Annual Leave'), 10, 10, 10),
((SELECT id FROM users WHERE email = 'user6@staff.com'), (SELECT id FROM leave_types WHERE name = 'Unpaid Leave'), 10, 0, 15),
((SELECT id FROM users WHERE email = 'user7@staff.com'), (SELECT id FROM leave_types WHERE name = 'Sick Leave'), 10, 0, 20),
((SELECT id FROM users WHERE email = 'user8@staff.com'), (SELECT id FROM leave_types WHERE name = 'Annual Leave'), 10, 14, 6),
((SELECT id FROM users WHERE email = 'user1@manager.com'), (SELECT id FROM leave_types WHERE name = 'Unpaid Leave'), 10, 0, 15),
((SELECT id FROM users WHERE email = 'user2@manager.com'), (SELECT id FROM leave_types WHERE name = 'Sick Leave'), 10, 10, 10);



