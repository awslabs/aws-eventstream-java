package software.amazon.eventstream;

import org.junit.jupiter.api.Test;

import java.nio.ByteBuffer;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class PreludeTest {
    @Test
    public void maxPayloadSize() throws Exception {
        ByteBuffer buf = ByteBuffer.wrap(new byte[15]);
        buf.putInt(16777217 + 19); // total length
        buf.put((byte) 1); // ApplicationData
        buf.putShort((short) 0); // unmodeled data
        buf.putInt(0); // headers_length
        buf.putInt(0xf0c8e628); // prelude_crc
        buf.flip();

        assertThrows(IllegalArgumentException.class, () -> Prelude.decode(buf));
    }

    @Test
    public void maxHeaderSize() throws Exception {
        ByteBuffer buf = ByteBuffer.wrap(new byte[15]);
        buf.putInt(131073 + 19); // total length
        buf.put((byte) 1); // ApplicationData
        buf.putShort((short) 0); // unmodeled data
        buf.putInt(131073); // headers_length
        buf.putInt(0x49fd415c); // prelude_crc
        buf.flip();

        assertThrows(IllegalArgumentException.class, () -> Prelude.decode(buf));
    }
}
