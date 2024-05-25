package security.keys.aes;

import java.util.Base64;
import java.util.Scanner;

import javax.crypto.spec.SecretKeySpec;

import security.KeyUtil;

// AES 對稱式加密應用
public class AESsample_ECB2 {

	private static String userName = "admin";
	private static String userPassword = "viPRerPf6RVeQuHPD/732w==";

	public static void main(String[] args) throws Exception {
		// 建立 AES 的 Key（AES-128bits, 16 bytes）
		final String KEY = "0123456789abcdef";
		// 建立 AES 密鑰規範
		SecretKeySpec aesKeySpec = new SecretKeySpec(KEY.getBytes(), "AES");

		Scanner scanner = new Scanner(System.in);
		System.out.print("請輸入帳號：");
		String inputUserName = scanner.nextLine();
		
		if (!inputUserName.equals(userName)) {
			System.out.println("帳號輸入錯誤");
			return;
		}
		
		System.out.print("請輸入密碼：");
		String inputUserPassword = scanner.nextLine();

		byte[] passwordEncryptedECB = KeyUtil.encryptWithAESKey(aesKeySpec, inputUserPassword);
		String passwordEncryptedECBBase64 = Base64.getEncoder().encodeToString(passwordEncryptedECB);
		
		if (!passwordEncryptedECBBase64.equals(userPassword)) {
			System.out.println("密碼輸入錯誤");
			return;
		}
		
		System.out.println("登入成功");

	}
}
