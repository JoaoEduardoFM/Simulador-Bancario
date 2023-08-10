package br.com.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.function.ToDoubleFunction;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import br.com.entity.Cliente;
import br.com.repository.ClienteRepository;
import br.com.response.ResponseRest;
import br.com.response.ResponseRest.messageType;
import springfox.documentation.annotations.ApiIgnore;

@Service
public class ClienteService {
	
    @Autowired
    private ClienteRepository clienteRepository;
    
    @Autowired
    private ModelMapper modelMapper;

    public Cliente salvar(Cliente cliente){
        return clienteRepository.save(cliente);
    }

    public List<Cliente> listaCliente(){
        return clienteRepository.findAll();
    }

    public Optional<Cliente> buscarPorId(Long id){
        return clienteRepository.findById(id);
    }

    public void removerPorId(Long id){
        clienteRepository.deleteById(id);
    }
    
    public ResponseEntity<?> RetornaSaldoDeBanco() {
    	  Iterable<Cliente> clientes = clienteRepository.findAll();
		return ResponseEntity.status(HttpStatus.OK).body(clientes);    	 
    }
    
    public ResponseEntity<ResponseRest> atualizarCliente(@PathVariable("id") Long id, @Valid Cliente cliente, @ApiIgnore ResponseRest response) {
		if (!validaSeExisteId(id)) {
			response.setMessage("Id não existe.");
			response.setType(messageType.ATENCAO);
			return new ResponseEntity<ResponseRest>(response, HttpStatus.BAD_REQUEST);
		}
		BigDecimal saldo = verificaSaldo(id);
		buscarPorId(id).map(clienteBase -> {
			modelMapper.map(cliente, clienteBase);
			clienteBase.setSaldo(saldo);
			clienteRepository.save(clienteBase);			
			return new ResponseEntity<>(clienteBase, HttpStatus.OK);
		});
		response.setMessage("Registro atualizado com sucesso.");
		response.setType(messageType.SUCESSO);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
    
    public ResponseEntity<?> removerCliente(@PathVariable("id") Long id, @ApiIgnore Cliente cliente,
			@ApiIgnore ResponseRest response) {

		if (!validaSeExisteId(cliente.getId())) {
			response.setMessage("Id não existe.");
			response.setType(messageType.ATENCAO);
			return new ResponseEntity<ResponseRest>(response, HttpStatus.BAD_REQUEST);
		}

		clienteRepository.deleteById(cliente.getId());
		response.setMessage("Registro excluído com sucesso");
		response.setType(messageType.SUCESSO);
		return new ResponseEntity<ResponseRest>(response, HttpStatus.OK);

	}     
    
    public ResponseEntity<?> buscarClientePorId(@PathVariable("id") Long id, @ApiIgnore Cliente cliente, @ApiIgnore ResponseRest response) {
		if (!validaSeExisteId(cliente.getId())) {
			response.setMessage("Id não existe.");
			response.setType(messageType.ATENCAO);
			return new ResponseEntity<ResponseRest>(response, HttpStatus.BAD_REQUEST);
		}
		return ResponseEntity.status(HttpStatus.OK).body(clienteRepository.findById(id));
	}
    
    public ResponseEntity<ResponseRest> salvarCliente(@Valid Cliente cliente,  @ApiIgnore ResponseRest response){
    	cliente.setFavorecido(null);
    	cliente.setSaldo(BigDecimal.ZERO);
    	if(validaSeExisteId(cliente.getId())){
			response.setMessage("Id já cadastrado.");
	    	response.setType(messageType.ATENCAO);
	    	return new ResponseEntity<ResponseRest>(response,HttpStatus.BAD_REQUEST);
			
		}
    	if(clienteRepository.save(cliente) != null) {
    	response.setMessage("Registro criado com sucesso.");
    	response.setType(messageType.SUCESSO);
    	return new ResponseEntity<>(response, HttpStatus.OK);
    	}   
    	response.setMessage("Erro ao salvar registro.");
    	response.setType(messageType.ERRO);
    	return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
    
    public Boolean validaSeExisteId(Long id) {
		Optional<Cliente> buscaPorID = clienteRepository.findById(id);
		try {
		if(buscaPorID.get().getId() != null) {
	     return true;
		}
		}catch(Exception e) {
		return false;
		}
		return false;
	}
    
    public BigDecimal verificaSaldo(Long id){
		Optional<Cliente> cliente = buscarPorId(id);
		if (cliente.isEmpty()) {
			return null;
		}
		return cliente.get().getSaldo();
    }
    
    public Double balancoSaldoClientes() {
    	List<Cliente> lista = new ArrayList<>();
    	List<Cliente> clientes = listaCliente();
    	lista.addAll(clientes);
    	Double soma =lista.stream().mapToDouble(value -> value.getSaldo().doubleValue()).sum();
		return soma;
    	
    }
    
    public OptionalDouble MediaSaldoClientes() {
    	List<Cliente> lista = new ArrayList<>();
    	List<Cliente> clientes = listaCliente();
    	lista.addAll(clientes);
    	OptionalDouble media =lista.stream().mapToDouble(value -> value.getSaldo().doubleValue()).average();
		return media;
    	
    }
    
    public Double divisaoSaldos() {
    	List<Cliente> clientes = listaCliente();
    	Double balancoSaldoClientes = balancoSaldoClientes().doubleValue();
    	balancoSaldoClientes= balancoSaldoClientes/clientes.size();
    	return balancoSaldoClientes;
    }
}
