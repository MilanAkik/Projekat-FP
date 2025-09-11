import java.util.{Timer, TimerTask}

object Ticker {

  var timer: Timer = _

  def start(f:()=>Unit, period: Int): Unit = {
    val task:TimerTask = new TimerTask {
      override def run(): Unit = f()
    }
    timer = new Timer()
    timer.schedule(task, 0, period)
  }

  def stop(): Unit = {
    timer.cancel()
    timer.purge()
  }

}