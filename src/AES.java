import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;
import java.lang.Object;
import org.json.JSONObject;

public class AES
{
    private static SecretKeySpec secretKey;
    private static byte[] key;

    public static void setKey(String myKey)
    {
        MessageDigest sha = null;
        try{
            key = myKey.getBytes("UTF-8");
            sha = MessageDigest.getInstance("SHA-1");
            key = sha.digest(key);
            key = Arrays.copyOf(key, 16);
            secretKey = new SecretKeySpec(key, "AES");
        }
        catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        }
        catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }
    }
    // making the encrypt method to make the encryption by "AES" algorithm
    public static String encrypt(String strToEncrypt, String secret)
    {
        try
        {
            setKey(secret);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return Base64.getEncoder().encodeToString((cipher.doFinal(strToEncrypt.getBytes("UTF-8"))));
        }
        catch (Exception e)
        {
            System.out.println("Error encrypting: "+ e.toString());
        }
        return null;
    }
    // // making the decrypt method to make the decryption by "AES" algorithm
    public static String decrypt(String strToDecrypt, String secret)
    {
        try
        {
            setKey(secret);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
        }
        catch (Exception e)
        {
            System.out.println("Error decrypting: "+ e.toString());
        }
        return null;
    }


    public static void main(String[] args)
    {
        final String secretKey = "idontknowwhattodo";
        String originalString = "Message to encrypt";
        String secondOriginalString = "Second Message to encrypt";

//        JSONObject obj = new JSONObject();
//        obj.put("text",originalString);
//        obj.put("text", secondOriginalString);

        String encryptedString = AES.encrypt(originalString, secretKey);
        String decryptedString = AES.decrypt(encryptedString, secretKey);



        System.out.println("text: "+ originalString);
        System.out.println("encrypted: "+ encryptedString);

        System.out.println("encrypted: "+ encryptedString);
        System.out.println("text: "+decryptedString);
    }

}


