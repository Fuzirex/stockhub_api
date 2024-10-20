create table ITEM
(
    CHASSIS_NUMBER         varchar(20) not null,
    NUMBER                 varchar(15) not null,
    SERIES                 varchar(15) not null,
    OPERATION_TYPE         int         not null,
    DEALER                 varchar(14) not null,
    AUDIT_CREATION_DATE    datetime    not null,
    AUDIT_LAST_UPDATE_DATE datetime    not null,

    constraint PK_ITEM
        primary key (CHASSIS_NUMBER, NUMBER, SERIES, OPERATION_TYPE, DEALER),

    constraint FK_ITEM_INVOICE
        foreign key (NUMBER, SERIES, OPERATION_TYPE, DEALER) references invoice (NUMBER, SERIES, OPERATION_TYPE, DEALER),

    constraint FK_ITEM_STOCK
        foreign key (CHASSIS_NUMBER) references stock (CHASSIS_NUMBER)
);
