package com.conference.Task4.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ConnectionPool {
    private static final Logger logger = LoggerFactory.getLogger(ConnectionPool.class);
    private final ConcurrentLinkedQueue<Connection> freeConnections = new ConcurrentLinkedQueue<>();
    private final BlockingQueue<Connection> givenConnections = new LinkedBlockingQueue<>();
    private final int poolSize;

    private static class Holder {
        static final ConnectionPool INSTANCE = new ConnectionPool();
    }

    public static ConnectionPool getInstance() {
        return Holder.INSTANCE;
    }

    private ConnectionPool() {
        Properties props = new Properties();
        try (InputStream is = getClass().getClassLoader().getResourceAsStream("db.properties")) {
            props.load(is);
            poolSize = Integer.parseInt(props.getProperty("db.pool.size"));
            String url = props.getProperty("db.url");
            String user = props.getProperty("db.user");
            String pass = props.getProperty("db.password");
            for (int i = 0; i < poolSize; i++) {
                Connection conn = DriverManager.getConnection(url, user, pass);
                freeConnections.add(conn);
            }
        } catch (Exception e) {
            logger.error("Pool init error", e);
            throw new RuntimeException(e);
        }
    }

    public Connection getConnection() {
        Connection conn = freeConnections.poll();
        if (conn == null) {
            try {
                conn = givenConnections.take();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                logger.error("Get conn interrupted", e);
                return null;
            }
        }
        givenConnections.add(conn);
        return conn;
    }

    public void releaseConnection(Connection conn) {
        givenConnections.remove(conn);
        freeConnections.add(conn);
    }

    public void closeAll() {
        for (Connection conn : freeConnections) {
            try {
                conn.close();
            } catch (SQLException e) {
                logger.error("Close conn error", e);
            }
        }
    }
}