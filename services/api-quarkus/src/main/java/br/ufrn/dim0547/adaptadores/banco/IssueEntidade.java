package br.ufrn.dim0547.adaptadores.banco;

import br.ufrn.dim0547.dominio.Issue;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "issues")
public class IssueEntidade extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(name = "fonte_id")
    public Long fonteId;

    public String titulo;
    public String descricao;
    public String estado;

    @Column(name = "criado_em")
    public Instant criadoEm;

    public Issue paraDominio() {
        return new Issue(id, fonteId, titulo, descricao, estado, criadoEm);
    }

    public static IssueEntidade deDominio(Issue issue) {
        IssueEntidade entidade = new IssueEntidade();
        entidade.id = issue.id();
        entidade.fonteId = issue.fonteId();
        entidade.titulo = issue.titulo();
        entidade.descricao = issue.descricao();
        entidade.estado = issue.estado() != null ? issue.estado() : "OPEN";
        entidade.criadoEm = issue.criadoEm() != null ? issue.criadoEm() : Instant.now();
        return entidade;
    }
}