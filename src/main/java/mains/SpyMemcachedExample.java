package mains;

import utils.SpyMemcacheClient;

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
			SpyMemcacheClient spyMemcacheClient = new SpyMemcacheClient();

			// バージョンを取得
			System.out.println(spyMemcacheClient.getVersions());

			// statsを取得
			System.out.println(spyMemcacheClient.getStats());

			// 値を設定
			String key = "exampleKey";
			String value = "exampleValue";
			spyMemcacheClient.setValue(key, value);

			// 値を取得
			System.out.println(spyMemcacheClient.getValue(key));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			System.out.println("■done.");
		}
	}
}