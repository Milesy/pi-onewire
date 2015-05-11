CREATE TABLE public.one_wire_data
(
    id INT PRIMARY KEY NOT NULL,
    one_wire_id INT NOT NULL,
    data VARCHAR(512) NOT NULL,
    timestamp timestamp DEFAULT current_timestamp NOT NULL
);

CREATE TABLE public.one_wire
(
    id INT PRIMARY KEY NOT NULL,
    name VARCHAR(512) DEFAULT 'unnamed' NOT NULL,
    description VARCHAR(512) DEFAULT 'unnamed' NOT NULL,
    serial VARCHAR(32) NOT NULL,
    timestamp timestamp DEFAULT current_timestamp NOT NULL
);