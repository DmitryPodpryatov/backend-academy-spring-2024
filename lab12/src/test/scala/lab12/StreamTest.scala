package lab12

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class StreamTest extends AnyFlatSpec with Matchers {

  "fs2.Stream" should "produce plain value" in {
    val result = ???

    result shouldBe List(1, 2, 3)
  }

  it should "produce effect (IO.println)" in {
    val result = ???
  }

  it should "acquire and release a resource" in {
    // достаточно вывести логи об открытии и закрытии файла
    ???
  }

  it should "parse data from file, process it, and write results into another file" in {
    // input.txt
    // найти все простые делители числа
    ???
  }

  it should "catch and handle errors" in {
    // input2.txt
    // обработать все NaN и вернуть -1 в таком случае
    ???
  }

  it should "merge two streams and process them concurrently" in {
    // конкуретно читать из двух файлов и записывать результаты в один
    ???
  }

  it should "emit values in timed intervals for 10 seconds" in {
    ???
  }

  it should "implement exponential backoff starting from 1 second and going until 30" in {
    ???
  }

  it should "produce values forever with sleep intervals of 2 seconds" in {
    // Запускать в Test.run, потому что нужно будет прервать руками
    ???
  }

  // and many more...

}
