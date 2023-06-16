insert into jpa_tab_uf(cod_uf_ufe,nom_uf_ufe) values('AC','Acre');
insert into jpa_tab_uf(cod_uf_ufe,nom_uf_ufe) values('AL','Alagoas');
insert into jpa_tab_uf(cod_uf_ufe,nom_uf_ufe) values('AP','Amapá');
insert into jpa_tab_uf(cod_uf_ufe,nom_uf_ufe) values('AM','Amazonas');
insert into jpa_tab_uf(cod_uf_ufe,nom_uf_ufe) values('BA','Bahia');
insert into jpa_tab_uf(cod_uf_ufe,nom_uf_ufe) values('CE','Ceara');
insert into jpa_tab_uf(cod_uf_ufe,nom_uf_ufe) values('DF','Distrito Federal');
insert into jpa_tab_uf(cod_uf_ufe,nom_uf_ufe) values('ES','Espírito Santo');
insert into jpa_tab_uf(cod_uf_ufe,nom_uf_ufe) values('GO','Goiás');
insert into jpa_tab_uf(cod_uf_ufe,nom_uf_ufe) values('MA','Maranhão');
insert into jpa_tab_uf(cod_uf_ufe,nom_uf_ufe) values('MT','Mato Grosso');
insert into jpa_tab_uf(cod_uf_ufe,nom_uf_ufe) values('MS','Mato Grosso do Sul');
insert into jpa_tab_uf(cod_uf_ufe,nom_uf_ufe) values('MG','Minas Gerais');
insert into jpa_tab_uf(cod_uf_ufe,nom_uf_ufe) values('PA','Pará');
insert into jpa_tab_uf(cod_uf_ufe,nom_uf_ufe) values('PB','Paraíba');
insert into jpa_tab_uf(cod_uf_ufe,nom_uf_ufe) values('PR','Paraná');
insert into jpa_tab_uf(cod_uf_ufe,nom_uf_ufe) values('PE','Pernambuco');
insert into jpa_tab_uf(cod_uf_ufe,nom_uf_ufe) values('PI','Piauí');
insert into jpa_tab_uf(cod_uf_ufe,nom_uf_ufe) values('RJ','Rio de Janeiro');
insert into jpa_tab_uf(cod_uf_ufe,nom_uf_ufe) values('RN','Rio Grande do Norte');
insert into jpa_tab_uf(cod_uf_ufe,nom_uf_ufe) values('RS','Rio Grande do Sul');
insert into jpa_tab_uf(cod_uf_ufe,nom_uf_ufe) values('RO','Rondônia');
insert into jpa_tab_uf(cod_uf_ufe,nom_uf_ufe) values('RR','Roraima');
insert into jpa_tab_uf(cod_uf_ufe,nom_uf_ufe) values('SC','Santa Catarina');
insert into jpa_tab_uf(cod_uf_ufe,nom_uf_ufe) values('SP','São Paulo');
insert into jpa_tab_uf(cod_uf_ufe,nom_uf_ufe) values('SE','Sergipe');
insert into jpa_tab_uf(cod_uf_ufe,nom_uf_ufe) values('TO','Tocantins');

insert into jpa_tab_municipio(nom_mun_mun,cod_uf_mun) values ('Uberlândia','MG');
insert into jpa_tab_municipio(nom_mun_mun,cod_uf_mun) values ('Belo Horizonte','MG');
insert into jpa_tab_municipio(nom_mun_mun,cod_uf_mun) values ('São Paulo','SP');
insert into jpa_tab_municipio(nom_mun_mun,cod_uf_mun) values ('Campinas','SP');
insert into jpa_tab_municipio(nom_mun_mun,cod_uf_mun) values ('Fortaleza','CE');

insert into jpa_cad_cozinha(nom_abre_coz) values('Tailandesa');
insert into jpa_cad_cozinha(nom_abre_coz) values('Indiana');
insert into jpa_cad_cozinha(nom_abre_coz) values('Italiana');

insert into jpa_cad_restaurante(nom_abre_res,vlr_taxa_frete_res,num_id_coz_res,nom_logr_res,cod_cep_res,cod_compl_res,num_imvl_res,nom_bair_res,num_id_mun_res,dta_inc_res,dta_atu_res) values ('Thai Gourmet',10, 1,'Rua José da Silva','12922600','Ao lado da igreja','120','Centro',1,sysdate,sysdate);
insert into jpa_cad_restaurante(nom_abre_res,vlr_taxa_frete_res,num_id_coz_res,dta_inc_res,dta_atu_res) values ('Thai Delivery', 9.50, 1,sysdate,sysdate);
insert into jpa_cad_restaurante(nom_abre_res,vlr_taxa_frete_res,num_id_coz_res,dta_inc_res,dta_atu_res) values ('Tuk Tuk Comida Indiana', 0, 2,sysdate,sysdate);

insert into jpa_tab_forma_pgto(des_forma_pgto_fpg) values('Cartão de crédito');
insert into jpa_tab_forma_pgto(des_forma_pgto_fpg) values('Cartão de débito');
insert into jpa_tab_forma_pgto(des_forma_pgto_fpg) values('Dinheiro');

insert into jpa_tab_grupo(cod_id_grp,des_grupo_grp) values('MTKEY01','MASTER KEYS');

insert into jpa_tab_permissao (nom_permis_per,des_permis_per) values('CONSULTAR_COZINHAS','Permite consultar cozinhas');
insert into jpa_tab_permissao (nom_permis_per,des_permis_per) values('EDITAR_COZINHAS','Permite editar cozinhas');

insert into jpa_rel_grp_per(cod_id_grp_rgp,num_id_per_rgp) values('MTKEY01',1);
insert into jpa_rel_grp_per(cod_id_grp_rgp,num_id_per_rgp) values('MTKEY01',2);

insert into jpa_rel_res_fpg(num_id_res_rfp,num_id_fpg_rfp)values(1,1);
insert into jpa_rel_res_fpg(num_id_res_rfp,num_id_fpg_rfp)values(1,2);
insert into jpa_rel_res_fpg(num_id_res_rfp,num_id_fpg_rfp)values(1,3);
insert into jpa_rel_res_fpg(num_id_res_rfp,num_id_fpg_rfp)values(2,1);
insert into jpa_rel_res_fpg(num_id_res_rfp,num_id_fpg_rfp)values(2,2);
insert into jpa_rel_res_fpg(num_id_res_rfp,num_id_fpg_rfp)values(2,3);
insert into jpa_rel_res_fpg(num_id_res_rfp,num_id_fpg_rfp)values(3,1);

insert into jpa_cad_produto(cod_id_prd,cod_situ_prd,des_prod_prd,nom_prod_prd,vlr_unit_prd,num_id_res_prd) values('ALAC0001','AT','Prato Feito Tradicional','PF Tradicional',23.90,1);
insert into jpa_cad_produto(cod_id_prd,cod_situ_prd,des_prod_prd,nom_prod_prd,vlr_unit_prd,num_id_res_prd) values('ALAC0002','AT','Feijoada','Feijoada',23.90,1);
insert into jpa_cad_produto(cod_id_prd,cod_situ_prd,des_prod_prd,nom_prod_prd,vlr_unit_prd,num_id_res_prd) values('ALAC0003','AT','Omelete','Omelete',23.90,1);
