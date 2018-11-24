package nl.knaw.dans.api

import com.fasterxml.jackson.annotation.JsonProperty
import javax.validation.constraints.Max

import scala.beans.BeanProperty

class Saying(@BeanProperty @JsonProperty val id: Long,
             @BeanProperty @JsonProperty @Max(3) val content: String)
