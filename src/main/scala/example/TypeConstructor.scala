package example

/**
 * Problem: Abstracting over both the element of the box and the box itself
 * Tutorial: https://stackoverflow.com/a/62366781/5205022
 * Doc: http://adriaanm.github.io/research/2010/10/06/new-in-scala-2.8-type-constructor-inference/
 */
object TypeConstructor extends App {
  trait Zar         // Zar is proper type                            - shape *               - box       of chocolates
  trait Foo[A]      // Foo is type constructor of 1st order kind     - shape * -> *          - box       of things
  trait Bar[F[_]]   // Bar is type constructor of higher order kind  - shape (* -> *) -> *   - container of things

  def f[A] = println("f takes proper type argument of * shape")
  f[Int]
  f[Zar]
  f[List[Int]]
  f[Tuple2[Int, String]]
  f[Function3[Int, Double, Char, String]]
  // f[List]                                   // error because actual * -> * shape does not fit expected * shape
  // f[Foo]                                    // error because actual * -> * shape does not fit expected * shape
  // f[Bar]                                    // error because actual (* -> *) -> * shape does not fit expected * shape
  // f[cats.Functor]                           // error because actual (* -> *) -> * shape does not fit expected * shape

  def g[F[_]] = println("g takes type constructor type argument of * -> * shape")
  // g[Int]                                    // error because actual * shape does not fit expected * -> * shape
  // g[Zar]                                    // error because actual * shape does not fit expected * -> * shape
  // g[List[Int]]                              // error because actual * shape does not fit expected * -> * shape
  // g[Tuple2[Int, String]]                    // error because actual * shape does not fit expected * -> * shape
  // g[Function3[Int, Double, Char, String]]   // error because actual * shape does not fit expected * -> * shape
  g[List]
  g[Foo]
  // g[Bar]                                    // error because actual (* -> *) -> * shape does not fit expected * -> * shape
  // g[cats.Functor]                           // error because actual (* -> *) -> * shape does not fit expected * -> * shape

  def h[F[G[_]]] = println("h takes type constructor type argument of (* -> *) -> * shape")
  // h[Int]                                    // error because actual * shape does not fit expected (* -> *) -> * shape
  // h[Zar]                                    // error because actual * shape does not fit expected (* -> *) -> * shape
  // h[List[Int]]                              // error because actual * shape does not fit expected (* -> *) -> * shape
  // h[Tuple2[Int, String]]                    // error because actual * shape does not fit expected (* -> *) -> * shape
  // h[Function3[Int, Double, Char, String]]   // error because actual * shape does not fit expected (* -> *) -> * shape
  // h[List]                                   // error because actual * -> * shape does not fit expected  (* -> *) -> * shape
  // h[Foo]                                    // error because actual * -> * shape does not fit expected  (* -> *) -> * shape
  h[Bar]
  h[cats.Functor]                              // error because actual (* -> *) -> * shape does not fit expected * -> * shape
}
