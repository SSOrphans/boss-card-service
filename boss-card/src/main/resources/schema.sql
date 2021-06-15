CREATE SCHEMA IF NOT EXISTS boss;

CREATE TABLE IF NOT EXISTS boss.user
(
    id          INT          UNSIGNED NOT NULL AUTO_INCREMENT UNIQUE,
    type_id     TINYINT      UNSIGNED NOT NULL,
    branch_id   INT          UNSIGNED NOT NULL,
    username    VARCHAR(16)           NOT NULL UNIQUE,
    email       VARCHAR(128)          NOT NULL UNIQUE,
    password    CHAR(64)              NOT NULL,
    created     BIGINT       UNSIGNED NOT NULL,
    deleted     BIGINT       UNSIGNED     NULL,
    enabled     BIT                   NOT NULL,
    locked      BIT                   NOT NULL,

    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS boss.card
(
    id              INT      UNSIGNED NOT NULL AUTO_INCREMENT UNIQUE,
    type_id         TINYINT  UNSIGNED NOT NULL,
    number_hash     CHAR(64)          NOT NULL UNIQUE,
    last_four       SMALLINT UNSIGNED NOT NULL,
    account_id      INT      UNSIGNED NOT NULL,
    created         BIGINT   UNSIGNED NOT NULL,
    active_since    BIGINT   UNSIGNED NOT NULL,
    expiration_date BIGINT   UNSIGNED NOT NULL,
    pin             CHAR(4)           NOT NULL,
    cvv             CHAR(3)           NOT NULL,
    confirmed       BIT               NOT NULL,
    active          BIT               NOT NULL,
    stolen          BIT               NOT NULL,
    PRIMARY KEY (id)
);