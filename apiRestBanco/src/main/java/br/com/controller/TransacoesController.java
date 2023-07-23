package br.com.controller;

import java.math.BigDecimal;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.entity.Cliente;
import br.com.response.ResponseRest;
import br.com.service.DepositoService;
import br.com.service.SaqueService;
import br.com.service.TransferirService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/transacoes")
@Api( value = "Efetura transações bancárias", tags = { "Transações" })
public class TransacoesController {

	@Autowired
	private SaqueService saqueService;
	
	@Autowired
	private TransferirService transferirService;
	
	@Autowired
    private DepositoService depositoService;

	@PatchMapping("/sacar/{id}")
	@ResponseBody
	@ApiOperation(
			value = "Efetua saque.", 
			notes = "Saca valor de conta corrente.")
	public ResponseEntity<?> sacaValor(
			@ApiIgnore @Valid Cliente cliente, Long id, BigDecimal saque, @ApiIgnore ResponseRest response) {
		ResponseEntity<?> sacarValor = saqueService.sacaValor(cliente, id, saque, response);
		return sacarValor;
	}
	
	@PatchMapping("/depositar/{id}")
	@ResponseBody 
	@ApiOperation (
			value = "Deposita valor em conta.",
			notes = "Deposita valor em conta vinculadas ao Id cadastrado.") 
	public ResponseEntity<?> depositaValor(Long id, BigDecimal deposito, @ApiIgnore @Valid Cliente cliente, @ApiIgnore ResponseRest response) {	
		ResponseEntity<?> depositar = depositoService.depositaValor(id, deposito, cliente, response);
		return depositar;
	}
	
	@PatchMapping("/transferir/{id}")
	@ResponseBody
	@ApiOperation(
			value = "Transferir valor.", 
			notes = "Trasnferir valor para outra conta corrente.")
	public ResponseEntity<?> tranferirValor(Long id, Long favorecido, BigDecimal saldo,
			@ApiIgnore @Valid Cliente cliente, @ApiIgnore ResponseRest response) {
		ResponseEntity<?> transferir = transferirService.tranferirValor(id, favorecido, saldo, cliente, response);
		return transferir;
	}
	
}
