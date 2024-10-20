create table INVOICE
(
    NUMBER                 varchar(15)    not null,
    SERIES                 varchar(15)    not null,
    OPERATION_TYPE         int            not null,
    DEALER                 varchar(14)    not null,
    EMISSION_DATE          date           not null,
    VALUE                  decimal(14, 2) null,
    DEALER_TO_TRANSFER     varchar(14)    null,
    CUSTOMER_DOCUMENT_TYPE varchar(10)    null,
    CUSTOMER_LEGAL_NUMBER  varchar(20)    null,
    CUSTOMER_NAME          varchar(100)   null,
    CUSTOMER_COUNTRY       varchar(100)   null,
    CUSTOMER_STATE         varchar(100)   null,
    CUSTOMER_CITY          varchar(100)   null,
    AUDIT_CREATION_DATE    datetime       not null,
    AUDIT_LAST_UPDATE_DATE datetime       not null,

    constraint PK_INVOICE
        primary key (NUMBER, SERIES, OPERATION_TYPE, DEALER),

    constraint FK_INVOICE_DEALER
        foreign key (DEALER) references dealer (CNPJ),

    constraint FK_INVOICE_DEALER_TO_TRANSFER
        foreign key (DEALER_TO_TRANSFER) references dealer (CNPJ)
);
