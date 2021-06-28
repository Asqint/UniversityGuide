create sequence hibernate_sequence start with 10 increment by 1;

create table element (
    id bigint not null,
    value varchar(max) not null,
    page_id bigint,
    primary key (id)
);

create table template (
    id bigint not null,
    name varchar(255),
    value varchar(max) not null,
    user_id bigint,
    primary key (id)
);

create table page (
    id bigint not null,
    name_page varchar(255) not null,
    parent_page_id bigint,
    primary key (id)
);

create table usr (
    id bigint not null,
    user_name varchar(255),
    password varchar(255),
    role varchar(255),
    primary key (id)
);

create table feedback (
     id bigint not null,
     name varchar(255),
     email varchar(255),
     message varchar(255),
     primary key (id)
);

alter table element
    add constraint element_page_fk
    foreign key (page_id) references page;
