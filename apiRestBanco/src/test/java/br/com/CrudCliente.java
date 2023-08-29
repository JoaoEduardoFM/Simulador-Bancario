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
import br.com.service.ClienteService;
import lombok.extern.slf4j.Slf4j;

/**
 * @author João
 * @apiNote Teste CRUD de clientes.
 */

@Slf4j
@SpringBootTest
@TestMethodOrder(MethodOrderer.DisplayName.class)
public class CrudCliente {
	
	@Autowired
    private ClienteService clienteService;
	
	Cliente cliente1 = new Cliente(1225L, 1414L, 19L, 1235L, "João", "teste1@email.com", "09824793956", BigDecimal.ONE, null);
	ResponseRest response = new ResponseRest();
	
	@Test
	@DisplayName("A")
	void inicializando(){
		log.info("Inicializando Testes Unitários classe CrudCliente");
		response.setMessage("realizando testes");
		response.setType(messageType.SUCESSO);
	}
	
	@Test
	@DisplayName("B")
	void cadastraCliente() {	
		log.info("Cadastrando Cliente");
		clienteService.salvar(cliente1);	
	}
	
	@Test
	@DisplayName("C")
	void atualizaCliente() {			
		log.info("Atualizando Cliente");
		cliente1.setNome("Tadeu");
		clienteService.atualizarCliente(1225L, cliente1, response);	
	}
	
	@Test
	@DisplayName("D")
	void buscarClientePorId() {
		log.info("Buscando Cliente");
		clienteService.buscarClientePorId(1225L, cliente1, response);
	}
	
	@Test
	@DisplayName("E")
	void listarRegistros(){
		log.info("Listando clientes");
		clienteService.listaCliente();	
	}
	
	@Test
	@DisplayName("F")
	void verificarSaldo(){
		log.info("Verificando saldo");
		clienteService.verificaSaldo(1225L);	
	}
	
	@Test
	@DisplayName("G")
	void excluirRegistro() {
		log.info("excluindo Cliente");
		clienteService.removerCliente(1225L, cliente1, response);
	}		
}