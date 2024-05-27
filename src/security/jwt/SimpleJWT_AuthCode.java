package security.jwt;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nimbusds.jwt.JWTClaimsSet;

import net.glxn.qrgen.QRCode;
import security.KeyUtil;

// 高鐵票 JWT + AuthCode（授權碼）
public class SimpleJWT_AuthCode {

	// 票務資料
	static Map<String, List<String>> tickets = new HashMap<>();
	static {
		tickets.put("069A", List.of("台北 06:30", "台南 08:01", "商務艙", "6", "9A", "203", "2023/09/11", "顏子惟"));
		tickets.put("12FF", List.of("高雄 07:15", "新竹 09:45", "自由座", "12", "F", "305", "2023/09/12", "李志明"));
		tickets.put("087C", List.of("台中 08:50", "台北 10:20", "經濟艙", "8", "7C", "101", "2023/09/13", "王小美"));
		tickets.put("10FF", List.of("桃園 06:00", "台南 08:30", "自由座", "10", "F", "204", "2023/09/14", "陳大華"));
		tickets.put("053E", List.of("台南 09:10", "高雄 10:40", "經濟艙", "5", "3E", "507", "2023/09/15", "張淑芬"));
		tickets.put("092C", List.of("新竹 07:00", "台中 09:30", "經濟艙", "9", "2C", "402", "2023/09/16", "劉偉強"));
	}

	public static void main(String[] args) throws Exception {

		// 1. 台灣高鐵公司簽名專用密鑰（JWK）
		String signingSecret = "abcdefghijklmnopqrstuvwxyz123456"; // 32 bytes（256 bits）

		// 2. 建立票務資訊 Payload（建立 JWT 的聲明 claims）
		JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
				.subject("高鐵票")
				.issuer("THSRC")
				.claim("authcode", "069A")
				.build();

		// 3. 對 JWT 簽名並取得 token
		String token = KeyUtil.signJWT(claimsSet, signingSecret);
		System.out.println("高鐵票 token：" + token);

		// 4. 產生 QRCode
		File file = new File("src/security/jwt/ticket.png");
		QRCode.from(token).withSize(300, 300).writeTo(new FileOutputStream(file));
		System.out.println("QRCode 產生完畢");

		// ------------------------------------------------------

		// 5. 驗證 JWT 簽名
		if (KeyUtil.verifyJWTSignature(token, signingSecret)) {
			System.out.println("JWT 簽名驗證成功");
			JWTClaimsSet claims = KeyUtil.getClaimsFromToken(token);
			String authCode = claims.getStringClaim("authcode");
			String data = null;
			
			try {
				data = tickets.get(authCode).toString();
			} catch (Exception e) {
				System.err.println("授權碼錯誤，驗證失敗");
			}
			
			if (data != null) {
				System.out.println("授權碼正確");
			}

			System.out.println("票務資訊：" + data);
		} else {
			System.out.println("JWT 簽名驗證失敗");
		}

	}

}
