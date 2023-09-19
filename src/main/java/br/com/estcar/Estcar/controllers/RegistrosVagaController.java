package br.com.estcar.Estcar.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import br.com.estcar.Estcar.models.RegistrosVaga;
import br.com.estcar.Estcar.repositorio.RegistrosVagaRepo;

@Controller
public class RegistrosVagaController {
	
	@Autowired
	private RegistrosVagaRepo repo;
	
	@GetMapping("/financeiro")
	public String index(Model model) {
		List<RegistrosVaga> registros = (List<RegistrosVaga>)repo.findAll();
		
	    // Formate a data antes de passá-la para o Thymeleaf
	    for (RegistrosVaga registro : registros) {
	        registro.setDataLiberecaoFormatada(registro.getDataLiberacaoFormatada());
	    }
	    
		model.addAttribute("registros", registros);
		
	return "financeiro/index";
	}
}
