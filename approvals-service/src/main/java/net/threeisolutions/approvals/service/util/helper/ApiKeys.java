package net.threeisolutions.approvals.service.util.helper;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Optional;

public final class ApiKeys {

    private static final SecureRandom RANDOM = new SecureRandom();

    private ApiKeys() {
    }

    @Getter
    @AllArgsConstructor
    public static final class Parsed {
        private final String clientCode;
        private final String keyId;
        private final String secret;
    }

    public static Optional<Parsed> parse(String header) {
        if (header == null) {
            return Optional.empty();
        }
        int last = header.lastIndexOf('.');
        int middle = last <= 0 ? -1 : header.lastIndexOf('.', last - 1);
        if (middle <= 0 || last == header.length() - 1 || middle + 1 == last) {
            return Optional.empty();
        }
        return Optional.of(new Parsed(header.substring(0, middle), header.substring(middle + 1, last),
                header.substring(last + 1)));
    }

    public static String newSecret() {
        byte[] bytes = new byte[32];
        RANDOM.nextBytes(bytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }

    public static byte[] hash(String secret) {
        try {
            return MessageDigest.getInstance("SHA-256").digest(secret.getBytes(StandardCharsets.UTF_8));
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(e);
        }
    }

    /// Constant-time, so the comparison doesn't leak how many bytes matched
    public static boolean matches(String secret, byte[] storedHash) {
        return MessageDigest.isEqual(hash(secret), storedHash);
    }
}
