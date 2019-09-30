/**
 * Copyright (c) 2018-present, http://a2-solutions.eu
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See
 * the License for the specific language governing permissions and limitations under the License.
 */

package eu.solutions.a2.cdc.oracle.standalone.avro;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class AvroSchema implements Serializable {

	private static final long serialVersionUID = 6661643576035090070L;

	private String type;
	private boolean optional;
	@JsonInclude(Include.NON_NULL)
	private Integer version;
	@JsonInclude(Include.NON_NULL)
	private String field;
	@JsonInclude(Include.NON_NULL)
	private String name;
	@JsonInclude(Include.NON_NULL)
	private List<AvroSchema> fields;

	public AvroSchema() {}

	private AvroSchema(final String type, final boolean optional) {
		this.type = type;
		this.optional = optional;
	}

	public static AvroSchema STRUCT_MANDATORY() {
		return new AvroSchema("struct", false);
	}
	public static AvroSchema INT8_MANDATORY() {
		return new AvroSchema("int8", false);
	}
	public static AvroSchema INT16_MANDATORY() {
		return new AvroSchema("int16", false);
	}
	public static AvroSchema INT32_MANDATORY() {
		return new AvroSchema("int32", false);
	}
	public static AvroSchema INT64_MANDATORY() {
		return new AvroSchema("int64", false);
	}
	public static AvroSchema FLOAT32_MANDATORY() {
		return new AvroSchema("float32", false);
	}
	public static AvroSchema FLOAT64_MANDATORY() {
		return new AvroSchema("float64", false);
	}
	public static AvroSchema STRING_MANDATORY() {
		return new AvroSchema("string", false);
	}
	public static AvroSchema BOOLEAN_MANDATORY() {
		return new AvroSchema("boolean", false);
	}
	public static AvroSchema BYTES_MANDATORY() {
		return new AvroSchema("bytes", false);
	}
	public static AvroSchema DATE_MANDATORY() {
		AvroSchema schema = INT32_MANDATORY();
		schema.setName("org.apache.kafka.connect.data.Date");
		schema.setVersion(1);
		return schema;
	}
	public static AvroSchema TIMESTAMP_MANDATORY() {
		AvroSchema schema = INT64_MANDATORY();
		schema.setName("org.apache.kafka.connect.data.Timestamp");
		schema.setVersion(1);
		return schema;
	}

	public static AvroSchema STRUCT_OPTIONAL() {
		return new AvroSchema("struct", true);
	}
	public static AvroSchema INT8_OPTIONAL() {
		return new AvroSchema("int8", true);
	}
	public static AvroSchema INT16_OPTIONAL() {
		return new AvroSchema("int16", true);
	}
	public static AvroSchema INT32_OPTIONAL() {
		return new AvroSchema("int32", true);
	}
	public static AvroSchema INT64_OPTIONAL() {
		return new AvroSchema("int64", true);
	}
	public static AvroSchema FLOAT32_OPTIONAL() {
		return new AvroSchema("float32", true);
	}
	public static AvroSchema FLOAT64_OPTIONAL() {
		return new AvroSchema("float64", true);
	}
	public static AvroSchema STRING_OPTIONAL() {
		return new AvroSchema("string", true);
	}
	public static AvroSchema BOOLEAN_OPTIONAL() {
		return new AvroSchema("boolean", true);
	}
	public static AvroSchema BYTES_OPTIONAL() {
		return new AvroSchema("bytes", true);
	}
	public static AvroSchema DATE_OPTIONAL() {
		AvroSchema schema = INT32_OPTIONAL();
		schema.setName("org.apache.kafka.connect.data.Date");
		schema.setVersion(1);
		return schema;
	}
	public static AvroSchema TIMESTAMP_OPTIONAL() {
		AvroSchema schema = INT64_OPTIONAL();
		schema.setName("org.apache.kafka.connect.data.Timestamp");
		schema.setVersion(1);
		return schema;
	}

	public void initFields() {
		this.fields = new ArrayList<AvroSchema>();
	}

	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

	public boolean isOptional() {
		return optional;
	}
	public void setOptional(boolean optional) {
		this.optional = optional;
	}

	public Integer getVersion() {
		return version;
	}
	public void setVersion(Integer version) {
		this.version = version;
	}

	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public List<AvroSchema> getFields() {
		return fields;
	}
	public void setFields(List<AvroSchema> fields) {
		this.fields = fields;
	}


}
