CREATE TABLE users
(
    id uuid NOT NULL,
    timestamp_created timestamp without time zone NOT NULL,
    timestamp_modified timestamp without time zone NOT NULL,
    email character varying(255),
    first_name character varying(255) NOT NULL,
    last_name character varying(255) NOT NULL,
    password character varying(255) NOT NULL,
    active boolean NOT NULL DEFAULT true,
    CONSTRAINT users_pkey PRIMARY KEY (id),
    CONSTRAINT uk_6dotkott2kjsp8vw4d0m25fb7 UNIQUE (email)
);

CREATE TABLE authority
(
    id uuid NOT NULL,
    timestamp_created timestamp without time zone NOT NULL,
    timestamp_modified timestamp without time zone NOT NULL,
    name character varying(255),
    CONSTRAINT authority_pkey PRIMARY KEY (id)
);

CREATE TABLE authority_role
(
    user_id uuid NOT NULL,
    authority_id uuid NOT NULL,
    CONSTRAINT fkbx74hejmy633p202glaq0yj8t FOREIGN KEY (authority_id)
        REFERENCES authority (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fkrwn5r2cfq80jlnqsnswdc46p5 FOREIGN KEY (user_id)
        REFERENCES users (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

CREATE TABLE IF NOT EXISTS category
(
    id uuid NOT NULL,
    timestamp_created timestamp without time zone NOT NULL,
    timestamp_modified timestamp without time zone NOT NULL,
    description character varying(255),
    name character varying(255),
    CONSTRAINT category_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS product
(
    id uuid NOT NULL,
    timestamp_created timestamp without time zone NOT NULL,
    timestamp_modified timestamp without time zone NOT NULL,
    availability boolean NOT NULL,
    description character varying(255),
    image_path character varying(255),
    name character varying(255),
    price double precision NOT NULL,
    category_id uuid,
    CONSTRAINT product_pkey PRIMARY KEY (id),
    CONSTRAINT fk1mtsbur82frn64de7balymq9s FOREIGN KEY (category_id)
    REFERENCES category (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
)
