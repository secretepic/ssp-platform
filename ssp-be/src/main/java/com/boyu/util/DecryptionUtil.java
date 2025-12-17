package com.boyu.util;

import javax.crypto.Cipher;
import javax.crypto.spec.OAEPParameterSpec;
import javax.crypto.spec.PSource;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.MGF1ParameterSpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

/**
 * 解密工具类 - 用于解密前端传递的加密密码
 */
public class DecryptionUtil {
    // private_pkcs8.pem
    private static final String PRIVATE_KEY = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCHeC6Me0SiOo1h" +
            "b/xHS3LAle99rjpLjj4nXZo2pzl+nBSjLbydy46yG8i8OAblZD7B07LybP2sZklt" +
            "DhKxsu5vZRiv1voQaUlSzZ4lTRQ7i+779FE106R7a2U2sTSGfoVgoLIzh3120Q0q" +
            "J5aRJ1KwE/wmUiTa3hauD6vpSpABp5af6cWqoFHS55m5KgRnJ1Nq7bLES4ugX1OQ" +
            "7M3fOkel5TsmkXDhnGZG0VIG0WUpfxUBmY+PC8kdFEVibgV0RUvuhKkJtC5ZncF9" +
            "EEEObY4WGO9i2YN95ouyTb0tOXCY8Ylv3J0zDFXJbGuaSSdHurxsiUQWdeDVRKrD" +
            "orXPMD3/AgMBAAECggEAJJk17jMRSEjMUGIqRHKgNmJreMdcrXPo28bAdnp4IHn3" +
            "Y1xkZDcNvpWeBieWyVtrTv8sDa2fXPenZ6SRLVvbadHkuQC5vzxKmGm/i3FHAY1d" +
            "C8mmNQYQHl3V0tBOEfXVf9T6P8yfez2gMl7OoTl/79z+VrYDifPNKcpHFfhUbnWd" +
            "LhcIMyS/GDQeREKF+36ddQ+3Y9gd5xOKhAy1TQRwVLGoyFJTUHptnzkb9UDGZ9Ud" +
            "D6hK6BoW0cJthsuzNvD402t51BNO8eTZIxKRbPXSCOQsPaJtcVwbDHLhHoYuYDf2" +
            "AK/cntcyjYGYUqV6SG+PKZavEVEHI8rHylZEAsvXYQKBgQC7oYy9T+zEHEDatv29" +
            "FAADHCQTtJ06fIUpEZYNND+TTWfaOoSKqAEMqu7wtRhQwSQFmFLwNjZueAh+pIIu" +
            "XrY0lfWCj+9Q3TdRrChFstWbbdH1JI2h3t+F20Ce2MA33tfqvS7VlUmk4J4TDpgh" +
            "FAzWjFDjGpP5wbJwqUANAOTi4QKBgQC41O+Y3ggC2EWtcV0yR6Dl99R3U+AsrBVs" +
            "iziDpC4i9e59cTqLMgMxCujy9RMvP7KEBHT2QMgCf7lsFQKte/fC/rU7LDPv8Izw" +
            "qM8bBalT0TqQD5IG6dnXJt87h+gDcqH2ikhvBjwuug8D6F4ytujUnf52QLZLqzdQ" +
            "8GOYHL4c3wKBgBiv/eOe04lOKl5cu3AYZJQELN8G4bUjUyX/MV4VZNQN2D9Hec8A" +
            "ofq3tu6m9UcS0pmF3EvM5kaMtplrLdHKFnE+1enS5GsgYDn3y+i3yhFEVt7Ja3VD" +
            "7Ocg3RTJciknwgMZ10K0/Nab01Cwl+WqTLIiDjaDdbrj+IWIT2wmlPchAoGAR67F" +
            "sarCvp+z/j1OILbo7ud77j+IXKrssCkaZOuivQpWUS49v0hJNpb9m2Ry6Usx58N2" +
            "vR6Is3dnqURIwpGmmGiCTw2oovDPKHK/x8IdVpV5ue9f+fBt/ORlhUgDMnsd12jw" +
            "CKcJZjHgqupOxipsCDGyQ5h7WsNvc6OXbKIRFU8CgYEAkIu6dS2+9vXUv3O+QbBx" +
            "uPEH5e4TucYAYTIUMmucGvyawQzc0lRf47Ftv5Ud25OjsRVp51/v6Qm0bZuYHXYB" +
            "72z3UzLJ/TqkFImvvfnBu7NKe/x3M7tL3gHDaP66hSGzuPrmjWWN6E6xho6sOfBc" +
            "U6V+U5IMn2OFaqaiR5Uh7B0=";

    private static final PrivateKey privateKey;

    static {
        try {
            // 初始化私钥
            byte[] keyBytes = Base64.getDecoder().decode(PRIVATE_KEY);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            privateKey = keyFactory.generatePrivate(keySpec);
        } catch (Exception e) {
            throw new RuntimeException("初始化解密工具失败", e);
        }
    }

    /**
     * 解密前端传递的加密字符串
     * @param encryptedText 加密后的字符串
     * @return 解密后的原始字符串
     */
    public static String decrypt(String encryptedText) {
        if (encryptedText == null || encryptedText.isEmpty()) {
            return "";
        }
        try {
            // 解密RSA加密内容
            Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-256AndMGF1Padding");
            // cipher.init(Cipher.DECRYPT_MODE, privateKey);
            // 2. 明确指定 OAEP 参数：MGF1 哈希用 SHA-256
            OAEPParameterSpec oaepParams = new OAEPParameterSpec(
                    "SHA-256",          // 主哈希算法（与前端一致）
                    "MGF1",             // 掩码生成函数（固定 MGF1）
                    new MGF1ParameterSpec("SHA-256"),  // MGF1 哈希算法（与主哈希一致，修复点）
                    PSource.PSpecified.DEFAULT         // 标签参数（默认即可）
            );

            // 3. 初始化 Cipher（传入自定义参数）
            cipher.init(Cipher.DECRYPT_MODE, privateKey, oaepParams);

            byte[] encryptedBytes = Base64.getDecoder().decode(encryptedText);
            byte[] decryptedBytes = cipher.doFinal(encryptedBytes);

            // 解混淆处理（与前端obfuscate对应）
            String obfuscatedText = new String(decryptedBytes, StandardCharsets.UTF_8);
            return deobfuscate(obfuscatedText);
        } catch (Exception e) {
            throw new SecurityException("解密失败", e);
        }
    }

    /**
     * 解混淆处理
     * 与前端的obfuscate方法对应
     */
    private static String deobfuscate(String str) {
        try {
            // 反转字符串并解码
            String reversed = new StringBuilder(str).reverse().toString();
            byte[] decodedBytes = Base64.getDecoder().decode(reversed);
            return URLDecoder.decode(new String(decodedBytes, StandardCharsets.UTF_8), StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new SecurityException("数据解混淆失败", e);
        }
    }
}
