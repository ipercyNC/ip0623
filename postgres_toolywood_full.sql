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
					
-- Insert data				
					
					
					
INSERT INTO tool_type(id, name)  
VALUES(1, 'Chainsaw'), 
(2, 'Ladder'),
(3, 'Jackhammer');

INSERT INTO tool_brand(id, name) 
VALUES(1, 'Stihl'), 
(2, 'Werner'), (3, 'DeWalt'), (4, 'Ridgid');
			
INSERT into tool_charges(id, type_id, daily_charge, weekday_charge, weekend_charge, holiday_charge)
VALUES (1, 2, 1.99, 1, 1, 0),
(2, 1, 1.49, 1, 0, 1),
(3, 3, 2.99, 1, 0, 0);

INSERT into tool_choices(id, code, brand_id, type_id)
VALUES(1, 'CHNS', 1, 1),
(2, 'LADW', 2, 2),
(3, 'JAKD', 3, 3),
(4, 'JAKR', 4, 3);