package br.ufrn.dim0547.dominio;

import java.util.List;
import java.util.Optional;

public interface RepositorioDeFontes {
    List<FonteIntegracao> listar(int pagina, int tamanho, String nome);
    long contar(String nome);
    Optional<FonteIntegracao> buscarPorId(Long id);
    FonteIntegracao salvar(FonteIntegracao fonte);
    boolean remover(Long id);
}