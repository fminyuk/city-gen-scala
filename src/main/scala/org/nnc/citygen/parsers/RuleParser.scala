package org.nnc.citygen.parsers

import org.nnc.citygen.ast._

trait RuleParser extends StmParser {

  def rules: Parser[Seq[Rule]] = rep(rule) ^^ {
    rules => rules
  }

  def rule: Parser[Rule] = int ~ ":" ~ identifier ~ cond ~ "->" ~ stm ~ prob ^^ {
    case id ~ _ ~ pre ~ cond ~ _ ~ suc ~ prob =>
      Rule(id.toInt, pre, cond, suc, prob)
  }

  def cond: Parser[Option[Expr]] = opt(":" ~ expr ^^ { case _ ~ expr => expr })

  def prob: Parser[Option[Double]] = opt(":" ~ float ^^ { case _ ~ prob => prob.toDouble })
}
