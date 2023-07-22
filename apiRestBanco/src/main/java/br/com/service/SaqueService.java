package br.com.service;

import java.math.BigDecimal;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.entity.Cliente;
import br.com.response.ResponseRest;
import br.com.response.ResponseRest.messageType;
import springfox.documentation.annotations.ApiIgnore;

@Service
public class SaqueService {
	
	@Autowired
	ClienteService clienteService;
	
	public ResponseEntity<ResponseRest> sacaValor(@ApiIgnore @Valid Cliente cliente, Long id, BigDecimal saque,
			@ApiIgnore ResponseRest response) {

		if (validaSeExisteId(id).equals(false)) {
			response.setMessage("O Id informado:" + id + " não existe.");
			response.setType(messageType.ERRO);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}

		if (saque == null) {
			response.setMessage("O campo referente ao valor de saque, não pode ser nulo.");
			response.setType(messageType.ERRO);
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}

		if (verificaSaldo(id) == null) {
			response.setMessage("Saldo insuficiente. saldo:0.00");
			response.setType(messageType.ATENCAO);
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}

		if (verificaSaldo(id).compareTo(saque) < 0 && verificaSaldo(id) != null) {
			response.setMessage("O Valor do saque é superior ao seu saldo de " + verificaSaldo(id));
			response.setType(messageType.ATENCAO);
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}
		if (verificaSaldo(id) != null) {
			cliente.setSaldo(verificaSaldo(id).subtract(saque));
		}
		alteraSaldo(cliente, cliente.getSaldo(), id);
		response.setMessage("Saque realizado com sucesso. saldo:" + cliente.getSaldo());
		response.setType(messageType.SUCESSO);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	public BigDecimal verificaSaldo(Long id) {
		Optional<Cliente> cliente = clienteService.buscarPorId(id);
		if (cliente.isEmpty()) {
			return null;
		}
		return cliente.get().getSaldo();
	}

	public Cliente alteraSaldo(Cliente cliente, BigDecimal saldo, Long id) {
		Optional<Cliente> clienteCadastrado = clienteService.buscarPorId(id);
		cliente.setCdCta(clienteCadastrado.get().getCdCta());
		cliente.setCpf(clienteCadastrado.get().getCpf());
		cliente.setEmail(clienteCadastrado.get().getEmail());
		cliente.setId(clienteCadastrado.get().getId());
		cliente.setNome(clienteCadastrado.get().getNome());
		cliente.setNrAgen(clienteCadastrado.get().getNrAgen());
		cliente.setNrInst(clienteCadastrado.get().getNrInst());
		cliente.setFavorecido(null);
		cliente.setSaldo(saldo);
		return clienteService.salvar(cliente);
	}

	public Boolean validaSeExisteId(Long id) {
		Optional<Cliente> cliente = clienteService.buscarPorId(id);
		try {
			if (cliente.get().getId() != null) {
				return true;
			}
		} catch (Exception e) {
			return false;
		}
		return false;
	}

}
