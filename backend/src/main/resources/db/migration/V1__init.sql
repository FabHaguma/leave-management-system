-- Initializes the migration history (this can be empty if you're not creating any tables yet)
-- Optional: You can create a dummy table just to test

CREATE TABLE IF NOT EXISTS dummy_init_table (
    id INT PRIMARY KEY,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
