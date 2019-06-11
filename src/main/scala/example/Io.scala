package example

/**
  * Problem: Make obvious impure parts of the program
  * Tutorial: https://stackoverflow.com/a/55839913/5205022
  * Doc: https://typelevel.org/blog/2017/05/02/io-monad-for-cats.html
  *
  * Further references
  *   - https://www.reddit.com/r/scala/comments/8ygjcq/can_someone_explain_to_me_the_benefits_of_io/e2bgdq6/
  *   - https://stackoverflow.com/questions/4063778/in-what-sense-is-the-io-monad-pure
  */
object Io extends App {
  lazy val nonFunctionalImpl = {
    import scala.io.StdIn

    def readStr(): String = StdIn.readLine()

    println("Welcome to Vulcan. What's your name?")
    val name = readStr()
    println("What planet do you come from?")
    val planet = readStr()
    println(s"Live Long and Prosper 🖖, $name from $planet.")
  }

  lazy val functionalImpl = {
    import cats.effect.IO
    import scala.io.StdIn

    val readStr: IO[String] = IO(StdIn.readLine()) // not how this is a val

    val program: IO[Unit] = for {
      _ <- IO { println("Welcome to Vulcan. What's your name?") }
      name <- readStr
      _ <- IO(println("What planet do you come from?"))
      planet <- readStr
      _ <- IO { println(s"Live Long and Prosper 🖖, $name from $planet.") }
    } yield ()

    program.unsafeRunSync() // actually execute effects
  }

  assert(RedirectStdInOut(nonFunctionalImpl) == RedirectStdInOut(functionalImpl))
}

// https://stackoverflow.com/a/56523167/5205022
object RedirectStdInOut {
  def apply(f: => Any): String = {
    import Console._
    import java.io.StringReader
    val inputStr =
      """|Jean-Luc Picard
         |Earth
      """.stripMargin
    val in = new StringReader(inputStr)
    val out = new java.io.ByteArrayOutputStream()
    withOut(out) {
      withIn(in) {
        f
      }
    }
    out.toString
  }
}
