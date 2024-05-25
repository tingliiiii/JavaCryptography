package security.mac;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.crypto.SecretKey;

import security.KeyUtil;

/*
 * 當我們考慮使用 MAC (Message Authentication Code) 而不是單純的 Hash，
 * 主要的情境是當我們想要確保消息不只是保持完整，還要確保它是從特定的發送者傳來的。
 * 使用 MAC 的時候，我們結合了訊息和一個秘密鑰匙來生成一個摘要(mac value)。
 * 只有知道那把「鑰匙」的人才能生成或驗證這個摘要。

 * 以下是一個場景：假設你是一家公司的財務部門的員工，你每月都會從 HR 部門收到你的薪資明細 my_salary.txt。
 * 為了確保這個消息的真實性和完整性，HR 使用一個他們和你都知道的秘密鑰匙生成 MAC，然後發送給你。
 * 你收到後，也使用同一把鑰匙去驗證 MAC，以確保訊息沒有被更改，且真的是從 HR 部門傳來的。
 */

public class SalaryMacCreator {

	public static void main(String[] args) throws Exception {
		
		// HR 部門：薪資檔案位置
		String filePath = "src/security/mac/my_salary.txt";
		
		// 建立 HMAC 密鑰
		String keyPath = "src/security/mac/myKey.key";
		SecretKey macKey = null;
		if(new File(keyPath).exists()) {
			// 若密鑰檔 myKey.key 已存在就讀取
			macKey = KeyUtil.getSecretKeyFromFile("HmacSHA256", keyPath);
		} else {
			// 不存在則生成密鑰物件，並將密鑰儲存至檔案中
			macKey = KeyUtil.generateKeyForHmac();
			KeyUtil.saveKeyToFile(macKey, keyPath);
		}
		
		// 取得 MAC 值
		String macValue = KeyUtil.generateMac("HmacSHA256", macKey, filePath);
		
		// 印出 MAC value
		System.out.printf("請員工將 HR 提供的 MAC 值記錄下來：%n%s%n", macValue);
		
		
	}
}