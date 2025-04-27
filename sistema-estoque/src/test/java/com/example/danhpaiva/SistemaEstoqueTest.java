package com.example.danhpaiva;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

public class SistemaEstoqueTest {
  public SistemaEstoque sistemaEstoque;
  
  @Before
    public void setUp() {
        sistemaEstoque = new SistemaEstoque();
    }
  
  @Test
    public void testAdicionarProdutoComSucesso() {
        sistemaEstoque.adicionarProduto("Caderno", 10);
        assertEquals(10, sistemaEstoque.consultarEstoque("Caderno"));
    }

    @Test
    public void testAdicionarProdutoComNomeVazio() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            sistemaEstoque.adicionarProduto("", 5);
        });
        assertEquals("Nome do produto não pode ser nulo ou vazio.", exception.getMessage());
    }

    @Test
    public void testAdicionarProdutoComQuantidadeInvalida() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            sistemaEstoque.adicionarProduto("Caneta", 0);
        });
        assertEquals("A quantidade deve ser maior que zero.", exception.getMessage());
    }

    @Test
    public void testRemoverProdutoComSucesso() {
        sistemaEstoque.adicionarProduto("Livro", 5);
        sistemaEstoque.removerProduto("Livro", 3);
        assertEquals(2, sistemaEstoque.consultarEstoque("Livro"));
    }

    @Test
    public void testRemoverProdutoComEstoqueInsuficiente() {
        sistemaEstoque.adicionarProduto("Lápis", 2);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            sistemaEstoque.removerProduto("Lápis", 3);
        });
        assertEquals("Estoque insuficiente para remover 3 unidade(s) de Lápis.", exception.getMessage());
    }

    @Test
    public void testConsultarEstoqueProdutoInexistente() {
        assertEquals(0, sistemaEstoque.consultarEstoque("Giz"));
    }

    @Test
    public void testObterHistoricoTransacoes() {
        sistemaEstoque.adicionarProduto("Apagador", 2);
        sistemaEstoque.removerProduto("Apagador", 1);

        List<String> historico = sistemaEstoque.obterHistoricoTransacoes();
        assertEquals(2, historico.size());
        assertTrue(historico.get(0).contains("Adicionado 2 unidade(s) de Apagador"));
        assertTrue(historico.get(1).contains("Removido 1 unidade(s) de Apagador"));
    }

    @Test
    public void testVerificarDisponibilidade() {
        sistemaEstoque.adicionarProduto("Marcador", 4);
        assertTrue(sistemaEstoque.verificarDisponibilidade("Marcador", 3));
        assertFalse(sistemaEstoque.verificarDisponibilidade("Marcador", 5));
    }
}
