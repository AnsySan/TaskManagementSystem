CREATE TABLE user(
    id SERIAL PRIMARY KEY,
    username VARCHAR(255),
    password VARCHAR(255),
    email VARCHAR(255) UNIQUE,
    role VARCHAR(255)
);

CREATE TABLE task(
    id SERIAL PRIMARY KEY,
    header VARCHAR(100) NOT NULL,
    description VARCHAR(200) NOT NULL,
    status VARCHAR(20)  NOT NULL,
    priority VARCHAR(20)  NOT NULL,
    created_date DATE NOT NULL,
    updated_date DATE NOT NULL,
    author_id BIGINT NOT NULL REFERENCES user (id),
    performer_id BIGINT NOT NULL REFERENCES user (id)
);

CREATE TABLE comment
(
    id SERIAL PRIMARY KEY,
    text TEXT NOT NULL,
    created_date DATE NOT NULL,
    updated_date DATE NOT NULL,
    author_email VARCHAR(255),
    task_id BIGINT REFERENCES task (id) ON DELETE CASCADE
);