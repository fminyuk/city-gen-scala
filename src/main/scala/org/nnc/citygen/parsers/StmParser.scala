package org.nnc.citygen.parsers

import org.nnc.citygen.ast.{Stm, StmGen, StmIdent}

trait StmParser extends ExprParser {

  private val genFuns = Seq(
    "subdiv",
    "repeat",
    "comb"
  ).map(Parser(_)).reduce(_ | _)

  def stm: Parser[Stm] = stmGen | stmIdent

  def stmIdent: Parser[Stm] = identifier ^^ {
    name => StmIdent(name)
  }

  def stmGen: Parser[Stm] = genFuns ~ ("(" ~> repsep(expr, ",") <~ ")") ~ ("{" ~> rep1sep(stm, "|") <~ "}") ^^ {
    case name ~ args ~ results => StmGen(name, args, results)
  }

//  def stmBlock: Parser[Stm] = rep1(stmModifier | "[" ~> stmBlock <~ "]") ~ opt(stmMatch) ^^ {
//    case items ~ Some(m) => StmBlock(items :+ m)
//    case items ~ None => StmBlock(items)
//  }
}
