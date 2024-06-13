package com.amstech.retail.util;

import java.security.SecureRandom;

public class OTPUtil {
    private static final int OTP_LENGTH = 6;
    private static final String DIGITS = "0123456789";
    private static SecureRandom random = new SecureRandom();

    public static String generateOTP() {
        StringBuilder otp = new StringBuilder(OTP_LENGTH);
        for (int i = 0; i < OTP_LENGTH; i++) {
            otp.append(DIGITS.charAt(random.nextInt(DIGITS.length())));
        }
        return otp.toString();
    }
}
