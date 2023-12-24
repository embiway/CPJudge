package com.judge.server;

import com.embi.Token;
import com.embi.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.hash.Hashing;
import com.judge.dao.AuthDAO;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.*;

@Component
public class AuthManagerImpl implements AuthManager {

    @Autowired
    AuthDAO authDAO;

    public AuthDAO getAuthDAO() {
        return authDAO;
    }

    public void setAuthDAO(AuthDAO authDAO) {
        this.authDAO = authDAO;
    }

    private final String secret = "abcd";

    private Date addHoursToJavaUtilDate(Date date, int hours) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR_OF_DAY, hours);
        return calendar.getTime();
    }

    private boolean validateToken(String token) throws Exception {
        if (token == null) return false;
        List<String> auxParts = new ArrayList<>(List.of(token.split(" ")));
        if (auxParts.size() != 2 || !auxParts.get(0).equals("Bearer")) {
            throw new Exception("Token is not as per the REST Auth Standard!!");
        }
        List<String> parts = new ArrayList<>(List.of(auxParts.get(1).split("\\.")));
        if (parts.size() != 3) {
            throw new Exception("Token is not as per the JWT standard!!");
        }
        String decodedHeader = new String(Base64.getDecoder().decode(parts.get(0).getBytes(StandardCharsets.UTF_8)));
        String decodedPayload = new String(Base64.getDecoder().decode(parts.get(1).getBytes(StandardCharsets.UTF_8)));

        String expectedSignature = Hashing.hmacSha256(secret.getBytes(StandardCharsets.UTF_8))
                .hashString(parts.get(0) + "." + parts.get(1), StandardCharsets.UTF_8)
                .toString();

        return expectedSignature.equals(parts.get(2));
    }

    /**
     * Token should be based on JWT pattern
     * */
    private String createToken(User user) throws JsonProcessingException {
        Token token = new Token();
        token.setUser(user);
        token.setExpiryDate(addHoursToJavaUtilDate(Date.from(Instant.now()), 1));

        ObjectMapper mapper = new ObjectMapper();
        String jsonEncodedToken = mapper.writeValueAsString(token);

        String header = "{\n" +
                "  \"alg\": \"HS256\",\n" +
                "  \"typ\": \"JWT\"\n" +
                "}";
        byte[] bytes = header.getBytes(StandardCharsets.UTF_8);
        String encodedHeader = Base64.getEncoder().encodeToString(bytes);

        bytes = jsonEncodedToken.getBytes(StandardCharsets.UTF_8);
        String encodedPayload = Base64.getEncoder().encodeToString(bytes);

        String originalString = encodedHeader + "." + encodedPayload;
        String sha256hex = Hashing.hmacSha256(secret.getBytes(StandardCharsets.UTF_8))
                .hashString(originalString, StandardCharsets.UTF_8)
                .toString();

        return (encodedHeader + "." + encodedPayload + "." + sha256hex);
    }

    @Override
    public User signup(User user) throws Exception {
        if (authDAO.getUser(user.getUserName()).isPresent()) {
            throw new Exception("User already present!!!");
        }

        // Validation for checking if password and emails are present:
        if (user.getPassword().length < 8) {
            throw new Exception("Password should be at least 8 characters long");
        }
        // Add user to DB with encrypted password.
        authDAO.addUser(user);
        // Also create a token and return the same
        user.setToken(createToken(user));

        return user;
    }

    @Override
    public boolean authenticateUser(User user) throws Exception {
        if (!validateToken(user.getToken())) return false;

        // Check if expiry date is passed
        // if not then return true
        List<String> parts = new ArrayList<>(List.of(user.getToken().split("\\.")));
        String decodedPayload = new String(Base64.getDecoder().decode(parts.get(1).getBytes(StandardCharsets.UTF_8)));

        JSONObject jsonObject = new JSONObject(decodedPayload);
        String expiryDate = jsonObject.getString("expiryDate");

        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
        Date expiry = formatter.parse(expiryDate);

        Date currentDate = Date.from(Instant.now());
        return !currentDate.after(expiry);
    }

    @Override
    public String login(User user) throws Exception {
        // First check if email and password provided. (Service will take care of populating the same).
        if (user.getUserName() == null || user.getPassword() == null) {
            throw new Exception("User name and password are both expected!!!");
        }
        if (user.getPassword().length == 0) {
            throw new Exception("Non empty password expected!!!");
        }
        Optional<User> dbUser = authDAO.getUser(user.getUserName());
        if (!dbUser.isPresent()) {
            throw new Exception("User not present in DB");
        }
        User finalUser = dbUser.get();

        if (!Arrays.equals(finalUser.getPassword(), user.getPassword())) {
            throw new Exception("Incorrect Password!!!");
        }

        return createToken(user);
    }
}
