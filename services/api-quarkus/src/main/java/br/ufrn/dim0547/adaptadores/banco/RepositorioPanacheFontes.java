package br.ufrn.dim0547.adaptadores.banco;

import br.ufrn.dim0547.dominio.FonteIntegracao;
import br.ufrn.dim0547.dominio.RepositorioDeFontes;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Page;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class RepositorioPanacheFontes implements RepositorioDeFontes, PanacheRepositoryBase<FonteEntidade, Long> {

    @Override
    public List<FonteIntegracao> listar(int pagina, int tamanho, String nome) {
        PanacheQuery<FonteEntidade> query;
        if (nome != null && !nome.isBlank()) {
            query = find("LOWER(nome) LIKE LOWER(?1)", Sort.by("id"), "%" + nome + "%");
        } else {
            query = findAll(Sort.by("id"));
        }
        return query.page(Page.of(pagina, tamanho))
                    .list()
                    .stream()
                    .map(FonteEntidade::paraDominio)
                    .toList();
    }

    @Override
    public long contar(String nome) {
        if (nome != null && !nome.isBlank()) {
            return count("LOWER(nome) LIKE LOWER(?1)", "%" + nome + "%");
        }
        return count();
    }

    @Override
    public Optional<FonteIntegracao> buscarPorId(Long id) {
        return findByIdOptional(id).map(FonteEntidade::paraDominio);
    }

    @Override
    @Transactional
    public FonteIntegracao salvar(FonteIntegracao fonte) {
        FonteEntidade entidade = FonteEntidade.deDominio(fonte);
        if (entidade.id == null) {
            persist(entidade);
        } else {
            entidade = getEntityManager().merge(entidade);
        }
        return entidade.paraDominio();
    }

    @Override
    @Transactional
    public boolean remover(Long id) {
        return deleteById(id);
    }
}