import controllers.UserAPI
import controllers.WorkoutAPI
import models.User
import models.Workout
import mu.KotlinLogging
import persistence.JSONSerializer
import utils.ScannerInput.readNextInt
import utils.ScannerInput.readNextLine
import utils.UserValidation.readValidUserEmailAddress
import utils.Utilities.validRating
import utils.ValidateInput.readValidIntensity
import utils.ValidateInput.readValidListIndex
import utils.ValidateInput.readValidWorkout
import utils.WorkoutOptions
import utils.WorkoutValidation.readValidWorkoutName
import java.io.File
import kotlin.system.exitProcess
import utils.WorkoutOptions.isValidWorkout
import utils.WorkoutOptions.categories
import utils.WorkoutOptions.intensity

private val logger = KotlinLogging.logger {}


private val UserAPI = UserAPI(JSONSerializer(File("user.json")))
private val WorkoutAPI = WorkoutAPI(JSONSerializer(File("workout.json")))

/**
 * Entry point for the Gym Management Application.
 */
fun main() {
    runMenu()
}

/**
 * Displays the main menu and reads the user's choice.
 * https://www.asciiart.eu/computers/computers
 * @return The chosen menu option as an Int.
 */
fun mainMenu(): Int {
    return readNextInt(
        """ 
         ▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄
         ██                     STEVE'S GYM MANAGEMENT APP                      ██
         ██                                                                     ██
         █████████████████████████████████████████████████████████████████████████                                                           
         ██  CRUD OPTIONS:                                                      ██
         ██    1) ADD USER          5) ADD WORKOUT        0) EXIT APP           ██
         ██    2) LIST USER         6) LIST WORKOUT       13) SAVE USERS        ██
         ██    3) UPDATE USER       7) UPDATE WORKOUT     14) LOAD USERS        ██
         ██    4) DELETE USER       8) DELETE WORKOUT     15) SAVE WORKOUT      ██
         ██                                               16) LOAD WORKOUT      ██
         ██                                                                     ██
         █████████████████████████████████████████████████████████████████████████                                             
         ██  ADDITIONAL FUNCTIONS:                                              ██
         ██    9) Open User Workout Menu                        ____            ██
         ██    10) Complete Workout                            ||""||           ██
         ██    11) Select gym's workout                        ||__||           ██
         ██    12) View User Password                     jgs  [ -=.]`)         ██
         ██                                                    ====== 0         ██
         ██                                                                     ██
         █████████████████████████████████████████████████████████████████████████

          
                ____
     ||""||
     ||__||
jgs  [ -=.]`)
     ====== 0

          
          
          
          
         Type here ==>> """.trimMargin(">")
    )
    // https://www.asciiart.eu/ascii-one-line
}


/**
 * Drives the main loop, executing functions based on user's choice.
 */
fun runMenu() { /*
 Method that allows the user to select options displayed in the ui, uses a switch statement to
 Which contains all the functions for the user
 */
    do {
        when (val option = mainMenu()) {
            1 -> addUser()
            2 -> listUsers()
            3 -> updateUser()
            4 -> deleteUser()
            5 -> addWorkout()
            6 -> listWorkout()
            7 -> updateWorkout()
            8 -> deleteWorkout()
            9 -> userWorkoutMenu()
            10 -> workoutCompletion()
            11 -> selectGymWorkout()
            12 -> viewUserPassword()
            13 -> saveUsers()
            14 -> loadUser()
            15 -> saveWorkout()
            16 -> loadWorkout()
            0 -> exitApp()
            else -> println("Invalid option entered: $option")
        }
    } while (true)
}

/**
 * Handles the addition of a new user.
 */

fun addUser() {
    val userID = readNextInt("Enter user ID: ")
    val userName = readNextLine("Enter your name: ")
    val userEmail = readValidUserEmailAddress("Enter your email address: ")
    val userPass = UserAPI.getANewSecurePassword(10)
    println("User Password: $userPass")
    val isAdded = userEmail?.let { User(userID, userName, it, userPass).let { UserAPI.add(it) } }
    if (isAdded == true) {
        println("User has been successfully added")
    } else {
        println("add failed")
    }
}

/**
 * Lists all the users.
 */
