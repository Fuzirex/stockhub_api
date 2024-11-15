create table PRODUCT_TYPE
(
    TYPE           varchar(3)  not null,
    TRANSLATION_PT varchar(50) not null,
    TRANSLATION_EN varchar(50) not null,
    TRANSLATION_ES varchar(50) not null,

    constraint PK_PRODUCT_TYPE primary key (TYPE)
);
