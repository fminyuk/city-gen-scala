package org.nnc.citygen.models

case class Vec3(_1: Double, _2: Double, _3: Double) {
  def +(other: Vec3): Vec3 = {
    Vec3(_1 + other._1, _2 + other._2, _3 + other._3)
  }

  def -(other: Vec3): Vec3 = {
    Vec3(_1 - other._1, _2 - other._2, _3 - other._3)
  }

  def *(scalar: Double): Vec3 = {
    Vec3(_1 * scalar, _2 * scalar, _3 * scalar)
  }

  def /(scalar: Double): Vec3 = {
    Vec3(_1 / scalar, _2 / scalar, _3 / scalar)
  }

  def dot(other: Vec3): Double = {
    _1 * other._1 + _2 * other._2 + _3 * other._3
  }

  def cross(other: Vec3): Vec3 = {
    Vec3(_2 * other._3 - _3 * other._2, _3 * other._1 - _1 * other._3, _1 * other._2 - _2 * other._1)
  }

  def length: Double = {
    math.sqrt(dot(this))
  }

  def norm: Vec3 = {
    this / length
  }
}
