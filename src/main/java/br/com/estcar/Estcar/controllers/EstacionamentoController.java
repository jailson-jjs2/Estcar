package br.com.estcar.Estcar.controllers;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.estcar.Estcar.models.Administrador;
import br.com.estcar.Estcar.models.RegistrosVaga;
import br.com.estcar.Estcar.models.VagaEstacionamento;
import br.com.estcar.Estcar.repositorio.AdministradoresRepo;
import br.com.estcar.Estcar.repositorio.RegistrosVagaRepo;
import br.com.estcar.Estcar.repositorio.VagaEstacionamentoRepo;
import br.com.estcar.Estcar.servico.CookieService;
import br.com.estcar.Estcar.servico.EstacionamentoService;


@Controller
public class EstacionamentoController {
	
	@Autowired
	private AdministradoresRepo adminRepo;
	
	@Autowired
	private VagaEstacionamentoRepo vagaRepo;
	
	@Autowired
	private EstacionamentoService estacionamentoService;
	
	@Autowired
	private RegistrosVagaRepo registroRepo;
	
	
	@GetMapping("/estacionamento")
	public String estacionamento(Model model, HttpServletRequest request) throws UnsupportedEncodingException {
	    // Obter o ID do administrador a partir do cookie
	    String adminIdCookie = CookieService.getCookie(request, "usuarioId");
	    if (adminIdCookie != null) {
	        int administradorId = Integer.parseInt(adminIdCookie);
	        Administrador administradorLogado = adminRepo.findById(administradorId).orElse(null);
	        if (administradorLogado != null) {
	            // Consultar as vagas de estacionamento associadas ao administrador logado
	            List<VagaEstacionamento> vagasEstacionamento = administradorLogado.getVagasEstacionamento();
	            
	            // Calcular o valor a pagar para cada vaga e adicioná-lo ao modelo
	            for (VagaEstacionamento vaga : vagasEstacionamento) {
	            	double valorAPagar = estacionamentoService.calcularValorAPagar(vaga, administradorLogado);
	            	vaga.setValorAPagar(valorAPagar);
	            }
	            
	            model.addAttribute("vagasEstacionamento", vagasEstacionamento);
	        }
	    }
	    return "estacionamento/index";
	}

	
	@GetMapping("/estacionamento/novo")
	public String novo(Model model, HttpServletRequest request) throws UnsupportedEncodingException {
	    String adminIdCookie = CookieService.getCookie(request, "usuarioId");
	    if (adminIdCookie != null) {
	        int administradorId = Integer.parseInt(adminIdCookie);
	        Administrador administradorLogado = adminRepo.findById(administradorId).orElse(null);
	        if (administradorLogado != null) {
	            // Obtenha a quantidade máxima de vagas permitidas pelo administrador
	            int quantidadeMaximaVagas = administradorLogado.getQuant_vaga();
	            
	            // Crie uma lista de todas as vagas disponíveis
	            List<Integer> todasVagasDisponiveis = new ArrayList<>();
	            for (int i = 1; i <= quantidadeMaximaVagas; i++) {
	                todasVagasDisponiveis.add(i);
	            }
	            
	            // Obtenha as vagas já criadas pelo administrador
	            List<VagaEstacionamento> vagasCriadas = administradorLogado.getVagasEstacionamento();
	            List<Integer> vagasCriadasList = vagasCriadas.stream()
	                    .map(vaga -> Integer.parseInt(vaga.getNumVaga()))
	                    .collect(Collectors.toList());
	            
	            // Remova as vagas já criadas das vagas disponíveis
	            todasVagasDisponiveis.removeAll(vagasCriadasList);
	            
	            // Passe as vagas disponíveis para o modelo
	            model.addAttribute("vagasDisponiveis", todasVagasDisponiveis);
	        }
	    }
	    
	    return "estacionamento/novo";
	}
	
	
	@PostMapping("/estacionamento/criar")
	public String criar(@RequestParam("numVaga") String numVaga,
	                    @RequestParam("placaCarro") String placaCarro,
	                    HttpServletRequest request) throws UnsupportedEncodingException {
	    // Obtenha o ID do administrador a partir do cookie
	    String adminIdCookie = CookieService.getCookie(request, "usuarioId");
	    if (adminIdCookie != null) {
	        int administradorId = Integer.parseInt(adminIdCookie);
	        Administrador administradorLogado = adminRepo.findById(administradorId).orElse(null);
	        if (administradorLogado != null) {
	            // Crie um novo objeto VagaEstacionamento
	            VagaEstacionamento vagaEstacionamento = new VagaEstacionamento();
	            vagaEstacionamento.setNumVaga(numVaga);
	            vagaEstacionamento.setPlacaCarro(placaCarro);
	            
	            // Associe o administrador ao objeto VagaEstacionamento
	            vagaEstacionamento.setAdministrador(administradorLogado);
	            
	            // Obtenha a data e hora atual
	            LocalDateTime entrada = LocalDateTime.now();
	            
	            // Salve a data e hora de entrada no objeto VagaEstacionamento como tempoEstacionado
	            vagaEstacionamento.setTempoEstacionado(entrada);
	            
	            // Salve o objeto VagaEstacionamento no repositório
	            vagaRepo.save(vagaEstacionamento);
	        }
	    }
	    
	    return "redirect:/estacionamento";
	}		
	
	
	
