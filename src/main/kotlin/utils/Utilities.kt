package utils

object Utilities {
    @JvmStatic
    fun validRange(numberToCheck: Int, min: Int, max: Int): Boolean {
        return numberToCheck in min..max
    }
    fun isString(obj: Any): Boolean = obj is String // https://www.baeldung.com/kotlin/type-checks-casts#:~:text=In%20Kotlin%2C%20we%20use%20the,form%20is%20'!is'.&text=As%20the%20code%20above%20shows,of%20type%20String%20or%20not.
}
