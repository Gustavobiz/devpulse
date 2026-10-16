package br.ufrn.dim0547.adaptadores.http;

import br.ufrn.dim0547.adaptadores.http.dto.NovaIssueDTO;
import br.ufrn.dim0547.dominio.Issue;
import br.ufrn.dim0547.dominio.RepositorioDeFontes;
import br.ufrn.dim0547.dominio.RepositorioDeIssues;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.net.URI;
import java.util.List;

@Path("/fontes/{fonteId}/issues")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Issues", description = "Gestão de problemas/tarefas aninhados por fonte")
public class IssueResource {

    @Inject
    RepositorioDeIssues repositorioIssues;

    @Inject
    RepositorioDeFontes repositorioFontes;

    private void validarFonteExiste(Long fonteId) {
        repositorioFontes.buscarPorId(fonteId)
                         .orElseThrow(() -> new NotFoundException("Fonte de integração com ID " + fonteId + " não encontrada"));
    }

    @GET
    @Operation(summary = "Lista as issues de uma fonte com paginação e filtro por estado")
    public Response listarPorFonte(
        @PathParam("fonteId") Long fonteId,
        @QueryParam("pagina") @DefaultValue("0") int pagina,
        @QueryParam("tamanho") @DefaultValue("10") int tamanho,
        @QueryParam("estado") String estado
    ) {
        validarFonteExiste(fonteId);

        int tamanhoEfetivo = Math.min(tamanho, 100);
        List<Issue> issues = repositorioIssues.listarPorFonte(fonteId, pagina, tamanhoEfetivo, estado);
        long total = repositorioIssues.contarPorFonte(fonteId, estado);

        return Response.ok(issues)
                       .header("X-Total-Count", total)
                       .header("X-Page-Number", pagina)
                       .header("X-Page-Size", tamanhoEfetivo)
                       .build();
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Busca uma issue específica de uma fonte")
    public Issue buscarPorId(@PathParam("fonteId") Long fonteId, @PathParam("id") Long id) {
        validarFonteExiste(fonteId);
        return repositorioIssues.buscarPorId(fonteId, id)
                                .orElseThrow(() -> new NotFoundException("Issue com ID " + id + " não encontrada para a fonte " + fonteId));
    }

    @POST
    @Operation(summary = "Cria uma nova issue para uma fonte")
    public Response criar(@PathParam("fonteId") Long fonteId, @Valid NovaIssueDTO dto) {
        validarFonteExiste(fonteId);
        Issue criada = repositorioIssues.salvar(dto.paraDominio(null, fonteId));
        return Response.created(URI.create("/fontes/" + fonteId + "/issues/" + criada.id()))
                       .entity(criada)
                       .build();
    }

    @PUT
    @Path("/{id}")
    @Operation(summary = "Atualiza uma issue de uma fonte")
    public Issue atualizar(@PathParam("fonteId") Long fonteId, @PathParam("id") Long id, @Valid NovaIssueDTO dto) {
        validarFonteExiste(fonteId);
        repositorioIssues.buscarPorId(fonteId, id)
                         .orElseThrow(() -> new NotFoundException("Issue com ID " + id + " não encontrada para a fonte " + fonteId));

        return repositorioIssues.salvar(dto.paraDominio(id, fonteId));
    }

    @DELETE
    @Path("/{id}")
    @Operation(summary = "Remove uma issue de uma fonte")
    public Response remover(@PathParam("fonteId") Long fonteId, @PathParam("id") Long id) {
        validarFonteExiste(fonteId);
        boolean removido = repositorioIssues.remover(fonteId, id);
        if (!removido) {
            throw new NotFoundException("Issue com ID " + id + " não encontrada para a fonte " + fonteId);
        }
        return Response.noContent().build();
    }
}