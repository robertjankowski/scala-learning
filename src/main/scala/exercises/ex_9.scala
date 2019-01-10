// package com.exercises

object ex9 extends App {

    def pack[A](ls: List[A]): List[List[A]] = {
        if (ls.isEmpty) List(List())
        else {
            var (packed, next) = ls.span { _ == ls.head }
            if (next == Nil) List(packed)
            else packed :: pack(next)
        }
    }

    def encode[A](ls: List[A]): List[(Int, A)] = {
        pack(ls).map {
            e => (e.length, e.head)
        }
    }
    val v = List('a, 'a, 'a, 'a, 'b, 'c, 'c, 'a, 'a, 'd, 'e, 'e, 'e, 'e)
    println(pack(v))
    println(encode(v))

    // TODO: p11
}
