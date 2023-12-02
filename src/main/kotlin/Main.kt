import controllers.userAPI
import mu.KotlinLogging
import persistence.JSONSerializer
import utils.ScannerInput.readNextInt
import utils.ScannerInput.readNextLine
import java.io.File
import kotlin.system.exitProcess


private val logger = KotlinLogging.logger {}
//private val noteAPI = NoteAPI(XMLSerializer(File("notes.xml")))
private val noteAPI = userAPI(JSONSerializer(File("user.json")))

fun main() { runMenu() }//code when run loads menu
fun mainMenu() : Int { //users notes app user interface menu
    return readNextInt(""" 
           ▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄
          █                                █
         █                                  █
         █     WORKOUT APP                  █
         █                                  █
         █▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄█
         █ ---> MENU:                       █  
         █                                  █
         █  11) Save a note                 █
         █  12) Load a note                 █
         █▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄█
         █  0) Exit {-_-}                   █
         █▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄█
         █▄▄▄▄▄▄▄▄▄▄▄▄██████████▄▄▄▄▄▄▄▄▄▄▄▄█
          ▀▄▄▄▄▄▄▄▄▄▄▄██████████▄▄▄▄▄▄▄▄▄▄▄▀
         
         
         Type here ==>> """.trimMargin(">"))
    //https://www.asciiart.eu/ascii-one-line

}

fun runMenu() { /*
 Method that allows the user to select options displayed in the ui, uses a switch statement to
 Which contains all the functions for the user
 */
    do {
        when (val option = mainMenu()) {

            11  -> save()
            12  -> load()
            0  -> exitApp()
            else -> println("Invalid option entered: $option")
        }
    } while (true)
}











fun save() { try { userAPI.store() } catch (e: Exception) { System.err.println("Error writing to file: $e") } } //saves notes

fun load() { try { userAPI.load() } catch (e: Exception) { System.err.println("Error reading from file: $e") } } //loads notes to the system
fun exitApp(){ logger.info { "exitApp() function invoked" }
    exitProcess(0) } // exits app when finished
