alter table invoice drop column EMISSION_DATE;

alter table invoice add EMISSION_DATE timestamp not null;
