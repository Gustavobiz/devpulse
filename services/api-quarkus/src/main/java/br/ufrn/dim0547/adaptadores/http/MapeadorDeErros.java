package br.ufrn.dim0547.adaptadores.http;

import br.ufrn.dim0547.adaptadores.http.dto.ProblemaDTO;
import jakarta.validation.ConstraintViolationException; // Import do Bean Validation (Jakarta)
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import org.jboss.resteasy.reactive.server.ServerExceptionMapper;

import java.util.List;

public class MapeadorDeErros {

    /**
     * Trata erros de recurso não encontrado (HTTP 404)
     */
    @ServerExceptionMapper
    public Response tratarNotFound(NotFoundException e) {
        ProblemaDTO problema = new ProblemaDTO(
            "/erros/nao-encontrado",
            "Recurso não encontrado",
            404,
            e.getMessage(),
            null
        );
        return Response.status(404)
                       .type("application/problem+json")
                       .entity(problema)
                       .build();
    }

    /**
     * Trata erros de validação do Bean Validation / DTO (HTTP 422 - Unprocessable Entity)
     * Requisito estrito para validações do corpo da requisição
     */
    @ServerExceptionMapper
    public Response tratarValidacaoDto(ConstraintViolationException e) {
        List<String> violacoes = e.getConstraintViolations()
                                   .stream()
                                   .map(v -> v.getPropertyPath() + ": " + v.getMessage())
                                   .toList();

        ProblemaDTO problema = new ProblemaDTO(
            "/erros/dados-invalidos",
            "Erro de validação de dados",
            422,
            "Um ou mais campos do corpo da requisição são inválidos",
            violacoes
        );
        return Response.status(422)
                       .type("application/problem+json")
                       .entity(problema)
                       .build();
    }

    /**
     * Trata erros de violação de restrições de integridade do Banco de Dados (HTTP 409 - Conflict)
     * Exemplo: Slug duplicado, Unique Key ou Chave Estrangeira
     */
    @ServerExceptionMapper
    public Response tratarViolacaoBanco(org.hibernate.exception.ConstraintViolationException e) {
        ProblemaDTO problema = new ProblemaDTO(
            "/erros/conflito-ou-violacao",
            "Violação de regra de integridade do banco de dados",
            409,
            "A operação viola uma restrição do banco (ex: registro duplicado ou chave estrangeira).",
            List.of(e.getMessage())
        );
        return Response.status(409)
                       .type("application/problem+json")
                       .entity(problema)
                       .build();
    }

    /**
     * Trata demais exceções da camada Web REST
     */
    @ServerExceptionMapper
    public Response tratarExcecoesGenericas(WebApplicationException e) {
        int status = e.getResponse() != null ? e.getResponse().getStatus() : 500;
        ProblemaDTO problema = new ProblemaDTO(
            "/erros/requisicao-invalida",
            "Erro no processamento da requisição",
            status,
            e.getMessage(),
            null
        );
        return Response.status(status)
                       .type("application/problem+json")
                       .entity(problema)
                       .build();
    }
}