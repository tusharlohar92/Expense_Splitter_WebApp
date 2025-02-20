package util;

public class RegexPatterns {
    // Email validation
    public static final String EMAIL = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
    
    // Password: 8+ chars, 1 uppercase, 1 lowercase, 1 number, 1 special char
    public static final String PASSWORD = 
        "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*]).{8,}$";
    
    // Amount: Positive decimal with up to 2 decimal places
    public static final String AMOUNT = "^\\d+(\\.\\d{1,2})?$";
    
    // Date: yyyy-MM-dd
    public static final String DATE = "^\\d{4}-\\d{2}-\\d{2}$";
    
    // Prevent instantiation
    private RegexPatterns() {}
}