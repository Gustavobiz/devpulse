package br.ufrn.dim0547;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/health")
public class HealthResource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "Hello from Quarkus REST";
    }
d
d
d
dim0547
dim0547
d
x

x
x

x
x
x
x
x
x
y

y
y
y
y
y
yy

}
