package com.exercises

object ex8 extends App {
    def compressFunctional[A](ls: List[A]): List[A] = 
        ls.foldRight(List[A]()) { (h, r) =>
            if (r.isEmpty || r.head != h) h :: r
            else r
        }
    
    def compressRecursive[A](ls: List[A]): List[A] = ls match {
        case Nil       => Nil
        case h :: tail => h :: compressRecursive(tail.dropWhile(_ == h))
    }

    val ls = List('a', 'a', 'b', 'c', 'c', 'e', 'e', 'e')
    val compressLs = compressFunctional(ls)
    val compressLsRec = compressRecursive(ls)
    println(s"Output1: $compressLs")
    println(s"Output2: $compressLsRec")
}
