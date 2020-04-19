create table date_based_schedule_entry(
	id              bigserial,
	group_id        bigint                          not null,
	group_part      character varying               not null,
	auditory_id     bigint                          not null,
	class_number    int                             not null,
	class_type      character varying               not null,
	scheduled_on    date                            not null,
	description     character varying,
	teacher         character varying,
	created_on      timestamp without time zone     not null,
	primary key (id),
	foreign key(group_id) references groups(id),
	foreign key(auditory_id) references auditory(id),
	unique (group_id, group_part, auditory_id, class_number, class_type, scheduled_on)
);

alter table groups add column group_type character varying not null;
alter table groups add column study_form character varying not null;