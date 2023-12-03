package persistence

import com.thoughtworks.xstream.XStream
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver
import models.User
import models.Workout
import java.io.File
import java.io.FileReader
import java.io.FileWriter

class JSONSerializer(private val file: File) : Serializer {

    private fun createXStream(): XStream {
        return XStream(JettisonMappedXmlDriver()).apply {
            allowTypes(arrayOf(User::class.java, Workout::class.java))
        }
    }

    @Throws(Exception::class)
    override fun read(): Any {
        val xStream = createXStream()
        val inputStream = xStream.createObjectInputStream(FileReader(file))
        val obj = inputStream.readObject() as Any
        inputStream.close()
        return obj
    }

    @Throws(Exception::class)
    override fun write(obj: Any?) {
        val xStream = createXStream()
        val outputStream = xStream.createObjectOutputStream(FileWriter(file))
        outputStream.writeObject(obj)
        outputStream.close()
    }
}
