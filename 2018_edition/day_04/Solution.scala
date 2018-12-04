import scala.io.Source
import java.text.SimpleDateFormat
import java.util.Date
import java.util.concurrent.TimeUnit

val dateTimeFormatter = new SimpleDateFormat("[yyyy-MM-dd HH:mm]")
val minuteFormatter   = new SimpleDateFormat("mm")

val guards = parseInput(readInput("input.txt"), dateTimeFormatter)

//guards.foreach(x => {
  //println("Guard: " + x.getId)
  //x.getNaps.foreach(y => {
    //println("Falls asleep: " + y.getStart + " => Wakes up: " + y.getEnd)
  //})
  //println()
//})

println("Solution to A: " + solveA(guards, minuteFormatter))

println("Solution to B: " + solveB(guards, minuteFormatter))



def solveA(guards: List[Guard], minuteFormatter: SimpleDateFormat): Int = {
  val sleepyGuard = guards.map(x => (x, x.calcTotalSleepTime))
                            .maxBy(x => x._2)
                            ._1

  val sleepyGuardsMinute = sleepyGuard.calcSleepyMinute(minuteFormatter)

  return sleepyGuard.getId * sleepyGuardsMinute
}



def solveB(guards: List[Guard], minuteFormatter: SimpleDateFormat): Int = {
  val minutesToGuardIds = guards.flatMap(guard => guard.getMinutesAsleep(minuteFormatter)
                                                       .map(minute => (minute, guard.getId)))
                                .groupBy(_._1)
                                .map({case (minute, minuteToGuardIdTuples) =>
                                           (minute, minuteToGuardIdTuples.map(_._2))})
                                .toMap

  val sleepyMinute = minutesToGuardIds.maxBy(_._2.size)
                                      ._1
  println("Sleepy minute: " + sleepyMinute)

  val sleepyGuardId = minutesToGuardIds(sleepyMinute).groupBy(x => x)
                                                     .maxBy(_._2.size)
                                                     ._1
  println("Sleepy Guard ID: " + sleepyGuardId)

  return sleepyGuardId * sleepyMinute
}





def readInput(filename: String): List[String] = {
  return Source.fromFile(filename)
               .getLines()
               .toList
}


def parseInput(input: List[String], dateTimeFormatter: SimpleDateFormat): List[Guard] = {
  val sortedEvents = input.map(x => parseLine(x, dateTimeFormatter))
                          .sortWith((x, y) => x._1.before(y._1))

  val guardIdsToEvents = mapGuardIdToEvents(sortedEvents, -1)

  return guardIdsToEvents.groupBy(_._1)
                         .map(idToEventsGroup => (idToEventsGroup._1, idToEventsGroup._2.map(_._2)))
                         .map(idToEvents => 
                              new Guard(idToEvents._1, groupEventsIntoNaps(idToEvents._2)))
                         .toList
}


def parseLine(line: String, dateTimeFormatter: SimpleDateFormat): (Date, String) = {
  return line.split(" ", 3) match {
    case Array(date, time, action) => {
      (dateTimeFormatter.parse(date + " " + time), action)
    }
  }
}


def mapGuardIdToEvents(dtToAction: List[(Date, String)], latestGuardId: Int):
    List[(Int, (Date, String))] = {
  dtToAction match {
    case Nil => return List()
    case head :: tail => {
      val dateTime = head._1
      val action = head._2
      val newGuardId = if(action.startsWith("Guard")) action.split(" ")(1).drop(1).toInt
                       else latestGuardId
      val newAction  = if (action.startsWith("Guard")) "shift"
                       else action

      return (newGuardId, (dateTime, newAction)) :: mapGuardIdToEvents(tail, newGuardId)
    }
  }
}


def groupEventsIntoNaps(events: List[(Date, String)]): List[Nap] = {
  return events.filterNot(_._2 == "shift")
               .grouped(2)
               .map(x => (x.head, x.tail.head))
               .map({ case (sleep, wake) => new Nap(sleep._1, wake._1)})
               .toList
}





class Guard(id: Int, naps: List[Nap]) {
  def getId(): Int = this.id;
  def getNaps(): List[Nap] = this.naps;

  def calcTotalSleepTime(): Int = {
    return this.naps
               .map(_.calcLength)
               .sum
  }

  def calcSleepyMinute(minuteFormatter: SimpleDateFormat): Int = {
  return this.getMinutesAsleep(minuteFormatter)
             .groupBy(x => x)
             .maxBy(_._2.size)
             ._1
  }

  def getMinutesAsleep(minuteFormatter: SimpleDateFormat): List[Int] = {
    return this.naps
               .flatMap(nap => {
                 val napStartMinute = minuteFormatter.format(nap.getStart).toInt
                 Range(napStartMinute, (napStartMinute + nap.calcLength))
               })
  }
} //end class


class Nap(sleep: Date, wake: Date) {
  def getStart(): Date = this.sleep;
  def getEnd(): Date = this.wake;

  def calcLength(): Int = {
    val sleepMillis = wake.getTime - sleep.getTime
    return TimeUnit.MILLISECONDS.toMinutes(sleepMillis).toInt
  }
} //end class
