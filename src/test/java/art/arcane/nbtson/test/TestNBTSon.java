package art.arcane.nbtson.test;

import art.arcane.nbtson.NBTSon;
import art.arcane.nbtson.io.JsonNBT;
import art.arcane.nbtson.tag.CompoundTag;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.StringReader;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestNBTSon {
    @Test
    public void testNBT() throws IOException {
        AllTheTypes object = new AllTheTypes();
        CompoundTag tag = NBTSon.toNBT(object);
        CompoundTag tag2 = NBTSon.toNBT(NBTSon.fromNBT(AllTheTypes.class, tag));
        assertEquals(tag, tag2);
    }

    @Test void testSNBT()
    {
        AllTheTypes object = new AllTheTypes();
        CompoundTag tag = NBTSon.toNBT(object);
        String stag = NBTSon.toSNBT(object);
        String stag2 = NBTSon.toSNBT(NBTSon.fromSNBT(AllTheTypes.class, stag));
        assertEquals(stag, stag2);
    }

    public static class AllTheTypes
    {
        private int aint = 23;
        private double adouble = 0.3456667;
        private float afloat = 0.566f;
        private long along = 35338558L;
        private short ashort = 244;
        private byte abyte = 3;
        private boolean aboolean = false;
        private String astring = "this is a string";
        private ASubObject sub = new ASubObject();
        private List<String> someList = List.of("a", "bb", "ccc");
        private List<ASubObject> subObjects = List.of(new ASubObject(), new ASubObject(), new ASubObject());
    }

    public static class ASubObject
    {
        private String someContent = "some content";
    }
}
