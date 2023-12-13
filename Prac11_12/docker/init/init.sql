--
-- PostgreSQL database dump
--

-- Dumped from database version 15.4
-- Dumped by pg_dump version 15.4

-- Started on 2023-12-13 19:03:44

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;


--
-- TOC entry 219 (class 1259 OID 26331)
-- Name: books; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.books (
    cost double precision NOT NULL,
    book_id bigint NOT NULL,
    seller_number bigint NOT NULL,
    author character varying(255),
    name character varying(255) NOT NULL,
    product_type character varying(255) NOT NULL,
    CONSTRAINT books_product_type_check CHECK (((product_type)::text = ANY ((ARRAY['ELECTRONIC'::character varying, 'BOOK'::character varying, 'PLUMBING'::character varying])::text[])))
);


ALTER TABLE public.books OWNER TO postgres;

--
-- TOC entry 218 (class 1259 OID 26330)
-- Name: books_book_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.books_book_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.books_book_id_seq OWNER TO postgres;

--
-- TOC entry 3356 (class 0 OID 0)
-- Dependencies: 218
-- Name: books_book_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.books_book_id_seq OWNED BY public.books.book_id;




--
-- TOC entry 221 (class 1259 OID 26341)
-- Name: telephones; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.telephones (
    battery_capacity integer NOT NULL,
    cost double precision NOT NULL,
    phone_id bigint NOT NULL,
    seller_number bigint NOT NULL,
    manufacturer character varying(255) NOT NULL,
    name character varying(255) NOT NULL,
    product_type character varying(255) NOT NULL,
    CONSTRAINT telephones_product_type_check CHECK (((product_type)::text = ANY ((ARRAY['ELECTRONIC'::character varying, 'BOOK'::character varying, 'PLUMBING'::character varying])::text[])))
);


ALTER TABLE public.telephones OWNER TO postgres;

--
-- TOC entry 220 (class 1259 OID 26340)
-- Name: telephones_phone_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.telephones_phone_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.telephones_phone_id_seq OWNER TO postgres;

--
-- TOC entry 3357 (class 0 OID 0)
-- Dependencies: 220
-- Name: telephones_phone_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.telephones_phone_id_seq OWNED BY public.telephones.phone_id;


--
-- TOC entry 223 (class 1259 OID 26351)
-- Name: washing_machines; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.washing_machines (
    cost double precision NOT NULL,
    tank_volume integer NOT NULL,
    seller_number bigint NOT NULL,
    washing_machine_id bigint NOT NULL,
    manufacturer character varying(255) NOT NULL,
    name character varying(255) NOT NULL,
    product_type character varying(255) NOT NULL,
    CONSTRAINT washing_machines_product_type_check CHECK (((product_type)::text = ANY ((ARRAY['ELECTRONIC'::character varying, 'BOOK'::character varying, 'PLUMBING'::character varying])::text[])))
);


ALTER TABLE public.washing_machines OWNER TO postgres;

--
-- TOC entry 222 (class 1259 OID 26350)
-- Name: washing_machines_washing_machine_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.washing_machines_washing_machine_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.washing_machines_washing_machine_id_seq OWNER TO postgres;

--
-- TOC entry 3358 (class 0 OID 0)
-- Dependencies: 222
-- Name: washing_machines_washing_machine_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.washing_machines_washing_machine_id_seq OWNED BY public.washing_machines.washing_machine_id;


--
-- TOC entry 3193 (class 2604 OID 26334)
-- Name: books book_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.books ALTER COLUMN book_id SET DEFAULT nextval('public.books_book_id_seq'::regclass);


--
-- TOC entry 3194 (class 2604 OID 26344)
-- Name: telephones phone_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.telephones ALTER COLUMN phone_id SET DEFAULT nextval('public.telephones_phone_id_seq'::regclass);


--
-- TOC entry 3195 (class 2604 OID 26354)
-- Name: washing_machines washing_machine_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.washing_machines ALTER COLUMN washing_machine_id SET DEFAULT nextval('public.washing_machines_washing_machine_id_seq'::regclass);

--
-- TOC entry 3204 (class 2606 OID 26339)
-- Name: books books_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.books
    ADD CONSTRAINT books_pkey PRIMARY KEY (book_id);



--
-- TOC entry 3206 (class 2606 OID 26349)
-- Name: telephones telephones_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.telephones
    ADD CONSTRAINT telephones_pkey PRIMARY KEY (phone_id);


--
-- TOC entry 3208 (class 2606 OID 26359)
-- Name: washing_machines washing_machines_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.washing_machines
    ADD CONSTRAINT washing_machines_pkey PRIMARY KEY (washing_machine_id);


-- Completed on 2023-12-13 19:03:44

--
-- PostgreSQL database dump complete
--

