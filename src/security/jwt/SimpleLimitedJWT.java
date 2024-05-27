package security.jwt;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;

import com.nimbusds.jwt.JWTClaimsSet;

import net.glxn.qrgen.QRCode;
import security.KeyUtil;

// 高鐵票 JWT（有時效性）
public class SimpleLimitedJWT {

	public static void main(String[] args) throws Exception {

		// 1. 台灣高鐵公司簽名專用密鑰
		String signingSecret = "abcdefghijklmnopqrstuvwxyz123456"; // 32 bytes（256 bits）

		Date time = new Date(new Date().getTime() + (5 * 1000)); // 現在時刻 + 5秒

		// 2. 建立票務資訊 Payload（建立 JWT 的聲明 claims）
		JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
				.subject("高鐵票")
				.issuer("THSRC")
				.claim("authcode", "069A")
				.expirationTime(time)
				.build();

		// 3. 對 JWT 簽名並取得 token
		String token = KeyUtil.signJWT(claimsSet, signingSecret);
		System.out.println("高鐵票 token：" + token);

		// 4. 產生 QRCode
		File file = new File("src/security/jwt/ticket.png");
		QRCode.from(token).withSize(300, 300).writeTo(new FileOutputStream(file));
		System.out.println("QRCode 產生完畢");
		// -------------------------------------------------------------------------------

		while (true) {
			// 5. 驗證 JWT 的簽名
			if (KeyUtil.verifyJWTSignature(token, signingSecret)) {
				System.out.println("JWT 簽名驗證成功");
				System.out.println("驗票閘門開啟⋯⋯");
			} else {
				System.out.println("JWT 簽名驗證失敗／已過期");
				break;
			}
			Thread.sleep(2000);
		}

	}

}
