package com.matheusgr.lunr;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class SimilaridadeServiceTest extends BaseTest {

	@Test
	void testSimilaridadeService() {

		assertEquals(0.5, super.similaridadeController.similaridade(TEXTO1_ID, TEXTO2_ID));

		String texto1 = "textoTeste1";
		String texto2 = "textoTeste2";

		this.documentoController.adicionaDocumentoTxt(texto1, "Uma casa feliz é uma casa bonita");
		this.documentoController.adicionaDocumentoTxt(texto2, "Um dia feliz é um bom dia");

		assertEquals(0.2, super.similaridadeController.similaridade(texto1, texto2));
	}

	@Test
	void testSimilaridadeServiceCampoNull() {
		try {
			super.similaridadeController.similaridade("textoQueNaoSeraEncontrado", TEXTO2_ID);
		} catch (Exception e) {
			assertEquals("Documento não encontrando", e.getMessage());
		}
	}
}
