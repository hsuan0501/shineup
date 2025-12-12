import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class GenerateBcrypt {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String hash = encoder.encode("123456");
        System.out.println(hash);
        System.out.println("Matches: " + encoder.matches("123456", hash));
        
        // Test existing hash
        String existingHash = "$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZRGdjGj/n3.Y8j7r5F6O7e/Z6e.YO";
        System.out.println("Existing hash matches 123456: " + encoder.matches("123456", existingHash));
    }
}
