package mains;

import java.util.Scanner;

import utils.SpyMemcachedClient;

/**
 * SpyMemcachedテスト.
 *
 * @author evosystem
 */
public class SpyMemcachedGetValueExample {

	/**
	 * メイン.
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("■start.");
		try {
			// クライアントを取得
			SpyMemcachedClient spyMemcachedClient = new SpyMemcachedClient();

			// バージョンを取得
			System.out.println(spyMemcachedClient.getVersions());

			// statsを取得
			System.out.println(spyMemcachedClient.getStats());

			// Scanner
			try (Scanner scanner = new Scanner(System.in)) {
				while (true) {
					System.out.print("キーを入力してください: ");
					String key = scanner.nextLine();

					// 値を取得
					System.out.println(spyMemcachedClient.getValue(key));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			System.out.println("■done.");
		}
	}
}