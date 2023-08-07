package br.com.estcar.Estcar.servico;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Optional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieService {
	
	public static void setCookie(HttpServletResponse response, String key, String valor, int segundos) throws IOException {
		Cookie cookie = new Cookie(key, URLEncoder.encode(valor, "UTF-8"));
		cookie.setMaxAge(segundos);
		cookie.setHttpOnly(true);
	    //add cookie to response
	    response.addCookie(cookie);
	}

	public static String getCookie(HttpServletRequest request, String key) throws UnsupportedEncodingException {
		
	    	String valor = Optional.ofNullable(request.getCookies())  // Obtém a matriz de cookies da requisição (pode ser nula)
	        .flatMap(cookies -> Arrays.stream(cookies)   // Converte a matriz de cookies em um fluxo (stream)
	        .filter(cookie -> key.equals(cookie.getName()))  // Filtra os cookies pelo nome correspondente à chave
	        .findAny()   // Encontra qualquer cookie correspondente
	        ).map(e -> e.getValue())  // Obtém o valor do cookie encontrado
	        .orElse(null);  // Retorna o valor do cookie ou nulo se não for encontrado
	    	
	    	 valor = URLDecoder.decode(valor, "UTF-8");
	    	 
	    	 return valor;
	}
}
