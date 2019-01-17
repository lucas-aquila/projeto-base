SET search_path = test;

TRUNCATE banco_ideias CASCADE;

INSERT INTO banco_ideias(id, created, updated, beneficios, duracao, equipe, feedback, 
justificativas, nome, obj_smart, origem_recurso, produto, requisitos, 
situacao_banco_ideias, stakeholders, tipologia_id, 
usuario_id, qual_origem_recurso)
VALUES (1001, now(), now(), 'Aumento de faturamento; ampliação da satisfação do cliente; r', 10, 'gerente do projeto; analista do PMO; consultor de projeto; analista de processos.', '', 
'Projetos fora do padrão; insatisfação;', 'meu banco de ideias', 'Implantar o escritório corporativo de projetos na empresa Acme,', 1, 'Escritório da empresa Acme.', 'Deve ter fluxograma da gestão de projetos; deve ter ', 
0, 'Consultoria em gestão de projetos; fornecedor do software de GP.', 1001, 1001, 'Itaipu');

INSERT INTO banco_ideias(id, created, updated, beneficios, duracao, equipe, feedback, 
justificativas, nome, obj_smart, origem_recurso, produto, requisitos, 
situacao_banco_ideias, stakeholders, tipologia_id, 
usuario_id, qual_origem_recurso)
	VALUES (1002, now(), now(), '', 10, '', '', 
	'', 'Projeto de Teste 1: usuário 1001', '', 1, '', '', 
	0, '', 1004, 
	1001, '');
	
INSERT INTO banco_ideias(id, created, updated, beneficios, duracao, equipe, feedback, 
justificativas, nome, obj_smart, origem_recurso, produto, requisitos, 
situacao_banco_ideias, stakeholders, tipologia_id, 
usuario_id, qual_origem_recurso)
	VALUES (1003, now(), now(), '', 10, '', '', 
	'', 'Projeto de Teste 2: usuário 1002', '', 1, '', '', 
	1, '', 1004, 
	1002, '');	

INSERT INTO banco_ideias(id, created, updated, beneficios, duracao, equipe, feedback, 
justificativas, nome, obj_smart, origem_recurso, produto, requisitos, 
situacao_banco_ideias, stakeholders, tipologia_id, 
usuario_id, qual_origem_recurso)
	VALUES (1004, now(), now(), '', 10, '', '', 
	'', 'Projeto de Teste 3: usuário 1002', '', 1, '', '', 
	2, '', 1004, 
	1002, '');		


COMMIT;