package org.nnc.citygen.parsers

import org.nnc.citygen.ast.{StmBlock, StmGen, StmIdent, StmMod, StmModRes, StmPrg, StmRes}

trait StmParser extends ExprParser {

  private val gens = oneof(StmParser.GENS)
  private val mods = oneof(StmParser.MODS)

  def stm: Parser[StmBlock] = rep1(stmPrg) ^^ {
    items => StmBlock(items)
  }

  def stmPrg: Parser[StmPrg] = stmModRes | ("[" ~> stm <~ "]")

  def stmModRes: Parser[StmModRes] = rep(stmMod) ~ stmRes ^^ {
    case mods ~ res => StmModRes(mods, res)
  }

  def stmMod: Parser[StmMod] = mods ~ ("(" ~> repsep(expr, ",") <~ ")") ^^ {
    case name ~ args => StmMod(name, args)
  }

  def stmRes: Parser[StmRes] = stmGen | stmIdent

  def stmIdent: Parser[StmRes] = ExprParser.IDENTIFIER ^^ {
    name => StmIdent(name)
  }

  def stmGen: Parser[StmRes] = gens ~ ("(" ~> repsep(expr, ",") <~ ")") ~ ("{" ~> rep1sep(stm, "|") <~ "}") ^^ {
    case name ~ args ~ results => StmGen(name, args, results)
  }

  def oneof(idents: Seq[String]): Parser[String] = {
    idents.map(Parser(_)).reduce(_ | _)
  }
}

object StmParser {
  val GENS: Seq[String] = Seq(
    "subdiv",
    "repeat",
    "comp"
  )

  val MODS: Seq[String] = Seq(
    "T",
    "R",
    "S"
  )
}
