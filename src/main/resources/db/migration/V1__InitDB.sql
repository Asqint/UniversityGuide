create sequence hibernate_sequence start with 10 increment by 1;

create table element (
    id bigint not null,
    type varchar(255) not null,
    value varchar(2048) not null,
    page_id bigint,
    primary key (id)
);

create table page (
    id bigint not null,
    name_page varchar(255) not null,
    primary key (id)
);

create table user (
    id bigint not null,
    email varchar(255),
    message varchar(2048),
    user_name varchar(255),
    primary key (id)
);

alter table element
    add constraint element_page_fk
    foreign key (page_id) references page;