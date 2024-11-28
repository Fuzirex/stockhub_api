create table DEALER
(
    CNPJ                   varchar(14)  not null,
    NAME                   varchar(100) not null,
    EMAIL                  varchar(200) not null,
    MOBILE_PHONE           varchar(20)  null,
    LANDLINE_PHONE         varchar(20)  null,
    COUNTRY_ID             bigint       not null,
    COUNTRY_DESC           varchar(100) not null,
    STATE_ID               bigint       not null,
    STATE_DESC             varchar(100) not null,
    CITY_ID                bigint       not null,
    CITY_DESC              varchar(100) not null,
    STREET                 varchar(200) null,
    NUMBER                 bigint       null,
    ACTIVE                 tinyint      not null,
    PASSWORD               varchar(255) not null,
    AUDIT_CREATION_DATE    timestamp    not null,
    AUDIT_LAST_UPDATE_DATE timestamp    not null,

    constraint PK_DEALER
        primary key (CNPJ)
);
