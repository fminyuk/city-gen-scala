package org.nnc.citygen.parsers

import org.nnc.citygen.ast._
import org.scalatest.FunSuite

class RuleParserTest extends FunSuite {

  private val p = new RuleParser {}

  test("empty") {
    val r = p.parseAll(p.rules, "")

    assert(r.successful)
    assert(r.get.isEmpty)
  }

  test("one simple") {
    val r = p.parseAll(p.rules, "1: pre -> suc")

    assert(r.successful)
    assert(r.get.size == 1)
    assert(r.get(0) == Rule(1, "pre", None, StmIdent("suc"), None))
  }

  test("one complex") {
    val r = p.parseAll(p.rules, "1: pre: h -> suc: 0.1")

    assert(r.successful)
    assert(r.get.size == 1)
    assert(r.get(0) == Rule(1, "pre", Some(ExprIdent("h")), StmIdent("suc"), Some(0.1)))
  }

  test("many") {
    val r = p.parseAll(p.rules, "1: A -> B\n2: B -> C\n3: C -> D")

    assert(r.successful)
    assert(r.get.size == 3)
    assert(r.get(0) == Rule(1, "A", None, StmIdent("B"), None))
    assert(r.get(1) == Rule(2, "B", None, StmIdent("C"), None))
    assert(r.get(2) == Rule(3, "C", None, StmIdent("D"), None))
  }
}
