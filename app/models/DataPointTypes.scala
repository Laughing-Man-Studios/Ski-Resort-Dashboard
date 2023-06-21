package models

sealed trait DataPointTypes
case object DailySnow extends DataPointTypes {
    override def toString: String = "dailySnow"
}
case object BaseDepth extends DataPointTypes {
    override def toString: String = "baseDepth"
}
case object Temperature extends DataPointTypes {
    override def toString: String = "temperature"
}
case object WindSpeed extends DataPointTypes {
    override def toString: String = "windSpeed"
}
case object WindDir extends DataPointTypes {
    override def toString: String = "windDir"
}