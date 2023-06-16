package com.algaworks.algafood.infrastructure.service;

import java.util.HashMap;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.filter.VendaDiariaFilter;
import com.algaworks.algafood.domain.service.VendaQueryService;
import com.algaworks.algafood.domain.service.VendaReportService;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Service
public class PdfVendaReportService implements VendaReportService{

	@Autowired
	private VendaQueryService vendaQueryService;
	
	@Override
	public byte[] emitirVendasDiarias(VendaDiariaFilter filtro, String timeoffset) {
		var inputStream = this.getClass().getResourceAsStream("/relatorios/vendas-diarias.jasper");
		
		var parameters = new HashMap<String, Object>();
		
		parameters.put("REPORT_LOCALE", new Locale("pt","BR"));
		
		var vendasDiarias = vendaQueryService.consultarVendaDiaria(filtro, timeoffset);
		
		var dataSource = new JRBeanCollectionDataSource(vendasDiarias);
		
		try {
			var jasperPrint = JasperFillManager.fillReport(inputStream, parameters, dataSource);
			
			return JasperExportManager.exportReportToPdf(jasperPrint);
		}
		catch(JRException e) {
			throw new ReportException("Não foi possivel exportar o reletório. "+e.getMessage());
		}		
	}

}
