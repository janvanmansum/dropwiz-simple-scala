package nl.knaw.dans

import io.dropwizard.setup.{ Bootstrap, Environment }
import io.dropwizard.Application
import io.dropwizard.auth.{ AuthDynamicFeature, AuthValueFactoryProvider }
import io.dropwizard.auth.basic.BasicCredentialAuthFilter
import io.dropwizard.configuration.{ EnvironmentVariableSubstitutor, SubstitutingSourceProvider }
import nl.knaw.dans.auth.{ ExampleAuthenticator, ExampleAuthorizer }
import nl.knaw.dans.core.User
import nl.knaw.dans.health.TemplateHealthCheck
import nl.knaw.dans.resources.HelloWorldResource
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature

object DropwizSimpleApplication extends Application[DropwizSimpleConfiguration] with App {
  run(args.toArray: _*)

  override def getName = "DropwizSimple"

  override def initialize(bootstrap: Bootstrap[DropwizSimpleConfiguration]): Unit = {
    bootstrap.setConfigurationSourceProvider(
      new SubstitutingSourceProvider(bootstrap.getConfigurationSourceProvider, new EnvironmentVariableSubstitutor(false)))
  }

  override def run(configuration: DropwizSimpleConfiguration, environment: Environment): Unit = {
    val resource = new HelloWorldResource(configuration.template, configuration.defaultName, configuration.uploadDir)
    environment.jersey.register(
      new AuthDynamicFeature(
        new BasicCredentialAuthFilter.Builder[User]()
          .setAuthenticator(new ExampleAuthenticator)
          .setAuthorizer(new ExampleAuthorizer)
          .setRealm("SUPER SECRET STUFF").buildAuthFilter))
    environment.jersey.register(new AuthValueFactoryProvider.Binder[User](classOf[User]))
    environment.jersey.register(classOf[RolesAllowedDynamicFeature])
    val healthCheck = new TemplateHealthCheck(configuration.template)
    environment.healthChecks.register("template", healthCheck)
    environment.jersey.register(resource)
  }
}
