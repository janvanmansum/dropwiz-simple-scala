package nl.knaw.dans.health

import com.codahale.metrics.health.HealthCheck
import com.codahale.metrics.health.HealthCheck.Result


class TemplateHealthCheck(val template: String) extends HealthCheck {
  @throws[Exception]
  override protected def check: HealthCheck.Result = {
    val saying = String.format(template, "TEST")
    if (!saying.contains("TEST")) return Result.unhealthy("template doesn't include a name")
    Result.healthy
  }
}
