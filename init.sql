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
            GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO deberts;
            GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA public to deberts;
        END IF;
    END
$do$;

CREATE TABLE IF NOT EXISTS players
(
    plr_id                SERIAL PRIMARY KEY,
    plr_name              VARCHAR(50) UNIQUE NOT NULL,
    plr_last_game_result  VARCHAR(4)         NOT NULL,
    plr_total_wins        INT                NOT NULL,
    plr_total_loses       INT                NOT NULL,
    plr_win_rate          NUMERIC(5, 2),
    plr_actual_win_streak INT                NOT NULL,
    plr_best_win_streak   INT                NOT NULL
);