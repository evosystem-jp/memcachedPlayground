package utils;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.Calendar;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import constants.Configurations;
import net.spy.memcached.ConnectionFactoryBuilder;
import net.spy.memcached.FailureMode;
import net.spy.memcached.MemcachedClient;
import net.spy.memcached.auth.AuthDescriptor;
import net.spy.memcached.auth.PlainCallbackHandler;

/**
 * SpyMemcacheClient.
 * 
 * @author evosystem
 */
public class SpyMemcacheClient {

	/**
	 * MemcachedClient.
	 */
	private MemcachedClient client;

	/**
	 * タイムアウト時間.
	 */
	private static final int CONNECTION_TIMEOUT = 500;

	/**
	 * タイムアウト時間.
	 */
	private static final int SOCKET_TIMEOUT = 500;

	/**
	 * クライアントを取得.
	 */
	public SpyMemcacheClient() {
		try {
			ConnectionFactoryBuilder builder = new ConnectionFactoryBuilder()
					.setProtocol(ConnectionFactoryBuilder.Protocol.BINARY)
					.setFailureMode(FailureMode.Redistribute)
					.setDaemon(true)
					.setOpTimeout(CONNECTION_TIMEOUT)
					.setMaxReconnectDelay(CONNECTION_TIMEOUT)
					.setFailureMode(FailureMode.Retry)
					.setAuthDescriptor(new AuthDescriptor(new String[] { "PLAIN" }, new PlainCallbackHandler(
							Configurations.MEMCACHED_USERNAME, Configurations.MEMCACHED_PASSWORD)));
			this.client = new MemcachedClient(builder.build(), Collections.singletonList(
					new InetSocketAddress(Configurations.MEMCACHED_HOST, Configurations.MEMCACHED_PORT)));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * バージョンを取得.
	 * 
	 * @return
	 */
	public Map<SocketAddress, String> getVersions() {
		return client.getVersions();
	}

	/**
	 * statsを取得.
	 * 
	 * @return
	 */
	public Map<SocketAddress, Map<String, String>> getStats() {
		return client.getStats();
	}

	/**
	 * statsを取得.
	 * 
	 * @param arg
	 * @return
	 */
	public Map<SocketAddress, Map<String, String>> getStats(String arg) {
		return client.getStats(arg);
	}

	/**
	 * 全ての値を削除.
	 * 
	 * @return
	 */
	public boolean flush() {
		boolean res = true;
		Future<Boolean> f = null;
		try {
			f = client.flush();
			res = f.get(SOCKET_TIMEOUT, TimeUnit.MILLISECONDS);
		} catch (Exception e) {
			if (f != null) {
				f.cancel(true);
			}
			e.printStackTrace();
		}
		return res;
	}

	/**
	 * 値を取得.
	 * 
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public String getValue(final String key) throws Exception {
		return (String) getObjectValue(key);
	}

	/**
	 * 値を取得.
	 * 
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public Object getObjectValue(final String key) throws Exception {
		Future<Object> f = null;
		try {
			f = client.asyncGet(key);
			Object value = f.get(SOCKET_TIMEOUT, TimeUnit.MILLISECONDS);
			return value;
		} catch (InterruptedException | ExecutionException | TimeoutException e) {
			if (f != null) {
				f.cancel(true);
			}
			throw e;
		}
	}

	/**
	 * 値を設定.
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean setValue(final String key, final String value) {
		boolean res = true;
		Future<Boolean> f = null;
		try {
			f = client.set(key, 0, value);
			res = f.get(SOCKET_TIMEOUT, TimeUnit.MILLISECONDS);
		} catch (Exception e) {
			if (f != null) {
				f.cancel(true);
			}
			res = false;
			e.printStackTrace();
		}
		return res;
	}

	/**
	 * 値を設定.
	 * 
	 * @param key
	 * @param value
	 * @param milliSec
	 * @return
	 */
	public boolean setValue(final String key, final String value, final int milliSec) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MILLISECOND, milliSec);
		return setObjectValue(key, value, cal);
	}

	/**
	 * 値を設定.
	 * 
	 * @param key
	 * @param value
	 * @param cal
	 * @return
	 */
	public boolean setValue(final String key, final String value, final Calendar cal) {
		return setObjectValue(key, value, cal);
	}

	/**
	 * 値を設定.
	 * 
	 * @param key
	 * @param value
	 * @param cal
	 * @return
	 */
	public boolean setObjectValue(final String key, final Object value, final Calendar cal) {
		boolean res = true;
		Future<Boolean> f = null;
		try {
			f = client.set(key, (int) (cal.getTime().getTime() / 1000), value);
			res = f.get(SOCKET_TIMEOUT, TimeUnit.MILLISECONDS);

		} catch (Exception e) {
			if (f != null) {
				f.cancel(true);
			}
			e.printStackTrace();
		}
		return res;
	}
}