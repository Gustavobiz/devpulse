package br.ufrn.dim0547.adaptadores.http.dto;

import java.util.List;

public record ProblemaDTO(
    String type,
    String title,
    int status,
    String detail,
    List<String> violacoes
) {}