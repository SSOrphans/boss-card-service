-- Cards
insert into boss.card (type_id, number_hash, last_four, account_id, created, active_since, expiration_date, pin,
                       cvv, confirmed, active, stolen)
values (0, '11111111111111111', 1111, 1, 0, 0, 0, 1111, 111, true, true, false);

insert into boss.card (type_id, number_hash, last_four, account_id, created, active_since, expiration_date, pin,
                       cvv, confirmed, active, stolen)
values (0, '22222222222222222', 2222, 1, 0, 0, 0, 111, 111, true, true, false);