fun listUsers() {
    if (UserAPI.numberOfUsers() > 0) {
        val option = readNextInt(
            """
                  ▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄
                  █>>> LIST USERS MENU:          █
                  ▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄
                  █   1) List ALL Users          █
                  ▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄
         > ==>> """.trimMargin(">")
        )
        when (option) { // submenu for the user to choose the type of notes listed
            1 -> listAllUsers()
            else -> println("Invalid option entered: $option")
        }
    } else { println("Option Invalid - No users stored") }
}


/**
 * Displays all users.
 */
fun listAllUsers() { println(UserAPI.listAllUsers()) }

/**
 * Updates a specific user's information.
 */
fun updateUser() {
    listUsers()
    if (UserAPI.numberOfUsers() > 0) {
        val indexToUpdate = readNextInt("Enter the index of what user to update: ")
        if (UserAPI.isValidIndex(indexToUpdate)) {
            val userID = readNextInt("Enter user ID: ")
            val userName = readNextLine("Enter your name: ")
            val userEmail = readValidUserEmailAddress("Enter your email address: ")
            val userPass = readNextLine("enter your password: ")
            if (UserAPI.updateUser(indexToUpdate, userEmail?.let { User(userID, userName, it, userPass) })) {
                println("update successful")
            } else {
                println("update failed")
            }
        } else {
            println("No users at this index!")
        }
    }
}
/**
 * Deletes a specific user.
 */

fun deleteUser() {
    listUsers()
    if (UserAPI.numberOfUsers() > 0) {
        val indexToDelete = readValidListIndex("Enter the index of the user you want to delete: ", UserAPI.numberOfUsers())
        val userToDelete = UserAPI.deleteUser(indexToDelete)
        if (userToDelete != null) {
            println("Delete successful! Deleted User: ${userToDelete.userName}")
        } else {
            println("Delete not successful")
        }
    }
}

/**
 * Handles the addition of a new workout.
 */

fun addWorkout() {
    val workoutID = readNextInt("Enter Workout ID: ")
    val workoutName = readValidWorkoutName("Enter workout name: ")
    val sessionType = readNextLine("Enter the type of session: ")
    val date = readNextInt("enter the date: ")
    val sessionDuration = readNextInt("Enter session duration: ")
    val isAdded = workoutName?.let {
        Workout(
            workoutID,
            it,
            sessionType,
            date,
            sessionDuration,
            sessionCompleted = false
        )
    }?.let { WorkoutAPI.addWorkout(it) }
    if (isAdded == true) {
        println("Workout has been successfully added")
    } else {
        println("Workout addition  failed")
    }
}

/**
 * Lists all workouts.
 */
fun listWorkout() {
    if (WorkoutAPI.numberOfWorkouts() > 0) {
        val option = readNextInt(
            """
                  ▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄
                  █>>> LIST WORKOUT MENU:        █
                  ▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄
                  █   1) List ALL Workouts       █
                  ▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄
         > ==>> """.trimMargin(">")
        )
        when (option) { // submenu for the user to choose the type of notes listed
            1 -> listAllWorkouts()
            else -> println("Invalid option entered: $option")
        }
    } else { println("Option Invalid - No workouts stored") }
}

/**
 * Displays all workouts.
 */
fun listAllWorkouts() { println(WorkoutAPI.listAllWorkouts()) }

/**
 * Updates a specific workout's information.
 */
fun updateWorkout() {
    listUsers()
    if (WorkoutAPI.numberOfWorkouts() > 0) {
        val indexToUpdate = readNextInt("Enter the index of what workout to update: ")
        if (WorkoutAPI.isValidIndex(indexToUpdate)) {
            val workoutID = readNextInt("Enter Workout ID: ")
            val workoutName = readNextLine("Enter workout name: ")
            val sessionType = readNextLine("Enter the type of session: ")
            val date = readNextInt("enter the date: ")
            val sessionDuration = readNextInt("Enter session duration: ")
            if (WorkoutAPI.updateWorkout(indexToUpdate, Workout(workoutID, workoutName, sessionType, date, sessionDuration, sessionCompleted = false))) {
                println("update successful")
            } else {
                println("update failed")
            }
        } else {
            println("No workouts at this index!")
        }
    }
}

/**
 * Deletes a specific workout.
 */
