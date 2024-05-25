package security.random;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class LineNotifyDemo {

	public static void main(String[] args) throws Exception {

		// 1. 要發送的資料
		String message = "https://maps.app.goo.gl/UuDabLiYYyq755yJA?g_st=il";
		String imageUrl = "https://images.pexels.com/photos/2707756/pexels-photo-2707756.jpeg"; // 圖片的網址

		// 2. 存取權杖(也稱為:授權 Token)
		// String token = "xA7ZWBqpNZwgYhT6X1wOODEvhydAnKdbvzUfXyitmD3"; // 班群
		String token = "8jMEtE3KWpLwVksSh67wqXjM5Pl2Ps4eUskFssA1A9U"; // 我自己
		// String token = "W3gtKc1abP7ir61ceFQSZ6TjdhSoUzr3gUC0xT3gS0L"; // 今天吃什麼
		// String token = "hTye220o2bQ7E4T4MuiWRSeBAdnoJr2Lf9PCMRoG8GX"; // 轉職

		// 3. Line Notify 的發送位置
		String lineNotifyUrl = "https://notify-api.line.me/api/notify";

		// 4. 發送前設定
		String postData = "message=" + message
					//  + "&stickerPackageId=789" + "&stickerId=10855";
						+ "&imageThumbnail=" + imageUrl + "&imageFullsize=" + imageUrl;
					//  + "&stickerPackageId=6325" + "&stickerId=10979906";
		URL url = new URL(lineNotifyUrl);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setDoOutput(true);
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Authorization", "Bearer " + token);

		// 5. 訊息發送
		try (DataOutputStream wr = new DataOutputStream(conn.getOutputStream())) {
			wr.write(postData.getBytes("UTF-8"));
		}

		// 6. 回應資料
		int responseCode = conn.getResponseCode();
		System.out.println("Response Code: " + responseCode);
	}
}