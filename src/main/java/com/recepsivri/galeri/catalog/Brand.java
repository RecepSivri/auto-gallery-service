package com.recepsivri.galeri.catalog;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Marka. Lombok'ta yalnızca güvenli anotasyonlar: @Data/@EqualsAndHashCode entity'de tehlikelidir.
 * equals/hashCode sadece id'ye bakar (JPA'da güvenli kalıp); kayıttan önce iki farklı nesne eşit sayılmaz.
 */
@Entity
@Table(name = "brand")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Brand {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, unique = true, length = 100)
	private String name;

	@Column(name = "created_at", nullable = false, updatable = false)
	private Instant createdAt = Instant.now();

	public Brand(String name) {
		this.name = name;
	}

	@Override
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof Brand that)) {
			return false;
		}
		return id != null && id.equals(that.id);
	}

	@Override
	public int hashCode() {
		return getClass().hashCode();
	}
}
