package com.recepsivri.galeri.kata;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class KatasTest {

	@Test
	void firstNonRepeating() {
		assertThat(Katas.firstNonRepeatingIndex("swiss")).isEqualTo(1);
		assertThat(Katas.firstNonRepeatingIndex("aabb")).isEqualTo(-1);
		assertThat(Katas.firstNonRepeatingIndex("")).isEqualTo(-1);
		assertThat(Katas.firstNonRepeatingIndex("a")).isZero();
	}

	@Test
	void palindromePlate() {
		assertThat(Katas.isPalindromePlate("34 ABC CBA 43")).isTrue();
		assertThat(Katas.isPalindromePlate("34 abc cba 43")).isTrue();
		assertThat(Katas.isPalindromePlate("34 ABC 123")).isFalse();
		assertThat(Katas.isPalindromePlate("")).isTrue();
	}
}
