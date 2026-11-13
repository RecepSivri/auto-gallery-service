package com.recepsivri.galeri.common;

import java.util.Locale;
import java.util.regex.Pattern;

/**
 * Türkiye plakası. "34 abc 123" -> "34ABC123".
 * Record olduğu için immutable: HashSet içinde değeri değişip kaybolma sorunu yok.
 */
public record Plate(String value) {

	// il kodu (01-81) + 1-3 harf + 2-4 rakam
	private static final Pattern FORMAT = Pattern.compile("(0[1-9]|[1-7][0-9]|8[01])[A-Z]{1,3}[0-9]{2,4}");

	public Plate {
		if (value == null) {
			throw new IllegalArgumentException("Plaka boş olamaz");
		}
		// Locale.ROOT: Türkçe locale'de "i".toUpperCase() "İ" olur, bu klasik tuzak
		value = value.replaceAll("\\s+", "").toUpperCase(Locale.ROOT);
		if (!FORMAT.matcher(value).matches()) {
			throw new IllegalArgumentException("Geçersiz plaka: " + value);
		}
	}
}
