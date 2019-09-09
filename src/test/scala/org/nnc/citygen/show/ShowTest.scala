package org.nnc.citygen.show

import org.nnc.citygen.ast._
import org.scalatest.FunSuite

class ShowTest extends FunSuite {

  import cats.implicits._

  test("expr value") {
    val e = ExprValue(10)

    assert(e.show == "10.0")
  }

  test("expr ident") {
    val e = ExprIdent("x")

    assert(e.show == "x")
  }

  test("expr function") {
    val e = ExprFunction("min", Seq(ExprValue(1), ExprIdent("a")))

    assert(e.show == "min(1.0, a)")
  }

  test("stm ident") {
    val s = StmIdent("A")

    assert(s.show == "A")
  }

  test("stm modifier") {
    val s = StmModifier("S", Seq(ExprValue(2), ExprFunction("min", Seq(ExprValue(1), ExprIdent("a")))))

    assert(s.show == "S(2.0, min(1.0, a))")
  }

  test("stm block") {
    val s = StmBlock(Seq(StmModifier("S", Seq(ExprValue(2))), StmModifier("I", Seq())))

    assert(s.show == "[S(2.0) I()]")
  }

  test("stm match") {
    val s = StmMatch(Seq(StmIdent("A"), StmIdent("B")))

    assert(s.show == "{A | B}")
  }

  test("rule simple") {
    val r = Rule(1, "A", None, StmIdent("B"), None)

    assert(r.show == "1: A -> B")
  }

  test("rule condition") {
    val r = Rule(1, "A", Some(ExprFunction(">", Seq(ExprIdent("h"), ExprValue(5)))), StmIdent("B"), None)

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
}
