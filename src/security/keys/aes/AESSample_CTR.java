package security.keys.aes;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.spec.SecretKeySpec;

import security.KeyUtil;

//CTR 模式
public class AESSample_CTR {

	public static void main(String[] args) throws Exception {

		String originalText = "三韻紅萱";
		
		// 建立 AES 的 Key（AES-128bits, 16 bytes）
		final String KEY = "0123456789abcdef";
		System.out.println("KEY： " + KEY);
		// 建立 AES 密鑰規範
		SecretKeySpec aesKeySpec = new SecretKeySpec(KEY.getBytes(), "AES");

		// 建立 IV：透過 SecureRandom 定義 IV 內容
		byte[] iv = new byte[16];
		SecureRandom secureRandom = new SecureRandom();
		secureRandom.nextBytes(iv);
		System.out.println("IV： " + Arrays.toString(iv));		

		// 加密 ============================================================

		byte[] encryptedCTR = KeyUtil.encryptWithAESKeyAndIVInCTRMode(aesKeySpec, originalText, iv);
		System.out.println("CTR 加密後（base64）： " + Base64.getEncoder().encodeToString(encryptedCTR));

		// 解密 ============================================================
		String decryptedCTR = KeyUtil.decryptWithAESKeyAndIVInCTRMode(aesKeySpec, encryptedCTR, iv);
		System.out.println("CTR 解密後： " + decryptedCTR);

	}
}
