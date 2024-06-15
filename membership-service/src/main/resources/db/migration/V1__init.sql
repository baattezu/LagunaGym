CREATE TABLE memberships (
                             id SERIAL PRIMARY KEY,
                             name VARCHAR(100),
                             description TEXT,
                             duration_days INTEGER,
                             price DECIMAL(10, 2)
);

CREATE TABLE user_memberships (
                                  user_id INTEGER,
                                  membership_id INTEGER,
                                  start_date DATE,
                                  end_date DATE,
                                  PRIMARY KEY (user_id, membership_id)
);
