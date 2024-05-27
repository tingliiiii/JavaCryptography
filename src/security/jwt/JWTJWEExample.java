package security.jwt;

import com.nimbusds.jose.EncryptionMethod;
import com.nimbusds.jose.JWEAlgorithm;
import com.nimbusds.jose.JWEHeader;
import com.nimbusds.jose.JWEObject;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.KeyLengthException;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.DirectDecrypter;
import com.nimbusds.jose.crypto.DirectEncrypter;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import security.KeyUtil;

/**
依據（JWA、JWK）與（JWE、JWS）產生 Token（JWT） 

	+-----+   +-----+   +-----+
	| JWK | → | JWE |   | JWS |
	+-----+   +-----+   +-----+
	   ↑            ↓   ↓
	+-----+        +-----+
	| JWA |        | JWT |
	+-----+        +-----+
	
*/
public class JWTJWEExample {

	public static void main(String[] args) throws Exception {

		// 1. JWA：決定演算法 HS256
		JWSHeader header = new JWSHeader(JWSAlgorithm.HS256);

		// 2. JWK：產生簽名用的密鑰給 JWS 使用
		// String signingSecret = KeyUtil.generateSecret(32); // 32 bytes（256位元）- 動態
		String signingSecret = "abcdefghijklmnopqrstuvwxyz123456"; // 固定
		System.out.println("signingSecret：" + signingSecret);

		// 3. 定義 payload
		JWTClaimsSet payload = new JWTClaimsSet.Builder().subject("人事公告") // 主題
				.issuer("HR") // 發行者
				.claim("Name", "Sam Yang") // 自定訊息
				.claim("Title", "升副總經理") // 自定訊息
				.claim("From", "2024-05-27")
				.claim("Privilege", "上班不用打卡")
				.claim("Bonus", "年終至少 12 個月")
				.claim("Salary", 3000000)
				.build();

		// 4. JWT：建立 JWT（尚未加密）
		SignedJWT signedJWT = new SignedJWT(header, payload);

		// 5. JWS：簽名（利用 JWK 生成的密鑰）
		JWSSigner jwsSigner = new MACSigner(signingSecret);

		// 6. 簽名
		signedJWT.sign(jwsSigner);

		// 7. JWE：對已簽名的 JWT 進行加密
		JWEHeader jweHeader = new JWEHeader.Builder(JWEAlgorithm.DIR, EncryptionMethod.A192GCM).contentType("JWT").build();
		JWEObject jweObject = new JWEObject(jweHeader, new Payload(signedJWT));
		
		// 8. 加密
		String encryptionSecure = KeyUtil.generateSecret(16);
		jweObject.encrypt(new DirectEncrypter(encryptionSecure.getBytes()));
		
		// 9. 得到加密後的 Token
		String token = jweObject.serialize();
		System.out.println("JWT(Token 已加密)："+token);
		
		// ========================= 驗證 ==============================
		// 10.
		JWEObject decryptedJweObject = JWEObject.parse(token);
		decryptedJweObject.decrypt(new DirectDecrypter(encryptionSecure.getBytes()));

		// 11. 驗證 JWT 簽名
		SignedJWT verifiedJWT = decryptedJweObject.getPayload().toSignedJWT();
		
		// 12. 取得密鑰
		JWSVerifier verifier = new MACVerifier(signingSecret);
		
		// 13. 進行驗證
		if(verifiedJWT.verify(verifier)) {
			System.out.println("JWT 的簽名驗證成功");
			// 顯示 payload 資料
			JWTClaimsSet claims = verifiedJWT.getJWTClaimsSet();
			System.out.printf("主題 Subject: %s%n", claims.getSubject());
			System.out.printf("發行者 Issuer: %s%n", claims.getIssuer());
			System.out.printf("Name：%s%n", claims.getStringClaim("Name"));
			System.out.printf("Title：%s%n", claims.getStringClaim("Title"));
			System.out.printf("From：%s%n", claims.getStringClaim("From"));
			System.out.printf("Privilege：%s%n", claims.getStringClaim("Privilege"));
			System.out.printf("Bonus：%s%n", claims.getStringClaim("Bonus"));
			System.out.printf("Salary：%s%n", claims.getIntegerClaim("Salary"));
		} else {
			System.out.println("JWT 的簽名驗證失敗");
		}
		
	}
}