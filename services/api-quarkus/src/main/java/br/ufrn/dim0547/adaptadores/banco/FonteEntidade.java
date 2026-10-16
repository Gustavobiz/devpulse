package br.ufrn.dim0547.adaptadores.banco;

import br.ufrn.dim0547.dominio.FonteIntegracao;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

@Entity
@Table(name = "fontes")
public class FonteEntidade extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public String nome;
    public String slug;
    public String tipo;

    public FonteIntegracao paraDominio() {
        return new FonteIntegracao(id, nome, slug, tipo);
    }

    public static FonteEntidade deDominio(FonteIntegracao fonte) {
        FonteEntidade entidade = new FonteEntidade();
        entidade.id = fonte.id();
        entidade.nome = fonte.nome();
        entidade.slug = fonte.slug();
        entidade.tipo = fonte.tipo();
        return entidade;
    }
}