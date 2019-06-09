package example

/**
  * Problem: Avoid polluting function signatures with configuration parameter
  * Tutorial: https://stackoverflow.com/a/55839913/5205022
  * Doc: https://typelevel.org/cats/datatypes/kleisli.html
  */
object Kleisli extends App   {

  val nonFunctionalImpl = {
    import java.util.Base64
    import scalaj.http.Http

    case class Config(username: String, password: String, host: String)

    def encodeCredentials(config: Config): String = {
      Base64.getEncoder.encodeToString(s"${config.username}:${config.password}".getBytes())
    }

    def basicAuth(credentials: String, config: Config): String = {
      Http(s"${config.host}/HTTP/Basic/")
        .header("Authorization", s"Basic $credentials")
        .asString
        .body
    }

    def validateResponse(body: String): Either[String, String] = {
      if (body.contains("Your browser made it"))
        Right("Credentials are valid!")
      else
        Left("Wrong credentials")
    }

    def program(config: Config): Either[String, String] = {
      val credentials = encodeCredentials(config)
      val response = basicAuth(credentials, config)
      val validation = validateResponse(response)
      validation
    }

    val config = Config("guest", "guest", "https://jigsaw.w3.org")
    program(config)
  }

  val functionalImpl = {
    import cats.data.Reader
    import java.util.Base64
    import scalaj.http.Http

    case class Config(username: String, password: String, host: String)

    def encodeCredentials: Reader[Config, String] = Reader { config =>
      Base64.getEncoder.encodeToString(s"${config.username}:${config.password}".getBytes())
    }

    def basicAuth(credentials: String): Reader[Config, String] = Reader { config =>
      Http(s"${config.host}/HTTP/Basic/")
        .header("Authorization", s"Basic $credentials")
        .asString
        .body
    }

    def validateResponse(body: String): Reader[Config, Either[String, String]] = Reader { _ =>
      if (body.contains("Your browser made it"))
        Right("Credentials are valid!")
      else
        Left("Wrong credentials")
    }

    def program: Reader[Config, Either[String, String]] = for {
      credentials       <- encodeCredentials
      response          <- basicAuth(credentials)
      validation        <- validateResponse(response)
    } yield validation


    val config = Config("guest", "guest", "https://jigsaw.w3.org")
    program.run(config)
  }

  assert(nonFunctionalImpl == functionalImpl)
}
