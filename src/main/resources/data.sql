drop table if exists device;

create table device(
    uuid uuid DEFAULT gen_random_uuid(),
    name varchar(54),
    athena_version varchar(10),
    toit_firmware_version varchar(10),
    date_created timestamp
)