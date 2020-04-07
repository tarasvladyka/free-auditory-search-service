create table institute(
    id          bigserial                       not null,
	abbr        character varying               not null,
	created_on  timestamp without time zone     not null,
	primary key(id)
);

create table groups(
	id              bigserial,
	institute_id    bigint                      not null,
	abbr            character varying           not null,
	created_on      timestamp without time zone not null,
	primary key(id),
	foreign key(institute_id) references institute(id)
);

create table campus(
	id          bigserial,
	"name"      character varying               not null,
	created_on  timestamp without time zone     not null,
	primary key(id)
);

create table auditory(
	id              bigserial,
	campus_id       bigint                          not null,
	"number"        varchar                         not null,
	created_on      timestamp without time zone     not null,
	primary key(id),
	foreign key(campus_id) references campus(id)
);

create table schedule_entry(
	id              bigserial,
	group_id        bigint                          not null,
	group_part      character varying               not null,
	auditory_id     bigint                          not null,
	occurrence      character varying               not null,
	"day"           character varying               not null,
	class_number    int                             not null,
	class_type      character varying               not null,
	created_on      timestamp without time zone     not null,
	primary key (id),
	foreign key(group_id) references groups(id),
	foreign key(auditory_id) references auditory(id),
	unique (group_id, group_part, auditory_id, occurrence, "day", class_number, class_type)
);
