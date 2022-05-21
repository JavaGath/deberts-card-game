DO
$do$
    BEGIN
        IF NOT EXISTS(SELECT
                      FROM pg_roles
                      WHERE rolname = 'deberts') THEN
            CREATE USER deberts WITH ENCRYPTED PASSWORD 'password123';
            GRANT ALL PRIVILEGES ON DATABASE postgres TO deberts;
            GRANT SELECT ON ALL TABLES IN SCHEMA public TO deberts;
            GRANT UPDATE ON ALL TABLES IN SCHEMA public TO deberts;
            GRANT INSERT ON ALL TABLES IN SCHEMA public TO deberts;
            GRANT DELETE ON ALL TABLES IN SCHEMA public TO deberts;
            GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO deberts;
            GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA public to deberts;
        END IF;
    END
$do$;

CREATE TABLE IF NOT EXISTS users
(
    usr_id                SERIAL PRIMARY KEY,
    usr_name              VARCHAR(50) UNIQUE NOT NULL,
    usr_email             VARCHAR(50) UNIQUE NOT NULL,
    usr_salt              TEXT               NOT NULL,
    usr_password          TEXT               NOT NULL,
    usr_last_game_result  VARCHAR(4),
    usr_total_wins        INT,
    usr_total_loses       INT,
    usr_win_rate          NUMERIC(5, 2),
    usr_actual_win_streak INT,
    usr_best_win_streak   INT
);

CREATE TABLE IF NOT EXISTS tokens
(
    tkn_usr_id SERIAL,
    tkn_tokens TEXT,
    constraint fk_users
        foreign key (tkn_usr_id)
            REFERENCES users (usr_id)
);