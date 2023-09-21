package br.com.estcar.Estcar.controllers;

import br.com.estcar.Estcar.models.Administrador;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import br.com.estcar.Estcar.repositorio.AdministradoresRepo;
import br.com.estcar.Estcar.servico.CookieService;




@Controller
public class AdministradoresController {

    @Autowired
    private AdministradoresRepo repo;

    private int getAdminIdFromCookie(HttpServletRequest request) throws UnsupportedEncodingException {
        // Obtenha o ID do administrador a partir do cookie
        String adminIdCookie = CookieService.getCookie(request, "usuarioId");
        if (adminIdCookie != null) {
            return Integer.parseInt(adminIdCookie);
        }
        return -1; // Retorne -1 ou outro valor que indique que nenhum administrador está logado
    }

    @GetMapping("/administradores")
    public String index(Model model, HttpServletRequest request) throws UnsupportedEncodingException {
        String adminIdCookie = CookieService.getCookie(request, "usuarioId");
        if (adminIdCookie != null) {
            int administradorId = Integer.parseInt(adminIdCookie);
            Administrador administradorLogado = repo.findById(administradorId).orElse(null);
            
            model.addAttribute("administradorLogado", administradorLogado);

            // Verifique se o administrador logado é "dev.ti.2023@gmail.com"
            if (administradorLogado != null && administradorLogado.getEmail().equals("dev.ti.2023@gmail.com")) {
                // Se for "dev.ti.2023@gmail.com", consulte todos os administradores
                List<Administrador> administradores = (List<Administrador>) repo.findAll();
                model.addAttribute("administradores", administradores);
            } else {
                // Caso contrário, consulte apenas o administrador logado
                List<Administrador> administradores = new ArrayList<>();
                administradores.add(administradorLogado);
                model.addAttribute("administradores", administradores);
            }
        }
        
        model.addAttribute("nome", CookieService.getCookie(request,	"usuarioNome"));
        model.addAttribute("pageName", "Admin.");
        
        return "administradores/index";
    }


    @GetMapping("/administradores/novo")
    public String novo() {
        return "administradores/novo";
    }

    @PostMapping("/administradores/criar")
    public String criar(Administrador administrador) {
        repo.save(administrador);
        return "redirect:/administradores";
    }

    @GetMapping("/administradores/{id}")
    public String buscar(@PathVariable int id, Model model, HttpServletRequest request) throws UnsupportedEncodingException {
        int adminId = getAdminIdFromCookie(request);
        Optional<Administrador> admin = repo.findById(id);

        // Verifique se o administrador logado tem permissão para atualizar
        if (adminId != -1) {
            if (admin.isPresent()) {
                model.addAttribute("administrador", admin.get());
                return "administradores/editar";
            }
        }
        return "redirect:/administradores";
    }

    @PostMapping("/administradores/{id}/atualizar")
    public String atualizar(@PathVariable int id, Administrador administrador, HttpServletRequest request) throws UnsupportedEncodingException {
        int adminId = getAdminIdFromCookie(request);
        if (adminId != -1 && adminId == id) {
            // Verifique se o administrador logado está tentando atualizar seus próprios dados
            administrador.setId(id);
            repo.save(administrador);
        }
        return "redirect:/administradores";
    }

    @GetMapping("/administradores/{id}/excluir")
    public String excluir(@PathVariable int id, HttpServletRequest request) throws UnsupportedEncodingException {
        int adminId = getAdminIdFromCookie(request);
        Optional<Administrador> admin = repo.findById(id);

        // Verifique se o administrador logado tem permissão para excluir
        if (adminId != -1) {
            if (admin.isPresent()) {
                repo.deleteById(id);
            }
        }
        return "redirect:/administradores";
    }
}