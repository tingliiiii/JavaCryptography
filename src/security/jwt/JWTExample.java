package security.jwt;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.KeyLengthException;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import security.KeyUtil;

/**
 * 依據（JWA、JWK）與（JWS）產生 Token（JWT）
 * 
 *  +-----+   +-----+ 
 *  | JWK | → | JWS | 
 *  +-----+   +-----+
 *     ↑         ↓ 
 *  +-----+   +-----+ 
 *  | JWA |   | JWT | 
 *  +-----+   +-----+
 * 
 */
public class JWTExample {

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
				.build();

		// 4. JWT：建立 JWT（尚未加密）
		SignedJWT signedJWT = new SignedJWT(header, payload);

		// 5. JWS：簽名（利用 JWK 生成的密鑰）
		JWSSigner jwsSigner = new MACSigner(signingSecret);

		// 6. 簽名
		signedJWT.sign(jwsSigner);

		// 7. 透過序列化技術產生 token（可以被安全傳遞、儲存）
		String token = signedJWT.serialize();
		System.out.println("JWT(Token)：" + token);

		// ========================= 驗證 ==============================
		// 8.
		System.out.println();
		System.out.println("驗證 JWT");
		System.out.println("已知 signingSecret：" + signingSecret);
		System.out.println("已知 token：" + token);

		// 9.從 token 取得簽名
		SignedJWT vertifiedJWT = SignedJWT.parse(token);

		// 10. 透過 signingSecret 取得密鑰
		JWSVerifier verifier = new MACVerifier(signingSecret);

		// 11. 進行驗證
		if (vertifiedJWT.verify(verifier)) {
			System.out.println("JWT 簽名驗證成功");

			// 顯示 payload 資料
			JWTClaimsSet claims = vertifiedJWT.getJWTClaimsSet();
			System.out.println("主題 Subject：" + claims.getSubject());
			System.out.printf("發行者 Issuer：%s%n", claims.getIssuer());
			System.out.printf("Name：%s%n", claims.getStringClaim("Name"));
			System.out.printf("Title：%s%n", claims.getStringClaim("Title"));
			System.out.printf("From：%s%n", claims.getStringClaim("From"));
		} else {
			System.out.println("JWT 簽名驗證失敗");
		}

	}
}