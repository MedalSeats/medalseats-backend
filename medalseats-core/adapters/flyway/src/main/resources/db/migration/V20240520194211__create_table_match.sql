CREATE TABLE match(
    id                     UUID NOT NULL PRIMARY KEY,
    title                  text NOT NULL,
    subtitle               text NOT NULL,
    description            text NOT NULL,
    date                   TIMESTAMP WITH TIME ZONE NOT NULL,
    latitude               DECIMAL NOT NULL,
    longitude              DECIMAL NOT NULL,
    banner_url             text NOT NULL,
    stadium_url            text NOT NULL
);