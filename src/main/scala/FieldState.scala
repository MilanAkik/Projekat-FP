enum FieldState {
  case Unopened, Zero, One, Two, Three, Four, Five, Six, Seven, Eight, Flag, Bomb

  def mapFieldState: String = this match
    case FieldState.Unopened => "|"
    case FieldState.Zero => " "
    case FieldState.One => "1"
    case FieldState.Two => "2"
    case FieldState.Three => "3"
    case FieldState.Four => "4"
    case FieldState.Five => "5"
    case FieldState.Six => "6"
    case FieldState.Seven => "7"
    case FieldState.Eight => "8"
    case FieldState.Flag => "P"
    case FieldState.Bomb => "@"

}