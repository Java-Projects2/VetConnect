alter table clinic
drop foreign key clinic_user_id_fk;

alter table clinic
drop column vet_id;

alter table user
    add clinic_id bigint null;

alter table user
    add constraint user_clinic_id_fk
        foreign key (clinic_id) references clinic (id);