package security.keys.aes;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.spec.SecretKeySpec;

import security.KeyUtil;

//CBC 模式
public class AESSample_CBC {

	public static void main(String[] args) throws Exception {

		String orginalText = "三韻紅萱";

		// 建立 AES 的 Key（AES-128bits, 16 bytes）
		final String KEY = "0123456789abcdef";
		System.out.println("KEY： " + KEY);
		// 建立 AES 密鑰規範
		SecretKeySpec aesKeySpec = new SecretKeySpec(KEY.getBytes(), "AES");

		// 建立 IV
		// 1. 自行定義 IV 內容
		// byte[] iv = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16};
		// 2. 透過 SecureRandom 定義 IV 內容
		byte[] iv = new byte[16];
		SecureRandom secureRandom = new SecureRandom();
		secureRandom.nextBytes(iv);
		System.out.println("IV： " + Arrays.toString(iv));

		// 加密 ============================================================

		byte[] encryptedCBC = KeyUtil.encryptWithAESKeyAndIV(aesKeySpec, orginalText, iv);
		System.out.println("CBC 加密後（base64）： " + Base64.getEncoder().encodeToString(encryptedCBC));

		// 解密 ============================================================
		String decryptedCBC = KeyUtil.decryptWithAESKeyAndIV(aesKeySpec, encryptedCBC, iv);
		System.out.println("CBC 解密後： " + decryptedCBC);

	}
}
