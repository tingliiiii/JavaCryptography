package security.hash;

import security.KeyUtil;

// 針對 my_salary.txt 產生 hash
public class SalaryHashGenerator {
	public static void main(String[] args) {
		// 宣告 my_salary.txt 的文件路徑
		String filePath = "src/security/hash/my_salary.txt";
		// 取得 Hash
		String fileHash = KeyUtil.generateFileHash(filePath);
		// 印出 Hash
		if(fileHash == null) {
			System.out.println("Error");
			return;
		}
		System.out.printf("%s 的 SHA-256 Hash: %n%s%n", filePath, fileHash);
	}
}

// src/security/hash/my_salary.txt 的 SHA-256 Hash: 
// e424d076e9a2fd7934e5c7307c22564a9ad9f62935d15b11a325cb0f7477a3ca