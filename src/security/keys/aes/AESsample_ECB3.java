package security.keys.aes;

import java.util.Base64;

import javax.crypto.spec.SecretKeySpec;

import security.KeyUtil;

// AES 對稱式加密應用
public class AESsample_ECB3 {

	public static void main(String[] args) throws Exception {
		
		// 建立 AES 的 Key（AES-128bits, 16 bytes）
		final String KEY = "0123456789abcdef";
		
		// 加密 ============================================================
		String original = "顏子惟是笨蛋";
		// 建立 AES 密鑰規範
		SecretKeySpec aesKeySpec = new SecretKeySpec(KEY.getBytes(), "AES");
		// 進行資料加密（將明文進行 AES-ECB 加密）
		byte[] encrypted = KeyUtil.encryptWithAESKey(aesKeySpec, original);
		// 將加密後的訊息轉 base64 便於儲存及傳輸
		String encryptedBase64 = Base64.getEncoder().encodeToString(encrypted);
		
		// 解密 ============================================================
		// 將 Base64 轉回 byte[]
		byte[] decrypted = Base64.getDecoder().decode(encryptedBase64);
		String ans = KeyUtil.decryptWithAESKey(aesKeySpec, decrypted);
		System.out.println(ans);
		
		
	}
}
