package com.recepsivri.galeri.vehicle;

import com.recepsivri.galeri.catalog.CarModel;
import com.recepsivri.galeri.common.Money;
import com.recepsivri.galeri.common.Plate;
import com.recepsivri.galeri.common.Vin;
import java.time.Instant;

import jakarta.persistence.*;

import java.util.LinkedHashSet;
import java.util.Set;

import static jakarta.persistence.FetchType.LAZY;

/**
 * Araç entity'si. SENİN PARÇAN: V3__vehicle.sql ile BİREBİR eşleşmeli (ddl-auto: validate kontrol eder).
 *
 * Yapılacaklar (her TODO'nun yanında ipucu var):
 *  1. Sınıfa @Entity ve @Table(name = "vehicle")
 *  2. id        -> @Id + @GeneratedValue(strategy = GenerationType.IDENTITY)   (Brand'e bak)
 *  3. carModel  -> @ManyToOne(fetch = LAZY, optional = false) + @JoinColumn(name = "car_model_id")
 *  4. vin       -> VARCHAR(17) UNIQUE. Vin record'unu String kolonuna çevirmek için
 *                  @Convert(converter = ...) YA DA şimdilik String tut ve Vin'i servis katmanında kullan.
 *                  (Kolay yol: String vin. Zor/öğretici yol: AttributeConverter<Vin,String>)
 *  5. plate     -> aynı mantık, kolon "plate" (nullable)
 *  6. productionYear -> kolon "production_year", mileageKm -> "mileage_km"
 *  7. fuelType, transmission, status -> @Enumerated(EnumType.STRING)  (ORDINAL KULLANMA, sebebini sen anlat)
 *  8. listPrice -> Money'yi @Embedded yap; @AttributeOverride ile kolonları
 *                  list_price_amount / list_price_currency'ye bağla
 *                  (Money bir record: Hibernate 7 record'ları @Embeddable olarak destekler -> Money'ye @Embeddable koymayı unutma)
 *  9. version   -> @Version  (optimistic lock; ne işe yaradığını 7. adımda göreceğiz)
 * 10. features -> @ElementCollection + @CollectionTable(name = "vehicle_feature", joinColumns = @JoinColumn(name = "vehicle_id"))
 *                 + @Column(name = "feature")
 * 11. equals/hashCode: id tabanlı (Brand'deki gibi). @Data KULLANMA.
 *
 * Bilerek deney: önce Lombok @Data ile yaz, toString'de carModel->... döngüsü / HashSet'te kayıp varlık
 * davranışını gör (docs/deneyler.md'ye not et), sonra id tabanlı güvenli sürüme geç.
 */
@Entity
@Table(name = "vehicle")
public class Vehicle {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, unique = true, length = 17)
	private String vin;

	@ManyToOne(fetch = LAZY, optional = false)
	@JoinColumn(name = "car_model_id", nullable = false)
	private CarModel carModel;

	@Column(name = "plate", length = 12)
	private String plate;

	@Column(name = "production_year", nullable = false)
	private int productionYear;
	@Column(name = "mileage_km", nullable = false)
	private int mileageKm;

	@Enumerated(EnumType.STRING)
	@Column(name = "fuel_type", nullable = false, length = 20)
	private FuelType fuelType;

	@Enumerated(EnumType.STRING)
	@Column(name = "transmission", nullable = false, length = 20)
	private Transmission transmission;

	@Column(name = "color", length = 30)
	private String color;

	// Money iki kolona açılır: list_price_amount ve list_price_currency
	@Embedded
	@AttributeOverrides({
			@AttributeOverride(name = "amount", column = @Column(name = "list_price_amount", nullable = false)),
			@AttributeOverride(name = "currency", column = @Column(name = "list_price_currency", nullable = false, length = 3))
	})
	private Money listPrice;
	@Enumerated(EnumType.STRING)
	@Column(name = "status", nullable = false, length = 20)
	private VehicleStatus status = VehicleStatus.IN_STOCK;

	// Optimistic lock: iki kişi aynı aracı aynı anda güncellerse ikincisi hata alır (7. adımda deneyeceğiz)
	@Version
	private Long version;
	@Column(name = "created_at", nullable = false, updatable = false)
	private Instant createdAt = Instant.now();

	// Ayrı tablo: vehicle_feature(vehicle_id, feature)
	@ElementCollection
	@CollectionTable(name = "vehicle_feature", joinColumns = @JoinColumn(name = "vehicle_id"))
	@Column(name = "feature", nullable = false, length = 60)
	private Set<String> features = new LinkedHashSet<>();

	/** Hibernate için boş constructor (dışarıdan kullanılmasın). */
	protected Vehicle() {
	}

	public Vehicle(CarModel carModel, Vin vin, Plate plate, int productionYear, int mileageKm,
			FuelType fuelType, Transmission transmission, String color, Money listPrice) {
		this.carModel = carModel;
		this.vin = vin.value();
		this.plate = plate == null ? null : plate.value();
		this.productionYear = productionYear;
		this.mileageKm = mileageKm;
		this.fuelType = fuelType;
		this.transmission = transmission;
		this.color = color;
		this.listPrice = listPrice;
	}

	public Long getId() {
		return id;
	}

	public String getVin() {
		return vin;
	}

	public String getPlate() {
		return plate;
	}

	public Long getVersion() {
		return version;
	}

	public Money getListPrice() {
		return listPrice;
	}

	public VehicleStatus getStatus() {
		return status;
	}

	public Set<String> getFeatures() {
		return features;
	}

	@Override
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof Vehicle that)) {
			return false;
		}
		return id != null && id.equals(that.id);
	}

	@Override
	public int hashCode() {
		return getClass().hashCode();
	}
}
