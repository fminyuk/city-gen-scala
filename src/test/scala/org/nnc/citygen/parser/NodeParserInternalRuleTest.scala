package org.nnc.citygen.parser

import org.nnc.citygen.ast._
import org.scalatest.FunSuite

class NodeParserInternalRuleTest extends FunSuite {
  private val p = new NodeParserInternal

  test("simple") {
    val r = p.parseAll(p.rule, "1: pre -> suc: 0.1")

    assert(r.successful)
    assert(r.get == Rule(1, "pre", null, StmIdent("suc"), 0.1))
  }

  test("condition") {
    val r = p.parseAll(p.rule, "1: pre: h > 1 -> suc: 0.1")

    assert(r.successful)
    assert(r.get == Rule(1, "pre", ExprFunction(">", List(ExprIdent("h"), ExprAbs(1))), StmIdent("suc"), 0.1))
  }
}
