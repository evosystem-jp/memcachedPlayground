package mains;

import utils.SpyMemcachedClient;

/**
 * 全ての値を削除.
 *
 * @author evosystem
 */
public class SpyMemcachedFlush {

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

			// 全ての値を削除
			if (spyMemcachedClient.flush()) {
				System.out.println("flushに成功");
			} else {
				System.err.println("flushに失敗");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			System.out.println("■done.");
		}
	}
}