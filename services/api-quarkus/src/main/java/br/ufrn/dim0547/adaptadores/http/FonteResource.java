package br.ufrn.dim0547.adaptadores.http;

import br.ufrn.dim0547.adaptadores.http.dto.NovaFonteDTO;
import br.ufrn.dim0547.dominio.FonteIntegracao;
import br.ufrn.dim0547.dominio.RepositorioDeFontes;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.net.URI;
import java.util.List;
import java.util.Map;

@Path("/fontes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Fontes de Integração", description = "Gestão das fontes de código e eventos")
public class FonteResource {

    @Inject
    RepositorioDeFontes repositorio;

    @GET
    @Operation(summary = "Lista todas as fontes com paginação e filtro por nome")
    public Response listar(
        @QueryParam("pagina") @DefaultValue("0") int pagina,
        @QueryParam("tamanho") @DefaultValue("10") int tamanho,
        @QueryParam("nome") String nome
    ) {
        int tamanhoEfetivo = Math.min(tamanho, 100);
        List<FonteIntegracao> fontes = repositorio.listar(pagina, tamanhoEfetivo, nome);
        long total = repositorio.contar(nome);

        return Response.ok(fontes)
                       .header("X-Total-Count", total)
                       .header("X-Page-Number", pagina)
                       .header("X-Page-Size", tamanhoEfetivo)
                       .build();
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Busca uma fonte de integração pelo ID")
    public FonteIntegracao buscarPorId(@PathParam("id") Long id) {
        return repositorio.buscarPorId(id)
                          .orElseThrow(() -> new NotFoundException("Fonte de integração com ID " + id + " não encontrada"));
    }

    @POST
    @Operation(summary = "Cadastra uma nova fonte de integração")
    public Response criar(@Valid NovaFonteDTO dto) {
        FonteIntegracao criada = repositorio.salvar(dto.paraDominio(null));
        return Response.created(URI.create("/fontes/" + criada.id()))
                       .entity(criada)
                       .build();
    }

    @PUT
    @Path("/{id}")
    @Operation(summary = "Atualiza uma fonte de integração existente")
    public FonteIntegracao atualizar(@PathParam("id") Long id, @Valid NovaFonteDTO dto) {
        repositorio.buscarPorId(id)
                   .orElseThrow(() -> new NotFoundException("Fonte de integração com ID " + id + " não encontrada"));
        
        return repositorio.salvar(dto.paraDominio(id));
    }

    @DELETE
    @Path("/{id}")
    @Operation(summary = "Remove uma fonte de integração")
    public Response remover(@PathParam("id") Long id) {
        boolean removido = repositorio.remover(id);
        if (!removido) {
            throw new NotFoundException("Fonte de integração com ID " + id + " não encontrada");
        }
        return Response.noContent().build();
    }
}