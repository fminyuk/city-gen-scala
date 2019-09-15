package org.nnc.citygen.parsers

import org.nnc.citygen.ast.{Stm, StmGen, StmIdent, StmMod, StmModRes, StmRes}

trait StmParser extends ExprParser {

  private val gens = Seq(
    "subdiv",
    "repeat",
    "comb"
  ).map(Parser(_)).reduce(_ | _)

  private val mods = Seq(
    "T",
    "R",
    "S"
  ).map(Parser(_)).reduce(_ | _)

  def stm: Parser[Stm] = stmModRes

  def stmRes: Parser[StmRes] = stmGen | stmIdent

  def stmIdent: Parser[StmRes] = identifier ^^ {
    name => StmIdent(name)
  }

  def stmGen: Parser[StmRes] = gens ~ ("(" ~> repsep(expr, ",") <~ ")") ~ ("{" ~> rep1sep(stm, "|") <~ "}") ^^ {
    case name ~ args ~ results => StmGen(name, args, results)
  }

  def stmMod: Parser[StmMod] = mods ~ ("(" ~> repsep(expr, ",") <~ ")") ^^ {
    case name ~ args => StmMod(name, args)
  }

  def stmModRes: Parser[StmModRes] = rep(stmMod) ~ stmRes ^^ {
    case mods ~ res => StmModRes(mods, res)
  }

  //  def stmBlock: Parser[Stm] = rep1(stmModifier | "[" ~> stmBlock <~ "]") ~ opt(stmMatch) ^^ {
  //    case items ~ Some(m) => StmBlock(items :+ m)
  //    case items ~ None => StmBlock(items)
  //  }
}
