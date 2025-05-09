/*
 * Copyright 2015-2025 the original author or authors.
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
package org.springframework.data.mongodb.core.geo;

import static org.assertj.core.api.Assertions.*;

import java.io.IOException;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.data.geo.Point;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Christoph Strobl
 * @author Artyom Muravlev
 */
class GeoJsonModuleUnitTests {

	ObjectMapper mapper;

	@BeforeEach
	void setUp() {

		mapper = new ObjectMapper();
		mapper.registerModule(new GeoJsonModule());
	}

	@Test // DATAMONGO-1181
	void shouldDeserializeJsonPointCorrectly() throws IOException {

		String json = "{ \"type\": \"Point\", \"coordinates\": [10.0, 20.0] }";

		assertThat(mapper.readValue(json, GeoJsonPoint.class)).isEqualTo(new GeoJsonPoint(10D, 20D));
	}

	@Test // DATAMONGO-1181
	void shouldDeserializeGeoJsonLineStringCorrectly()
			throws IOException {

		String json = "{ \"type\": \"LineString\", \"coordinates\": [ [10.0, 20.0], [30.0, 40.0], [50.0, 60.0] ]}";

		assertThat(mapper.readValue(json, GeoJsonLineString.class))
				.isEqualTo(new GeoJsonLineString(Arrays.asList(new Point(10, 20), new Point(30, 40), new Point(50, 60))));
	}

	@Test // DATAMONGO-1181
	void shouldDeserializeGeoJsonMultiPointCorrectly()
			throws IOException {

		String json = "{ \"type\": \"MultiPoint\", \"coordinates\": [ [10.0, 20.0], [30.0, 40.0], [50.0, 60.0] ]}";

		assertThat(mapper.readValue(json, GeoJsonLineString.class))
				.isEqualTo(new GeoJsonMultiPoint(Arrays.asList(new Point(10, 20), new Point(30, 40), new Point(50, 60))));
	}

	@Test // DATAMONGO-1181
	@SuppressWarnings("unchecked")
	void shouldDeserializeGeoJsonMultiLineStringCorrectly()
			throws IOException {

		String json = "{ \"type\": \"MultiLineString\", \"coordinates\": [ [ [10.0, 20.0], [30.0, 40.0] ], [ [50.0, 60.0] , [70.0, 80.0] ] ]}";

		assertThat(mapper.readValue(json, GeoJsonMultiLineString.class)).isEqualTo(new GeoJsonMultiLineString(
				Arrays.asList(new Point(10, 20), new Point(30, 40)), Arrays.asList(new Point(50, 60), new Point(70, 80))));
	}

	@Test // DATAMONGO-1181
	void shouldDeserializeGeoJsonPolygonCorrectly() throws IOException {

		String json = "{ \"type\": \"Polygon\", \"coordinates\": [ [ [100.0, 0.0], [101.0, 0.0], [101.0, 1.0], [100.0, 1.0], [100.0, 0.0] ] ]}";

		assertThat(mapper.readValue(json, GeoJsonPolygon.class)).isEqualTo(new GeoJsonPolygon(
				Arrays.asList(new Point(100, 0), new Point(101, 0), new Point(101, 1), new Point(100, 1), new Point(100, 0))));
	}

	@Test // DATAMONGO-1181
	void shouldDeserializeGeoJsonMultiPolygonCorrectly()
			throws IOException {

		String json = "{ \"type\": \"Polygon\", \"coordinates\": ["
				+ "[[[102.0, 2.0], [103.0, 2.0], [103.0, 3.0], [102.0, 3.0], [102.0, 2.0]]],"
				+ "[[[100.0, 0.0], [101.0, 0.0], [101.0, 1.0], [100.0, 1.0], [100.0, 0.0]],"
				+ "[[100.2, 0.2], [100.8, 0.2], [100.8, 0.8], [100.2, 0.8], [100.2, 0.2]]]"//
				+ "]}";

		assertThat(mapper.readValue(json, GeoJsonMultiPolygon.class)).isEqualTo(new GeoJsonMultiPolygon(Arrays.asList(
				new GeoJsonPolygon(Arrays.asList(new Point(102, 2), new Point(103, 2), new Point(103, 3), new Point(102, 3),
						new Point(102, 2))),
				new GeoJsonPolygon(Arrays.asList(new Point(100, 0), new Point(101, 0), new Point(101, 1), new Point(100, 1),
						new Point(100, 0))),
				new GeoJsonPolygon(Arrays.asList(new Point(100.2, 0.2), new Point(100.8, 0.2), new Point(100.8, 0.8),
						new Point(100.2, 0.8), new Point(100.2, 0.2))))));

	}