	@GetMapping("/estacionamento/{id}")
	public String buscar(@PathVariable int id, Model model, HttpServletRequest request) throws UnsupportedEncodingException {
	    Optional<VagaEstacionamento> vaga = vagaRepo.findById(id);
	    if (vaga.isPresent()) {
	        model.addAttribute("vagaEstacionamento", vaga.get());

	        // Obtenha o ID do administrador a partir do cookie
	        String adminIdCookie = CookieService.getCookie(request, "usuarioId");
	        if (adminIdCookie != null) {
	            int administradorId = Integer.parseInt(adminIdCookie);
	            Administrador administradorLogado = adminRepo.findById(administradorId).orElse(null);
	            if (administradorLogado != null) {
	                // Obtenha a quantidade máxima de vagas permitidas pelo administrador
	                int quantidadeMaximaVagas = administradorLogado.getQuant_vaga();

	                // Crie uma lista de todas as vagas disponíveis
	                List<Integer> todasVagasDisponiveis = new ArrayList<>();
	                for (int i = 1; i <= quantidadeMaximaVagas; i++) {
	                    todasVagasDisponiveis.add(i);
	                }

	                // Obtenha as vagas já criadas pelo administrador
	                List<VagaEstacionamento> vagasCriadas = administradorLogado.getVagasEstacionamento();
	                List<Integer> vagasCriadasList = vagasCriadas.stream()
	                        .map(v -> Integer.parseInt(v.getNumVaga()))
	                        .collect(Collectors.toList());

	                // Remova a vaga atual das vagas disponíveis (para que ela possa ser selecionada novamente)
	                VagaEstacionamento vagaAtual = vaga.get();
	                todasVagasDisponiveis.add(Integer.parseInt(vagaAtual.getNumVaga()));

	                // Remova as vagas já criadas das vagas disponíveis
	                todasVagasDisponiveis.removeAll(vagasCriadasList);

	                // Passe as vagas disponíveis para o modelo
	                model.addAttribute("vagasDisponiveis", todasVagasDisponiveis);

	                return "estacionamento/editar";
	            }
	        }
	    }
	    return "redirect:/estacionamento";
	}

    @PostMapping("/estacionamento/{id}/atualizar")
    public String atualizar(@PathVariable int id, VagaEstacionamento vaga) {
        Optional<VagaEstacionamento> vagaExistente = vagaRepo.findById(id);
        if (vagaExistente.isPresent()) {
            VagaEstacionamento vagaAtual = vagaExistente.get();
            vagaAtual.setNumVaga(vaga.getNumVaga());
            vagaAtual.setPlacaCarro(vaga.getPlacaCarro());
            vagaRepo.save(vagaAtual);
        }
        return "redirect:/estacionamento";
    }
    
    @GetMapping("/estacionamento/{id}/excluir")
    public String excluir(@PathVariable int id, HttpServletRequest request) {
        Optional<VagaEstacionamento> vagaOptional = vagaRepo.findById(id);
        if (vagaOptional.isPresent()) {
            VagaEstacionamento vaga = vagaOptional.get();
            
            // Crie um novo registro de liberação
            RegistrosVaga registroLiberacao = new RegistrosVaga();
            registroLiberacao.setAdministrador(vaga.getAdministrador());
            registroLiberacao.setDataLiberacao(LocalDateTime.now());
            registroLiberacao.setPlacaCarro(vaga.getPlacaFormatada());
            
            // Use o método calcularValorAPagar para obter o valor a ser pago
            double valorAPagar = estacionamentoService.calcularValorAPagar(vaga, vaga.getAdministrador());
            registroLiberacao.setValorPago(valorAPagar);
           
            // Salve o registro de liberação no banco de dados
            registroRepo.save(registroLiberacao);
            
            // Exclua a vaga do estacionamento
            vagaRepo.deleteById(id);
        }
        return "redirect:/estacionamento";
    }

}
