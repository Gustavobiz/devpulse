package br.ufrn.dim0547.dominio;

public record FonteIntegracao(
    Long id,
    String nome,
    String slug,
    String tipo
) {}