	@Test // GH-4950
	void shouldSerializeJsonPointCorrectly() throws IOException {

		String json = "{\"type\":\"Point\",\"coordinates\":[10.0,20.0]}";

		assertThat(mapper.writeValueAsString(new GeoJsonPoint(10D, 20D))).isEqualTo(json);
	}

	@Test // GH-4950
	void shouldSerializeGeoJsonLineStringCorrectly()
			throws IOException {

		String json = "{\"type\":\"LineString\",\"coordinates\":[[10.0,20.0],[30.0,40.0],[50.0,60.0]]}";

		assertThat(mapper.writeValueAsString(new GeoJsonLineString(Arrays.asList(new Point(10, 20), new Point(30, 40), new Point(50, 60)))))
				.isEqualTo(json);
	}

	@Test // GH-4950
	void shouldSerializeGeoJsonMultiPointCorrectly()
			throws IOException {

		String json = "{\"type\":\"MultiPoint\",\"coordinates\":[[10.0,20.0],[30.0,40.0],[50.0,60.0]]}";

		assertThat(mapper.writeValueAsString(new GeoJsonMultiPoint(Arrays.asList(new Point(10, 20), new Point(30, 40), new Point(50, 60)))))
				.isEqualTo(json);
	}

	@Test // GH-4950
	@SuppressWarnings("unchecked")
	void shouldSerializeGeoJsonMultiLineStringCorrectly()
			throws IOException {

		String json = "{\"type\":\"MultiLineString\",\"coordinates\":[[[10.0,20.0],[30.0,40.0]],[[50.0,60.0],[70.0,80.0]]]}";

		assertThat(mapper.writeValueAsString(new GeoJsonMultiLineString(
				Arrays.asList(new Point(10, 20), new Point(30, 40)), Arrays.asList(new Point(50, 60), new Point(70, 80)))))
				.isEqualTo(json);
	}

	@Test // GH-4950
	void shouldSerializeGeoJsonPolygonCorrectly() throws IOException {

		String json = "{\"type\":\"Polygon\",\"coordinates\":[[[100.0,0.0],[101.0,0.0],[101.0,1.0],[100.0,1.0],[100.0,0.0]]]}";

		assertThat(mapper.writeValueAsString(new GeoJsonPolygon(
				Arrays.asList(new Point(100, 0), new Point(101, 0), new Point(101, 1), new Point(100, 1), new Point(100, 0)))))
				.isEqualTo(json);
	}

	@Test // GH-4950
	void shouldSerializeGeoJsonMultiPolygonCorrectly()
			throws IOException {

		String json="{\"type\":\"MultiPolygon\",\"coordinates\":["
				+"[[[102.0,2.0],[103.0,2.0],[103.0,3.0],[102.0,3.0],[102.0,2.0]]],"
				+"[[[100.0,0.0],[101.0,0.0],[101.0,1.0],[100.0,1.0],[100.0,0.0]]],"
				+"[[[100.2,0.2],[100.8,0.2],[100.8,0.8],[100.2,0.8],[100.2,0.2]]]"//
				+"]}";

		assertThat(mapper.writeValueAsString(new GeoJsonMultiPolygon(Arrays.asList(
				new GeoJsonPolygon(Arrays.asList(new Point(102, 2), new Point(103, 2), new Point(103, 3), new Point(102, 3),
						new Point(102, 2))),
				new GeoJsonPolygon(Arrays.asList(new Point(100, 0), new Point(101, 0), new Point(101, 1), new Point(100, 1),
						new Point(100, 0))),
				new GeoJsonPolygon(Arrays.asList(new Point(100.2, 0.2), new Point(100.8, 0.2), new Point(100.8, 0.8),
						new Point(100.2, 0.8), new Point(100.2, 0.2))))))).isEqualTo(json);

	}
}
