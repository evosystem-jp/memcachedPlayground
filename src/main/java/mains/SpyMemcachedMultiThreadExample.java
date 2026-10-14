package mains;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import utils.SpyMemcachedClient;

/**
 * SpyMemcachedループテスト.
 *
 * @author evosystem
 */
public class SpyMemcachedMultiThreadExample {

	/**
	 * 処理件数.
	 */
	private static final int TASK_NUM = 1000;

	/**
	 * メイン.
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("■start.");

		// ExecutorServiceを作成
		ExecutorService executorService = Executors.newCachedThreadPool();

		try {
			// クライアントを取得
			SpyMemcachedClient spyMemcachedClient = new SpyMemcachedClient();

			// バージョンを取得
			System.out.println("Versions: " + spyMemcachedClient.getVersions());

			// statsを取得
			System.out.println("Stats: " + spyMemcachedClient.getStats());

			// status itemsを取得
			System.out.println("Stats items: " + spyMemcachedClient.getStats("items"));

			// Futureを作成
			List<Future<?>> futures = new ArrayList<>();
			for (int i = 1; i <= TASK_NUM; i++) {
				final int index = i;
				futures.add(executorService.submit(() -> {
					try {
						long startTime = System.currentTimeMillis();

						// 値を設定
						String key = String.format("exampleKey-%d", index);
						String value = String.format("exampleValue-%d", index);
						spyMemcachedClient.setValue(key, value);

						// 値を取得
						String retrievedValue = spyMemcachedClient.getValue(key);
						System.out.println(String.format("Retrieved: %s (Thread: %s, Time taken: %d ms)",
								retrievedValue, Thread.currentThread().getName(),
								(System.currentTimeMillis() - startTime)));
					} catch (Exception e) {
						e.printStackTrace();
					}
				}));
			}

			// 全てのFutureに対して実行
			for (Future<?> future : futures) {
				// Futureを解決
				future.get();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			executorService.shutdown();
			try {
				if (!executorService.awaitTermination(30, TimeUnit.SECONDS)) {
					executorService.shutdownNow();
				}
			} catch (InterruptedException e) {
				executorService.shutdownNow();
				Thread.currentThread().interrupt();
			}
			System.out.println("■done.");
		}
	}
}