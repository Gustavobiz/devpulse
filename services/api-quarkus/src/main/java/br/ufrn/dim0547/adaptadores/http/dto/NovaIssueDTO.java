package br.ufrn.dim0547.adaptadores.http.dto;

import br.ufrn.dim0547.dominio.Issue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.Instant;

public record NovaIssueDTO(
    @NotBlank(message = "O titulo nao pode ficar em branco")
    @Size(max = 200, message = "O titulo deve ter no maximo 200 caracteres")
    String titulo,

    String descricao,

    String estado
) {
    public Issue paraDominio(Long id, Long fonteId) {
        return new Issue(
            id,
            fonteId,
            titulo,
            descricao,
            estado != null ? estado : "OPEN",
            Instant.now()
        );
    }
}