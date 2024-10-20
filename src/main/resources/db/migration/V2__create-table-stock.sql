create table STOCK
(
    CHASSIS_NUMBER         varchar(20) not null,
    MODEL                  varchar(20) not null,
    COMMERCIAL_SERIES      varchar(20) not null,
    ITEM_CODE              varchar(20) not null,
    DEALER                 varchar(14) not null,
    STATUS                 int         not null,
    AUDIT_CREATION_DATE    datetime    not null,
    AUDIT_LAST_UPDATE_DATE datetime    not null,

    constraint PK_STOCK
        primary key (CHASSIS_NUMBER),

    constraint FK_STOCK_DEALER
        foreign key (DEALER) references dealer (CNPJ)
);
