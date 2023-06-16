create table JPA_CAD_RESTAURANTE
(
  num_id_res         NUMBER(19) generated always as identity,
  dta_atu_res        DATE not null,
  dta_inc_res        DATE not null,
  nom_logr_res       VARCHAR2(70 CHAR),
  nom_bair_res       VARCHAR2(20 CHAR),
  cod_cep_res        VARCHAR2(8 CHAR),
  cod_compl_res      VARCHAR2(20 CHAR),
  num_imvl_res       VARCHAR2(7 CHAR),
  nom_abre_res       VARCHAR2(30 CHAR) not null,
  vlr_taxa_frete_res NUMBER(19,2) not null,
  num_id_coz_res     NUMBER(19) not null,
  num_id_mun_res     NUMBER(19)
);
alter table JPA_CAD_RESTAURANTE add constraint PK_JPA_CAD_RESTAURANTE primary key (NUM_ID_RES);
alter table JPA_CAD_RESTAURANTE add constraint FK_JPA_CAD_RESTAURANTE_01 foreign key (NUM_ID_COZ_RES) references JPA_CAD_COZINHA (NUM_ID_COZ);
alter table JPA_CAD_RESTAURANTE add constraint FK_JPA_CAD_RESTAURANTE_02 foreign key (NUM_ID_MUN_RES) references JPA_TAB_MUNICIPIO (NUM_ID_MUN);

create table JPA_CAD_PRODUTO
(
  cod_id_prd     VARCHAR2(10 CHAR) not null,
  cod_situ_prd   VARCHAR2(2) not null,
  des_prod_prd   VARCHAR2(70 CHAR) not null,
  nom_prod_prd   VARCHAR2(20 CHAR) not null,
  vlr_unit_prd   NUMBER(19,2) not null,
  num_id_res_prd NUMBER(19) not null
);
alter table JPA_CAD_PRODUTO add constraint PK_JPA_CAD_PRODUTO primary key (COD_ID_PRD);
alter table JPA_CAD_PRODUTO add constraint FK_JPA_CAD_PRODUTO_01 foreign key (NUM_ID_RES_PRD) references JPA_CAD_RESTAURANTE (NUM_ID_RES);

create table JPA_CAD_USUARIO
(
  num_id_usu    NUMBER(19) generated always as identity,
  dta_atu_usu   DATE not null,
  dta_inc_usu   DATE not null,
  cod_email_usu VARCHAR2(255 CHAR),
  nom_usu_usu   VARCHAR2(255 CHAR),
  cod_sen_usu   VARCHAR2(255 CHAR)
);
alter table JPA_CAD_USUARIO add constraint PK_JPA_CAD_USUARIO primary key (NUM_ID_USU);

create table JPA_TAB_GRUPO
(
  cod_id_grp    VARCHAR2(7 CHAR) not null,
  des_grupo_grp VARCHAR2(20 CHAR) not null
);
alter table JPA_TAB_GRUPO add constraint PK_JPA_TAB_GRUPO primary key (COD_ID_GRP);

create table JPA_TAB_PERMISSAO
(
  num_id_per     NUMBER(19) generated always as identity,
  des_permis_per VARCHAR2(30 CHAR),
  nom_permis_per VARCHAR2(20 CHAR)
);
alter table JPA_TAB_PERMISSAO add constraint PK_JPA_TAB_PERMISSAO primary key (NUM_ID_PER);

create table JPA_REL_GRP_PER
(
  cod_id_grp_rgp VARCHAR2(7 CHAR) not null,
  num_id_per_rgp NUMBER(19) not null
);
alter table JPA_REL_GRP_PER add constraint FK_JPA_REL_GRP_PER_01 foreign key (COD_ID_GRP_RGP) references JPA_TAB_GRUPO (COD_ID_GRP);
alter table JPA_REL_GRP_PER add constraint FK_JPA_REL_GRP_PER_02 foreign key (NUM_ID_PER_RGP) references JPA_TAB_PERMISSAO (NUM_ID_PER);

create table JPA_TAB_FORMA_PGTO
(
  num_id_fpg         NUMBER(19) generated always as identity,
  des_forma_pgto_fpg VARCHAR2(20 CHAR)
);
alter table JPA_TAB_FORMA_PGTO add constraint PK_JPA_TAB_FORMA_PGTO primary key (NUM_ID_FPG);

create table JPA_REL_RES_FPG
(
  num_id_res_rfp NUMBER(19) not null,
  num_id_fpg_rfp NUMBER(19) not null
);
alter table JPA_REL_RES_FPG add constraint FK_JPA_TAB_FORMA_PGTO_01 foreign key (NUM_ID_RES_RFP) references JPA_CAD_RESTAURANTE (NUM_ID_RES);
alter table JPA_REL_RES_FPG add constraint FK_JPA_TAB_FORMA_PGTO_02 foreign key (NUM_ID_FPG_RFP) references JPA_TAB_FORMA_PGTO (NUM_ID_FPG);

create table JPA_REL_USU_GRP
(
  num_id_usu_rug NUMBER(19) not null,
  cod_id_grp_rug VARCHAR2(7 CHAR) not null
);
alter table JPA_REL_USU_GRP add constraint FK_JPA_REL_USU_GRP_01 foreign key (COD_ID_GRP_RUG) references JPA_TAB_GRUPO (COD_ID_GRP);
alter table JPA_REL_USU_GRP add constraint FK_JPA_REL_USU_GRP_02 foreign key (NUM_ID_USU_RUG) references JPA_CAD_USUARIO (NUM_ID_USU);
