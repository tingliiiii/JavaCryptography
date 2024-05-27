package security.jwt;

import java.io.File;
import java.io.FileOutputStream;
import com.nimbusds.jwt.JWTClaimsSet;

import net.glxn.qrgen.QRCode;
import security.KeyUtil;

// 高鐵票 JWT
public class SimpleJWT {

	public static void main(String[] args) throws Exception {
		
		// 1. 台灣高鐵公司簽名專用密鑰
		String signingSecret = "abcdefghijklmnopqrstuvwxyz123456"; // 32 bytes（256 bits）
		
		// 2. 建立票務資訊 Payload（建立 JWT 的聲明 claims）
		JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
				.subject("高鐵票")
				.issuer("THSRC")
				.claim("起點", "台北 06:30")
				.claim("終點", "台南 08:01")
				.claim("艙等", "商務艙")
				.claim("車廂", "6")
				.claim("座位", "9A")
				.claim("車次", "203")
				.claim("日期", "2023/09/11")
				.build();
		
		// 3. 對 JWT 簽名並取得 token
		String token = KeyUtil.signJWT(claimsSet, signingSecret);
		System.out.println("高鐵票 token："+token);
		
		// 4. 產生 QRCode
		File file = new File("src/security/jwt/ticket.png");
		QRCode.from(token).withSize(300, 300).writeTo(new FileOutputStream(file));
		System.out.println("QRCode 產生完畢");
		
		
		
		
	}
	
}
