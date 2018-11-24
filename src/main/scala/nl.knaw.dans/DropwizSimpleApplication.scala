package nl.knaw.dans

import io.dropwizard.setup.{ Bootstrap, Environment }
import io.dropwizard.Application
import io.dropwizard.configuration.{ EnvironmentVariableSubstitutor, SubstitutingSourceProvider }
import nl.knaw.dans.health.TemplateHealthCheck
import nl.knaw.dans.resources.HelloWorldResource

object DropwizSimpleApplication extends Application[DropwizSimpleConfiguration] with App {
  run(args.toArray: _*)

  override def getName = "DropwizSimple"

  override def initialize(bootstrap: Bootstrap[DropwizSimpleConfiguration]): Unit = {
    bootstrap.setConfigurationSourceProvider(
      new SubstitutingSourceProvider(bootstrap.getConfigurationSourceProvider, new EnvironmentVariableSubstitutor(false)))
  }

  override def run(configuration: DropwizSimpleConfiguration, environment: Environment): Unit = {
    val resource = new HelloWorldResource(configuration.template, configuration.defaultName)
    val healthCheck = new TemplateHealthCheck(configuration.template)
    environment.healthChecks.register("template", healthCheck)
    environment.jersey.register(resource)
  }
}
