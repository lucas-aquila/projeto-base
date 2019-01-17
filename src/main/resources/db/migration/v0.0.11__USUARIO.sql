---------------------------------------------------------------------
-- CADASTRO: usuario
---------------------------------------------------------------------

SET search_path = auditing, pg_catalog;

SET default_with_oids = false;

CREATE TABLE usuario_audited (
    id bigint NOT NULL,
    revision bigint NOT NULL,
    revision_type smallint,
    codigo character varying(255),
    email character varying(150),
    nome character varying(100),
    senha character varying(100),
    status boolean,
    tentativas_login smallint,
    ultima_tentativa_login timestamp without time zone,
    ultimo_acesso timestamp without time zone
);

CREATE TABLE usuario_perfil_audited (
    revision bigint NOT NULL,
    usuario_id bigint NOT NULL,
    perfis integer NOT NULL,
    revision_type smallint
);


SET search_path = public, pg_catalog;


CREATE TABLE usuario (
    id bigint NOT NULL,
    created timestamp without time zone NOT NULL,
    updated timestamp without time zone NOT NULL,
    codigo character varying(255),
    email character varying(150) NOT NULL,
    nome character varying(100) NOT NULL,
    senha character varying(100) NOT NULL,
    status boolean NOT NULL,
    tentativas_login smallint,
    ultima_tentativa_login timestamp without time zone,
    ultimo_acesso timestamp without time zone
);

CREATE SEQUENCE usuario_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER SEQUENCE usuario_id_seq OWNED BY usuario.id;


CREATE TABLE usuario_perfil (
    usuario_id bigint NOT NULL,
    perfis integer NOT NULL
);


ALTER TABLE ONLY usuario ALTER COLUMN id SET DEFAULT nextval('usuario_id_seq'::regclass);


SET search_path = auditing, pg_catalog;
SET search_path = public, pg_catalog;


SELECT pg_catalog.setval('usuario_id_seq', 2, false);


SET search_path = auditing, pg_catalog;

ALTER TABLE ONLY usuario_audited
    ADD CONSTRAINT usuario_audited_pkey PRIMARY KEY (id, revision);

ALTER TABLE ONLY usuario_perfil_audited
    ADD CONSTRAINT usuario_perfil_audited_pkey PRIMARY KEY (revision, usuario_id, perfis);


SET search_path = public, pg_catalog;


ALTER TABLE ONLY usuario
    ADD CONSTRAINT uk_usuario_email UNIQUE (email);


ALTER TABLE ONLY usuario_perfil
    ADD CONSTRAINT usuario_perfil_pkey PRIMARY KEY (usuario_id, perfis);

ALTER TABLE ONLY usuario
    ADD CONSTRAINT usuario_pkey PRIMARY KEY (id);


SET search_path = auditing, pg_catalog;

ALTER TABLE ONLY usuario_audited
    ADD CONSTRAINT fk_usuario_audited_revision FOREIGN KEY (revision) REFERENCES revision(id);

ALTER TABLE ONLY usuario_perfil_audited
    ADD CONSTRAINT fk_usuario_perfil_audited_revision FOREIGN KEY (revision) REFERENCES revision(id);


SET search_path = public, pg_catalog;


ALTER TABLE ONLY usuario_perfil
    ADD CONSTRAINT fk_usuario_perfil_usuario_id FOREIGN KEY (usuario_id) REFERENCES usuario(id);


--------------------------------------------------------------------- 
-- DEFAULT DATA
---------------------------------------------------------------------

INSERT INTO usuario(
            id, created, updated, status, email, nome, senha, codigo, ultimo_acesso, 
            tentativas_login, ultima_tentativa_login)
    VALUES (1, now(), now(), true, 'admin@admin.com', 'Administrador de Sistemas', '$2a$10$bAdAVLvM.k3DqPaPYi0gnO1OffPSHLref8MElAk.u.fFQ17v9YKC2', null, null, 
            null, null);

INSERT INTO usuario_perfil( usuario_id, perfis ) 
	VALUES ( 1, 0 );

