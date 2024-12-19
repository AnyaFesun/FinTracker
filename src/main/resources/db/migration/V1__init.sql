CREATE TABLE users (
                       id BIGSERIAL PRIMARY KEY,
                       name VARCHAR(255) NOT NULL
);

CREATE TABLE category (
                          id BIGSERIAL PRIMARY KEY,
                          name VARCHAR(255) NOT NULL
);

CREATE TABLE records (
                         id BIGSERIAL PRIMARY KEY,
                         user_id BIGINT NOT NULL,
                         category_id BIGINT NOT NULL,
                         creation_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                         costs DOUBLE PRECISION NOT NULL,
                         CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
                         CONSTRAINT fk_category FOREIGN KEY (category_id) REFERENCES category(id) ON DELETE CASCADE
);
