CREATE SCHEMA IF NOT EXISTS boss;
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
    pin             SMALLINT UNSIGNED NOT NULL,
    cvv             TINYINT  UNSIGNED NOT NULL,
    confirmed       BIT               NOT NULL,
    active          BIT               NOT NULL,
    stolen          BIT               NOT NULL,
    PRIMARY KEY (id)
);
