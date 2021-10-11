package com.mrtrakwon.msa.api.composite.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@AllArgsConstructor
public class ReviewSummary {
	private final int reviewId;
	private final String author;
	private final String subject;
}
