package br.tech.oe.plan.utils;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.UUID;

public class Base64Utils {

    public static String value = "-oeplan-token";

    public static String uuidToBase64(UUID uuid) {
        byte[] extraBytes = value.getBytes(StandardCharsets.UTF_8);
        ByteBuffer buffer = ByteBuffer.allocate(16 + extraBytes.length);
        buffer.putLong(uuid.getMostSignificantBits());
        buffer.putLong(uuid.getLeastSignificantBits());
        buffer.put(extraBytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(buffer.array());
    }

    public static UUID toUUID(String base64) {
        byte[] decoded = Base64.getUrlDecoder().decode(base64);
        ByteBuffer buffer = ByteBuffer.wrap(decoded);

        // get the UUID
        byte[] uuidBytes = new byte[16];
        buffer.get(uuidBytes);

        ByteBuffer uuidBuffer = ByteBuffer.wrap(uuidBytes);
        long high = uuidBuffer.getLong();
        long low = uuidBuffer.getLong();
        return new UUID(high, low);
    }
}
