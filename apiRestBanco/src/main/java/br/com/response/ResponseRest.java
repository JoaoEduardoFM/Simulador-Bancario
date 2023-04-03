package br.com.response;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseRest {

	String message;	

	private messageType type;

	public enum messageType {
		
		ERRO("Erro"),

		CONSISTENCIA("Consistência"),

		ATENCAO("Atenção"),

		INFORMACAO("Informação"),

		SUCESSO("Sucesso");

		private String description;

		private messageType(String description) {
			this.description = description;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}
		
		
	}

}
