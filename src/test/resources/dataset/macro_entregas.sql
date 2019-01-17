SET search_path = test;

--TRUNCATE macro_entrega CASCADE;

INSERT INTO macro_entrega(id, created, updated, data_final, data_inicial, nome, orcamento, banco_ideias_id) 
	VALUES (1001, now(), now(), now()+interval '5 days', now(), 'Relatório de Avaliação de Maturidade.', 497.12, 1001);
INSERT INTO macro_entrega(id, created, updated, data_final, data_inicial, nome, orcamento, banco_ideias_id) 
	VALUES (1002, now(), now()+interval '8 days', '2018-03-13', now(), 'Processos de Gestão de Portfólio.', 666.88, 1001);
INSERT INTO macro_entrega(id, created, updated, data_final, data_inicial, nome, orcamento, banco_ideias_id) 
	VALUES (1003, now(), now()+interval '12 days', '2018-03-13', now(), 'Processos de Gestão de Programas.', 8475.64, 1001);
INSERT INTO macro_entrega(id, created, updated, data_final, data_inicial, nome, orcamento, banco_ideias_id) 
	VALUES (1004, now(), now()+interval '28 days', '2018-03-13', now(), 'Processos de Gestão de Projetos.', 128.00, 1001);
INSERT INTO macro_entrega(id, created, updated, data_final, data_inicial, nome, orcamento, banco_ideias_id) 
	VALUES (1005, now(), now()+interval '45 days', '2018-04-16', now(), 'Treinamentos', 342.12, 1001);
	
INSERT INTO macro_entrega(id, created, updated, data_final, data_inicial, nome, orcamento, banco_ideias_id) 
	VALUES (1006, now(), now(), now()+interval '5 days', now(), 'PRIMERIA ENTREGA', 888.55, 1002);
INSERT INTO macro_entrega(id, created, updated, data_final, data_inicial, nome, orcamento, banco_ideias_id) 
	VALUES (1007, now(), now(), now()+interval '7 days', now(), 'SEGUNDA ENTREGA', 9565.55, 1002);
INSERT INTO macro_entrega(id, created, updated, data_final, data_inicial, nome, orcamento, banco_ideias_id) 
	VALUES (1008, now(), now(), now()+interval '14 days', now(), 'TERCEIRA ENTREGA', 5487.44, 1002);
INSERT INTO macro_entrega(id, created, updated, data_final, data_inicial, nome, orcamento, banco_ideias_id) 
	VALUES (1009, now(), now(), now()+interval '28 days', now(), 'MACRO ENTREGA LOREN IPSUM', 10000.00, 1002);	

commit;