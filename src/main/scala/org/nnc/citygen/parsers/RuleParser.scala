package org.nnc.citygen.parsers

import org.nnc.citygen.ast._
import scala.util.matching.Regex

private trait RuleParser extends StmParser {

  protected val integer: Regex = """[+-]?\d+""".r

  def rules: Parser[Seq[Rule]] = rep(rule) ^^ {
    rules => rules
  }

  def rule: Parser[Rule] = integer ~ ":" ~ identifier ~ cond ~ "->" ~ stm ~ prob ^^ {
    case id ~ _ ~ pre ~ cond ~ _ ~ suc ~ prob =>
      Rule(id.toInt, pre, cond, suc, prob)
  }

  def cond: Parser[Option[Expr]] = opt(":" ~ expr ^^ { case _ ~ expr => expr })

  def prob: Parser[Option[Double]] = opt(":" ~ real ^^ { case _ ~ prob => prob.toDouble })
}
