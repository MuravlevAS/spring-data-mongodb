/*
 * Copyright 2011-2025 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.data.mongodb.config;

import static org.assertj.core.api.Assertions.*;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Integration tests for {@link MongoDatabaseFactory}.
 *
 * @author Thomas Risberg
 * @author Oliver Gierke
 */
@RunWith(SpringRunner.class)
@ContextConfiguration
public class MongoDbFactoryNoDatabaseRunningTests {

	@Autowired MongoTemplate mongoTemplate;

	@Test // DATAMONGO-139
	public void startsUpWithoutADatabaseRunning() {
		assertThat(mongoTemplate.getClass().getName()).isEqualTo("org.springframework.data.mongodb.core.MongoTemplate");
	}

	@Test
	public void failsDataAccessWithoutADatabaseRunning() {
		assertThatExceptionOfType(DataAccessResourceFailureException.class)
				.isThrownBy(() -> mongoTemplate.getCollectionNames());
	}
}
