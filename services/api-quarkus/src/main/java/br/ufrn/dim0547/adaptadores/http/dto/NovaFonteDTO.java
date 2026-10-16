package br.ufrn.dim0547.adaptadores.http.dto;

import br.ufrn.dim0547.dominio.FonteIntegracao;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record NovaFonteDTO(
    @NotBlank(message = "O nome nao pode ficar em branco")
    @Size(max = 100, message = "O nome deve ter no maximo 100 caracteres")
    String nome,

    @NotBlank(message = "O slug nao pode ficar em branco")
    @Size(max = 100, message = "O slug deve ter no maximo 100 caracteres")
    String slug,

    @NotBlank(message = "O tipo nao pode ficar em branco")
    String tipo
) {
    public FonteIntegracao paraDominio(Long id) {
        return new FonteIntegracao(id, nome, slug, tipo);
    }
}