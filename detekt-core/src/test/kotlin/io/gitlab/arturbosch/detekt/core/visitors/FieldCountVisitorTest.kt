package io.gitlab.arturbosch.detekt.core.visitors

import io.gitlab.arturbosch.detekt.core.NUMBER_OF_FIELDS_KEY
import io.gitlab.arturbosch.detekt.core.path
import io.gitlab.arturbosch.detekt.test.compileForTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class FieldCountVisitorTest {

	@Test
	fun defaultFieldCount() {
		val file = compileForTest(path.resolve("../fields/ClassWithFields.kt"))
		val count = with(file) {
			accept(FieldCountVisitor())
			getUserData(NUMBER_OF_FIELDS_KEY)
		}
		assertThat(count).isEqualTo(2)
	}
}