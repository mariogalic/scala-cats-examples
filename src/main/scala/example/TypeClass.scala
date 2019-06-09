package example

/**
  * Problem: Polymorphic implementation of general function without using class inheritance
  * Tutorial: https://medium.com/@olxc/type-classes-explained-a9767f64ed2c
  * Doc: https://typelevel.org/cats/typeclasses.html
  */
object TypeClass extends App {
  val nonFunctionalImpl = {
    println("Polymorphism by Subtyping")

    trait Shape {
      def area: Double
    }
    class Rectangle(val width: Double, val length: Double) extends Shape {
      override def area: Double = width * length
    }
    class Circle(val radius: Double) extends Shape {
      override def area: Double = Math.PI * (radius * radius)
    }

    def areaOf(shape: Shape): Double =
      shape.area

    val rectangle = new Rectangle(2, 3)
    val circle = new Circle(2)

    (
      areaOf(rectangle),
      areaOf(circle)
    )
  }

  val functionalImpl1 = {
    println("Type class with no implicits")

    trait Area[A] {
      def area(a: A): Double
    }

    case class Rectangle(width: Double, length: Double)
    case class Circle(radius: Double)

    val rectangleArea = new Area[Rectangle] {
      override def area(a: Rectangle): Double = a.width * a.length
    }

    val circleArea = new Area[Circle] {
      override def area(a: Circle): Double = Math.PI * (a.radius * a.radius)
    }

    def areaOf[A](a: A, A: Area[A]): Double =
      A.area(a)

    val rectangle = new Rectangle(2, 3)
    val circle = new Circle(2)

    (
      areaOf(rectangle, rectangleArea),
      areaOf(circle, circleArea)
    )
  }

  val functionalImpl2 = {
    println("Type class with implicits")

    trait Area[A] {
      def area(a: A): Double
    }

    case class Rectangle(width: Double, length: Double)
    case class Circle(radius: Double)

    implicit val rectangleArea = new Area[Rectangle] {
      override def area(a: Rectangle): Double = a.width * a.length
    }

    implicit val circleArea = new Area[Circle] {
      override def area(a: Circle): Double = Math.PI * (a.radius * a.radius)
    }

    def areaOf[A](a: A)(implicit A: Area[A]): Double =
      A.area(a)

    val rectangle = new Rectangle(2, 3)
    val circle = new Circle(2)

    (
      areaOf(rectangle),
      areaOf(circle)
    )
  }

  val functionalImpl3 = {
    println("Type class with implicits, and context bound syntactic sugar")

    trait Area[A] {
      def area(a: A): Double
    }

    case class Rectangle(width: Double, length: Double)
    case class Circle(radius: Double)

    implicit val rectangleArea = new Area[Rectangle] {
      override def area(a: Rectangle): Double = a.width * a.length
    }

    implicit val circleArea = new Area[Circle] {
      override def area(a: Circle): Double = Math.PI * (a.radius * a.radius)
    }

    def areaOf[A: Area](a: A): Double =
      implicitly[Area[A]].area(a)

    val rectangle = new Rectangle(2, 3)
    val circle = new Circle(2)

    (
      areaOf(rectangle),
      areaOf(circle)
    )
  }

  val functionalImpl4 = {
    println("Type class with implicits, and context bound syntatic sugar, and with apply utility method on the companion object to avoid implicitly")

    trait Area[A] {
      def area(a: A): Double
    }

    object Area {
      def apply[A: Area]: Area[A] = implicitly[Area[A]]
    }

    case class Rectangle(width: Double, length: Double)
    case class Circle(radius: Double)

    implicit val rectangleArea = new Area[Rectangle] {
      override def area(a: Rectangle): Double = a.width * a.length
    }

    implicit val circleArea = new Area[Circle] {
      override def area(a: Circle): Double = Math.PI * (a.radius * a.radius)
    }

    def areaOf[A: Area](a: A): Double =
      Area[A].area(a)

    val rectangle = new Rectangle(2, 3)
    val circle = new Circle(2)

    (
      areaOf(rectangle),
      areaOf(circle)
    )
  }

  assert(
    List(
      functionalImpl1,
      functionalImpl2,
      functionalImpl3,
      functionalImpl4
    ).forall(_ == nonFunctionalImpl)
  )

}
