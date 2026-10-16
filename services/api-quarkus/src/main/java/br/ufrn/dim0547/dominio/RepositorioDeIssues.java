package br.ufrn.dim0547.dominio;

import java.util.List;
import java.util.Optional;

public interface RepositorioDeIssues {
    List<Issue> listarPorFonte(Long fonteId, int pagina, int tamanho, String estado);
    long contarPorFonte(Long fonteId, String estado);
    Optional<Issue> buscarPorId(Long fonteId, Long id);
    Issue salvar(Issue issue);
    boolean remover(Long fonteId, Long id);
}