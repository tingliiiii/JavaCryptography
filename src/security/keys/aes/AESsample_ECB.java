package security.keys.aes;

import java.util.Arrays;
import java.util.Base64;
import java.util.Scanner;

import javax.crypto.spec.SecretKeySpec;

import security.KeyUtil;

// AES 對稱式加密應用
public class AESsample_ECB {

	public static void main(String[] args) throws Exception {
		
		// 建立 AES 的 Key（AES-128bits, 16 bytes）
		final String KEY = "0123456789abcdef";
		System.out.println("AES 加密範例");
		Scanner scanner = new Scanner(System.in);
		System.out.print("請輸入原始訊息（明文）：");
		String originalText = scanner.nextLine();
//		System.out.println("明文："+orginalText);
		System.out.println("----------------------------");
		
		// 加密 ============================================================
		
		// 建立 AES 密鑰規範
		SecretKeySpec aesKeySpec = new SecretKeySpec(KEY.getBytes(), "AES");
		// ECB 模式
		System.out.println("ECB 模式");
		// 進行資料加密（將明文進行 AES-ECB 加密）
		byte[] encryptedECB = KeyUtil.encryptWithAESKey(aesKeySpec, originalText);
		System.out.println("加密後的訊息："+Arrays.toString(encryptedECB));
		// 將加密後的訊息轉 base64 便於儲存及傳輸
		String encryptedECBBase64 = Base64.getEncoder().encodeToString(encryptedECB);
		System.out.println("加密後的訊息（base64）："+encryptedECBBase64);
		System.out.println("----------------------------");
		
		// 解密 ============================================================
		
		System.out.print("請輸入密文（base64）：");
		String base64 = scanner.next();
		// 將 Base64 轉回 byte[]
		byte[] encryptedECB2 = Base64.getDecoder().decode(base64);
		String decryptedECB = KeyUtil.decryptWithAESKey(aesKeySpec, encryptedECB2);
		System.out.println("解密後的訊息："+decryptedECB);

		
		
	}
}
