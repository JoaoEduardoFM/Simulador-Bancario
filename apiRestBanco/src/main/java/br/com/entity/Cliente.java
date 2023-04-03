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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getNrInst() {
		return nrInst;
	}

	public void setNrInst(Long nrInst) {
		this.nrInst = nrInst;
	}

	public Long getNrAgen() {
		return nrAgen;
	}

	public void setNrAgen(Long nrAgen) {
		this.nrAgen = nrAgen;
	}

	public Long getCdCta() {
		return cdCta;
	}

	public void setCdCta(Long cdCta) {
		this.cdCta = cdCta;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public BigDecimal getSaldo() {
		return saldo;
	}

	public void setSaldo(BigDecimal saldo) {
		this.saldo = saldo;
	}

	public Long getFavorecido() {
		return favorecido;
	}

	public void setFavorecido(Long favorecido) {
		this.favorecido = favorecido;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
