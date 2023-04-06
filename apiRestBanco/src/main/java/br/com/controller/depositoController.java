package br.com.controller;

import java.math.BigDecimal;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.entity.Cliente;
import br.com.response.ResponseRest;
import br.com.response.ResponseRest.messageType;
import br.com.service.ClienteService;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/deposito")
public class depositoController {
	
	@Autowired
    private ClienteService clienteService;
	
	@PatchMapping("{id}")
	@ResponseBody 
	@ApiOperation (
      value = "Deposita valor em conta.",
      notes = "Deposita valor em conta vinculadas ao Id cadastrado."
    ) 
	public ResponseEntity<ResponseRest> depositaValor(@PathVariable("id") Long id, @RequestBody @Valid Cliente cliente, @ApiIgnore ResponseRest response) {	
			
		if(cliente.getSaldo() == null) {
			response.setMessage("O campo referente ao valor de depósito, não pode ser nulo.");
        	response.setType(messageType.ERRO);    	
        	return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}
		if(validaSeExisteId(id).equals(false)) {
    		response.setMessage("O Id informado:"+ id + " não existe.");
        	response.setType(messageType.ERRO);    	
        	return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    	}
		if(verificaSaldo(id) != null){
    	cliente.setSaldo(verificaSaldo(id).add(cliente.getSaldo()));
		}
    	alteraSaldo(cliente, cliente.getSaldo(), id);
		response.setMessage("Depósito realizado com sucesso. saldo:" + cliente.getSaldo() );
		response.setType(messageType.SUCESSO);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	public Boolean validaSeExisteId(Long id) {
		Optional<Cliente> cliente = clienteService.buscarPorId(id);
		try {
		if(cliente.get().getId() != null) {
	     return true;
		}
		}catch(Exception e) {
		return false;
		}
		return false;
	}
		
	public BigDecimal verificaSaldo(Long id){
		Optional<Cliente> cliente = clienteService.buscarPorId(id);
		if (cliente.isEmpty()) {
			return null;
		}
		return cliente.get().getSaldo();
    }
	
	public Cliente alteraSaldo(Cliente cliente, BigDecimal saldo, Long id){
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

}
