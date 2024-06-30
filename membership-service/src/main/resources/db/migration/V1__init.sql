CREATE TABLE memberships (
                             id SERIAL PRIMARY KEY,
                             name VARCHAR(100),
                             description TEXT,
                             months INTEGER,
                             price DECIMAL(10, 2)
);

CREATE TABLE user_membership (
                                  user_id INTEGER,
                                  membership_id INTEGER,
                                  start_date DATE,
                                  end_date DATE,
                                  freeze_until DATE,
                                  last_freeze DATE,
                                  PRIMARY KEY (user_id, membership_id)
);
