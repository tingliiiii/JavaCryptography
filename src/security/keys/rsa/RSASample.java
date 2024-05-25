package security.keys.rsa;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Arrays;

import security.KeyUtil;

// RSA
public class RSASample {

	public static void main(String[] args) throws Exception {
		
		// 1. 生成 RSA 密鑰對（公／私）
		System.out.println("1. 生成 RSA 密鑰對（公／私）");
		KeyPair keyPair = KeyUtil.generateRSAKeyPair(); // RSA-2048（2048位元的RSA鑰匙對）
		PublicKey publicKey = keyPair.getPublic();
		PrivateKey privateKey = keyPair.getPrivate();
		System.out.println("公鑰："+publicKey);
		System.out.println("私鑰："+privateKey);
		
		// 2. 要加密的資訊（原始資料）
		System.out.println("\n2. 要加密的資訊（原始資料）");
		String originalText = "龜記三韻紅萱好好喝";
		System.out.println("要加密的資訊（原始資料）："+originalText);
		
		// 3. 利用公鑰進行資料加密
		System.out.println("\n3. 利用公鑰進行資料加密");
		byte[] encrypted = KeyUtil.encryptWithPublicKey(publicKey, originalText.getBytes());
		System.out.println("以 Base64 編碼來呈現加密後的資訊："+Arrays.toString(encrypted));
		
		// 4. 利用私鑰進行資料解密
		System.out.println("\n4. 利用私鑰進行資料解密");
		System.out.print("解密中・");
		Thread.sleep(1000);
		System.out.print("・");
		Thread.sleep(1000);
		System.out.println("・");
		Thread.sleep(1000);
		byte[] decrypted = KeyUtil.decryptWithPrivateKey(privateKey, encrypted);
		System.out.println("解密後的訊息："+ new String(decrypted));
	}
}
