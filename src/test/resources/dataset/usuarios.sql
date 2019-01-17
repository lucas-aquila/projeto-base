SET search_path = test;

TRUNCATE usuario CASCADE;

	INSERT INTO usuario(
	            id, created, updated, status, email, nome, senha, codigo, ultimo_acesso, 
	            tentativas_login, ultima_tentativa_login, organizacao_id)
	    VALUES (1001, now(), now(), true, 'admin@admin.com', 'Administrador de Sistemas', '$2a$10$bAdAVLvM.k3DqPaPYi0gnO1OffPSHLref8MElAk.u.fFQ17v9YKC2', null, null, 
	            null, null, null);
	            
	INSERT INTO usuario(
	            id, created, updated, status, email, nome, senha, codigo, ultimo_acesso, 
	            tentativas_login, ultima_tentativa_login, organizacao_id)
	    VALUES (1002, now(), now(), true, 'gerente@gerente.com', 'Gerente de Projetos', '$2a$10$bAdAVLvM.k3DqPaPYi0gnO1OffPSHLref8MElAk.u.fFQ17v9YKC2', null, null, 
	            null, null, null);
	            
	INSERT INTO usuario(
	            id, created, updated, status, email, nome, senha, codigo, ultimo_acesso, 
	            tentativas_login, ultima_tentativa_login, organizacao_id)
	    VALUES (1003, now(), now(), true, 'usuario@usuario.com', 'Usuário normal', '$2a$10$bAdAVLvM.k3DqPaPYi0gnO1OffPSHLref8MElAk.u.fFQ17v9YKC2', null, null, 
	            null, null, null);	            