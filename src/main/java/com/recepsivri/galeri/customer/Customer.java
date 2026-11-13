package com.recepsivri.galeri.customer;

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

@Entity
@Table(name = "customer")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "full_name", nullable = false, length = 150)
	private String fullName;

	@Column(length = 30)
	private String phone;

	@Column(length = 150)
	private String email;

	/** Müşteriden sorumlu satış danışmanı (8. adımda Keycloak kullanıcı kimliği). */
	@Column(name = "owner_id", nullable = false, length = 64)
	private String ownerId;

	@Column(name = "created_at", nullable = false, updatable = false)
	private Instant createdAt = Instant.now();

	public Customer(String fullName, String phone, String email, String ownerId) {
		this.fullName = fullName;
		this.phone = phone;
		this.email = email;
		this.ownerId = ownerId;
	}

	@Override
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof Customer that)) {
			return false;
		}
		return id != null && id.equals(that.id);
	}

	@Override
	public int hashCode() {
		return getClass().hashCode();
	}
}
