// 代码生成时间: 2025-09-16 10:05:40
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.QueryValue;
import javax.inject.Singleton;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

/**
 * Hash Calculation Tool Controller
 * This controller provides an API endpoint to calculate hash values for strings.
 */
@Controller("/hash")
@Singleton
public class HashCalculationTool {

    /**
     * Calculates the hash value for a given string.
     * @param input The string for which to calculate the hash value.
     * @param algorithm The hash algorithm to use (e.g., SHA-256).
     * @return The hash value of the input string.
     */
    @Get("/calculate")
    public String calculateHash(
            @QueryValue Optional<String> input,
            @QueryValue Optional<String> algorithm) {

        if (!input.isPresent()) {
            throw new IllegalArgumentException("Input string is required");
        }

        String hashAlgorithm = algorithm.orElse("SHA-256");
        try {
            // Encode the input string into bytes
            byte[] inputBytes = input.get().getBytes();

            // Get a MessageDigest instance for the specified algorithm
            MessageDigest digest = MessageDigest.getInstance(hashAlgorithm);

            // Update the digest using the input bytes
            digest.update(inputBytes);

            // Calculate the hash value in bytes
            byte[] hashBytes = digest.digest();

            // Convert the hash bytes to a hexadecimal string
            StringBuilder sb = new StringBuilder();
            for (byte b : hashBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Hash algorithm not supported", e);
        }
    }
}
