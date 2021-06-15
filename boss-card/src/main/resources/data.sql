-- Users
insert into boss.user (type_id, branch_id, username, email, password, created, deleted, enabled, locked)
-- PASSWORD: password2021?!
values (5, 1, 'Admin', 'admin@boss.com', '$2y$10$GodHI/CpXYoIxC2r9SBIROarrnmzdTl4vnCgoy3tEs4//RDYbtf/. ', 0, NULL, true, false);

insert into boss.user (type_id, branch_id, username, email, password, created, deleted, enabled, locked)
values (4, 1, 'Sora', 'sorakatadzuma@gmail.com', '$2y$10$8GxWo9OZN9FRri1vR7wC.e7cVfIApSOFLL9dwDcKUgSracduyIak6 ', 0, NULL, true, false);

-- Cards
insert into boss.card (type_id, number_hash, last_four, account_id, created, active_since, expiration_date, pin,
                       cvv, confirmed, active, stolen)
-- 6766048474492311
values (0, '$2y$10$TXGKrHHL2zU97JOPx56OOemig.JFin8JwDz5jLNZj2o/fzapHmiha', 2311, 1, 1546600672000, 1546601672000,
        1735689600000, '0219', '288', true, true, false);

insert into boss.card (type_id, number_hash, last_four, account_id, created, active_since, expiration_date, pin,
                       cvv, confirmed, active, stolen)
-- 2462002559567896
values (1, '$2y$10$wOM0dRn5y9rKNV/XVSRNy.uvpWlPtTi09nDL1XKPW1riH4A9oLEj6', 7896, 1, 1546600672000, 0, 0, '0380', '170',
        true, false, false);

insert into boss.card (type_id, number_hash, last_four, account_id, created, active_since, expiration_date, pin,
                       cvv, confirmed, active, stolen)
-- 0893407263424121
values (0, '$2y$10$Fhqmy1YiMgUt22RESWNRne/opJNZlt7Fh.l6fNnwIs6vBok10b5wq', 4121, 1, 1546600672000, 1546600672000,
        1735689600000, '1034', '648', true, true, false);

insert into boss.card (type_id, number_hash, last_four, account_id, created, active_since, expiration_date, pin,
                       cvv, confirmed, active, stolen)
-- 5108903698064899
values (2, '$2y$10$vBkabVKz3hMB4EQhOWOw8uI7dh29xke4VBzsxPAEu.py6UbkOfzr2', 4899, 1, 1546600672000, 1546600672000,
        1735689600000, '2391', '085', true, false, true);