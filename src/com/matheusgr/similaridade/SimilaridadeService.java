package com.matheusgr.similaridade;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import com.matheusgr.lunr.documento.Documento;
import com.matheusgr.lunr.documento.DocumentoService;

/**
 * Componente para tratamento da lógica de negócio relativa a similaridade.
 */
public class SimilaridadeService {

	private DocumentoService documentoService;

	/**
	 * Inicialização da lógica de serviço.
	 * 
	 * @param documentoService DocumentoService a ser utilizado pelo
	 *                         SimilaridadeService.
	 */
	public SimilaridadeService(DocumentoService documentoService) {
		this.documentoService = documentoService;
	}

	/**
	 * Calcula e retorna a similaridade.
	 * 
	 * Para o cálculo da similaridade:
	 * <ul>
	 * <li>Pega o documento 1</li>
	 * <li>Pega o documento 2</li>
	 * <li>Pega os termos do documento 1 e coloca em um conjunto (Termos1)</li>
	 * <li>Pega os termos do documento 2 e coloca em um conjunto (Termos2)</li>
	 * <li>Calcula a interseção entre Termos1 e Termos2 (Inters)</li>
	 * <li>Calcula a união entre Termos1 e Termos2 (Uniao)</li>
	 * <li>A similaridade é o tamanho de Inters sobre o tamanho do conjunto
	 * Uniao</li>
	 * </ul>
	 * 
	 * @param docId1 Documento 1.
	 * @param docId2 Documento 2.
	 * @return Valor de similaridade (entre 0 e 1, inclusives) representando a
	 *         semelhança entre os documentos.
	 */
	public double similaridade(String docId1, String docId2) {
		Optional<Documento> documento1 = this.documentoService.recuperaDocumento(docId1);
		Optional<Documento> documento2 = this.documentoService.recuperaDocumento(docId2);

		if (documento1.isEmpty() || documento2.isEmpty())
			throw new NullPointerException("Documento não encontrando");

		Set<String> texto1 = new HashSet<>(Arrays.asList(documento1.get().getTexto()));
		Set<String> texto2 = new HashSet<>(Arrays.asList(documento2.get().getTexto()));

		int ocorrenciasPalavras = 0;

		Set<String> uniao = new HashSet<String>();

		uniao.addAll(texto1);
		uniao.addAll(texto2);

		for (String palavra : texto1) {
			if (texto2.contains(palavra))
				ocorrenciasPalavras++;
		}

		return (double) ocorrenciasPalavras / (uniao.size());
	}
}