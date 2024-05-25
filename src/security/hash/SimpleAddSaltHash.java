package security.hash;

import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Scanner;

import security.KeyUtil;

// 加鹽 Hash
public class SimpleAddSaltHash {

	public static void main(String[] args) throws Exception {
		
		// 1. 設定密碼
		String password = "1234";
		
		// 2. 隨機生成鹽（Salt）
		byte[] salt = new byte[16];
		SecureRandom secureRandom = new SecureRandom();
		secureRandom.nextBytes(salt);
		System.out.printf("鹽：%s%n", Arrays.toString(salt));
		System.out.printf("鹽（Hex）：%s%n",KeyUtil.bytesToHex(salt));
		
		// 3. 獲取 SHA-256 消息摘要物件，幫助生成 Hash
		MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
		
		// 4. 加鹽
		messageDigest.update(salt);
		
		// 5. 將密碼轉換成 byte[] 生成 Hash code
		byte[] hash = messageDigest.digest(password.getBytes());
		
		// 6. 將 byte[] 轉 Hex
		String hashHex = KeyUtil.bytesToHex(hash);
		System.out.printf("原始密碼：%s%n", password);
		System.out.printf("加鹽後的Hash：%s%n", hashHex);
		
		// 7. 模擬使用者輸入密碼進行驗證
		Scanner scanner = new Scanner(System.in);
		System.out.print("請輸入密碼：");
		String inputPassword = scanner.nextLine();
		
		// 8. 生成使用者
		messageDigest.reset();
		messageDigest.update(salt);
		byte[] inputHash = messageDigest.digest(inputPassword.getBytes());
		String inputHashHex = KeyUtil.bytesToHex(inputHash);
		System.out.println("使用者輸入密碼經過加鹽的Hash："+inputHashHex);
		
		if(inputHashHex.equals(hashHex)) {
			System.out.println("驗證成功");
		} else {
			System.out.println("驗證失敗");
		}
		
	}
}
