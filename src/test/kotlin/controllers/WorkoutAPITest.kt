package controllers

import models.Workout
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertEquals
import persistence.JSONSerializer
import java.io.File

class WorkoutAPITest {

    private var yoga: Workout? = null
    private var back: Workout? = null
    private var legs: Workout? = null
    private var cycling: Workout? = null
    private var treadmill: Workout? = null
    private var populatedWorkouts: WorkoutAPI? = WorkoutAPI(JSONSerializer(File("workouts.json")))
    private var emptyWorkouts: WorkoutAPI? = WorkoutAPI(JSONSerializer(File("workouts.json")))

    @BeforeEach
    fun setup() {
        yoga = Workout(2, "yoga", "cardio", 10, 20, false)
        back = Workout(3, "back", "hypertrophy", 1, 60, true)
        legs = Workout(4, "legs", "hypertrophy", 17, 70, false)
        cycling = Workout(5, "cycling", "cardio", 20, 10, true)
        treadmill = Workout(6, "treadmill", "cardio", 12, 90, false)
        // adding 5 Note to the notes api
        populatedWorkouts!!.addWorkout(yoga!!)
        populatedWorkouts!!.addWorkout(back!!)
        populatedWorkouts!!.addWorkout(legs!!)
        populatedWorkouts!!.addWorkout(cycling!!)
        populatedWorkouts!!.addWorkout(treadmill!!)
    }

    @AfterEach
    fun tearDown() {
        yoga = null
        back = null
        legs = null
        cycling = null
        treadmill = null
        populatedWorkouts = null
        emptyWorkouts = null
    }

    @Nested
    inner class PersistenceTests {

        @Test
        fun `saving and loading an empty collection in JSON doesn't crash app`() {
            // Saving an empty notes.json file.
            val storingNotes = WorkoutAPI(JSONSerializer(File("workout.json")))
            storingNotes.store()

            // Loading the empty notes.json file into a new object
            val loadedNotes = WorkoutAPI(JSONSerializer(File("workout.json")))
            loadedNotes.load()

            // Comparing the source of the notes (storingNotes) with the json loaded notes (loadedNotes)
            assertEquals(0, storingNotes.numberOfWorkouts())
            assertEquals(0, loadedNotes.numberOfWorkouts())
            assertEquals(storingNotes.numberOfWorkouts(), loadedNotes.numberOfWorkouts())
        }
    }
}
