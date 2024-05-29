CREATE TABLE account(
    id                      UUID  NOT NULL PRIMARY KEY,
    name                    text  NOT NULL,
    email                   text  NOT NULL,
    birthday                TIMESTAMP WITH TIME ZONE NOT NULL,
    password                text NOT NULL
);