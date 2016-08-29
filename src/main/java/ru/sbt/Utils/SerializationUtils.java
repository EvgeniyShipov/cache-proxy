package ru.sbt.Utils;

import com.sun.xml.internal.ws.encoding.soap.SerializationException;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class SerializationUtils {

    public static void serialize(Serializable o, String file) throws SerializationException {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file))) {
            out.writeObject(o);
        } catch (IOException e) {
            throw new SerializationException(e);
        }
    }

    public static <T> T deserialize(String file) throws SerializationException {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(file))) {
            return (T) inputStream.readObject();
        } catch (IOException e) {
            throw new SerializationException(e);
        } catch (ClassNotFoundException e) {
            throw new SerializationException(e);
        }
    }
}
