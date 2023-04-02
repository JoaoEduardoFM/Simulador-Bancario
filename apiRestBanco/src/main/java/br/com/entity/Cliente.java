package br.com.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Cliente implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "nrInst")
	private Long nrInst;

	@Column(name = "nrAgen")
	private Long nrAgen;

	@Column(name = "cdCta")
	private Long cdCta;

	@Column(name = "nome", nullable = false)
	private String nome;

	@Column(name = "email")
	private String email;

	@Column(name = "cpf")
	private String cpf;

	@Column(name = "saldo")
	private BigDecimal saldo;
	
	@Column(name = "favorecido")
	private Long favorecido;

}
