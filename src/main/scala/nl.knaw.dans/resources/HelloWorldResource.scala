package nl.knaw.dans.resources

import java.io.InputStream
import java.net.URI
import java.util.concurrent.atomic.AtomicLong
import java.util.Optional

import better.files.File
import com.codahale.metrics.annotation.Timed
import javax.annotation.security.RolesAllowed
import javax.ws.rs.{ Consumes, GET, Path, POST, Produces, QueryParam }
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response
import nl.knaw.dans.api.Saying
import org.apache.commons.io.FileUtils
import org.slf4j.LoggerFactory


@Path("/hello-world")
@Produces(Array(MediaType.APPLICATION_JSON))
class HelloWorldResource(val template: String, val defaultName: String, val uploadDir: File) {
  private val counter: AtomicLong = new AtomicLong()
  private val logger = LoggerFactory.getLogger(getClass)

  @RolesAllowed(Array("ADMIN"))
  @GET
  @Timed
  def sayHello(@QueryParam("name") name: Optional[String]): Saying = {
    val value = String.format(template, name.orElse(defaultName))
    new Saying(counter.incrementAndGet(), value)
  }

  @RolesAllowed(Array("ADMIN"))
  @POST
  @Path("/upload")
  @Consumes(Array(MediaType.APPLICATION_OCTET_STREAM))
  def uploadFile(is: InputStream): Response= {
    logger.debug("in upload!!!")
    FileUtils.copyInputStreamToFile(is, (uploadDir / "uploaded").toJava)
    Response.created(new URI("http://bogus.com")).build()
  }
}
