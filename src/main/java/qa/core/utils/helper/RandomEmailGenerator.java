package qa.core.utils.helper;
import org.apache.commons.lang3.RandomStringUtils;

public class RandomEmailGenerator {

    public static String generateRandomEmail() {
        String randomString = RandomStringUtils.randomAlphabetic(10);
        String timestamp = String.valueOf(System.currentTimeMillis());
        String emailDomain = "@example.com";

        return randomString + timestamp + emailDomain;
    }
}
