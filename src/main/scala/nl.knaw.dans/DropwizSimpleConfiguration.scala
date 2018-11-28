package nl.knaw.dans

import better.files.File
import com.fasterxml.jackson.annotation.JsonProperty
import io.dropwizard.Configuration
import org.hibernate.validator.constraints.NotEmpty

class DropwizSimpleConfiguration extends Configuration {
  @NotEmpty @JsonProperty val template: String = ""
  @NotEmpty @JsonProperty val defaultName = "Stranger"
  @JsonProperty val uploadDir = File("/tmp")
}
