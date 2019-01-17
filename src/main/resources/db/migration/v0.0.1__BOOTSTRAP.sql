---------------------------------------------------------------------
-- FILTER
---------------------------------------------------------------------

CREATE EXTENSION IF NOT EXISTS unaccent;

CREATE OR REPLACE FUNCTION FILTER(needles text, VARIADIC haystacks text [])
  RETURNS boolean AS $$
SELECT trim(needles) IS NULL OR trim(needles) = '' OR EXISTS(
    SELECT DISTINCT 1
    FROM unnest(haystacks) haystack,
          unnest(string_to_array(needles, ',')) needle
    WHERE unaccent(haystack) ILIKE '%' || unaccent(needle) || '%');
$$ LANGUAGE SQL;


--------------------------------------------------------------------- 
-- DEFAULT SCHEMAS: public and auditing
---------------------------------------------------------------------

CREATE SCHEMA IF NOT EXISTS auditing;
CREATE SCHEMA IF NOT EXISTS public;


---------------------------------------------------------------------
-- REVISION
---------------------------------------------------------------------

SET search_path = auditing, pg_catalog;

SET default_with_oids = false;

CREATE TABLE revision (
    id bigint NOT NULL,
    "timestamp" bigint NOT NULL,
    user_id bigint
);

CREATE SEQUENCE revision_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER SEQUENCE revision_id_seq OWNED BY revision.id;

ALTER TABLE ONLY revision ALTER COLUMN id SET DEFAULT nextval('revision_id_seq'::regclass);

SELECT pg_catalog.setval('revision_id_seq', 1, false);

ALTER TABLE ONLY revision
    ADD CONSTRAINT revision_pkey PRIMARY KEY (id);