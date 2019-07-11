package org.nnc.citygen.parser

import org.nnc.citygen.ast._

import scala.util.parsing.combinator.RegexParsers

private class NodeParserInternal extends RegexParsers {
  private val integer = """[+-]?\d+""".r
  private val real = """[+-]?\d+(:?\.\d*)?(:?[eE][+-]?\d+)?""".r
  private val identifier = """[a-zA-Z_][a-zA-Z_0-9]*""".r

  def rule: Parser[Node] = integer ~ ":" ~ identifier ~ opt(":" ~ expr) ~ "->" ~ stm ~ ":" ~ real ^^ {
    case id ~ _ ~ pre ~ Some(_ ~ cond) ~ _ ~ suc ~ _ ~ prob => Rule(id.toInt, pre, cond, suc, prob.toDouble)
    case id ~ _ ~ pre ~ None ~ _ ~ suc ~ _ ~ prob => Rule(id.toInt, pre, null, suc, prob.toDouble)
  }

  def expr: Parser[Expr] = operators(List(
    ("+ -", unary),
    ("!", unary),
    ("* /", binary),
    ("+ -", binary),
    ("== != >= <= > <", binary),
    ("&&", binary),
    ("||", binary)
  ))(exprFactor)

  def exprAbs: Parser[Expr] = real ^^ {
    value => ExprAbs(value.toDouble)
  }

  def exprRel: Parser[Expr] = real ~ "r" ^^ {
    case value ~ _ => ExprRel(value.toDouble)
  }

  def exprIdent: Parser[Expr] = identifier ^^ {
    name => ExprIdent(name)
  }

  def exprFun: Parser[Expr] = identifier ~ ("(" ~> repsep(expr, ",") <~ ")") ^^ {
    case name ~ args => ExprFunction(name, args)
  }

  def exprFactor: Parser[Expr] = exprRel | exprAbs | exprFun | exprIdent | "(" ~> expr <~ ")"

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

  def operators(ops: Seq[(String, (Parser[Expr], Parser[String]) => Parser[Expr])])(s: Parser[Expr]): Parser[Expr] = {
    ops.foldLeft(s) { (p, op) => op._2(p, operexpr(op._1)) }
  }

  def unary(p: Parser[Expr], ops: Parser[String]): Parser[Expr] = opt(ops) ~ p ^^ {
    case None ~ e => e
    case Some(op) ~ e => ExprFunction(op, List(e))
  }

  def binary(p: Parser[Expr], ops: Parser[String]): Parser[Expr] = p ~ opt(ops ~ p) ^^ {
    case e ~ None => e
    case l ~ Some(op ~ r) => ExprFunction(op, List(l, r))
  }

  def operexpr(ops: String): Parser[String] = ops.split("""\s+""").map(Parser(_)).reduce(_ | _)
}
