package br.com.estcar.Estcar.controllers;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.estcar.Estcar.models.Administrador;
import br.com.estcar.Estcar.models.RegistrosVaga;
import br.com.estcar.Estcar.repositorio.AdministradoresRepo;
import br.com.estcar.Estcar.repositorio.RegistrosVagaRepo;
import br.com.estcar.Estcar.servico.CookieService;

@Controller
public class RegistrosVagaController {
	
	@Autowired
	private RegistrosVagaRepo repo;
	
	@Autowired
    private AdministradoresRepo adminRepo;
	

	@GetMapping("/financeiro")
	public String index(@RequestParam(name = "mes", required = false) String mes,
	                    @RequestParam(name = "ano", required = false) String ano,
	                    Model model, HttpServletRequest request) throws UnsupportedEncodingException {
	    // Obter o ID do administrador a partir do cookie
	    String adminIdCookie = CookieService.getCookie(request, "usuarioId");
	    if (adminIdCookie != null) {
	        int administradorId = Integer.parseInt(adminIdCookie);
	        Administrador administradorLogado = adminRepo.findById(administradorId).orElse(null);
	        if (administradorLogado != null) {
	            List<RegistrosVaga> registros;

	            // Verifique se mes e ano não estão presentes na solicitação
	            if (mes == null || mes.isEmpty() || ano == null || ano.isEmpty()) {
	                // Obtém a data atual
	                LocalDateTime dataAtual = LocalDateTime.now();

	                // Formata a data atual para o formato esperado
	                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	                String dataAtualFormatada = dataAtual.format(formatter);

	                // Consulta os registros financeiros para o dia atual
	                registros = repo.findByAdministradorAndDay(administradorLogado.getId(), dataAtualFormatada);
	            } else {
	                // Consultar os registros financeiros associados ao administrador logado para o mês e ano selecionados
	                registros = repo.findByAdministradorAndMonthAndYear(administradorLogado.getId(), mes, ano);
	            }


	            // Formate a data antes de passá-la para o Thymeleaf
	            for (RegistrosVaga registro : registros) {
	                registro.setDataLiberecaoFormatada(registro.getDataLiberacaoFormatada());
	            }

	            // Calcular a soma dos valores pagos
	            double valorTotalAPagar = registros.stream()
	                    .mapToDouble(RegistrosVaga::getValorPago)
	                    .sum();

	            model.addAttribute("registros", registros);
	            model.addAttribute("valorTotalAPagar", valorTotalAPagar);
	        }
	    }
	    return "financeiro/index";
	}
}
