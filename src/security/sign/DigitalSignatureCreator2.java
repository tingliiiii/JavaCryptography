package security.sign;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

import javax.crypto.SecretKey;

import security.KeyUtil;

/*
 * 情境說明：
 * 小王是一家公司的經理，為了提高工作效率和保證文件的安全性，他決定使用數位簽章技術對公司的合同進行簽署。
 * 數位簽章不僅可以確保文件的完整性（即文件在傳輸過程中沒有被修改），還可以確認文件是由特定的人簽署的，
 * 在這種情況下，即由小王簽署。
 *
 * 本程式「DigitalSignatureCreator」的目的是生成一個數位簽章，該簽章對「my_contract.txt」文件進行簽署。
 * 這個數位簽章可以被存儲（在signature.sig文件中）和分享，供其他人或系統進行驗證。
 *
 * 當其他接收者接收到這個簽署的文件和數位簽章時，他們可以使用小王的公鑰來驗證文件的完整性和簽署者的身份。
 */

public class DigitalSignatureCreator2 {

	public static void main(String[] args) throws Exception {

		// 小王

		// 合約位置
		String contractPath = "src/security/sign/myContract.txt";
		// 公鑰檔位置
		String publicKeyPath = "src/security/sign/publicKey.key";
		// 私鑰檔位置
		String privateKeyPath = "src/security/sign/privateKey.key";
		// 數位簽章檔位置
		String signaturePath = "src/security/sign/signature.sig";

		PrivateKey privateKey; // 私鑰
		PublicKey publicKey; // 公鑰

		// 取得公/私鑰
		if(Files.exists(Paths.get(publicKeyPath))&&Files.exists(Paths.get(privateKeyPath))) {
			publicKey = KeyUtil.getPublicKeyFromFile("RSA",publicKeyPath);
			privateKey = KeyUtil.getPrivateKeyFromFile("RSA", privateKeyPath);
		} else {
			KeyPair keyPair = KeyUtil.generateRSAKeyPair();
			publicKey = keyPair.getPublic();
			privateKey = keyPair.getPrivate();
		}
		
		byte[] digitalSignature = KeyUtil.signWithPrivateKey(privateKey, contractPath);
		KeyUtil.saveSignatureToFile(digitalSignature, signaturePath);
		
		System.out.println("數位簽章："+Base64.getEncoder().encodeToString(digitalSignature));
	}

}
