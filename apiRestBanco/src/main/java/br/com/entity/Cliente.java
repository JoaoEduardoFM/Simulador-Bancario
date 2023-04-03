package br.com.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.br.CPF;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

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

	@Range(min = 0, max = 99999, message = "O campo referente ao número da instituição, deve possuir no máximo 5 dígitos." )
	@Column(name = "nrInst")
	private Long nrInst;

	@Range(min = 0, max = 99999, message = "O campo referente ao número da agência, deve possuir no máximo 5 dígitos.")
	@Column(name = "nrAgen")
	private Long nrAgen;
	
	@Range(min = 0,max = 999999999999999L, message = "O campo referente a conta corrente, deve possuir no máximo 15 dígitos.")
	@Column(name = "cdCta")
	private Long cdCta;

	@Length(min = 0, max = 60, message = "O campo referente ao nome, deve possuir no máximo 60 caracteres.")
	@Column(name = "nome", nullable = false)
	private String nome;

	@Length(min = 0, max = 60, message = "O campo referente ao email, deve possuir no máximo 60 caracteres.")
	@Column(name = "email")
	private String email;

	@CPF(message = "Cpf inválido.")
	@Length(min = 0, max = 11, message = "O campo referente ao cpf, deve possuir no máximo 11 caracteres.")
	@Column(name = "cpf")
	private String cpf;

	@DecimalMax(value = "9999999999999.99", message = "O campo referente ao saldo não pode ser nulo e deve possuir no máximo 13 dígitos inteiros e 2 dígitos decimais.")
	@DecimalMin(value = "0", message = "O campo referente ao saldo não pode ser nulo e deve possuir no máximo 13 dígitos inteiros e 2 dígitos decimais.")
	@Column(name = "saldo")
	@JsonProperty("valor")
	private BigDecimal saldo;

	@Range(min = 0,max = 999999999999999L, message = "O campo referente ao favorecido, deve possuir no máximo 15 dígitos.")
	@Column(name = "favorecido")
	private Long favorecido;
}
