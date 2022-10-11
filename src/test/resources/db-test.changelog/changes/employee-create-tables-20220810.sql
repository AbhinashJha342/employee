CREATE SEQUENCE primary_sequence
    START WITH 1
    INCREMENT BY 1;

CREATE TABLE IF NOT EXISTS employee (
    id serial not null primary key,
    date_of_birth varchar(255),
    deleted boolean not null,
    employee_id uuid,
    gender varchar(255),
    phone varchar(255),
    name_id int8
);

create table IF NOT EXISTS address (
    id serial not null primary key,
    exterior varchar(255),
    interior varchar(255),
    street varchar(255),
    city varchar(255),
    postal_code varchar(255),
    state varchar(255),
    employee_id int8
);

create table IF NOT EXISTS email (
    id serial not null primary key,
    email_address varchar(255),
    email_type varchar(255),
    email_id int8
);

create table IF NOT EXISTS employee_names (
    id serial not null primary key,
    first varchar(255),
    last varchar(255)
    );

create table IF NOT EXISTS employee_role_and_salary (
    id serial not null primary key,
    employee_id uuid,
    end_date varchar(255),
    role varchar(255),
    salary varchar(255),
    start_date varchar(255) not null
);

create table IF NOT EXISTS employee_salary_and_difference_view (
    id serial not null primary key,
    max_salary int4 not null,
    min_salary int4 not null,
    salary_diff int4 not null
);

create table IF NOT EXISTS user_and_role (
    id serial not null primary key,
    password varchar(255),
    role varchar(255),
    username varchar(255)
);