package br.ufrn.dim0547.adaptadores.http;

import br.ufrn.dim0547.adaptadores.http.dto.ProblemaDTO;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import org.jboss.resteasy.reactive.RestResponse;
import org.jboss.resteasy.reactive.server.ServerExceptionMapper;
import org.hibernate.exception.ConstraintViolationException;

import java.util.List;

public class MapeadorDeErros {

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
                       .header("Content-Type", "application/problem+json")
                       .entity(problema)
                       .build();
    }

    @ServerExceptionMapper
    public Response tratarViolacaoBanco(ConstraintViolationException e) {
        ProblemaDTO problema = new ProblemaDTO(
            "/erros/conflito-ou-violacao",
            "Violacao de regra de integridade do banco de dados",
            409,
            "A operacao viola uma restricao de integridade (ex: slug duplicado ou chave estrangeira).",
            List.of(e.getMessage())
        );
        return Response.status(409)
                       .header("Content-Type", "application/problem+json")
                       .entity(problema)
                       .build();
    }

    @ServerExceptionMapper
    public Response tratarExcecoesGenericas(WebApplicationException e) {
        int status = e.getResponse() != null ? e.getResponse().getStatus() : 500;
        ProblemaDTO problema = new ProblemaDTO(
            "/erros/requisicao-invalida",
            "Erro no processamento da requisicao",
            status,
            e.getMessage(),
            null
        );
        return Response.status(status)
                       .header("Content-Type", "application/problem+json")
                       .entity(problema)
                       .build();
    }
}