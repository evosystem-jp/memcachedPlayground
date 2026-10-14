package mains;

import utils.SpyMemcachedClient;

/**
 * SpyMemcachedループテスト.
 *
 * @author evosystem
 */
public class SpyMemcachedLoopExample {

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
			System.out.println("Versions: " + spyMemcachedClient.getVersions());

			// statsを取得
			System.out.println("Stats: " + spyMemcachedClient.getStats());

			// status itemsを取得
			System.out.println("Stats items: " + spyMemcachedClient.getStats("items"));

			// ループ
			for (int i = 1; i < 1000; i++) {
				try {
					long startTime = System.currentTimeMillis();

					// 値を設定
					String key = String.format("exampleKey-%d", i);
					String value = String.format("exampleValue-%d", i);
					spyMemcachedClient.setValue(key, value);

					// 値を取得
					String retrievedValue = spyMemcachedClient.getValue(key);
					System.out.println(String.format("Retrieved: %s (Time taken: %d ms)", retrievedValue,
							(System.currentTimeMillis() - startTime)));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			System.out.println("■done.");
		}
	}
}