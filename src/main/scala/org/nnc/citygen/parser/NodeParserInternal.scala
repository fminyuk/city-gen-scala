package org.nnc.citygen.parser

import org.nnc.citygen.ast._

import scala.util.parsing.combinator.RegexParsers

private class NodeParserInternal extends RegexParsers {
  private val integer = """[+-]?\d+""".r
  private val real = """[+-]?\d+(:?\.\d*)?(:?[eE][+-]?\d+)?""".r
  private val identifier = """[a-zA-Z_][a-zA-Z_0-9]*""".r

  def rule: Parser[Node] = integer ~ ":" ~ identifier ~ opt(":" ~ cond) ~ "->" ~ real ^^ {
    case id ~ _ ~ p ~ Some(_ ~ cond) ~ _ ~ prob => Rule(id.toInt, p, cond, prob.toDouble)
    case id ~ _ ~ p ~ None ~ _ ~ prob => Rule(id.toInt, p, null, prob.toDouble)
  }

  def cond: Parser[Expr] = expr

  def expr: Parser[Expr] = operators(List(
    ("+ -", unary),
    ("!", unary),
    ("* /", binary),
    ("+ -", binary),
    ("== != >= <= > <", binary),
    ("&&", binary),
    ("||", binary)
  ))(factor)

  def func: Parser[Expr] = identifier ~ opt("(" ~> repsep(expr, ",") <~ ")") ^^ {
    case name ~ None => ExprIdent(name)
    case name ~ Some(args) => ExprFunction(name, args)
  }

  def value: Parser[Expr] = real ~ opt("r") ^^ {
    case value ~ None => ExprAbs(value.toDouble)
    case value ~ Some(_) => ExprRel(value.toDouble)
  }

  def factor: Parser[Expr] = value | func | "(" ~> expr <~ ")"

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
