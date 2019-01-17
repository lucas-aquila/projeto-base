SET search_path = test;

TRUNCATE tipologia CASCADE;

INSERT INTO tipologia(id, created, updated, status, descricao, nome, sigla) 
	VALUES (1001, now(), now(), true, 'Formação, qualificação e capacitação de pessoas.', 'Educação', 'ED');
INSERT INTO tipologia(id, created, updated, status, descricao, nome, sigla) 
	VALUES (1002, now(), now(), true, 'Inserção de conhecimento na sociedade.', 'Extensão', 'EX');
INSERT INTO tipologia(id, created, updated, status, descricao, nome, sigla) 
	VALUES (1003, now(), now(), true, 'Geração de conhecimento.', 'Pesquisa', 'PQ');
INSERT INTO tipologia(id, created, updated, status, descricao, nome, sigla) 
	VALUES (1004, now(), now(), true, 'Geração de tecnologias.', 'Desenvolvimento', 'DT');
INSERT INTO tipologia(id, created, updated, status, descricao, nome, sigla) 
	VALUES (1005, now(), now(), true, 'Bens e serviços no mercado.', 'Inovação', 'IN');
INSERT INTO tipologia(id, created, updated, status, descricao, nome, sigla) 
	VALUES (1006, now(), now(), true, 'Bens e serviços disponibilizados para o PTI.', 'Infraestrutura', 'IF');
	
commit;