package mains;

import utils.SpyMemcachedClient;

/**
 * SpyMemcachedテスト.
 *
 * @author evosystem
 */
public class SpyMemcachedExample {

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

			// 値を設定
			String key = "exampleKey";
			String value = "exampleValue";
			spyMemcachedClient.setValue(key, value);

			// 値を取得
			System.out.println(spyMemcachedClient.getValue(key));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			System.out.println("■done.");
		}
	}
}