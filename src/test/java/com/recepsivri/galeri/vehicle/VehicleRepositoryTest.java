package com.recepsivri.galeri.vehicle;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.recepsivri.galeri.TestcontainersConfiguration;
import com.recepsivri.galeri.catalog.Brand;
import com.recepsivri.galeri.catalog.BrandRepository;
import com.recepsivri.galeri.catalog.CarModel;
import com.recepsivri.galeri.catalog.CarModelRepository;
import com.recepsivri.galeri.common.Money;
import com.recepsivri.galeri.common.Plate;
import com.recepsivri.galeri.common.Vin;
import jakarta.persistence.EntityManager;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;
import org.springframework.dao.DataIntegrityViolationException;

/**
 * Gerçek Postgres (Testcontainers) üzerinde: Flyway migration'ları çalışır, Hibernate validate eder.
 * Her test kendi transaction'ında koşar ve sonunda geri alınır (rollback).
 */
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(TestcontainersConfiguration.class)
class VehicleRepositoryTest {

	@Autowired
	BrandRepository brands;
	@Autowired
	CarModelRepository models;
	@Autowired
	VehicleRepository vehicles;
	@Autowired
	EntityManager em;

	private CarModel savedModel() {
		Brand brand = brands.save(new Brand("Toyota"));
		return models.save(new CarModel(brand, "Corolla"));
	}

	private Vehicle vehicle(CarModel model, String vin) {
		return new Vehicle(model, new Vin(vin), new Plate("34 abc 123"), 2022, 15000,
				FuelType.HYBRID, Transmission.AUTOMATIC, "Beyaz", new Money(new BigDecimal("1250000"), "TRY"));
	}

	@Test
	void saveAndReadBack() {
		Vehicle v = vehicle(savedModel(), "JTDKB20U793000001");
		v.getFeatures().add("Sunroof");
		vehicles.saveAndFlush(v);
		em.clear(); // 1. seviye cache'i temizle: aşağıdaki find gerçekten SELECT atsın

		Vehicle found = vehicles.findById(v.getId()).orElseThrow();

		assertThat(found.getPlate()).isEqualTo("34ABC123");
		assertThat(found.getStatus()).isEqualTo(VehicleStatus.IN_STOCK);
		assertThat(found.getListPrice()).isEqualTo(new Money(new BigDecimal("1250000.00"), "TRY"));
		assertThat(found.getFeatures()).containsExactly("Sunroof");
		assertThat(found.getVersion()).isZero();
	}

	@Test
	void duplicateVinIsRejectedByDatabase() {
		CarModel model = savedModel();
		vehicles.saveAndFlush(vehicle(model, "JTDKB20U793000002"));

		assertThatThrownBy(() -> vehicles.saveAndFlush(vehicle(model, "JTDKB20U793000002")))
				.isInstanceOf(DataIntegrityViolationException.class);
	}
}
