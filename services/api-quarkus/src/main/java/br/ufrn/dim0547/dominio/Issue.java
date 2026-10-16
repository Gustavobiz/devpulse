package br.ufrn.dim0547.dominio;

import java.time.Instant;

public record Issue(
    Long id,
    Long fonteId,
    String titulo,
    String descricao,
    String estado,
    Instant criadoEm
) {}