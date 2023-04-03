package br.com.util;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.entity.Cliente;
import br.com.service.ClienteService;
import lombok.experimental.UtilityClass;

public class VerificaCampos {

	@Autowired
	ClienteService service;

	public Boolean validaSeExisteId(Long id) {
		Optional<Cliente> cliente = service.buscarPorId(id);
		try {
		if(cliente.get().getId() != null) {
	     return true;
		}
		}catch(Exception e) {
		return false;
		}
		return false;
	}

}
