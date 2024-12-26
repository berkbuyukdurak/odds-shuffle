CREATE TABLE IF NOT EXISTS matches (
    match_id UUID PRIMARY KEY,
    league VARCHAR(255) NOT NULL,
    home_team VARCHAR(255) NOT NULL,
    away_team VARCHAR(255) NOT NULL,
    draw_odds DOUBLE NOT NULL,
    away_odds DOUBLE NOT NULL,
    start_time TIMESTAMP NOT NULL
    );