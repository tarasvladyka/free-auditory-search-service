create table property(
	code        character varying               not null,
	value       character varying               not null,
	updated_on  timestamp without time zone,
	primary key(code)
);

insert into property (code, value, updated_on)
values('week.type', 'CHYS', current_timestamp);