package br.com.estcar.Estcar.controllers;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.estcar.Estcar.models.Administrador;
import br.com.estcar.Estcar.models.VagaEstacionamento;
import br.com.estcar.Estcar.repositorio.AdministradoresRepo;
import br.com.estcar.Estcar.repositorio.VagaEstacionamentoRepo;

@Controller
public class EstacionamentoController {
	
	@Autowired
	private AdministradoresRepo adminRepo;
	
	@Autowired
	private VagaEstacionamentoRepo vagaRepo;
	
	
	@GetMapping("/estacionamento")
	public String estacionamento(Model model) {
	    List<VagaEstacionamento> vagasEstacionamento = (List<VagaEstacionamento>) vagaRepo.findAll();
	    model.addAttribute("vagasEstacionamento", vagasEstacionamento);
	    return "estacionamento/index";
	}
	
	
	@GetMapping("/estacionamento/novo")
	public String novo() {
		
		return "estacionamento/novo";
	}
	
	
	@PostMapping("/estacionamento/criar")
	public String criar(@RequestParam("numVaga") String numVaga,
						@RequestParam("placaCarro") String placaCarro) {
							
							LocalDateTime entrada = LocalDateTime.now();
							int tempoEstacionado = (int) Duration.between(entrada, LocalDateTime.now()).toHoursPart();
							
							VagaEstacionamento vagaEstacionamento = new VagaEstacionamento();
							vagaEstacionamento.setNumVaga(numVaga);
							vagaEstacionamento.setPlacaCarro(placaCarro);
							vagaEstacionamento.setTempoEstacionado(tempoEstacionado);
							
							vagaRepo.save(vagaEstacionamento);
							
							return "redirect:/estacionamento";
						}
						

}
