package art.arcane.nbtson.test;

import art.arcane.nbtson.NBTSon;
import art.arcane.nbtson.tag.CompoundTag;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestNBTSon {
    @Test
    public void testNBT()
    {
        AllTheTypes object = new AllTheTypes();
        CompoundTag tag = NBTSon.toNBT(object);
        CompoundTag tag2 = NBTSon.toNBT(NBTSon.fromNBT(AllTheTypes.class, tag));
        String stag = NBTSon.toSNBT(object);
        String stag2 = NBTSon.toSNBT(NBTSon.fromSNBT(AllTheTypes.class, stag));
        assertEquals(stag, stag2);
        assertEquals(tag, tag2);
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
    }

    public static class ASubObject
    {
        private String someContent;
    }
}
