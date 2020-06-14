# Scala Cats Examples

The goal of this project is to provide runnable examples for [data types](https://typelevel.org/cats/datatypes.html) 
and [type classes](https://typelevel.org/cats/typeclasses.html) found in Cats [documentation](https://typelevel.org/cats/#documentation)

The format of each example is as follows

```scala
/**
 * 1. One-line summary of the design problem the concept is trying to address  
 * 2. Tutorial reference
 * 3. Cat's documentation references
 */
object Concept extends App {

  // 1. Non-functional implementation of the concept
  val nonFunctionalImpl = {
    // all the imports
    
    // implementation
    
    // output
  }
  
  // 2. Functional implementation of the concept
  val functionalImpl = {
    // all the imports
    
    // implementation
    
    // output
  }
  
  
  assert(nonFunctionalImpl == functionalImpl)

}
```

Providing side-by-side the two approaches should aid understanding the functional concept by seeing how it 
differs from its non-functional counterpart. IDE's `Split Vertically` editor feature could aid the comparison even
further.  

Each example is its own app and is runnable with

```sbtshell
sbt "runMain example.Concept"
```

## Available examples

| Problem | Solution |
| ------------- | ------------- |
| Abstracting over both the element of the box and the box itself | [Type constructor polymorphism](https://github.com/mario-galic/scala-cats-examples/blob/master/src/main/scala/example/TypeConstructor.scala) |
| Polymorphic implementation of general function without using class inheritance | [Type class](https://github.com/mario-galic/scala-cats-examples/blob/master/src/main/scala/example/TypeClass.scala) |
| Avoid polluting function signatures with configuration parameter | [Kleisli (Reader)](https://github.com/mario-galic/scala-cats-examples/blob/master/src/main/scala/example/Kleisli.scala) |
| Make obvious impure parts of the program | [IO](https://github.com/mario-galic/scala-cats-examples/blob/master/src/main/scala/example/Io.scala) |
