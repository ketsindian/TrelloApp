
-- Drop table

-- DROP TABLE public.card_d;

CREATE TABLE public.card_d (
	card_id int4 NOT NULL,
	card_name varchar NOT NULL,
	card_desc varchar NULL,
	CONSTRAINT card_d_pk PRIMARY KEY (card_id),
	CONSTRAINT card_d_un_1 UNIQUE (card_id, card_name, card_desc)
);

-- Drop table

-- DROP TABLE public.list_d;

CREATE TABLE public.list_d (
	list_id int4 NOT NULL,
	list_name varchar NOT NULL,
	list_desc varchar NULL,
	CONSTRAINT list_d_pk PRIMARY KEY (list_id),
	CONSTRAINT list_d_un UNIQUE (list_name, list_desc, list_id)
);

-- Drop table

-- DROP TABLE public.user_d;

CREATE TABLE public.user_d (
	user_id int4 NOT NULL,
	first_name varchar NULL,
	last_name varchar NULL,
	email_id varchar NOT NULL,
	phone_number varchar NULL,
	CONSTRAINT user_d_pk PRIMARY KEY (user_id),
	CONSTRAINT user_d_un UNIQUE (email_id)
);
CREATE INDEX user_d_email_id_idx ON public.user_d USING btree (email_id);
CREATE INDEX user_d_first_name_idx ON public.user_d USING btree (first_name, last_name);
CREATE INDEX user_d_last_name_idx ON public.user_d USING btree (last_name);

-- Drop table

-- DROP TABLE public.board_d;

CREATE TABLE public.board_d (
	board_id int4 NOT NULL,
	board_name varchar NOT NULL,
	board_owner_id int4 NOT NULL,
	CONSTRAINT board_d_pk PRIMARY KEY (board_id),
	CONSTRAINT board_d_un UNIQUE (board_name),
	CONSTRAINT board_d_fk FOREIGN KEY (board_owner_id) REFERENCES user_d(user_id) ON UPDATE RESTRICT ON DELETE RESTRICT
);

-- Drop table

-- DROP TABLE public.board_list_xref;

CREATE TABLE public.board_list_xref (
	board_id int4 NOT NULL,
	list_id int4 NOT NULL,
	CONSTRAINT board_list_xref_pk_1 PRIMARY KEY (board_id, list_id),
	CONSTRAINT board_list_xref_un UNIQUE (list_id),
	CONSTRAINT board_list_xref_fk FOREIGN KEY (board_id) REFERENCES board_d(board_id) ON UPDATE CASCADE ON DELETE CASCADE,
	CONSTRAINT board_list_xref_fk_1 FOREIGN KEY (list_id) REFERENCES list_d(list_id) ON UPDATE CASCADE ON DELETE CASCADE
);

-- Drop table

-- DROP TABLE public.board_user_xref;

CREATE TABLE public.board_user_xref (
	board_id int4 NOT NULL,
	primary_user_id int4 NOT NULL,
	secondary_user_id int4 NOT NULL,
	CONSTRAINT board_user_xref_un UNIQUE (board_id, primary_user_id, secondary_user_id),
	CONSTRAINT board_user_xref_fk FOREIGN KEY (board_id) REFERENCES board_d(board_id) ON UPDATE CASCADE ON DELETE CASCADE,
	CONSTRAINT board_user_xref_fk_1 FOREIGN KEY (primary_user_id) REFERENCES user_d(user_id) ON UPDATE CASCADE ON DELETE CASCADE,
	CONSTRAINT board_user_xref_fk_2 FOREIGN KEY (secondary_user_id) REFERENCES user_d(user_id) ON UPDATE CASCADE ON DELETE CASCADE
);

-- Drop table

-- DROP TABLE public.list_card_xref;

CREATE TABLE public.list_card_xref (
	list_id int4 NOT NULL,
	card_id int4 NOT NULL,
	card_priority_id int4 NOT NULL,
	CONSTRAINT list_card_xref_pk PRIMARY KEY (list_id, card_id, card_priority_id),
	CONSTRAINT list_card_xref_fk FOREIGN KEY (list_id) REFERENCES list_d(list_id) ON UPDATE CASCADE ON DELETE CASCADE,
	CONSTRAINT list_card_xref_fk_1 FOREIGN KEY (card_id) REFERENCES card_d(card_id) ON UPDATE CASCADE ON DELETE CASCADE
);

-- Drop table

-- DROP TABLE public.user_security_d;

CREATE TABLE public.user_security_d (
	user_id int4 NOT NULL,
	"password" varchar NOT NULL,
	CONSTRAINT newtable_pk PRIMARY KEY (user_id, password),
	CONSTRAINT user_security_d_fk FOREIGN KEY (user_id) REFERENCES user_d(user_id) ON UPDATE CASCADE ON DELETE CASCADE
);