fun deleteWorkout() {
    listWorkout()
    if (WorkoutAPI.numberOfWorkouts() > 0) {
        val indexToDelete = readValidListIndex("Enter the index of the workout you want to delete: ", UserAPI.numberOfUsers())
        val workoutToDelete = WorkoutAPI.deleteWorkout(indexToDelete)
        if (workoutToDelete != null) {
            println("Delete successful! Deleted Workout: ${workoutToDelete.workoutName}")
        } else {
            println("Delete not successful")
        }
    }
}
/**
 * Opens a specific menu for user workout management.
 */

fun userWorkoutMenu() {
    if (WorkoutAPI.numberOfWorkouts() > 0) {
        val option = readNextInt(
            """
                  ▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄
                  █>>> YOUR WORKOUT MENU         █
                  ▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄
                  █   1) Choose workout         █
                  ▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄
         > ==>> """.trimMargin(">")
        )
        when (option) { // submenu for the user to choose the type of notes listed
            1 -> WorkoutAPI.chooseWorkout()
            else -> println("Invalid option entered: $option")
        }
    } else { println("Option Invalid - No workouts stored") }
}
/**
 * Handles the logging and completion of workouts.
 */
fun workoutCompletion() {
    println("Welcome to the workout completion logger!")
    if (WorkoutAPI.numberOfWorkouts() > 0) {
        val option = readNextInt(
            """
                  ▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄
                  █>>> WORKOUT COMPLETION LOGGER █
                  ▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄
                  █   1) log workout completion  █
                  █   2) view archived workouts  █  
                  ▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄
         > ==>> """.trimMargin(">")
        )
        when (option) { // submenu for the user to choose the type of notes listed
            1 -> WorkoutAPI.logWorkoutCompletion()
            2 -> viewArchivedWorkouts()
            else -> println("Invalid option entered: $option")
        }
    } else { println("Option Invalid - No workouts stored") }
}
/**
 * Displays archived workouts.
 */
fun viewArchivedWorkouts() {
    val archivedWorkout = WorkoutAPI.getArchivedWorkouts()
    if (archivedWorkout.isEmpty()) {
        println("There were no archived workouts stored")
    } else {
        println("All archived workouts: ")
        archivedWorkout.forEach { workout ->
            println(workout)
        }
    }
}

fun viewUserPassword() {
    println(listAllUsers())
    if (UserAPI.numberOfUsers() > 0) {
        val indexToView = readNextInt("Enter the index of what user to view password: ")
        val selectedUser = UserAPI.findUser(indexToView)
        if (selectedUser != null) {
            println("password for: ${selectedUser.userName} is: ${selectedUser.userPassword}")
        } else{
            println("index not found")
        }
    } else{
        println("invalid index")
    }
}

/**
 * Allows the user to select and rate a gym workout.
 */
fun selectGymWorkout(){
    val selectWorkout = readValidWorkout("Select a workout from the options listed: $categories")
    println("The workout you have selected is: $selectWorkout")
    val setIntensity  = readValidIntensity("Select the intensity for your workout from the options listed: $intensity")
    println("The workout intensity you have chosen is: $setIntensity")
    val rateTheWorkout = readNextInt("Rate your workout: 1 - 10: ")
    if (validRating(rateTheWorkout)) {
        println("$selectWorkout Rating: $rateTheWorkout")
    } else{
        println("not a valid range of 1-10: ")
    }

}




/**
 * Saves user data to persistent storage.
 */

/*running methods*/
fun saveUsers() {
    println("Users successfully saved!")
    try {
        UserAPI.store()
    } catch (e: Exception) {
        System.err.println("Error writing to file: $e")
    }
}

fun loadUser() {
    println("Users successfully loaded!")
    try { UserAPI.load() } catch (e: Exception) {
        System.err.println("Error reading from file: $e") }
}

fun saveWorkout() {
    println("Workouts successfully saved!")
    try { WorkoutAPI.store() } catch (e: Exception) {
        System.err.println("Error writing to file: $e") }
}

fun loadWorkout() {
    println("Workouts successfully loaded!")
    try {
        WorkoutAPI.load()
    } catch (e: Exception) {
        System.err.println("Error reading from file: $e")
    }
}

fun exitApp() {
    logger.info { "exitApp() function invoked" }
    exitProcess(0)
} // exits app when finished
