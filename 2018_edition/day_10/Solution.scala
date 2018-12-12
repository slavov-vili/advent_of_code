import scala.io.Source

val pixels = parseInput(readInput("input.txt"))
//pixels.foreach(println)
println("Pixels: " + pixels.size)

val (lastPixels, lastSecond) = solve(pixels, 0)

println("Solution (Second = " + lastSecond + "): ")
val picture = drawPicture(lastPixels)
println(picture)



def solve(pixels: List[Pixel], curSecond: Int): (List[Pixel], Int) = {
  val oldPixelsSize = getPixelsSize(pixels)

  val newPixels = pixels.map(_.move)
  val newPixelsSize = getPixelsSize(newPixels)

  if(oldPixelsSize < newPixelsSize) (pixels, curSecond)
  else solve(newPixels, curSecond+1)
}





def readInput(filename: String): List[String] = {
  return Source.fromFile(filename)
               .getLines()
               .toList
}


def parseInput(input: List[String]): List[Pixel] = {
  val numberExtractor = "-?[0-9]+".r
  // Reverse the coordinates in the input, because else I get confused
  val lineNumbers = input.map(x => numberExtractor.findAllIn(x)
                                                  .map(_.toInt)
                                                  .toList)
  return lineNumbers.map(x => new Pixel(new Pair(x(1), x(0)),
                                        new Pair(x(3), x(2))))
}


def drawPicture(pixels: List[Pixel]): String = {
  val (firstLine, lastLine) = getFirstLastLine(pixels)
  val (firstColumn, lastColumn) = getFirstLastColumn(pixels)

  //val lines = Range(firstLine, lastLine+1).toList
  //val columns = Range(firstColumn, lastColumn+1).toList
  //println(lines.size)
  //println(columns.size)
  val lines = Range(firstLine, lastLine+1).map(x =>
              Range(firstColumn, lastColumn+1).map(y => {
                val curPos = new Pair(x, y)
                if(pixels.exists(_.getPosition == curPos)) '#'
                else '.'
              }))
  return lines.map(_.mkString(""))
              .mkString("\n")
}


def getFirstLastLine(pixels: List[Pixel]): (Int, Int) = {
  val xCoords = pixels.map(_.getPosition.getX)
  return (xCoords.min, xCoords.max)
}


def getFirstLastColumn(pixels: List[Pixel]): (Int, Int) = {
  val yCoords = pixels.map(_.getPosition.getY)
  return (yCoords.min, yCoords.max)
}


def getPixelsSize(pixels: List[Pixel]): Int = {
  val firstLastLinePixels = getFirstLastLine(pixels)
  val firstLastColumnPixels = getFirstLastColumn(pixels)
  return (Math.abs(firstLastLinePixels._2 + 1 - firstLastLinePixels._1) +
          Math.abs(firstLastColumnPixels._2 + 1 - firstLastColumnPixels._1))

}




class Pixel(position: Pair, velocity: Pair) {
  def getPosition(): Pair = return this.position;
  def getVelocity(): Pair = return this.velocity;

  def move(): Pixel = new Pixel(this.position + this.velocity,
                                 this.velocity)

  override def toString(): String =
    return "(Pixel: " + this.position + " : " + this.velocity + ")"

  override def equals(obj: Any): Boolean = obj match {
    case obj: Pixel => this.position == obj.getPosition &&
                       this.velocity == obj.getVelocity
    case _ => false
  }

}


class Pair(x: Int, y: Int) {
  def getX(): Int = this.x;
  def getY(): Int = this.y;

  def +(that: Pair): Pair = new Pair(this.x + that.getX,
                                     this.y + that.getY)

  override def toString(): String = "(" + this.x + ", " + this.y + ")"

  override def equals(obj: Any): Boolean = obj match {
    case obj: Pair => this.x == obj.getX &&
                      this.y == obj.getY
    case _ => false
  }

}
