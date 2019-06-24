package rest;

import java.nio.ByteBuffer;

import static java.nio.ByteBuffer.allocate;
import static java.util.stream.IntStream.of;
import static java.util.stream.IntStream.range;

public class ApiConverter {

    public static int[] toIntArray(byte[] array) {
        return range(0, array.length)
                .map(i -> array[i])
                .toArray();
    }

    public static byte[] toByteArray(int[] array) {
        ByteBuffer byteBuffer = allocate(array.length);
        of(array).forEach(value -> byteBuffer.put((byte) value));
        return byteBuffer.array();
    }
}
