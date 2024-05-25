package security.hash;

import security.KeyUtil;

// 驗證 my_salary.txt 的內容是否遭受竄改？
public class SalaryHashValidator {

	public static void main(String[] args) {
		// 透過 SalaryHashGenerator.java 先得知 Hash
		String knowHash = "e424d076e9a2fd7934e5c7307c22564a9ad9f62935d15b11a325cb0f7477a3ca";
		
		// 重新針對 my_salary.txt 產生 hash
		// 宣告 my_salary.txt 的文件路徑
		String filePath = "src/security/hash/my_salary.txt";
		// 取得 Hash
		String fileHash = KeyUtil.generateFileHash(filePath);
		
		// 比較 knowHash == fileHash 是否相等 ?
		if(knowHash.equals(fileHash)) {
			System.out.printf("%s 沒有遭受竄改%n", filePath);
		} else {
			System.out.printf("%s 可能已經遭受竄改%n", filePath);
		}
		
	}

}