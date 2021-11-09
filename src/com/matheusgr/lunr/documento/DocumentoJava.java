package com.matheusgr.lunr.documento;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import biblitex.TransformaTexto;

/**
 * Documento base java. As palavras-chave da linguagem ainda são preservadas
 * (como public, private, etc), mas elimina-se documentação e comentários.
 */
class DocumentoJava extends DocumentoAbstract {

	/**
	 * Cria o DocumentoJava, extraindo o texot base.
	 * 
	 * @param id       ID do documento a ser criado.
	 * @param original Código java original.
	 */
	public DocumentoJava(String id, String original) {
		super(id, original);
		TransformaTexto transformaTexto = new TransformaTexto();
		var txt = transformaTexto.transforma(TransformaTexto.Algoritmos.java, original);
		super.limpo = transformaTexto.transforma(TransformaTexto.Algoritmos.clean, txt).strip();
	}

	@Override
	public Map<String, String> getMetadados() {
		return super.getMetadados("java", this.extractData(super.original));
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DocumentoJava other = (DocumentoJava) obj;
		return Objects.equals(super.id, other.id);
	}

	/*
	 * Metadados particulares de java: número de imports e autor da classe.
	 */
	private Map<String, String> extractData(String original2) {
		Map<String, String> metadados2 = new HashMap<>();
		metadados2.put("IMPORTS", "" + ((super.limpo.length() - super.limpo.replaceAll("import ", "").length()) / 7));
		metadados2.put("AUTHOR", "" + (super.original.indexOf("@author") != -1 ? "TRUE" : ""));
		return metadados2;
	}

}
