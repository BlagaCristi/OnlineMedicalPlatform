
create sequence role_sequence start 101;
create sequence user_sequence start 101;
create sequence patient_sequence start 101;
create sequence caregiver_sequence start 101;
create sequence doctor_sequence start 101;
create sequence medication_plan_sequence start 101;
create sequence medication_sequence start 101;
create sequence medication_plan_drugs_sequence start 101;
create sequence patient_activity_sequence start 101;

create type role_type as enum('ADMIN', 'PATIENT', 'DOCTOR', 'CAREGIVER');
create type gender_type as enum ('MALE', 'FEMALE');

create table user_role (
                         id integer default nextval('role_sequence') primary key,
                         role_name role_type not null
);

create table user_table (
                          id integer default nextval('user_sequence') primary key,
                          username varchar not null unique,
                          password varchar not null,
                          person_name varchar not null,
                          birth_date DATE not null,
                          gender gender_type not null,
                          address varchar not null,
                          email varchar not null,
                          role_id integer references user_role(id) on delete cascade on update cascade
);

create table caregiver (
                         id integer default nextval ('caregiver_sequence') primary key,
                         user_id integer references user_table(id) on delete cascade on update cascade
);

create table patient (
                       id integer default nextval('patient_sequence') primary key,
                       medical_record varchar,
                       user_id integer references user_table(id) on delete cascade on update cascade,
                       caregiver_id integer references caregiver(id) on delete cascade on update cascade
);

create table doctor (
                      id integer default nextval ('doctor_sequence') primary key,
                      user_id integer references user_table(id) on delete cascade on update cascade
);

create table medication (
                          id integer default nextval('medication_sequence') primary key,
                          name varchar not null,
                          side_effect varchar not null,
                          dosage varchar not  null
);

create table medication_plan (
                               id integer default nextval('medication_plan_sequence') primary key,
                               start_date DATE not null,
                               end_date DATE not null,
                               interval varchar not null,
                               hour_in_day int8 not null,
                               patient_id integer references patient(id) on delete cascade on update cascade,
                               doctor_id integer references doctor(id) on delete cascade on update cascade
);


create table medication_plan_drugs (
                                     id integer default nextval('medication_plan_drugs_sequence') primary key,
                                     medication_plan_id integer references medication_plan(id) on delete cascade on update cascade,
                                     medication_id integer references medication(id) on delete cascade on update cascade
);

create table activity_patient (
                       id integer default nextval('patient_activity_sequence') primary key,
                       activity varchar,
                       start_time TIMESTAMP not null,
                       end_time TIMESTAMP not null,
                       patient_id integer references patient(id) on delete cascade on update cascade
);