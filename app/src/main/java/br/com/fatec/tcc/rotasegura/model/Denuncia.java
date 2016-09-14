package br.com.fatec.tcc.rotasegura.model;

import java.io.Serializable;

public class Denuncia implements Serializable{

	private String descricao;
	private String tipo;
	private String data;
	private String cidade;
	private String estado;
	private String logradouro;
	private Double latitude;
	private Double longitude;

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public String getCidade() {
		return cidade;
	}
	public void setCidade(String cidade) {
		this.cidade = cidade;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public String getLogradouro() {
		return logradouro;
	}
	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {

		switch (Integer.valueOf(tipo)){

			case 1:
				this.tipo = "Furto";
				break;
			case 2:
				this.tipo = "Roubo";
				break;
			case 4:
				this.tipo = "Sequestro Relampago";
				break;
			case 5:
				this.tipo = "Arrombamento Veicular";
				break;
			case 9:
				this.tipo = "Roubo de Veiculo";
				break;
			case 10:
				this.tipo = "Arrastao";
				break;
			default:
				this.tipo = tipo;
				break;
		}

	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}



}
