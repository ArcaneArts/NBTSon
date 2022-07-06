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

    /**
     * Your custom gson must register the BooleanTypeAdapterFactory
     */
    public static String toSNBT(Object object, Gson gson) {
        try {
            return new SNBTSerializer().toString(JsonNBT.fromJsonObject(new JsonReader(new StringReader(gson.toJson(object)))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String toSNBT(Object object) {
        return toSNBT(object, gson);
    }

    /**
     * Your custom gson must register the BooleanTypeAdapterFactory
     */
    public static <T> T fromSNBT(Class<T> clazz, String snbt, Gson gson) {
        try {
            return gson.fromJson(JsonNBT.toJson(new SNBTDeserializer().fromString(snbt)), clazz);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T fromSNBT(Class<T> clazz, String snbt) {
        return fromSNBT(clazz, snbt, gson);
    }

    /**
     * Your custom gson must register the BooleanTypeAdapterFactory
     */
    public static CompoundTag toNBT(Object object, Gson gson) {
        try {
            return JsonNBT.fromJsonObject(new JsonReader(new StringReader(gson.toJson(object))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static CompoundTag toNBT(Object object) {
        return toNBT(object, gson);
    }

    /**
     * Your custom gson must register the BooleanTypeAdapterFactory
     */
    public static <T> T fromNBT(Class<T> clazz, CompoundTag tag, Gson gson) {
        return gson.fromJson(JsonNBT.toJson(tag), clazz);
    }

    public static <T> T fromNBT(Class<T> clazz, CompoundTag tag) {
        return fromNBT(clazz, tag, gson);
    }
}
