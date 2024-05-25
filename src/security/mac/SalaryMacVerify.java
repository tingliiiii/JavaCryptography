package security.mac;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.crypto.SecretKey;

import security.KeyUtil;

/*
 * 
 * 用於驗證薪資明細的 MAC
 * 
 * 證明成功實施了 MAC 驗證策略，並且可以正確地驗證您的薪資明細的完整性和來源。
 * 使用這樣的策略可以確保只有擁有正確 MAC 密鑰的人（在這個例子中是 HR 部門）
 * 才能生成有效的 MAC，而其他人則不能。
 * 
 * 這是一個非常重要的安全策略，特別是在涉及敏感資訊（如薪資明細）的場合。
 * 只要保護好您的密鑰，就可以確保消息的真實性和完整性。
*/
public class SalaryMacVerify {

	public static void main(String[] args) throws Exception {
		// 員工
		// 取得薪資及金鑰檔案位置
		String filePath = "src/security/mac/my_salary.txt";
		String keyPath = "src/security/mac/myKey.key";

		// 儲存 HR 部門生成的 macValue
		String macValueFromHR = "0942369816ff2958cfe2f275910bdfb2c4a135ba1a29a421082b7919da4ef1c4";
		
		// 將密鑰檔案 myKey.key 轉成密鑰物件
		SecretKey macKey = KeyUtil.getSecretKeyFromFile("HmacSHA256", keyPath);
		
		// 員工透過 macKey + filePath 自行生成 macValue
		// 透過相同的 macKey 才能產生出相同的 macValue
		String macValueFromEmp = KeyUtil.generateMac("HmacSHA256", macKey, filePath);
		
		// 驗證判斷資料來源
		if(macValueFromEmp.equals(macValueFromHR)) {
			System.out.println("驗證成功");
			String fileContent = Files.readString(Paths.get(filePath));
			System.out.println(fileContent);
		} else {
			System.out.println("驗證失敗");
		}
		
	}

}