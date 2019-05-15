package functional.forexpressions

case class Book(title: String, authors: List[String])

/**
  * 1.1-1.2 tutorial sections
  */
object ForExpressions extends App {

  val books: List[Book] = List(
    Book(title = "Structure and Interpretation of Computer Programs",
         authors = List("Abelson, Harald", "Sussman, Gerald J.")),
    Book(title = "Introduction to Functional Programming",
         authors = List("Bird, Richard", "Wadler, Phil")),
    Book(title = "Effective Java", authors = List("Bloch, Joshua")),
    Book(title = "Effective Java 2", authors = List("Bloch, Joshua")),
    Book(title = "Java Puzzlers",
         authors = List("Bloch, Joshua", "Gafter, Neal")),
    Book(title = "Programming in Scala",
         authors = List("Odersky, Martin", "Spoon, Lex", "Venners, Bill"))
  )

  // run some queries
  val birdTitle = for {
    b <- books
    a <- b.authors if a startsWith ("Bird,")
  } yield b.title
  println("For-expressions")
  println(birdTitle)

  val birdTitleHigherOrderFunction = books.flatMap { b =>
    for (a <- b.authors.withFilter(a => a startsWith "Bird,")) yield b.title
  }
  val birdTitleHigherOrderFunction2 = books
    .flatMap { b =>
      b.authors.withFilter(a => a startsWith "Bird,").map(_ => b.title)
    }
  println("With-filter and map")
  println(birdTitleHigherOrderFunction2)

  val authorsTwoBooks = {
    for {
      b1 <- books
      b2 <- books
      if b1.title < b2.title
      a1 <- b1.authors
      a2 <- b2.authors
      if a1 == a2
    } yield a1
  }.distinct

  val bookSets = books.toSet
  val setSolution = for {
    b1 <- bookSets
    b2 <- bookSets
    if b1.title < b2.title
    a1 <- b1.authors
    a2 <- b2.authors
    if a1 == a2
  } yield a1

  println(authorsTwoBooks.mkString(" ,"))
  println(setSolution.mkString(" ,"))

}
