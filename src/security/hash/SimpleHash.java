package security.hash;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import security.KeyUtil;

public class SimpleHash {

	public static void main(String[] args) throws NoSuchAlgorithmException {
		// 一段密碼
		String password = "1234";
		System.out.println("原始資料："+password);
		
		// 1. 獲取 SHA-256 消息摘要物件，幫助生成 Hash code
		MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
		
		// 2. 生成 Hash code
		byte[] hash = messageDigest.digest(password.getBytes());
		
		// 3. Hash 轉 Hex（16進位）
		String hashedString = KeyUtil.bytesToHex(hash);
		System.out.println("原始轉Hex："+hashedString);

		
	}
}
