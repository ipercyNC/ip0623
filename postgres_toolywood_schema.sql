 -- Table: public.tool_brand
	
-- DROP TABLE IF EXISTS public.tool_brand;

CREATE TABLE IF NOT EXISTS public.tool_brand
(
    id SERIAL,
    name character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT tool_brand_pkey PRIMARY KEY (id)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.tool_brand
    OWNER to root;
	

-- Table: public.tool_charges

-- DROP TABLE IF EXISTS public.tool_charges;

CREATE TABLE IF NOT EXISTS public.tool_charges
(
    id SERIAL,
    type_id integer NOT NULL,
    daily_charge double precision NOT NULL,
    weekday_charge integer NOT NULL,
    weekend_charge integer NOT NULL,
    holiday_charge integer NOT NULL,
    CONSTRAINT tool_charges_pkey PRIMARY KEY (id)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.tool_charges
    OWNER to root;
	
	
	
-- Table: public.tool_choices

-- DROP TABLE IF EXISTS public.tool_choices;

CREATE TABLE IF NOT EXISTS public.tool_choices
(
    id SERIAL,
    code character varying(255) COLLATE pg_catalog."default" NOT NULL,
    brand_id integer NOT NULL,
    type_id integer NOT NULL,
    CONSTRAINT tool_choices_pkey PRIMARY KEY (id)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.tool_choices
    OWNER to root;
	

-- Table: public.tool_type

-- DROP TABLE IF EXISTS public.tool_type;

CREATE TABLE IF NOT EXISTS public.tool_type
(
    id SERIAL,
    name character varying(255) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT tool_type_pkey PRIMARY KEY (id)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.tool_type
    OWNER to root;                   
					
