INSERT INTO dealer (CNPJ, NAME, EMAIL, MOBILE_PHONE, LANDLINE_PHONE, COUNTRY_ID, COUNTRY_DESC, STATE_ID, STATE_DESC, CITY_ID, CITY_DESC, STREET, NUMBER, ACTIVE, PASSWORD, AUDIT_CREATION_DATE, AUDIT_LAST_UPDATE_DATE)
VALUES ('37785316000146', 'Dealer 1', 'dealer1@example.com', '5551999999999', '555130000000', 76, 'Brasil', 43, 'Rio Grande do Sul', 4311403, 'Lajeado', 'Av. Bento Gonçalves', 123, 1, '$2a$10$pIoi/ofhH04RNiOTf40J1OXGnJGTirH8LmYrfKrKlujzd7VEvUbkW', '2023-10-01 08:30:00', '2023-10-01 08:30:00');
INSERT INTO dealer (CNPJ, NAME, EMAIL, MOBILE_PHONE, LANDLINE_PHONE, COUNTRY_ID, COUNTRY_DESC, STATE_ID, STATE_DESC, CITY_ID, CITY_DESC, STREET, NUMBER, ACTIVE, PASSWORD, AUDIT_CREATION_DATE, AUDIT_LAST_UPDATE_DATE)
VALUES ('89342675000122', 'Dealer 2', 'dealer2@example.com', '5551888888888', '555133333333', 76, 'Brasil', 43, 'Rio Grande do Sul', 4311403, 'Lajeado', 'Av. Bento Gonçalves', 123, 1, '$2a$10$pIoi/ofhH04RNiOTf40J1OXGnJGTirH8LmYrfKrKlujzd7VEvUbkW', '2023-10-01 08:30:00', '2023-10-01 08:30:00');


INSERT INTO product_type (TYPE, TRANSLATION_PT, TRANSLATION_EN, TRANSLATION_ES)
VALUES ('CF', 'Carregador frontal', 'Front Loader', 'Pala Frontal');


INSERT INTO stock (CHASSIS_NUMBER, MODEL, COMMERCIAL_SERIES, ITEM_CODE, DEALER, STATUS, AUDIT_CREATION_DATE, AUDIT_LAST_UPDATE_DATE, TYPE)
VALUES ('00000JJJJJ11111KKK', 'MOD123', 'SER123', 'ITCD123', '37785316000146', 1, '2024-11-15 12:00:41', '2024-11-25 23:49:09', 'CF');
INSERT INTO stock (CHASSIS_NUMBER, MODEL, COMMERCIAL_SERIES, ITEM_CODE, DEALER, STATUS, AUDIT_CREATION_DATE, AUDIT_LAST_UPDATE_DATE, TYPE)
VALUES ('11111AAAAA22222BBB', 'MOD123', 'SER123', 'ITCD123', '37785316000146', 2, '2024-11-15 12:00:41', '2024-11-27 13:48:46', 'CF');
INSERT INTO stock (CHASSIS_NUMBER, MODEL, COMMERCIAL_SERIES, ITEM_CODE, DEALER, STATUS, AUDIT_CREATION_DATE, AUDIT_LAST_UPDATE_DATE, TYPE)
VALUES ('ZZZAA1111111111TTT', 'MOD123', 'SER123', 'ITCD123', '37785316000146', 1, '2024-11-15 12:00:41', '2024-11-27 13:48:46', 'CF');


INSERT INTO invoice (NUMBER, SERIES, OPERATION_TYPE, DEALER, INVOICE_VALUE, DEALER_TO_TRANSFER, CUSTOMER_DOCUMENT_TYPE, CUSTOMER_LEGAL_NUMBER, CUSTOMER_NAME, CUSTOMER_COUNTRY, CUSTOMER_STATE, CUSTOMER_CITY, AUDIT_CREATION_DATE, AUDIT_LAST_UPDATE_DATE, CURRENCY, EMISSION_DATE, CUSTOMER_COUNTRY_ID, CUSTOMER_STATE_ID, CUSTOMER_CITY_ID, CUSTOMER_ADDRESS, CUSTOMER_ADDRESS_COMPLEMENT)
VALUES ('111', '111', 1, '37785316000146', 333.33, null, 'CPF', '89755885056', 'Test', 'Brasil', 'Alagoas', 'Anadia', '2024-11-27 13:48:46', '2024-11-27 13:48:46', 'USD', '2024-11-27 00:00:00', 76, 27, 2700201, 'Rua ABC', 'AP 999');


INSERT INTO item (CHASSIS_NUMBER, NUMBER, SERIES, OPERATION_TYPE, DEALER, AUDIT_CREATION_DATE, AUDIT_LAST_UPDATE_DATE)
VALUES ('11111AAAAA22222BBB', '111', '111', 1, '37785316000146', '2024-11-27 13:48:46', '2024-11-27 13:48:46');