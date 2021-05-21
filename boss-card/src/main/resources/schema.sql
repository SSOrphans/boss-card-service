create schema if not exists boss;
create table if not exists boss.card
(
    id              INT      NOT NULL AUTO_INCREMENT UNIQUE,
    type_id         TINYINT  NOT NULL,
    number_hash     char(64) NOT NULL,
    account_id      int      NOT NULL,
    created         DATETIME NOT NULL,
    active_since    DATE     NOT NULL,
    expiration_date DATE     NOT NULL,
    pin             smallint NOT NULL,
    cvv             tinyint  NOT NULL,
    confirmed       bit(1)   NOT NULL,
    active          bit(1)   NOT NULL,
    stolen          bit(1)   NOT NULL,
    PRIMARY KEY (id)
    );
