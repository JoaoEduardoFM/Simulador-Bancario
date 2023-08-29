package br.com;

import java.math.BigDecimal;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.entity.Cliente;
import br.com.response.ResponseRest;
import br.com.response.ResponseRest.messageType;
import br.com.service.DepositoService;
import br.com.service.SaqueService;
import br.com.service.TransferirService;
import lombok.extern.slf4j.Slf4j;

/**
 * @author João
 * @apiNote Testes operacoes bancárias
 */

@Slf4j
@SpringBootTest
@TestMethodOrder(MethodOrderer.DisplayName.class)
public class Transacoes {
	
	@Autowired
    private DepositoService depositoService;
	
	@Autowired
	private SaqueService saqueService;
	
	@Autowired
	private TransferirService transferirService;

	Cliente cliente1 = new Cliente(1225L, 1414L, 19L, 1235L, "João", "teste@email.com", "09824793956", BigDecimal.TEN, null);
	Cliente cliente2 = new Cliente(111L, 1414L, 19L, 3335L, "jorge", "teste2@email.com", "12324793956", BigDecimal.TEN, null);
	ResponseRest response = new ResponseRest();
	
	@Test
	@DisplayName("A")
	void inicializando(){
		log.info("Inicializando Testes Unitários classe Transacoes");
		response.setMessage("realizando testes");
		response.setType(messageType.SUCESSO);
	}
	
	@Test
	@DisplayName("B")
	void realizaDeposito() {
		log.info("Realizando deposito");
		depositoService.depositaValor(1225L, BigDecimal.ONE, cliente1, response);
	}
	
	@Test
	@DisplayName("C")
	void realizaSaque() {
		log.info("Realizando saque");
		saqueService.sacaValor(cliente1, 1225L, BigDecimal.ONE, response);
	}
	
	@Test
	@DisplayName("D")
	void realizaTransferencia() {
		log.info("Realizando tranferencia");
		transferirService.tranferirValor(1225L, 111L, BigDecimal.ONE, cliente1, response);
	}
}
