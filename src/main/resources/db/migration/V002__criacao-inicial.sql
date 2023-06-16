create table JPA_CAD_COZINHA
(
  num_id_coz   NUMBER(19) generated always as identity,
  nom_abre_coz VARCHAR2(20 CHAR) not null
);
alter table EDS_DBA.JPA_CAD_COZINHA add constraint PK_JPA_CAD_COZINHA primary key (NUM_ID_COZ);

create table JPA_TAB_UF
(
  cod_uf_ufe VARCHAR2(2 CHAR) not null,
  nom_uf_ufe VARCHAR2(20 CHAR)
);
alter table JPA_TAB_UF add constraint PK_JPA_TAB_UF primary key (COD_UF_UFE);

create table JPA_TAB_MUNICIPIO
(
  num_id_mun  NUMBER(19) generated always as identity,
  nom_mun_mun VARCHAR2(30 CHAR),
  cod_uf_mun  VARCHAR2(2 CHAR) not null
);
alter table JPA_TAB_MUNICIPIO add constraint PK_JPA_TAB_MUNICIPIO primary key (NUM_ID_MUN);
alter table JPA_TAB_MUNICIPIO add constraint FK_JPA_TAB_MUNICIPIO_01 foreign key (COD_UF_MUN) references JPA_TAB_UF (COD_UF_UFE);
