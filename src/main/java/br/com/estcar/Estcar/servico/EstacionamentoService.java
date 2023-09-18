package br.com.estcar.Estcar.servico;

import java.time.Duration;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import br.com.estcar.Estcar.models.Administrador;
import br.com.estcar.Estcar.models.VagaEstacionamento;

@Service
public class EstacionamentoService {
	
	public double calcularValorAPagar(VagaEstacionamento vaga, Administrador administrador) {
		
		Duration tempoEstacionado = Duration.between(vaga.getTempoEstacionado(), LocalDateTime.now());
		double valorHora = administrador.getValor_hora();
		double valorEstacionamento = administrador.getValor_estacionamento();
		double valorAPagar = (tempoEstacionado.toMinutes() / 60.0) * valorHora + valorEstacionamento;
		
		valorAPagar = Math.round(valorAPagar * 2) /2.0;
		
		return valorAPagar;
	}
	
}
