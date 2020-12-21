import scala.annotation.tailrec

object Assignment1 {
  private val list = List("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday")
  private val map5 = scala.collection.immutable.Map("x" -> 20.0, "y" -> 30.0, "z" -> 40.0)
  private val list6 = List(1, 2, 3, 4, 5)
  private val list7 = List(-10.0, -1.2, 1.4, 10.5, 12.9)
  private val tuple8 = Tuple3(1, "String", 2.44)
  private val list9 = List(0, 2, 0, 4, 5, 0, 1)
  private val map10 = scala.collection.immutable.Map("x" -> Option(20.0), "y" -> Option.empty[Double], "z" -> Option(40.0))

  def main(args: Array[String]) = {
    println("task 1.a:" + task1_a(list))
    println("task 1.b:" + task1_b(list, 's'))
    println("task 1.c:" + task1_c(list))

    println("task 2.a:" + task2_a(list))
    println("task 2.b:" + task2_b(list))

    println("task 3:" + task3(list, ""))

    println("task 4.a:" + task4_a(list))
    println("task 4.b:" + task4_b(list))
    println("task 4.c:" + task4_c(list, 's'))

    println("task 5:" + task5(map5))

    println("task 6:" + task6(list6))

    println("task 7:" + task7(list7))

    print("task 8:")
    task8(tuple8)

    println("task 9:" + task9(list9, List()))

    //task - return map with products, that have value with 10% price increase
    println("task 10:" + task10(map10))


  }
}

object task1_a extends (List[String] => String) {
  def apply(list: List[String]): String = {
    var res: String = ""
    for (day <- list) {
      res = res.concat(day).concat(",")
    }
    res.substring(0, res.length - 1)
  }
}

object task1_b extends ((List[String], Char) => String) {
  def apply(list: List[String], char: Char): String = {
    var res: String = ""
    for (day <- list) {
      if (day.toLowerCase.contains(char))
        res = res.concat(day).concat(",")
    }
    res.substring(0, res.length - 1)
  }
}

object task1_c extends (List[String] => String) {
  def apply(list: List[String]): String = {
    var res: String = ""
    var index: Int = 0
    while (index < list.length) {
      res = res.concat(list(index)).concat(",")
      index += 1
    }
    res.substring(0, res.length - 1)
  }
}

object task2_a extends (List[String] => String) {
  def apply(list: List[String]): String = {
    if (list.isEmpty)
      ""
    else {
      if (list.tail.isEmpty)
        list.head + apply(list.tail)
      else
        list.head + "," + apply(list.tail)
    }
  }
}

object task2_b extends (List[String] => String) {
  def apply(list: List[String]): String = {
    if (list.isEmpty)
      ""
    else {
      if (list.tail.isEmpty)
        apply(list.tail) + list.head
      else
        apply(list.tail) + ',' + list.head
    }
  }
}

object task3 extends ((List[String], String) => String) {
  @tailrec
  def apply(list: List[String], string: String): String = {
    if (list.isEmpty)
      ""
    else {
      if (list.tail.isEmpty)
        string + list.head
      else
        apply(list.tail, string + list.head + ",")
    }
  }
}

object task4_a extends (List[String] => String) {
  def apply(list: List[String]): String = {
    list.foldLeft("") {
      (res, eachVal) =>
        if (res.equals(""))
          res + eachVal
        else
          res + "," + eachVal
    }
  }
}

object task4_b extends (List[String] => String) {
  def apply(list: List[String]): String = {
    list.foldRight("") {
      (eachVal, res) =>
        if (res.equals(""))
          eachVal + res
        else
          eachVal + "," + res
    }
  }
}

object task4_c extends ((List[String], Char) => String) {
  def apply(list: List[String], char: Char): String = {
    list.foldLeft("") {
      (res, eachVal) =>
        if (eachVal.toLowerCase().contains(char)) {
          if (res.equals(""))
            res + eachVal
          else
            res + "," + eachVal
        }
        else
          res
    }
  }
}

object task5 extends (Map[String, Double] => Map[String, Double]) {
  def apply(map: Map[String, Double]): Map[String, Double] = {
    map.transform((k, v) => v - v * 0.1)
  }
}

object task6 extends (List[Int] => List[Int]) {
  def apply(list: List[Int]): List[Int] = {
    list.map(n => n + 1)
  }
}

object task7 extends (List[Double] => List[Double]) {
  def apply(list: List[Double]): List[Double] = {
    list.filter(n => n.>(-5) && n.<(12)).map(_ abs)
  }
}

object task8 extends (Tuple3[Int, String, Double] => Unit) {
  def apply(tuple: Tuple3[Int, String, Double]): Unit = {
    println(tuple._1 + " " + tuple._2 + " " + tuple._3 + " ")
  }
}

object task9 extends ((List[Int], List[Int]) => List[Int]) {
  def apply(list: List[Int], res: List[Int]): List[Int] = {
    if (list.isEmpty)
      res
    else {
      if (list.head.equals(0))
        apply(list.tail, res)
      else
        apply(list.tail, res.appended(list.head))
    }
  }
}

object task10 extends (Map[String, Option[Double]] => Map[String, Double]) {
  def apply(map: Map[String, Option[Double]]): Map[String, Double] = {
    map.filter(p => p._2.isDefined).transform((k, v) => {
      v.get + v.get * 0.1
    })
  }
}
