package art.arcane.nbtson.io;

import art.arcane.nbtson.tag.*;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;

import java.io.IOException;

public class JsonNBT {
    public static CompoundTag fromJsonObject(JsonReader reader) throws IOException {
        reader.beginObject();
        CompoundTag tag = new CompoundTag();
        String nextKey = null;
        looping: while(reader.hasNext())
        {
            JsonToken t = reader.peek();

            switch(t){
                case BEGIN_ARRAY -> {
                    ListTag<Tag<?>> array = fromJsonArray(reader);
                    tag.put(nextKey, array);
                }
                case END_ARRAY -> reader.endArray();
                case BEGIN_OBJECT -> {
                    CompoundTag ttag = fromJsonObject(reader);
                    tag.put(nextKey, ttag);
                }
                case END_OBJECT, END_DOCUMENT -> {
                    break looping;
                }
                case NAME -> nextKey = reader.nextName();
                case STRING -> tag.put(nextKey, new StringTag(reader.nextString()));
                case NUMBER -> {
                    tag.put(nextKey, toNumberTag(reader.nextDouble()));
                }
                case BOOLEAN -> tag.put(nextKey, new ByteTag((byte) (reader.nextBoolean() ? 1 : 0)));
                case NULL -> {
                    return null;
                }
            }
        }

        reader.endObject();

        return tag;
    }

    public static Tag<?> toNumberTag(double number) {
        if(Math.round(number) == number) {
            byte b = (byte) number;
            if (b == number) {
                return new ByteTag(b);
            }
            short s = (short) number;
            if(s == number)
            {
                return new ShortTag(s);
            }
            int i = (int) number;
            if(i == number)
            {
                return new IntTag(i);
            }
            return new LongTag((long) number);
        }

        float f = (float) number;
        if(f == number) // TODO: This does not work it always is false with even 1 decimal
        {
            return new FloatTag(f);
        }

        return new DoubleTag(number);
    }

    public static ListTag<Tag<?>> fromJsonArray(JsonReader reader) throws IOException {
        reader.beginArray();
        ListTag<Tag<?>> tag = (ListTag<Tag<?>>) ListTag.createUnchecked(EndTag.class);

        looping: while(reader.hasNext())
        {
            JsonToken t = reader.peek();

            switch(t){
                case BEGIN_ARRAY -> {
                    ListTag<Tag<?>> ttag = fromJsonArray(reader);
                    tag.add(ttag);
                }
                case END_ARRAY, END_DOCUMENT -> {
                    break looping;
                }
                case BEGIN_OBJECT -> {
                    tag.add(fromJsonObject(reader));
                }
                case END_OBJECT -> reader.endObject();
                case NAME -> reader.nextName();
                case STRING -> tag.add(new StringTag(reader.nextString()));
                case NUMBER -> tag.add(toNumberTag(reader.nextDouble()));
                case BOOLEAN -> tag.add(new ByteTag((byte) (reader.nextBoolean() ? 1 : 0)));
                case NULL -> {
                    return null;
                }
            }
        }

        reader.endArray();

        return tag;
    }

    public static JsonElement toJson(Tag<?> tag) {
        if(tag instanceof StringTag s) {
            return new JsonPrimitive(s.getValue());
        }

        else if(tag instanceof IntTag t) {
            return new JsonPrimitive(t.getValue());
        }

        else if(tag instanceof DoubleTag t) {
            return new JsonPrimitive(t.getValue());
        }

        else if(tag instanceof LongTag t) {
            return new JsonPrimitive(t.getValue());
        }

        else if(tag instanceof ShortTag t) {
            return new JsonPrimitive(t.getValue());
        }

        else if(tag instanceof ByteTag t) {
            return new JsonPrimitive(t.getValue());
        }

        else if(tag instanceof FloatTag t) {
            return new JsonPrimitive(t.getValue());
        }

        else if(tag instanceof ListTag<?> t) {
            JsonArray a = new JsonArray();

            for(Tag<?> i : t) {
                a.add(toJson(i));
            }

            return a;
        }

        else if(tag instanceof CompoundTag t) {
            JsonObject o = new JsonObject();

            for(String i : t.keySet()) {
                o.add(i, toJson(t.get(i)));
            }

            return o;
        }

        System.out.println("Unknown Tag Type " + tag.getClass());

        return null;
    }
}
