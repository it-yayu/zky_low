/*==============================================================*/
/* table : temp_affix                                           */
/*==============================================================*/
create table temp_affix (
ta_rbs               char(16)             not null,
ta_datetime          char(14)             not null,
ta_num               char(4)              not null,
ta_filecliname       varchar(255)         null,
ta_filesername       varchar(255)         null,
ta_flag              char(1)              null,
ta_comment           varchar(255)         null,
 primary key  (ta_rbs, ta_datetime, ta_num)
);