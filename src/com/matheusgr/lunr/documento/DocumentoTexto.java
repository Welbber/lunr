package com.matheusgr.lunr.documento;

import java.util.Map;
import java.util.Objects;

import biblitex.TransformaTexto;

/**
 * Documento de texto simples. Não precisa de tratamento complexos nem tem
 * metadados próprios.
 */
class DocumentoTexto extends DocumentoAbstract {

	/**
	 * Construtor padrão do documento de texto.
	 * 
	 * @param id  ID do documento.
	 * @param txt Texto do documento.
	 */
	public DocumentoTexto(String id, String txt) {
		super(id, txt);
		super.limpo = new TransformaTexto().transforma(TransformaTexto.Algoritmos.clean, txt).strip();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DocumentoTexto other = (DocumentoTexto) obj;
		return Objects.equals(super.id, other.id);
	}

	@Override
	public Map<String, String> getMetadados() {
		return super.formataMetadados("txt");
	}

}
