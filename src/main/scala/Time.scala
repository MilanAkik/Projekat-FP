class Time(var seconds: Int ) {
  def increment(ammount: Int = 1): Unit = seconds = seconds + ammount

  def formatted: String =  "%02d:%02d".format(seconds / 60,seconds % 60)

}
