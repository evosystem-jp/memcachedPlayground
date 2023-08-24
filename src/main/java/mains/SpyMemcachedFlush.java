package mains;

import utils.SpyMemcacheClient;

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
			SpyMemcacheClient spyMemcacheClient = new SpyMemcacheClient();

			// 全ての値を削除
			if (spyMemcacheClient.flush()) {
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