package br.com.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.entity.Cliente;
import br.com.response.ResponseRest;
import br.com.response.ResponseRest.messageType;
import br.com.service.ClienteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/cliente")
@Api( value = "Conta Corrente", tags = { "Conta" })
public class ClienteController{

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping()
	@ResponseBody 
	@ApiOperation (
      value = "Cadastra uma conta corrente.",
      notes = "cadastra um cliente vinculado a uma conta corrente."
    )
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ResponseRest> salvar(@RequestBody @Valid Cliente cliente, ResponseRest response){
    	cliente.setFavorecido(null);
    	cliente.setSaldo(null);
    	if(clienteService.salvar(cliente) != null) {
    	response.setMessage("Registro criado com sucesso.");
    	response.setType(messageType.SUCESSO);
    	return new ResponseEntity<>(response, HttpStatus.OK);
    	}   
    	response.setMessage("Erro ao salvar registro.");
    	response.setType(messageType.ERRO);
    	return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    
    @GetMapping
    @ResponseBody 
	@ApiOperation (
      value = "Lista contas cadastradas.",
      notes = "Lista contas vinculadas as contas corrente."
    )
    @ResponseStatus(HttpStatus.OK)
    public List<Cliente> listaCliente(){
        return clienteService.listaCliente();
    }

    @GetMapping("/{id}")
    @ResponseBody 
	@ApiOperation (
      value = "Lista conta cadastrada pelo Id.",
      notes = "Lista conta vinculadas a um Id."
    )
    @ResponseStatus(HttpStatus.OK)
    public Cliente buscarClientePorId(@PathVariable("id") Long id){
    	
        return clienteService.buscarPorId(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente nao encontrado."));
    }

    @DeleteMapping("/{id}")
    @ResponseBody 
	@ApiOperation (
      value = "Exclui conta.",
      notes = "Exclui uma conta vinculadas a um Id."
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removerCliente(@PathVariable("id") Long id){
        clienteService.buscarPorId(id)
                .map(cliente -> {
                    clienteService.removerPorId(cliente.getId());
                    return Void.TYPE;
                }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente nao encontrado."));
    }           

	@PutMapping("/{id}")
	@ApiOperation (
		      value = "Atualizar conta.",
		      notes = "Atualiza uma conta vinculadas a um Id."
		    )
	public ResponseEntity<ResponseRest> atualizarCliente(@PathVariable("id") Long id,
			@RequestBody @Valid Cliente cliente, ResponseRest response) {
		BigDecimal saldo = verificaSaldo(id);
		clienteService.buscarPorId(id).map(clienteBase -> {
			modelMapper.map(cliente, clienteBase);
			clienteBase.setSaldo(saldo);
			clienteService.salvar(clienteBase);			
			return new ResponseEntity<>(clienteBase, HttpStatus.OK);
		});
		response.setMessage("Registro atualizado com sucesso.");
		response.setType(messageType.SUCESSO);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	public BigDecimal verificaSaldo(Long id){
		Optional<Cliente> cliente = clienteService.buscarPorId(id);
		if (cliente.isEmpty()) {
			return null;
		}
		return cliente.get().getSaldo();
    }
}
