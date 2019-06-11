package example

import org.scalatest._

class IoSpec extends FlatSpec with Matchers {
  "IO non-functional implementation" should "include 🖖, name, and planet" in {
    val out = RedirectStdInOut(Io.nonFunctionalImpl)
    out.toString should (include ("🖖") and include ("Jean-Luc Picard") and include ("Earth"))
  }

  "IO functional implementation" should "include 🖖, name, and planet" in {
    val out = RedirectStdInOut(Io.functionalImpl)
    out.toString should (include ("🖖") and include ("Jean-Luc Picard") and include ("Earth"))
  }
}
