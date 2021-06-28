create sequence hibernate_sequence start with 10 increment by 1;

create table elements (
    id bigint not null,
    value varchar(max) not null,
    page_id bigint,
    editor varchar(255),
    primary key (id)
);

create table templates (
    id bigint not null,
    name varchar(255),
    value varchar(max) not null,
    user_id bigint,
    primary key (id)
);

create table pages (
    id bigint not null,
    name_page varchar(255) not null,
    parent_page_id bigint,
    primary key (id)
);

create table users (
    id bigint not null,
    user_name varchar(255),
    password varchar(255),
    primary key (id)
);

create table feedback (
     id bigint not null,
     name varchar(255),
     email varchar(255),
     message varchar(255),
     primary key (id)
);

create table roles (
    id bigint not null,
    name varchar(255),
    primary key (id)
);

create table users_roles (
    users_id bigint not null,
    roles_id bigint not null,
    primary key (users_id, roles_id)
);

alter table elements
    add constraint elements_pages_fk
    foreign key (page_id) references pages;

alter table users_roles
    add constraint users_roles_roles_fk
    foreign key (roles_id) references roles;

alter table users_roles
    add constraint users_roles_users_fk
    foreign key (users_id) references users;