package demo.jaxrs.server;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;
import org.apache.cxf.jaxrs.ext.multipart.MultipartBody;

@Path("/addfile/")
@Produces("text/xml")
public class MultipartService {

    @POST
    @Path("/add/")
    public Response addAttachments(MultipartBody multipartBody) throws Exception {
        System.out.println("Attachments size: " + multipartBody.getAllAttachments().size());

        multipartBody.getAllAttachments().forEach(at -> System.out.println(at.getDataHandler().getName()));
        return Response.ok("Abc").build();
    }

    @GET
    @Path("/get")
    public Response getAtt() {
        return Response.ok("abc2").build();
    }

    @PATCH
    @Path("/patch")
    public Response patch() {
        return Response.ok("abc3").build();
    }

    @GET
    @Path("/fail")
    public Response toFail() {
        return Response.status(Response.Status.BAD_GATEWAY).build();
    }
}