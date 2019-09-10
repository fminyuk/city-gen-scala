package org.nnc.citygen.parsers

import org.nnc.citygen.ast.{Stm, StmBlock, StmIdent, StmMatch, StmModifier}

trait StmParser extends ExprParser {

  def stm: Parser[Stm] = stmBlock | stmIdent

  def stmIdent: Parser[Stm] = identifier ^^ {
    name => StmIdent(name)
  }

  def stmModifier: Parser[Stm] = identifier ~ ("(" ~> repsep(expr, ",") <~ ")") ^^ {
    case name ~ args => StmModifier(name, args)
  }

  def stmMatch: Parser[Stm] = "{" ~> rep1sep(stm, "|") <~ "}" ^^ {
    items => StmMatch(items)
  }

  def stmBlock: Parser[Stm] = rep1(stmModifier | "[" ~> stmBlock <~ "]") ~ opt(stmMatch) ^^ {
    case items ~ Some(m) => StmBlock(items :+ m)
    case items ~ None => StmBlock(items)
  }
}
