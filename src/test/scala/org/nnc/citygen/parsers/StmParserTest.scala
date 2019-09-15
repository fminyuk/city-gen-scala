package org.nnc.citygen.parsers

import org.nnc.citygen.ast._
import org.scalatest.FunSuite

class StmParserTest extends FunSuite {

  private val p = new StmParser {}

  test("ident") {
    val r = p.parseAll(p.stm, "x")

    assert(r.successful)
    assert(r.get == StmModRes(Seq(), StmIdent("x")))
  }

  test("gen") {
    val r = p.parseAll(p.stm, "repeat(x, 1f) {A | B}")

    assert(r.successful)
    assert(r.get == StmModRes(Seq(), StmGen(
      "repeat",
      Seq(ExprIdent("x"), ExprFloat(1)),
      Seq(StmModRes(Seq(), StmIdent("A")), StmModRes(Seq(), StmIdent("B"))))))
  }
}
