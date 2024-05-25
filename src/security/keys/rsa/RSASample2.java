package security.keys.rsa;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Arrays;

import security.KeyUtil;

// RSA
public class RSASample2 {

	public static void main(String[] args) throws Exception {
		
		// 1. 生成 RSA 密鑰對（公／私）
		KeyPair keyPair = KeyUtil.generateRSAKeyPair();
		PublicKey publicKey = keyPair.getPublic();
		PrivateKey privateKey = keyPair.getPrivate();
		
		// 2. 要加密的資訊（原始資料）
		String original = "我是原始資料";
		
		// 3. 利用公鑰進行資料加密
		byte[] encrypted = KeyUtil.encryptWithPublicKey(publicKey, original.getBytes());
		
		// 4. 利用私鑰進行資料解密
		byte[] decrypted = KeyUtil.decryptWithPrivateKey(privateKey, encrypted);
		System.out.println(new String(decrypted));
		
	}
}
