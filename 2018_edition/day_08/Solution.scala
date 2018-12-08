import scala.io.Source

val input = readInput("input.txt")

val root = getNextNode(input)

println("Solution to A: " + solveA(root))

println("Solution to B: " + solveB(root))



def solveA(root: Node): Int = {
  println("Root: " + root)
  //println("Ancestors: ")
  //(root :: root.getAllAncestors).foreach(println)
  return (root :: root.getAllAncestors)
             .flatMap(_.getMetaData)
             .sum
  //return -1
}


def solveB(root: Node): Int = {
  return root.calcValue
}



def getNextNode(curInput: List[Int]): Node = {
  //println("Tail From Next Child: " + curInput)
  val myHeader = new Header(curInput.head, curInput.tail.head)
  //println("Next Child Header: " + myHeader)
  val myBody = curInput.tail.tail

  return myHeader.getChildCount match {
    case 0 => return new Node(myHeader, List(), myBody.take(myHeader.getMetaDataCount))
    case _ => {
      // reverse the list, cos prepending
      val myChildren = findChildren(curInput, List()).reverse
      val myMetaData = myBody.drop(calcChildrenSizeSum(myChildren))
                             .take(myHeader.getMetaDataCount)

      return new Node(myHeader, myChildren, myMetaData)
    }
  }
}


def findChildren(curInput: List[Int], curChildrenList: List[Node]): List[Node] = {
  //println("Input: " + curInput)
  val myHeader = new Header(curInput.head, curInput.tail.head)
  //println("Header: " + myHeader)
  //println("Cur Children List: " + curChildrenList)

  return if(curChildrenList.size == myHeader.getChildCount) return curChildrenList
         else {
           val tailFromNextChild = getTailFromNextChild(curInput.tail.tail, curChildrenList)
           val nextChild = getNextNode(tailFromNextChild)
           //println("Next Child: " + nextChild)

           return findChildren(curInput, nextChild :: curChildrenList)
         }
}


def getTailFromNextChild(curTail: List[Int], prevChildrenList: List[Node]): List[Int] = {
  return curTail.drop(calcChildrenSizeSum(prevChildrenList))
}


def calcChildrenSizeSum(children: List[Node]): Int = {
  return children.map(_.calcSize).sum
}





def readInput(filename: String): List[Int] = {
  return Source.fromFile(filename)
               .getLines()
               .next
               .split(" ")
               .toList
               .map(_.toInt)
}


class Node(header: Header, children: List[Node], metaData: List[Int]) {
  def getHeader(): Header = this.header;
  def getChildren(): List[Node] = this.children;
  def getMetaData(): List[Int] = this.metaData;

  def calcSize(): Int = {
    return this.children.map(_.calcSize).sum +
           this.metaData.size + 2
  }

  def getAllAncestors(): List[Node] = {
    return if(this.children.size == 0) List()
           else this.children ::: this.children.flatMap(_.getAllAncestors)
  }

  def calcValue(): Int = {
    return if(this.children.size == 0) this.metaData.sum
           else {
             val indicesSet = Range(0, this.children.size).toSet
             this.metaData.map(_ - 1) // turn them into actual indices
                          .filter(idx => indicesSet.contains(idx))
                          .map(idx => this.children(idx))
                          .map(child => child.calcValue)
                          .sum
           }
  }

  override def toString(): String = {
    return "(" + this.header.toString + " : "+ this.children + ", " + this.metaData + ")"
  }
}


class Header(childCount: Int, metaDataCount: Int) {
  def getChildCount(): Int = this.childCount;
  def getMetaDataCount(): Int = this.metaDataCount;

  override def toString(): String = {
    return "(" + this.childCount + ", "+ this.metaDataCount + ")"
  }
}
