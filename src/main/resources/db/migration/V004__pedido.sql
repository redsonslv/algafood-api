create table jpa_cad_pedido(
  num_id_pdd number(19) generated always as identity,
  vlr_taxa_frete_pdd number not null,
  vlr_total_pdd number not null,
  cod_sta_pdd varchar2(10) not null,
  dta_inc_pdd date not null,
  dta_confrm_pdd date,
  dta_canc_pdd date,
  dta_entrg_pdd date,
  num_id_res_pdd number not null,
  num_id_usu_pdd number not null,
  num_id_fpg_pdd number,
  cod_cep_pdd varchar2(8),
  nom_logr_pdd varchar2(70),
  num_imvl_pdd varchar2(7),
  nom_bair_pdd varchar2(20),
  cod_compl_pdd varchar2(20),
  num_id_mun_pdd number
);  
alter table jpa_cad_pedido add constraint pk_jpa_cad_pedido primary key (num_id_pdd);
alter table jpa_cad_pedido add constraint fk_jpa_cad_pedido_01 foreign key (num_id_res_pdd) references jpa_cad_restaurante (num_id_res);
alter table jpa_cad_pedido add constraint fk_jpa_cad_pedido_02 foreign key (num_id_usu_pdd) references jpa_cad_usuario (num_id_usu);
alter table jpa_cad_pedido add constraint fk_jpa_cad_pedido_03 foreign key (num_id_fpg_pdd) references jpa_tab_forma_pgto (num_id_fpg);

create table jpa_cad_itens_pedido(
  num_id_ipd number not null,
  num_id_pdd_ipd number not null,
  cod_id_prd_ipd varchar2(10) not null,
  qtd_item_ipd number not null,
  vlr_unit_ipd number not null,
  vlr_tot_ipd number not null,
  des_obs_ipd varchar2(200)
);
alter table jpa_cad_itens_pedido add constraint pk_jpa_cad_itens_pedido primary key (num_id_pdd_ipd,num_id_ipd);
alter table jpa_cad_itens_pedido add constraint fk_jpa_cad_itens_pedido_01 foreign key (num_id_pdd_ipd) references jpa_cad_pedido (num_id_pdd);
alter table jpa_cad_itens_pedido add constraint fk_jpa_cad_itens_pedido_02 foreign key (cod_id_prd_ipd) references jpa_cad_produto (cod_id_prd);
