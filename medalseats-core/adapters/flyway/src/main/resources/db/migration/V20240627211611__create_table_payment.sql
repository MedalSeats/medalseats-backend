CREATE TABLE payment(
    id                      UUID  NOT NULL PRIMARY KEY,
    match_id                UUID  NOT NULL,
    email                   text  NOT NULL,
    status                  text  NOT NULL,
    proposed_at             TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at              TIMESTAMP WITH TIME ZONE NOT NULL,
    amount                  DECIMAL NOT NULL,
    currency                text NOT NULL,
    qr_code                 text NOT NULL
);
