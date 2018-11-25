package nl.knaw.dans.auth

import java.util.Optional

import io.dropwizard.auth.Authenticator
import io.dropwizard.auth.basic.BasicCredentials
import javax.naming.AuthenticationException
import nl.knaw.dans.core.User

class ExampleAuthenticator
  extends Authenticator[BasicCredentials, User] {
  private val VALID_USERS: Map[String, Set[String]] = Map(
    "guest" -> Set.empty[String],
    "good-guy" -> Set("BASIC_GUY"),
    "chief-wizard" -> Set("ADMIN", "BASIC_GUY"))

  @throws[AuthenticationException]
  override def authenticate(credentials: BasicCredentials): Optional[User] = {
    if (VALID_USERS.contains(credentials.getUsername) && "secret" == credentials.getPassword)
      Optional.of(new User(credentials.getUsername, VALID_USERS(credentials.getUsername)))
    else Optional.empty()
  }
}