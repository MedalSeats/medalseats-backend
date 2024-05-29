CREATE TABLE ticket(
    category                TEXT    NOT NULL,
    amount                  NUMERIC NOT NULL,
    currency                TEXT    NOT NULL,
    match_id                UUID    NOT NULL,

    CONSTRAINT ticket_pkey primary key (category, match_id),
    CONSTRAINT fk_match FOREIGN KEY(match_id) REFERENCES match(id)
);