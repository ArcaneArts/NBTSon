package art.arcane.nbtson;

import art.arcane.nbtson.io.*;
import art.arcane.nbtson.tag.CompoundTag;

import java.io.*;

public class NBTSon {
    public static <T> T fromSNBT(Class<T> clazz, String in) {
        StringReader reader = new StringReader(in);
        T t = fromSNBT(clazz, reader);
        reader.close();
        return t;
    }

    public static <T> T fromSNBT(Class<T> clazz, Reader in) {
        try {
            return fromNBT(clazz, ((CompoundTag) new SNBTDeserializer().fromReader(in)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String toSNBT(Object object){
        StringWriter sw = new StringWriter();
        toSNBT(object, sw);
        try {
            sw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return sw.toString();
    }

    public static void toSNBT(Object object, Writer writer) {
        try {
            SNBTWriter.write(toNBT(object), writer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static CompoundTag toNBT(Object object) {
        try {
            return NBTObjectSerializer.serialize(object);
        } catch (IllegalAccessException | UnserializableClassException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T fromNBT(Class<T> clazz, CompoundTag tag) {
        try {
            return NBTObjectSerializer.deserialize(clazz, tag);
        } catch (IllegalAccessException | InstantiationException | UnserializableClassException e) {
            throw new RuntimeException(e);
        }
    }
}
