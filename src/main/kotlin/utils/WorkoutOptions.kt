package utils

object WorkoutOptions {
        @JvmStatic
        val categories = setOf ("Strength Training", "Cardio", "HIIT", "Yoga", "Dance", "Boxing", "Crossfit", "Circuit Training")
        val intensity = setOf ("Low Intensity","Moderate Intensity" ,"High Intensity", "Very High Intensity", "Endurance Intensity", "Recovery Intensity")

        fun isValidWorkout(categoryToCheck: String?): Boolean {
                for (category in categories) {
                        if (category.equals(categoryToCheck, ignoreCase = true)) {
                                return true
                        }
                }
                return false
        }

        fun isValidIntensity(categoryToCheck: String?): Boolean {
                for (category in intensity) {
                        if (category.equals(categoryToCheck, ignoreCase = true)) {
                                return true
                        }
                }
                return false
        }
}