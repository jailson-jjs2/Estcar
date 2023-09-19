package br.com.estcar.Estcar.controllers;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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
    public String index(Model model, HttpServletRequest request) throws UnsupportedEncodingException {
        // Obter o ID do administrador a partir do cookie
        String adminIdCookie = CookieService.getCookie(request, "usuarioId");
        if (adminIdCookie != null) {
            int administradorId = Integer.parseInt(adminIdCookie);
            Administrador administradorLogado = adminRepo.findById(administradorId).orElse(null);
            if (administradorLogado != null) {
                // Consultar os registros financeiros associados ao administrador logado
                List<RegistrosVaga> registros = repo.findByAdministrador(administradorLogado);

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
