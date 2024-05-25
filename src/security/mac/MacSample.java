package security.mac;

import javax.crypto.SecretKey;

import security.KeyUtil;

// MAC 訊息驗證碼簡單範例
public class MacSample {

	public static void main(String[] args) throws Exception {
		
		// 1. 定義要加上 MAC 的訊息
		String message = "本月加發 10,000 元";
		System.out.printf("原始訊息：%s%n",message);
		
		// 2. 產生專用於 HMAC 的密鑰
		// Hash-based Message Authentication Code
		SecretKey macKey = KeyUtil.generateKeyForHmac();
		
		// 3. 利用此密鑰（macKey）和訊息（message）生成 MAC 值
		byte[] macValue = KeyUtil.generateMac("HmacSHA256", macKey, message.getBytes());
		
		// 4. 轉換成 Hex
		String macHex = KeyUtil.bytesToHex(macValue);
		System.out.printf("產生的 MAC（Hex）：%s%n",macHex);
		
		// 5. 實際應用中，接收方會收到 message 以及 macHex（或 macValue）
		//    雙方（傳送方及接收方）都要有 macKey
		//    接收方根據 message ＋ macKey 所產生的值與 macHex 進行比對
		byte[] computedMacValue = KeyUtil.generateMac("HmacSHA256", macKey, message.getBytes());
		String computedMacHex = KeyUtil.bytesToHex(computedMacValue);
		
		// 6. 比較 macValue 與 computedMacValue 是否相等
		if(computedMacHex.equals(macHex)) {
			System.out.println("MAC 驗證成功，來源正確");
		} else {
			System.out.println("MAC 驗證失敗，來源不正確");
		}
	}
}
