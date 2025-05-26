package helpers

object DateTimeHelper {
    fun durationToHM(duration: Int): String {
        val hours = duration / 3600
        val minutes = duration % 3600 / 60
        return buildString {
            append(hours)
            append(if (LocaleHelper.getCurrentLocale() == "fa") "س" else "h")
            if (minutes > 0) {
                append(" ")
                append(minutes)
                append(if (LocaleHelper.getCurrentLocale() == "fa") "د" else "m")
            }
        }
    }
}