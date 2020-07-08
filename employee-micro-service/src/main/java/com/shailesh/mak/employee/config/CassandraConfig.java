package com.shailesh.mak.employee.config;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.config.CassandraCqlClusterFactoryBean;
import org.springframework.data.cassandra.config.SchemaAction;
import org.springframework.data.cassandra.core.cql.keyspace.CreateKeyspaceSpecification;
import org.springframework.data.cassandra.core.cql.keyspace.KeyspaceOption;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

import com.shailesh.mak.employee.EmployeeApplication;

@Configuration
@EnableCassandraRepositories(basePackages = "com.blog.musicstore")
public class CassandraConfig extends AbstractCassandraConfiguration {

	@Value("${spring.data.cassandra.keyspace-name}")
	private String keyspaceName;

	@Value("${spring.data.cassandra.port}")
	private Integer port;

	@Value("${spring.data.cassandra.contact-points}")
	private String contactPoints;

	@Bean
	@Override
	public CassandraCqlClusterFactoryBean cluster() {
		CassandraCqlClusterFactoryBean bean = new CassandraCqlClusterFactoryBean();
		bean.setKeyspaceCreations(getKeyspaceCreations());
		bean.setContactPoints(contactPoints);
		bean.setPort(port);
		bean.setJmxReportingEnabled(false);
		return bean;
	}

	@Override
	protected String getKeyspaceName() {
		return keyspaceName;
	}

	@Override
	public String[] getEntityBasePackages() {
		return new String[] { EmployeeApplication.class.getPackage().getName() };
	}

	@Override
	protected List<CreateKeyspaceSpecification> getKeyspaceCreations() {
		return Collections.singletonList(CreateKeyspaceSpecification.createKeyspace(getKeyspaceName()).ifNotExists()
				.with(KeyspaceOption.DURABLE_WRITES, true).withSimpleReplication());
	}

	@Override
	public SchemaAction getSchemaAction() {
		return SchemaAction.CREATE_IF_NOT_EXISTS;
	}
}