package br.com.estcar.Estcar.controllers;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import br.com.estcar.Estcar.servico.CookieService;

@Controller
public class HomeController {
	
	@GetMapping("/")
	public String index(Model model, HttpServletRequest request) throws UnsupportedEncodingException {
		
		model.addAttribute("nome", CookieService.getCookie(request,	"usuarioNome"));
		
		return "home/index";
	}

}
