package nl.knaw.dans.core

import java.security.Principal

import scala.beans.BeanProperty

class User(@BeanProperty val name: String, @BeanProperty val roles: Set[String]) extends Principal