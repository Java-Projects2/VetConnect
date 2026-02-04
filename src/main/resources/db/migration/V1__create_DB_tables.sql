
create table user
(
    id         bigint auto_increment
        primary key,
    name       varchar(255)                                            not null,
    email      varchar(255)                                            not null,
    password   varchar(255)                                            not null,
    role       enum ('user', 'vet', 'admin') default 'user'            not null,
    created_at timestamp                     default current_timestamp null,
    constraint unique_email
        unique (email)
);

create table clinic
(
    id      bigint auto_increment
        primary key,
    name        varchar(255) not null,
    address     varchar(255) not null,
    phone       varchar(11)  not null,
    createdAt   timestamp null,
    updatedAt   timestamp null,
    vet_id      bigint       not null,
    constraint clinic_user_id_fk
        foreign key (vet_id) references user (id)
);

create table pet
(
    id      bigint auto_increment
        primary key,
    name    bigint       not null,
    type    varchar(255) not null,
    breed   varchar(255) not null,
    age     int          not null,
    user_id bigint       not null,
    constraint pet_user_id_fk
        foreign key (user_id) references user (id)
);

create table appointment
(
    id               bigint auto_increment
        primary key,
    appointment_time datetime                                                                                not null,
    status           enum ('pending', 'paid', 'attended', 'cancelled', 'refunded') default 'pending'         not null,
    notes            text                                                                                    null,
    user_id          bigint                                                                                  not null,
    pet_id           bigint                                                                                  not null,
    vet_id           bigint                                                                                  not null,
    clinic_id        bigint                                                                                  not null,
    created_at       timestamp                                                     default current_timestamp null,
    constraint appointment_clinic_id_fk
        foreign key (clinic_id) references clinic (id),
    constraint appointment_pet_id_fk
        foreign key (pet_id) references pet (id),
    constraint appointment_user_id_fk
        foreign key (user_id) references user (id),
    constraint appointment_user_id_fk_2
        foreign key (vet_id) references user (id)
);

