package br.com.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.entity.Cliente;
import br.com.response.ResponseRest;
import br.com.response.ResponseRest.messageType;
import br.com.service.ClienteService;

@RestController
@RequestMapping("/transferir")
public class TransferirController {
	
	@Autowired
    private ClienteService clienteService;
	
	 @PatchMapping("{id}")
		public ResponseEntity<ResponseRest> tranferirValor(@PathVariable("id") Long id, @RequestBody @Valid Cliente cliente, ResponseRest response) {
	    	
	    	BigDecimal valorJsonSaldo = new BigDecimal(cliente.getSaldo().toString());
	    	
	    	if(validaSeExisteId(id).equals(false)) {
	    		response.setMessage("O Id informado:"+ id + " não existe.");
	        	response.setType(messageType.ERRO);    	
	        	return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	    	}	    	
	    	
	    	if(validaSeExisteId(cliente.getFavorecido()).equals(false)) {
	    		response.setMessage("A conta favorecido informada:"+ cliente.getFavorecido() + " não existe.");
	        	response.setType(messageType.ERRO);    	
	        	return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	    	}
	    	
	    	if(cliente.getSaldo() == null) {
				response.setMessage("O campo referente ao valor a transferir, não pode ser nulo.");
	        	response.setType(messageType.ERRO);    	
	        	return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
			}
	    	
	    	if(verificaSaldo(id) == null) {  
	    		response.setMessage("Saldo insuficiente. Saldo:0.00");
	        	response.setType(messageType.ATENCAO);    	
	        	return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	    	}
	    	
	    	if(verificaSaldo(id).compareTo(cliente.getSaldo()) < 0 && verificaSaldo(id) != null) { 
	    		response.setMessage("O Valor de transferência é superior ao seu saldo de " + verificaSaldo(id));
	        	response.setType(messageType.ATENCAO);    	
	        	return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	    	} 
	    	
	    	if(cliente.getFavorecido().equals(id)) {
	    		response.setMessage("A conta preenchida deve ser diferente da conta débito: " + id);
	        	response.setType(messageType.ERRO);    	
	        	return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	    	}    	
	    	
	    	// credita valor na conta corrente favorecido.
	    	if(verificaSaldo(id) != null){
	    	creditaValor(cliente.getSaldo(), cliente.getFavorecido(), cliente);
	    	// debita valor da conta corrente débito.
	    	debitaValor(valorJsonSaldo, id, cliente);	    	
			response.setMessage("transferência realizada com sucesso. saldo:" + cliente.getSaldo() );
			response.setType(messageType.SUCESSO);
			return new ResponseEntity<>(response, HttpStatus.OK);
	    	}
	    	response.setMessage("Erro ao efetuar transferência.");
			response.setType(messageType.ERRO);
	    	return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}
	 
		public BigDecimal verificaSaldo(Long id) {
			Optional<Cliente> cliente = clienteService.buscarPorId(id);
			if (cliente.isEmpty()) {
				return null;
			}
			return cliente.get().getSaldo();
		}

		public void creditaValor(BigDecimal valor, Long id, Cliente cliente) {
			if(verificaSaldo(cliente.getFavorecido()) != null){
			cliente.setSaldo(verificaSaldo(id).add(valor));	
			}
			alteraSaldo(cliente, cliente.getSaldo(), id);
		}

		public void debitaValor(BigDecimal valor, Long id, Cliente cliente) {
			cliente.setSaldo(verificaSaldo(id).subtract(valor));
			alteraSaldo(cliente, cliente.getSaldo(), id);
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
	    
	    @ResponseStatus(HttpStatus.BAD_REQUEST)
	    @ExceptionHandler(MethodArgumentNotValidException.class)
		public ResponseEntity<Object> MethodArgumentNotValidException(MethodArgumentNotValidException ex) {
			ResponseRest response = new ResponseRest();
			List<String> erros = ex.getBindingResult().getFieldErrors().stream().map(FieldError::getDefaultMessage)
					.collect(Collectors.toList());	
			
			for (String listaErro : erros) {
				response.setMessage(listaErro);
			}

			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	    }

}
