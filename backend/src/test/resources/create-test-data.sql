DROP TABLE IF EXISTS users;

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

INSERT INTO users(usr_id, usr_name, usr_email, usr_salt, usr_password, usr_last_game_result,
                  usr_total_wins, usr_total_loses, usr_win_rate, usr_actual_win_streak,
                  usr_best_win_streak)
VALUES ('1', 'Plitochnik', 'Plitochnik@gmail.com', 'LExkyZ/GnDOAnntwOfrkwO',
        'Q2ti1YG5isZh9aN8csiy..KpCqt3v6Vbr3HDQ3Chojc1IdqdhNAgm', null, 0, 0, 0.00, 0, 0);