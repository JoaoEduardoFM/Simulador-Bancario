package br.com.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.OptionalDouble;

import javax.validation.Valid;

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

import br.com.model.entity.Cliente;
import br.com.model.response.ResponseRest;
import br.com.service.ClienteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/cliente")
@Api( value = "Conta Corrente", tags = { "Conta" }, description = " CRUD para cadastro de cliente.")
public class ClienteController{

    @Autowired
    private ClienteService clienteService;

    @PostMapping()
	@ResponseBody 
	@ApiOperation (
			value = "Cadastra uma conta corrente.",
			notes = "cadastra um cliente vinculado a uma conta corrente.")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> salvar(@RequestBody @Valid Cliente cliente,  @ApiIgnore ResponseRest response){
    	return clienteService.salvarCliente(cliente, response);
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
	@ApiOperation(
			value = "Lista conta pelo Id.", 
			notes = "Lista conta vinculadas a um Id.")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<?> buscarClientePorId(@PathVariable("id") Long id, @ApiIgnore Cliente cliente, @ApiIgnore ResponseRest response) {
		return clienteService.buscarClientePorId(id, cliente, response);
	}

	@DeleteMapping("/{id}")
	@ResponseBody
	@ApiOperation(
			value = "Exclui conta.", 
			notes = "Exclui uma conta vinculadas a um Id.")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<?> removerCliente(@PathVariable("id") Long id, @ApiIgnore Cliente cliente, @ApiIgnore ResponseRest response) {
		return clienteService.removerCliente(id, cliente, response);
	}          

	@PutMapping("/{id}")
	@ApiOperation (
		      value = "Atualizar conta.",
		      notes = "Atualiza uma conta vinculadas a um Id.")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<?> atualizarCliente(@PathVariable("id") Long id, @RequestBody @Valid Cliente cliente, @ApiIgnore ResponseRest response) {
		return clienteService.atualizarCliente(id, cliente, response);
	}
	
	@GetMapping("/saldo/{id}")
	@ResponseBody
	@ApiOperation(
			value = "Verifica saldo baseado no id cadastrado.", 
			notes = "Verifica saldo.")
	@ResponseStatus(HttpStatus.OK)
	public BigDecimal verificarSaldo(@PathVariable("id") Long id) {
		return clienteService.verificaSaldo(id);
	}
	
	@GetMapping("/balancoSaldos")
	@ResponseBody
	@ApiOperation(
			value = "Calcula o saldo de todos os clientes cadastrados", 
			notes = "Balanço saldo de clientes cadastrados.")
	@ResponseStatus(HttpStatus.OK)
	public Double balancoSaldoClientes() {
		return clienteService.balancoSaldoClientes();
	}
	
	@GetMapping("/mediaSaldos")
	@ResponseBody
	@ApiOperation(
			value = "Calcula a média saldo de todos os clientes cadastrados", 
			notes = "Média dos saldos de clientes cadastrados.")
	@ResponseStatus(HttpStatus.OK)
	public OptionalDouble MediaSaldoClientes() {
		return clienteService.MediaSaldoClientes();
	}
	
	@GetMapping("/divisaoSaldos")
	@ResponseBody
	@ApiOperation(
			value = "Divide os saldos baseados na quantidade de clientes Saldos/Clientes",
			notes = "Divide saldos baseados na quantidade de clientes.")
	@ResponseStatus(HttpStatus.OK)
	public Double divisaodeSaldosCLientes() {
		return clienteService.divisaoSaldos();
	}
}
