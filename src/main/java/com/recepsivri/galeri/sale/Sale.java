package com.recepsivri.galeri.sale;

import com.recepsivri.galeri.common.Money;
import com.recepsivri.galeri.customer.Customer;
import com.recepsivri.galeri.vehicle.Vehicle;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.Instant;

/** Satış. Bir araç yalnızca bir kez satılabilir: vehicle_id kolonu UNIQUE (V5). */
@Entity
@Table(name = "sale")
public class Sale {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	// Bir satış bir araca ait, bir araç en fazla bir satışa. Yabancı anahtar bu tabloda.
	@OneToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "vehicle_id", nullable = false, unique = true)
	private Vehicle vehicle;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "customer_id", nullable = false)
	private Customer customer;

	@Embedded
	@AttributeOverrides({
			@AttributeOverride(name = "amount", column = @Column(name = "sale_price_amount", nullable = false)),
			@AttributeOverride(name = "currency", column = @Column(name = "sale_price_currency", nullable = false, length = 3))
	})
	private Money salePrice;

	@Enumerated(EnumType.STRING)
	@Column(name = "payment_type", nullable = false, length = 20)
	private PaymentType paymentType;

	@Column(name = "sold_by", nullable = false, length = 64)
	private String soldBy;

	@Column(name = "sold_at", nullable = false, updatable = false)
	private Instant soldAt = Instant.now();

	/** Hibernate için. */
	protected Sale() {
	}

	public Sale(Vehicle vehicle, Customer customer, Money salePrice, PaymentType paymentType, String soldBy) {
		this.vehicle = vehicle;
		this.customer = customer;
		this.salePrice = salePrice;
		this.paymentType = paymentType;
		this.soldBy = soldBy;
	}

	public Long getId() {
		return id;
	}

	public Money getSalePrice() {
		return salePrice;
	}

	public PaymentType getPaymentType() {
		return paymentType;
	}

	@Override
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof Sale that)) {
			return false;
		}
		return id != null && id.equals(that.id);
	}

	@Override
	public int hashCode() {
		return getClass().hashCode();
	}
}
