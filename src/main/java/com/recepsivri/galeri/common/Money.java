package com.recepsivri.galeri.common;

import jakarta.persistence.Embeddable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.regex.Pattern;

/**
 * Para değer nesnesi (immutable record).
 * - amount null/negatif olamaz, 2 basamağa yuvarlanır (HALF_EVEN) -> 2.0 ile 2.00 aynı değere gelir, equals tutar.
 * - currency 3 harfli büyük harf (TRY, EUR...).
 * - Comparable: sadece aynı para biriminde karşılaştırılır.
 */
@Embeddable
public record Money(BigDecimal amount, String currency) implements Comparable<Money> {

	private static final Pattern CURRENCY = Pattern.compile("[A-Z]{3}");

	public Money {
		if (amount == null) {
			throw new IllegalArgumentException("amount boş olamaz");
		}
		if (amount.signum() < 0) {
			throw new IllegalArgumentException("amount negatif olamaz: " + amount);
		}
		if (currency == null || !CURRENCY.matcher(currency).matches()) {
			throw new IllegalArgumentException("currency 3 büyük harf olmalı: " + currency);
		}
		// record'da compact constructor sonunda parametreye atama alanı belirler
		amount = amount.setScale(2, RoundingMode.HALF_EVEN);
	}

	@Override
	public int compareTo(Money other) {
		if (!currency.equals(other.currency)) {
			throw new IllegalArgumentException("Farklı para birimleri karşılaştırılamaz: " + currency + " vs " + other.currency);
		}
		return amount.compareTo(other.amount);
	}
}
