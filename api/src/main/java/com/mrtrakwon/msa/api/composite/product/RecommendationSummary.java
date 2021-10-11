package com.mrtrakwon.msa.api.composite.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@AllArgsConstructor
public class RecommendationSummary {
	private final int recommendationId;
	private final String author;
	private final int rate;
}
