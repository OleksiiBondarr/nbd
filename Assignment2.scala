object Assignment2 {
  def main(args: Array[String]) = {
    println("task 1:" + task1("Monday") + ", " + task1("Saturday") + ", " + task1("xx"))

    val b1 = new BankAccount(1.1)
    val b2 = new BankAccount()
    println("task 2:" + b1.currentBalance + ", " + b2.currentBalance)

    val p1 = new Person("Alex","Bondar")
    val p2 = new Person("Anna","Steklyanova")
    val p3 = new Person("Bohdan","Burliai")
    println("task 3:" + task23(p1) + ", " + task23(p2) + ", " + task23(p3))

    def task4Sup(int:Int):Int = if (int>0) int +1 else if (int < 0) (int-1).abs else 1000

    println("task 4:" + task24(0,task4Sup) + ", " + task24(-2,task4Sup))

    val e1 = new Person5 with Employee {
      override val firstName: String = "name1"
      override val lastName: String = "surn1"
      override var _salary: Double = 1000.0
    }
    val t1 = new Person5 with Teacher {
      override val firstName: String = "name2"
      override val lastName: String = "surn2"
      override var _salary: Double = 1000.0
    }
    val s1 = new Person5 with Student {
      override val firstName: String = "name1"
      override val lastName: String = "surn1"
    }

    val es = new Person5 with Employee with Student {
      override val firstName: String = "se1"
      override val lastName: String = "se1"
      override var _salary: Double = 1000.0
    }

    val se = new Person5 with Student with Employee {
      override val firstName: String = "es1"
      override val lastName: String = "es1"
      override var _salary: Double = 1000.0
    }

    println("task 5:Employee(sal=1000)" + e1.taxToPay + ",Teacher(sal=1000) " + t1.taxToPay + ",Student " + s1.taxToPay +
      ",StudentEmployee(sal=1000) " + se.taxToPay + ",EmployeeStudent(sal=1000) " + es.taxToPay)

  }
}

object task1 extends (String => String) {
  def apply(day: String): String = {
    val workDay = "work"
    val weekendDay = "weekend"
    day match {
      case "Monday" => workDay
      case "Tuesday" => workDay
      case "Wednesday" => workDay
      case "Thursday" => workDay
      case "Friday" => workDay
      case "Saturday" => weekendDay
      case "Sunday" => weekendDay
      case _ => "no such day"
    }
  }
}

class BankAccount() {
  //default constructor provides currentBalance = 0.0
  private var _currentBalance = 0.0

  def currentBalance: Double = _currentBalance

  def this(initBalance: Double) {
    this()
    this._currentBalance = initBalance
  }

  def deposit(value: Double): Unit = {
    this._currentBalance += value
  }

  def withdraw(value: Double): Unit = {
    this._currentBalance -= value
  }
}


class Person(var firstName: String, var lastName: String) {

}

object task23 extends (Person => String) {
  def apply(person: Person): String = {
    val strangeCriteria = person.firstName.length % 3 + person.lastName.length % 2
    strangeCriteria match {
      case x if x < 2 => "Hello"
      case 2 => "Hi there!"
      case 3 => "Hello Mr/Mrs " + person.firstName
      case _ => "Hi, sorry, you broke me"
    }
  }
}

object task24 extends ((Int,Int=>Int) => Int) {
  def apply(int: Int, f: Int=>Int): Int = {
    f(f(f(int)))
  }
}

trait Student extends Person5 {
  override val _taxToPay: Double = 0.0
  override def taxToPay: Double = _taxToPay
}

trait Teacher extends Employee {
  override val _taxToPay: Double = 0.1
}

trait Employee extends Person5 {
  var _salary:Double
  def salary:Double = _salary
  def salary_(salary:Double): Unit = _salary = salary

  override val _taxToPay: Double = 0.2
  override def taxToPay: Double = _taxToPay*_salary

}

abstract class Person5() {
  val firstName: String
  val lastName: String
  val _taxToPay: Double
  def taxToPay:Double = _taxToPay
}