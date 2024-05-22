CREATE TABLE match(
    id                      UUID        NOT NULL PRIMARY KEY,
    name                    VARCHAR(50) NOT NULL,
    latitude                DECIMAL NOT NULL,
    longitude               DECIMAL  NOT NULL
);