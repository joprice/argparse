package com.joprice

trait ArgParser[T] {
  def parse(arg: String): T
}

object ArgParser {
  def apply[T](f: String => T) = new ArgParser[T] {
    def parse(arg: String) = f(arg)
  }

  implicit val BooleanArgParser = ArgParser[Boolean](_.toBoolean)

  implicit val StringArgParser = ArgParser[String](identity _)

  implicit val IntArgParser = ArgParser[Int](_.toInt)
}

class Args(args: Array[String]) {

  val lifted = args.lift

  def apply[T: ArgParser](pos: Int) = lifted(pos).map(implicitly[ArgParser[T]].parse(_))
}

object Args {
  def main(in: Array[String]): Unit = {
    val args = new Args(in)
    val one = args[String](0)
    val two = args[Int](1)
    println(s"$one, ${one.map(_.getClass)}, $two, ${two.map(_.getClass)}")
  }
}

