object Assignment10 {
  def main(args: Array[String]): Unit = {

    def x = for (i <- LazyList.from(1).iterator;
                 j <- (1 to i).iterator if i % j == 0) yield (i, j)
    println("task1:")
    println("next:")
    println(x.next())
    println("take:")
    x.take(20).foreach(println)
  }

}
