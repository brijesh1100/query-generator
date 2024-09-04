package com.hasura.query.engine;

import java.io.Closeable;
import java.time.Duration;
import java.util.Map;

import javax.sql.DataSource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class DataSourceFactory {

	/**
	   * Constructs a new {@link DataSource} using the provided configuration.
	   *
	   * @param username The username of the database user.
	   * @param password The password of the database user.
	   * @param driverClassName The fully qualified name of the JDBC driver class.
	   * @param jdbcConnectionString The JDBC connection string.
	   * @return The configured {@link DataSource}.
	   */
	  public static DataSource create(final String username,
	                                  final String password,
	                                  final String driverClassName,
	                                  final String jdbcConnectionString) {
	    return new DataSourceBuilder()
	        .withDriverClassName(driverClassName)
	        .withJdbcUrl(jdbcConnectionString)
	        .withPassword(password)
	        .withUsername(username)
	        .build();
	  }
	  
	  public static DataSourceBuilder NEW_INSTANCE() {
		  return new DataSourceBuilder();
	  }

	
	/**
	   * Utility method that attempts to close the provided {@link DataSource} if it implements
	   * {@link Closeable}.
	   *
	   * @param dataSource The {@link DataSource} to close.
	   * @throws Exception if unable to close the data source.
	   */
	  public static void close(final DataSource dataSource) throws Exception {
	    if (dataSource != null) {
	      if (dataSource instanceof AutoCloseable closeable) {
	        closeable.close();
	      }
	    }
	  }

	/**
	 * Builder class used to configure and construct {@link DataSource} instances.
	 */
	public static class DataSourceBuilder {

		private Map<String, String> connectionProperties = Map.of();
		private String database;
		private String driverClassName;
		private String host;
		private String jdbcUrl;
		private int maximumPoolSize = 10;
		private int minimumPoolSize = 0;
		private Duration connectionTimeout = Duration.ZERO;
		private String password;
		private int port = 5432;
		private String username;

		private DataSourceBuilder() {
		}

		public DataSourceBuilder withConnectionProperties(final Map<String, String> connectionProperties) {
			if (connectionProperties != null) {
				this.connectionProperties = connectionProperties;
			}
			return this;
		}

		public DataSourceBuilder withDatabase(final String database) {
			this.database = database;
			return this;
		}

		public DataSourceBuilder withDriverClassName(final String driverClassName) {
			this.driverClassName = driverClassName;
			return this;
		}

		public DataSourceBuilder withHost(final String host) {
			this.host = host;
			return this;
		}

		public DataSourceBuilder withJdbcUrl(final String jdbcUrl) {
			this.jdbcUrl = jdbcUrl;
			return this;
		}

		public DataSourceBuilder withMaximumPoolSize(final Integer maximumPoolSize) {
			if (maximumPoolSize != null) {
				this.maximumPoolSize = maximumPoolSize;
			}
			return this;
		}

		public DataSourceBuilder withMinimumPoolSize(final Integer minimumPoolSize) {
			if (minimumPoolSize != null) {
				this.minimumPoolSize = minimumPoolSize;
			}
			return this;
		}

		public DataSourceBuilder withConnectionTimeout(final Duration connectionTimeout) {
			if (connectionTimeout != null) {
				this.connectionTimeout = connectionTimeout;
			}
			return this;
		}

		public DataSourceBuilder withPassword(final String password) {
			this.password = password;
			return this;
		}

		public DataSourceBuilder withPort(final Integer port) {
			if (port != null) {
				this.port = port;
			}
			return this;
		}

		public DataSourceBuilder withUsername(final String username) {
			this.username = username;
			return this;
		}

		public DataSource build() {

			final HikariConfig config = new HikariConfig();

			config.setDriverClassName(driverClassName);
			config.setJdbcUrl(jdbcUrl != null ? jdbcUrl
					: String.format(driverClassName, host, port, database));
			config.setMaximumPoolSize(maximumPoolSize);
			config.setMinimumIdle(minimumPoolSize);
			// HikariCP uses milliseconds for all time values:
			// https://github.com/brettwooldridge/HikariCP#gear-configuration-knobs-baby
			config.setConnectionTimeout(connectionTimeout.toMillis());
			config.setPassword(password);
			config.setUsername(username);
			config.setAutoCommit(false);

			config.setInitializationFailTimeout(Integer.MIN_VALUE);

			connectionProperties.forEach(config::addDataSourceProperty);

			return new HikariDataSource(config);
		}

	}
}
