package security.keys.aes;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.spec.SecretKeySpec;

import security.KeyUtil;

//CBC 模式
public class AESSample_CBC2 {

	public static void main(String[] args) throws Exception {
		
		String original = "CBC";
		final String KEY = "0123456789abcdef";
		byte[] iv = new byte[16];
		SecureRandom secureRandom = new SecureRandom();
		secureRandom.nextBytes(iv);
		SecretKeySpec keySpec = new SecretKeySpec(KEY.getBytes(), "AES");
		byte[] encrypted = KeyUtil.encryptWithAESKeyAndIV(keySpec, original, iv);
		String encryptedBase64 = Base64.getEncoder().encodeToString(encrypted);
		System.out.println(encryptedBase64);
		
		byte[] decrypted = Base64.getDecoder().decode(encryptedBase64);
		String ans = KeyUtil.decryptWithAESKeyAndIV(keySpec, decrypted, iv);
		System.out.println(ans);
	}
}
