package br.com.controller;

import java.math.BigDecimal;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.entity.Cliente;
import br.com.response.ResponseRest;
import br.com.service.ClienteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/cliente")
@Api( value = "Conta Corrente", tags = { "Conta" })
public class ClienteController{

    @Autowired
    private ClienteService clienteService;

    @PostMapping()
	@ResponseBody 
	@ApiOperation (
      value = "Cadastra uma conta corrente.",
      notes = "cadastra um cliente vinculado a uma conta corrente.")
    public ResponseEntity<?> salvar(@Valid Cliente cliente,  @ApiIgnore ResponseRest response){
    	ResponseEntity<?> salvaCliente = clienteService.salvarCliente(cliente, response);
    	return salvaCliente;
    }

    @GetMapping
    @ResponseBody 
	@ApiOperation (
      value = "Lista contas cadastradas.",
      notes = "Lista contas vinculadas as contas corrente.")
    @ResponseStatus(HttpStatus.OK)
    public List<Cliente> listaCliente(){
        return clienteService.listaCliente();
    }

	@GetMapping("/{id}")
	@ResponseBody
	@ApiOperation(value = "Lista conta pelo Id.", notes = "Lista conta vinculadas a um Id.")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<?> buscarClientePorId(@PathVariable("id") Long id, @ApiIgnore Cliente cliente, @ApiIgnore ResponseRest response) {
		ResponseEntity<?> buscaPorId = clienteService.buscarClientePorId(id, cliente, response);
		return buscaPorId;
	}

	@DeleteMapping("/{id}")
	@ResponseBody
	@ApiOperation(
			value = "Exclui conta.", 
			notes = "Exclui uma conta vinculadas a um Id.")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<?> removerCliente(@PathVariable("id") Long id, @ApiIgnore Cliente cliente,
			@ApiIgnore ResponseRest response) {
		ResponseEntity<?> remover = clienteService.removerCliente(id, cliente, response);
		return remover;
	}          

	@PutMapping("/{id}")
	@ApiOperation (
		      value = "Atualizar conta.",
		      notes = "Atualiza uma conta vinculadas a um Id.")
	public ResponseEntity<?> atualizarCliente(@PathVariable("id") Long id, @Valid Cliente cliente, @ApiIgnore ResponseRest response) {
		ResponseEntity<?> atualiza = clienteService.atualizarCliente(id, cliente, response);
		return atualiza;
	}
	
	@GetMapping("/saldo/{id}")
	@ResponseBody
	@ApiOperation(value = "Verifica saldo baseado no id cadastrado.", notes = "Verifica saldo.")
	@ResponseStatus(HttpStatus.OK)
	public BigDecimal verificarSaldo(@PathVariable("id") Long id) {
		BigDecimal verificaSaldo = clienteService.verificaSaldo(id);
		return verificaSaldo;
	}
}
