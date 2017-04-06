package crown.dafish.com.utils;

import java.security.MessageDigest;

/**
 * Encode an MD5 digest into a String.
 * <p>
 * The 128 bit MD5 hash is converted into a 32 character long String.
 * Each character of the String is the hexadecimal representation of 4 bits
 * of the digest.
 *
 * @author Remy Maucherat
 * @version $Revision: 467222 $ $Date: 2006-10-24 05:17:11 +0200 (mar., 24 oct. 2006) $
 */

public final class MD5Encoder {


    private static final char[] HEX =
            { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

    public static final String encode(String source)
    {
        try
        {
            byte[] sourceBytes = source.getBytes();
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            mdInst.update(sourceBytes);
            byte[] md = mdInst.digest();
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++)
            {
                str[k++] = HEX[md[i] >>> 4 & 0xf];
                str[k++] = HEX[md[i] & 0xf];
            }
            return new String(str);
        } catch (Exception e)
        {
            return null;
        }
    }

    public static final boolean isPasswordValid(String encPass, String rawPass)
    {
        if (encPass.equals(rawPass))
        {
            return true;
        }
        return MD5Encoder.encode(rawPass).equals(encPass);
    }

    public static void main(String[] args)
    {
        System.out.println(System.currentTimeMillis());

        System.out.println(MD5Encoder.encode("pwd"));
    }
}
