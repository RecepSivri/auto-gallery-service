package com.recepsivri.galeri.common;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

class ValueObjectsTest {

	@Test
	void plateIsNormalized() {
		assertThat(new Plate("34 abc 123")).isEqualTo(new Plate("34ABC123"));
	}

	@Test
	void vinMustBe17Chars() {
		assertThatThrownBy(() -> new Vin("ABC")).isInstanceOf(IllegalArgumentException.class);
	}

	@Test
	void moneyWithDifferentScaleIsEqual() {
		assertThat(new Money(new BigDecimal("2.0"), "TRY")).isEqualTo(new Money(new BigDecimal("2.00"), "TRY"));
	}

	@Test
	void negativeMoneyRejected() {
		assertThatThrownBy(() -> new Money(new BigDecimal("-1"), "TRY")).isInstanceOf(IllegalArgumentException.class);
	}

	@Test
	void validVinIsAccepted() {
		assertThat(new Vin("wvwzzz1jzxw000001").value()).isEqualTo("WVWZZZ1JZXW000001");
	}

	@Test
	void vinRejectsForbiddenLetters() {
		assertThatThrownBy(() -> new Vin("WVWZZZ1JZXW00000I")).isInstanceOf(IllegalArgumentException.class);
	}

	@Test
	void moneyCompareToRejectsDifferentCurrency() {
		assertThatThrownBy(() -> new Money(BigDecimal.ONE, "TRY").compareTo(new Money(BigDecimal.ONE, "EUR")))
				.isInstanceOf(IllegalArgumentException.class);
	}

	@Test
	void plateRejectsInvalidProvinceCode() {
		assertThatThrownBy(() -> new Plate("99 ABC 123")).isInstanceOf(IllegalArgumentException.class);
	}
}
