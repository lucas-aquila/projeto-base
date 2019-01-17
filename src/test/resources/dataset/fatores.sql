SET search_path = test;

--TRUNCATE fator CASCADE;

INSERT INTO fator( id, created, updated, descricao, tipo_fator, banco_ideias_id ) VALUES ( 1001, now(), now(), 'Premissa 01', 0, 1001 );
INSERT INTO fator( id, created, updated, descricao, tipo_fator, banco_ideias_id ) VALUES ( 1002, now(), now(), 'Premissa 02', 0, 1001 );
INSERT INTO fator( id, created, updated, descricao, tipo_fator, banco_ideias_id ) VALUES ( 1003, now(), now(), 'Restrição 01', 1, 1001 );
INSERT INTO fator( id, created, updated, descricao, tipo_fator, banco_ideias_id ) VALUES ( 1004, now(), now(), 'Restrição 02', 1, 1001 );
INSERT INTO fator( id, created, updated, descricao, tipo_fator, banco_ideias_id ) VALUES ( 1005, now(), now(), 'Risco 01', 2, 1001 );
INSERT INTO fator( id, created, updated, descricao, tipo_fator, banco_ideias_id ) VALUES ( 1006, now(), now(), 'Risco 02', 2, 1001 );

INSERT INTO fator( id, created, updated, descricao, tipo_fator, banco_ideias_id ) VALUES ( 1007, now(), now(), 'Premissa 01', 0, 1002 );
INSERT INTO fator( id, created, updated, descricao, tipo_fator, banco_ideias_id ) VALUES ( 1008, now(), now(), 'Premissa 02', 0, 1002 );
INSERT INTO fator( id, created, updated, descricao, tipo_fator, banco_ideias_id ) VALUES ( 1009, now(), now(), 'Restrição 01', 1, 1002 );
INSERT INTO fator( id, created, updated, descricao, tipo_fator, banco_ideias_id ) VALUES ( 1010, now(), now(), 'Restrição 02', 1, 1002 );
INSERT INTO fator( id, created, updated, descricao, tipo_fator, banco_ideias_id ) VALUES ( 1011, now(), now(), 'Risco 01', 2, 1002 );
INSERT INTO fator( id, created, updated, descricao, tipo_fator, banco_ideias_id ) VALUES ( 1012, now(), now(), 'Risco 02', 2, 1002 );

COMMIT;
