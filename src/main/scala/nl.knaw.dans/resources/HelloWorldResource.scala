package nl.knaw.dans.resources

import java.util.concurrent.atomic.AtomicLong
import java.util.Optional

import com.codahale.metrics.annotation.Timed
import javax.annotation.security.RolesAllowed
import javax.ws.rs.{ GET, Path, Produces, QueryParam }
import javax.ws.rs.core.MediaType
import nl.knaw.dans.api.Saying

@Path("/hello-world")
@Produces(Array(MediaType.APPLICATION_JSON))
class HelloWorldResource(val template: String, val defaultName: String) {
  private val counter: AtomicLong = new AtomicLong()

  @RolesAllowed(Array("ADMIN"))
  @GET
  @Timed
  def sayHello(@QueryParam("name") name: Optional[String]): Saying = {
    val value = String.format(template, name.orElse(defaultName))
    new Saying(counter.incrementAndGet(), value)
  }
}
