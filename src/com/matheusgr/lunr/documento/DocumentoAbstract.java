package com.matheusgr.lunr.documento;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import biblitex.TransformaTexto;

public abstract class DocumentoAbstract implements Documento {

	protected String id;
	protected String original;
	protected String limpo;
	protected String[] split;
	protected Map<String, String> metadados;

	public DocumentoAbstract(String id, String original) {
		this.id = id;
		this.original = original;
	}

	/**
	 * Retorna a quantidade de texto útil, sendo definido como a quantidade de
	 * caracteres úteis (sem caracteres estranhos e sem espaços) sobre o total de
	 * caracteres original (incluindo espaços).
	 * 
	 * @return Percentual de texto útil (entre 0 e 1, inclusives).
	 */
	@Override
	public double metricaTextoUtil() {
		long extractedSize = (new TransformaTexto()).transforma(TransformaTexto.Algoritmos.cleanSpaces, this.limpo)
				.length();
		return (1.0 * extractedSize) / this.original.length();
	}

	/**
	 * Retorna a identificação do documento. A documentação é definida pelo próprio
	 * documento e é uma String sem formatação específica. Todo documento gerado
	 * pelo Lunr começa com o símbolo "_". O identificador não pode ser vazio ou
	 * nulo.
	 * 
	 * @return Identificação do documento.
	 * @throws NullPointerException Caso o ID seja nulo.
	 */
	@Override
	public String getId() {
		return this.id;
	}

	/**
	 * Retorna os termos do texto em formato de String. Não podem existir termos
	 * vazios (só de espaços, tabulações ou string vazia).
	 * 
	 * @return Array de termos extraídos do documento.
	 */
	@Override
	public String[] getTexto() {
		if (this.split == null) {
			this.split = (new TransformaTexto()).transforma(TransformaTexto.Algoritmos.cleanLines, this.limpo)
					.split(" ");
			Arrays.sort(this.split);
		}
		return this.split;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public String toString() {
		return "===" + this.id + System.lineSeparator() + this.limpo;
	}

	/**
	 * Metodo destinado a formatar preencher o Map que possui a informação dos
	 * metadados do arquivo unindo as informações com quem pelo parametro header com
	 * as informações padrões são elas:
	 * <ul>
	 * <li>Linhas: total de linhas do arquivo</li>
	 * <li>Tamaho: total de caracteres que o arquivo possui</li>
	 * <li>METADATADATE: data de criação dos metadados</li>
	 * <li>TIPO: Tipo do arquivo que está sendo utilizado</li>
	 * </ul>
	 * 
	 * @param tipo   Tipo do documento que está sendo utilizado
	 * @param header Um map que já possui informações à ser adicionado as
	 *               informçãoes padrões do metadado
	 * @return Map Com metadados preenchidos
	 */
	protected Map<String, String> getMetadados(String tipo, Map<String, String> header) {
		if (this.metadados != null) {
			return this.metadados;
		}
		this.metadados = header;
		this.metadados.put("LINHAS", "" + this.original.chars().filter((value) -> '\n' == value).count());
		this.metadados.put("TAMANHO", "" + this.limpo.length());
		this.metadados.put("METADATADATE", "" + System.currentTimeMillis());
		this.metadados.put("TIPO", "" + tipo);
		return this.metadados;
	}

	/**
	 * Metodo destinado a formatar preencher o Map que possui a informação dos
	 * metadados do arquivo
	 * 
	 * @param tipo Tipo do documento que está sendo utilizado
	 * @return Map Com metadados preenchidos
	 */
	protected Map<String, String> formataMetadados(String tipo) {
		if (this.metadados != null) {
			return this.metadados;
		} else {
			this.metadados = new HashMap<>();
		}
		this.metadados.put("LINHAS", "" + this.original.chars().filter((value) -> '\n' == value).count());
		this.metadados.put("TAMANHO", "" + this.limpo.length());
		this.metadados.put("METADATADATE", "" + System.currentTimeMillis());
		this.metadados.put("TIPO", "" + tipo);
		return this.metadados;
	}

}
