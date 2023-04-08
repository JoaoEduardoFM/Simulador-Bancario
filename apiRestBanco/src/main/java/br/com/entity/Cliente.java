package br.com.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(value = { "saldo", "favorecido" }, allowGetters = true)
public class Cliente implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@Range(min = 0, max = 99999, message = "O campo referente ao id do usuário, deve possuir no máximo 5 dígitos." )
	@Column(name = "id")
	@ApiModelProperty(value = "Id usuário", required = true)
	private Long id;

	@Range(min = 0, max = 99999, message = "O campo referente ao número da instituição, deve possuir no máximo 5 dígitos." )
	@Column(name = "nrInst")
	@ApiModelProperty(value = "Número da instituição", required = true)
	private Long nrInst;

	@Range(min = 0, max = 99999, message = "O campo referente ao número da agência, deve possuir no máximo 5 dígitos.")
	@Column(name = "nrAgen")
	@ApiModelProperty(value = "Número da agência", required = true)
	private Long nrAgen;
	
	@Range(min = 0,max = 999999999999999L, message = "O campo referente a conta corrente, deve possuir no máximo 15 dígitos.")
	@Column(name = "cdCta")
	@ApiModelProperty(value = "Código da conta corrente", required = true)
	private Long cdCta;

	@Length(min = 0, max = 60, message = "O campo referente ao nome, deve possuir no máximo 60 caracteres.")
	@Column(name = "nome", nullable = false)
	@ApiModelProperty(value = "Nome", required = true)
	private String nome;

	@Length(min = 0, max = 60, message = "O campo referente ao email, deve possuir no máximo 60 caracteres.")
	@Column(name = "email")
	@ApiModelProperty(value = "E-mail", required = true)
	private String email;

	@Length(min = 0, max = 11, message = "O campo referente ao cpf, deve possuir no máximo 11 caracteres.")
	@Column(name = "cpf")
	@ApiModelProperty(value = "Cpf", required = true)
	private String cpf;

	@DecimalMax(value = "9999999999999.99", message = "O campo referente ao saldo não pode ser nulo e deve possuir no máximo 13 dígitos inteiros e 2 dígitos decimais.")
	@DecimalMin(value = "0", message = "O campo referente ao saldo não pode ser nulo e deve possuir no máximo 13 dígitos inteiros e 2 dígitos decimais.")
	@Column(name = "saldo")
	@JsonProperty("valor")
	@ApiModelProperty(value = "Saldo da conta", required = false, hidden = true)
	private BigDecimal saldo;

	@Range(min = 0,max = 999999999999999L, message = "O campo referente ao favorecido, deve possuir no máximo 15 dígitos.")
	@Column(name = "favorecido")
	@ApiModelProperty(value = "Id favorecido", required = false, hidden = true)
	private Long favorecido;
}
