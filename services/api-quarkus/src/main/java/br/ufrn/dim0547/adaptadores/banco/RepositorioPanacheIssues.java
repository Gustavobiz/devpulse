package br.ufrn.dim0547.adaptadores.banco;

import br.ufrn.dim0547.dominio.Issue;
import br.ufrn.dim0547.dominio.RepositorioDeIssues;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Page;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class RepositorioPanacheIssues implements RepositorioDeIssues, PanacheRepositoryBase<IssueEntidade, Long> {

    @Override
    public List<Issue> listarPorFonte(Long fonteId, int pagina, int tamanho, String estado) {
        PanacheQuery<IssueEntidade> query;
        if (estado != null && !estado.isBlank()) {
            query = find("fonteId = ?1 AND estado = ?2", Sort.by("id"), fonteId, estado);
        } else {
            query = find("fonteId = ?1", Sort.by("id"), fonteId);
        }
        return query.page(Page.of(pagina, tamanho))
                    .list()
                    .stream()
                    .map(IssueEntidade::paraDominio)
                    .toList();
    }

    @Override
    public long contarPorFonte(Long fonteId, String estado) {
        if (estado != null && !estado.isBlank()) {
            return count("fonteId = ?1 AND estado = ?2", fonteId, estado);
        }
        return count("fonteId = ?1", fonteId);
    }

    @Override
    public Optional<Issue> buscarPorId(Long fonteId, Long id) {
        return find("fonteId = ?1 AND id = ?2", fonteId, id).firstResultOptional().map(IssueEntidade::paraDominio);
    }

    @Override
    @Transactional
    public Issue salvar(Issue issue) {
        IssueEntidade entidade = IssueEntidade.deDominio(issue);
        if (entidade.id == null) {
            persist(entidade);
        } else {
            entidade = getEntityManager().merge(entidade);
        }
        return entidade.paraDominio();
    }

    @Override
    @Transactional
    public boolean remover(Long fonteId, Long id) {
        return delete("fonteId = ?1 AND id = ?2", fonteId, id) > 0;
    }
}