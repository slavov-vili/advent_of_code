import scala.io.Source
import java.text.SimpleDateFormat
import java.util.Date

val dateTimeFormatter = new SimpleDateFormat("[yyyy-MM-dd HH:mm]")
val minuteFormatter = new SimpleDateFormat("mm")

val input = readInput("input.txt", dateTimeFormatter)

println("Solution to A: " + solveA(input, minuteFormatter))

//println("Solution to B: " + solveB(input.map(_.toList)))



def solveA(input: Map[String, List[(Date, String)]], minuteFormatter: SimpleDateFormat): Int = {
  val guardSleepSchedule = input.mapValues(x => 
                                 x.filter(y => y._2 == "falls asleep")
                                  .map(y => y._1))
  println(guardSleepSchedule)
  val sleepyGuardId = guardSleepSchedule.mapValues(x => x.size).maxBy(x => x._2)._1
  println(sleepyGuardId)

  val sleepyMinute = guardSleepSchedule(sleepyGuardId).map(x => minuteFormatter.format(x).toInt)
                                                      .groupBy(x => x)
                                                      .maxBy(x => x._2.size)
                                                      ._1
  println(sleepyMinute)

  return sleepyGuardId.drop(1).toInt * sleepyMinute
}





def readInput(filename: String, dateTimeFormatter: SimpleDateFormat): Map[String, List[(Date, String)]] = {
  return getGuardIdToActions(Source.fromFile(filename)
                                   .getLines()
                                   .map(x => dateTimeToAction(x, dateTimeFormatter))
                                   .toList
                                   .sortWith((x, y) => x._1.before(y._1)))
}


def dateTimeToAction(line: String, dateTimeFormatter: SimpleDateFormat): (Date, String) = {
  return line.split(" ", 3) match {
    case Array(date, time, action) => {
      (dateTimeFormatter.parse(date + " " + time), action)
    }
  }
}


def getGuardIdToActions(tdToAction: List[(Date, String)]): Map[String, List[(Date, String)]] = {
  tdToAction.foreach(println)
  getGuardIdToActions_helper(tdToAction, "").groupBy(x => x._1)
                                            .mapValues(x => x.map(_._2))
}


def getGuardIdToActions_helper(tdToAction: List[(Date, String)], latestGuard: String):
    List[(String, (Date, String))] = {
  tdToAction match {
    case Nil => List()
    case head :: tail => {
      head._2.toLowerCase match {
        case act if act.startsWith("guard") => {
          val newGuard = act.split(" ")(1)
          (newGuard, (head._1, "shift")) :: getGuardIdToActions_helper(tail, newGuard)
        }
        case otherAct => {
          (latestGuard, (head._1, otherAct)) :: getGuardIdToActions_helper(tail, latestGuard)
        }
      } //end match
    }
  }
}


// parse - create
// format - output
