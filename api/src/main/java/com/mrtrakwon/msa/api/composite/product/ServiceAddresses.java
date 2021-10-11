package com.mrtrakwon.msa.api.composite.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class ServiceAddresses {
	private final String cmp;
	private final String pro;
	private final String rev;
	private final String rec;
}
