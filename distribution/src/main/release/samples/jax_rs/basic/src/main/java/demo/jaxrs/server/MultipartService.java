package demo.jaxrs.server;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;
import org.apache.cxf.jaxrs.ext.multipart.Attachment;
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

    @PUT
    @Path("/attachmentsDemo/")
    public Response addAttachmentsDemo(MultipartBody multipartBody) throws Exception {
        StringBuilder body = new StringBuilder();
        body.append("Main Body type ").append(multipartBody.getType()).append("\r\n");

        body.append("<h3>Attachments (").append(multipartBody.getAllAttachments().size()).append(")</h3>\r\n");

        for (Attachment at : multipartBody.getAllAttachments()) {
            body.append("Headers ").append(at.getHeaders()).append("\r\n");
            body.append("Content id ").append(at.getContentId()).append("\r\n");
            body.append("Content name ").append(at.getDataHandler().getName()).append("\r\n");
            InputStream in = at.getDataHandler().getInputStream();
            OutputStream os = new FileOutputStream("C:/dataout/httpWrite/" + at.getDataHandler().getName());
            StringBuilder content = new StringBuilder();
            int i;
            while ((i = in.read()) != -1) {
                content.append((char) i);
                os.write(i);
            }
            in.close();
            os.flush();
            os.close();
            body.append("Content ").append(content).append("\r\n");

            body.append("\r\n");
        }
        return Response.ok(body.toString()).build();
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