package org.nnc.citygen.parsers

import org.nnc.citygen.ast._
import org.scalatest.FunSuite

class StmParserTest extends FunSuite {

  private val p = new StmParser {}

  test("ident") {
    val r = p.parseAll(p.stm, "x")

    assert(r.successful)
    assert(r.get == StmIdent("x"))
  }

  test("gen") {
    val r = p.parseAll(p.stm, "subdiv(x, 1, 2) {A | B}")

    assert(r.successful)
    assert(r.get == StmGen("subdiv", Seq(ExprIdent("x"), ExprInt(1), ExprInt(2)), Seq(StmIdent("A"), StmIdent("B"))))
  }
}
