object Assignment9 {
  def main(args: Array[String]): Unit = {

    val container: Container[String] = new Container[String]("sss")
    println("task1: " + container.applyFunction(f1))
    val no: No = new No()
    val yes: Yes[String] = new Yes[String]("aaa")
    println("task2: ")
    println("no.isInstanceOf(Maybe[_]): " + no.isInstanceOf[Maybe[_]])
    println("yes.isInstanceOf(Maybe[_]): " + yes.isInstanceOf[Maybe[_]])

    println("task3: ")
    val yes3: Yes[Int] = yes.applyFunction(f1)
    println("yes test : " + yes3.getContent)

    val no3: No = no.applyFunction(f1)
    println("no test : " + no3.isInstanceOf[No])

    println("task4: ")
    println("yes test : " + yes.getOrElse("rrr"))

    println("no test : " + no.getOrElse("eee"))
  }

}

class Container[A](private val value: A) {

  def getContent: A = {
    value
  }

  def applyFunction[R](f: A => R): R = {
    f(value)
  }
}

trait Maybe[A] {
  def applyFunction[R](f: A => R): Maybe[_]

  def getOrElse[B >: A](v: B): B
}

class No extends Maybe[Nothing] {
  override def applyFunction[R](f: Nothing => R): No = {
    new No()
  }

  override def getOrElse[B](v: B): B = {
    v
  }
}

class Yes[A](var v1: A) extends Maybe[A] {
  def getContent: A = {
    v1
  }

  override def applyFunction[R](f: A => R): Yes[R] = {
    new Yes[R](f(v1))
  }

  override def getOrElse[B >: A](v: B): B = {
    v1
  }
}

object f1 extends (String => Int) {
  def apply(str: String): Int = {
    str.length
  }
}