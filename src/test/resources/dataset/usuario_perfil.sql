SET search_path = test;

TRUNCATE usuario_perfil CASCADE;

INSERT INTO usuario_perfil( usuario_id, perfis ) 
    VALUES ( 1001, 0 ); 
    
INSERT INTO usuario_perfil( usuario_id, perfis ) 
    VALUES ( 1002, 1 );

INSERT INTO usuario_perfil( usuario_id, perfis ) 
    VALUES ( 1003, 2 );    