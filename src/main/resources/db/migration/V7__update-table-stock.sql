alter table STOCK add TYPE varchar(3) not null;

alter table STOCK add constraint FK_STOCK_PRODUCT_TYPE
    foreign key (TYPE) references PRODUCT_TYPE (TYPE);
