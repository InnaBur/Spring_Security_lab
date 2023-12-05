
DROP TABLE IF EXISTS tasks;
DROP TABLE IF EXISTS users;

create table users(
                        user_id SERIAL PRIMARY KEY,
                        username VARCHAR(255) UNIQUE,
                        password VARCHAR(255),
                        role VARCHAR(255) []
);

CREATE TABLE tasks (
                       id SERIAL PRIMARY KEY,
                       time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                       user_id BIGINT,
                       task TEXT,
                       task_status VARCHAR(20) DEFAULT 'PLANNED',
                       FOREIGN KEY (user_id) REFERENCES users(user_id)
);