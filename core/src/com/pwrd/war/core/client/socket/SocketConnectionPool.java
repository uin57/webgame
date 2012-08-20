package com.pwrd.war.core.client.socket;

import java.io.IOException;

import org.apache.commons.pool.impl.GenericObjectPool;
import org.apache.mina.common.ByteBuffer;

/**
 * Socket Connection连接池
 * 
 */
public class SocketConnectionPool {
	private final GenericObjectPool pool;

	public SocketConnectionPool(SocketConnectionFactory factory, GenericObjectPool.Config config) {
		this.pool = new GenericObjectPool(factory, config);
	}

	/**
	 * 取得一个连接,在使用完成该连接之后必须调用{@link ISocketConnection#close()}将该连接释放
	 * 
	 * @return
	 * @throws Exception
	 */
	public ISocketConnection getConnection() throws IOException {
		try {
			ISocketConnection _connection = (ISocketConnection) this.pool.borrowObject();
			return new WrapperDBSConnection(_connection);
		} catch (Exception e) {
			throw new IOException(e);
		}
	}

	/**
	 * 对{@link SocketConnectionImpl}的封装,当客户端调用close方法的时候将{@link #connection}返回到
	 * {@link SocketConnectionPool#pool} 中
	 * 
	  *
	 * 
	 */
	private final class WrapperDBSConnection implements ISocketConnection {
		public final ISocketConnection connection;

		public WrapperDBSConnection(ISocketConnection connection) {
			if (connection == null) {
				throw new IllegalArgumentException("The connection can't be null");
			}
			this.connection = connection;
		}

		public void close() throws IOException {
			try {
				pool.returnObject(connection);
			} catch (Exception e) {
				throw new IOException(e);
			}
		}

		public void closeNative() throws IOException {
			try {
				pool.invalidateObject(connection);
			} catch (Exception e) {
				throw new IOException(e);
			}
		}

		public boolean isConnected() {
			return connection.isConnected();
		}

		public ByteBuffer receive() throws IOException {
			try {
				return connection.receive();
			} catch (IOException e) {
				connection.close();
				throw e;
			}
		}

		public void send(ByteBuffer buffer) throws IOException {
			try {
				connection.send(buffer);
			} catch (IOException e) {
				connection.close();
				throw e;
			}
		}
	}

}
