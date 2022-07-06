package art.arcane.nbtson;

import art.arcane.nbtson.io.*;
import art.arcane.nbtson.tag.CompoundTag;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.*;

public class NBTSon {
    private static final Gson gson = new GsonBuilder()
            .setLenient()
            .registerTypeAdapterFactory(new BooleanTypeAdapterFactory())
            .create();

    public static <T> T readNBT(InputStream stream, Class<T> clazz, Gson gson) throws IOException {
        return fromNBT((CompoundTag) new NBTDeserializer().fromStream(stream).getTag(), clazz, gson);
    }

    public static <T> T readNBT(InputStream stream, Class<T> clazz) throws IOException {
        return fromNBT((CompoundTag) new NBTDeserializer().fromStream(stream).getTag(), clazz);
    }

    public static <T> T readUncompressedNBT(InputStream stream, Class<T> clazz, Gson gson) throws IOException {
        return fromNBT((CompoundTag) new NBTDeserializer(false).fromStream(stream).getTag(), clazz, gson);
    }

    public static <T> T readUncompressedNBT(InputStream stream, Class<T> clazz) throws IOException {
        return fromNBT((CompoundTag) new NBTDeserializer(false).fromStream(stream).getTag(), clazz);
    }

    public static void writeNBT(Object object, OutputStream stream, Gson gson) throws IOException {
        new NBTSerializer().toStream(new NamedTag("", toNBT(object, gson)), stream);
    }

    public static void writeNBT(Object object, OutputStream stream) throws IOException {
        new NBTSerializer().toStream(new NamedTag("", toNBT(object)), stream);
    }

    public static void writeUncompressedNBT(Object object, OutputStream stream, Gson gson) throws IOException {
        new NBTSerializer(false).toStream(new NamedTag("", toNBT(object, gson)), stream);
    }

    public static void writeUncompressedNBT(Object object, OutputStream stream) throws IOException {
        new NBTSerializer(false).toStream(new NamedTag("", toNBT(object)), stream);
    }

    /**
     * Convert this object into SNBT serializing objects using gson as an intermediate step.
     * When providing custom gson objects, make sure to register the BooleanTypeAdapterFactory.
     * @param object the object
     * @param gson the custom gson object
     * @return the string representation of NBT
     */
    public static String toSNBT(Object object, Gson gson) {
        try {
            return new SNBTSerializer().toString(JsonNBT.fromJsonObject(new JsonReader(new StringReader(gson.toJson(object)))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Convert this object into SNBT serializing objects using gson as an intermediate step.
     * @param object the object
     * @return the string representation of NBT
     */
    public static String toSNBT(Object object) {
        return toSNBT(object, gson);
    }

    /**
     * Convert this string NBT back into an object using gson as an intermediate step
     * When providing custom gson objects, make sure to register the BooleanTypeAdapterFactory.
     * @param clazz the class type
     * @param snbt the NBT string
     * @param gson the custom gson
     * @return the object
     * @param <T> the class of the desired return object
     */
    public static <T> T fromSNBT(String snbt, Class<T> clazz, Gson gson) {
        try {
            return gson.fromJson(JsonNBT.toJson(new SNBTDeserializer().fromString(snbt)), clazz);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Convert this string NBT back into an object using gson as an intermediate step
     * @param clazz the class type
     * @param snbt the NBT string
     * @return the object
     * @param <T> the class of the desired return object
     */
    public static <T> T fromSNBT(String snbt, Class<T> clazz) {
        return fromSNBT(snbt, clazz, gson);
    }

    /**
     * Convert this object into NBT serializing objects using gson as an intermediate step.
     * When providing custom gson objects, make sure to register the BooleanTypeAdapterFactory.
     * @param object the object
     * @param gson the custom gson object
     * @return the NBT
     */
    public static CompoundTag toNBT(Object object, Gson gson) {
        try {
            return JsonNBT.fromJsonObject(new JsonReader(new StringReader(gson.toJson(object))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Convert this object into NBT serializing objects using gson as an intermediate step.
     * @param object the object
     * @return the NBT
     */
    public static CompoundTag toNBT(Object object) {
        return toNBT(object, gson);
    }

    /**
     * Convert this NBT back into an object using gson as an intermediate step
     * When providing custom gson objects, make sure to register the BooleanTypeAdapterFactory.
     * @param clazz the class type
     * @param tag the NBT
     * @param gson the custom gson
     * @return the object
     * @param <T> the class of the desired return object
     */
    public static <T> T fromNBT(CompoundTag tag, Class<T> clazz, Gson gson) {
        return gson.fromJson(JsonNBT.toJson(tag), clazz);
    }

    /**
     * Convert this NBT back into an object using gson as an intermediate step
     * @param clazz the class type
     * @param tag the NBT
     * @return the object
     * @param <T> the class of the desired return object
     */
    public static <T> T fromNBT(CompoundTag tag, Class<T> clazz) {
        return fromNBT(tag, clazz, gson);
    }
}
