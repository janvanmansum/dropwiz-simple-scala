package nl.knaw.dans.auth

import io.dropwizard.auth.Authorizer
import nl.knaw.dans.core.User

class ExampleAuthorizer extends Authorizer[User] {

  override def authorize(user: User, role: String): Boolean = {
    user.getRoles != null && user.getRoles.contains(role)
  }
}
