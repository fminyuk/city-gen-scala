package org.nnc.citygen.show

import org.nnc.citygen.ast._
import org.scalatest.FunSuite

class ShowTest extends FunSuite {

  import cats.implicits._

  test("expr value: bool") {
    val e = ExprBool(true)

    assert(e.show == "true")
  }

  test("expr value: int") {
    val e = ExprInt(10)

    assert(e.show == "10")
  }

  test("expr value: float") {
    val e = ExprFloat(10)

    assert(e.show == "10.0")
  }

  test("expr ident") {
    val e = ExprIdent("x")

    assert(e.show == "x")
  }

  test("expr function") {
    val e = ExprFunction("min", Seq(ExprInt(1), ExprIdent("a")))

    assert(e.show == "min(1, a)")
  }

  test("stm ident") {
    val s = StmIdent("A")

    assert(s.show == "A")
  }

  test("stm gen") {
    val s = StmGen("repeat", Seq(ExprIdent("x"), ExprFloat(2)), Seq(mod(StmIdent("A")), mod(StmIdent("B"))))

    assert(s.show == "repeat(x, 2.0) {A | B}")
  }

  test("rule simple") {
    val r = Rule(1, "A", None, StmIdent("B"), None)

    assert(r.show == "1: A -> B")
  }

  test("rule condition") {
    val r = Rule(1, "A", Some(ExprFunction(">", Seq(ExprIdent("h"), ExprFloat(5)))), StmIdent("B"), None)

    assert(r.show == "1: A: >(h, 5.0) -> B")
  }

  test("rule probability") {
    val r = Rule(1, "A", None, StmIdent("B"), Some(0.5))

    assert(r.show == "1: A -> B: 0.5")
  }

  test("rule complex") {
    val r = Rule(1, "A", Some(ExprIdent("r")), StmIdent("B"), Some(0.5))

    assert(r.show == "1: A: r -> B: 0.5")
  }

  private def mod(prg: StmRes): StmModRes = StmModRes(Seq(), prg)
}
