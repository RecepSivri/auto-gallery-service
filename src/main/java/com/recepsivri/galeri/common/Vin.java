package com.recepsivri.galeri.common;

import java.util.Locale;
import java.util.regex.Pattern;

/**
 * Şasi numarası: 17 karakter, büyük harf, I/O/Q yasak.
 */
public record Vin(String value) {

	private static final Pattern FORMAT = Pattern.compile("[A-HJ-NPR-Z0-9]{17}");

	public Vin {
		if (value == null) {
			throw new IllegalArgumentException("VIN boş olamaz");
		}
		value = value.trim().toUpperCase(Locale.ROOT);
		if (!FORMAT.matcher(value).matches()) {
			throw new IllegalArgumentException("Geçersiz VIN: " + value);
		}
	}
}
