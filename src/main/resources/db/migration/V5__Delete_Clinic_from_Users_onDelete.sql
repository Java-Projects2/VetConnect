alter table user
    drop foreign key user_clinic_id_fk;

alter table user
    add constraint user_clinic_id_fk
        foreign key (clinic_id) references clinic (id)
            on delete set null;

alter table clinic
    alter column created_at set default (CURRENT_TIMESTAMP);