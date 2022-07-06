package art.arcane.nbtson.io;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

public class BooleanTypeAdapterFactory implements TypeAdapterFactory {
    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
        final TypeAdapter<T> delegate = gson.getDelegateAdapter(this, type);

        if(!(type.getRawType().equals(boolean.class) || type.getRawType().equals(Boolean.class))) {
            return null;
        }

        return new TypeAdapter<>() {
            public void write(JsonWriter out, T value) {writeSafeJson(delegate, out, value);}

            @SuppressWarnings("unchecked")
            public T read(JsonReader in) throws IOException {
                JsonToken token = in.peek();

                if(token == JsonToken.NUMBER) {
                    return (T)Boolean.valueOf(in.nextInt() == 1);
                }

                return delegate.read(in);
            }
        };
    }

    public <T> void writeSafeJson(TypeAdapter<T> delegate, JsonWriter out, T value) {
        try {
            delegate.write(out, value);
        } catch (IOException e) {
            try {
                delegate.write(out, null);
            } catch(IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}
