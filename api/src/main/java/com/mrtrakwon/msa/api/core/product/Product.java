package com.mrtrakwon.msa.api.core.product;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Product {
	private final int productId;
	private final String name;
	private final int weight;
	private final String serviceAddress;

	public Product() {
		this.productId = 0;
		this.name = null;
		this.weight = 0;
		this.serviceAddress = null;
	}
}
