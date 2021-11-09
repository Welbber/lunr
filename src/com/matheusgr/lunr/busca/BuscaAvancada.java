package com.matheusgr.lunr.busca;

import java.util.HashMap;
import java.util.Map;

import com.matheusgr.lunr.documento.Documento;
import com.matheusgr.lunr.documento.DocumentoService;

public class BuscaAvancada implements Busca {

	private Map<String, String> metatados;

	/**
	 * Construtor padrão com os metadados a serem encontrados.
	 * 
	 * Os termos não vazios são ignorados. Pelo menos 1 termo deve ser não vazio.
	 * 
	 * @param termos Termos a serem pesquisados.
	 */
	public BuscaAvancada(Map<String, String> metatados) {
		this.metatados = metatados;
	}

	/**
	 * Realiza a busca a partir da consulta ao DocumentoService.
	 * 
	 * O DocumentoService realiza apenas operações simples de busca, mas sem
	 * ordenação ou tratamento da lógica de relevância.
	 * 
	 * @param ds DocumentoService a ser utilizado para busca.
	 * @return Mapa com os documentos encontrados e o fator de relevância de cada
	 *         operação.
	 */
	@Override
	public Map<Documento, Integer> busca(DocumentoService ds) {
		Map<Documento, Integer> respostaDocumento = new HashMap<>();

		for (Documento documento : ds.busca(metatados)) {
			respostaDocumento.put(documento, respostaDocumento.getOrDefault(documento, 0) + 1);
		}

		return respostaDocumento;
	}

	/**
	 * Descreve uma consulta para explicar a consulta que foi feita.
	 * 
	 * @return Descrição da busca, onde cada linha representa um parâmetro de busca
	 *         e as colunas representam um detelhamento de cada parâmetro.
	 */
	@Override
	public String[][] descreveConsulta() {
		String[][] resultado = new String[this.metatados.size()][];
		int i = 0;
		for (var par : this.metatados.entrySet()) {
			resultado[i] = new String[] {par.getKey(), par.getValue()};
			i++;
		}
		return resultado;
	}

}
