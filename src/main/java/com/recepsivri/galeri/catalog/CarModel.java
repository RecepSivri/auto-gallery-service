package com.recepsivri.galeri.catalog;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/** Marka altındaki model. İlişki LAZY: 5. adımda N+1 deneyinde bunu kullanacağız. */
@Entity
@Table(name = "car_model")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CarModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "brand_id", nullable = false)
	private Brand brand;

	@Column(nullable = false, length = 100)
	private String name;

	public CarModel(Brand brand, String name) {
		this.brand = brand;
		this.name = name;
	}

	@Override
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof CarModel that)) {
			return false;
		}
		return id != null && id.equals(that.id);
	}

	@Override
	public int hashCode() {
		return getClass().hashCode();
	}
}
