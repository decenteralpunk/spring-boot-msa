package com.mrtrakwon.msa.composite.product.services;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;

import com.mrtrakwon.msa.api.composite.product.ProductAggregate;
import com.mrtrakwon.msa.api.composite.product.ProductCompositeService;
import com.mrtrakwon.msa.api.composite.product.RecommendationSummary;
import com.mrtrakwon.msa.api.composite.product.ReviewSummary;
import com.mrtrakwon.msa.api.composite.product.ServiceAddresses;
import com.mrtrakwon.msa.api.core.product.Product;
import com.mrtrakwon.msa.api.core.recommendation.Recommendation;
import com.mrtrakwon.msa.api.core.review.Review;
import com.mrtrakwon.msa.util.http.ServiceUtil;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class ProductCompositeServiceImpl implements ProductCompositeService {

	private final static Logger logger = LoggerFactory.getLogger(ProductCompositeService.class);
	private final ServiceUtil serviceUtil;

	private final ProductCompositeIntegration productCompositeIntegration;

	@Override
	public ProductAggregate getProduct(int productId) {
		Product product = this.productCompositeIntegration.getProduct(productId);
		List<Recommendation> recommendations = this.productCompositeIntegration.getRecommendations(productId);
		List<Review> reviews = this.productCompositeIntegration.getReviews(productId);

		return createProductAggregate(product, recommendations, reviews, serviceUtil.getServiceAddress());
	}
	private ProductAggregate createProductAggregate(Product product, List<Recommendation> recommendations, List<Review> reviews, String serviceAddress) {

		// 1. Setup product info
		int productId = product.getProductId();
		String name = product.getName();
		int weight = product.getWeight();

		// 2. Copy summary recommendation info, if available
		List<RecommendationSummary> recommendationSummaries = (recommendations == null) ? null :
			recommendations.stream()
				.map(r -> new RecommendationSummary(r.getRecommendationId(), r.getAuthor(), r.getRate()))
				.collect(Collectors.toList());

		// 3. Copy summary review info, if available
		List<ReviewSummary> reviewSummaries = (reviews == null)  ? null :
			reviews.stream()
				.map(r -> new ReviewSummary(r.getReviewId(), r.getAuthor(), r.getSubject()))
				.collect(Collectors.toList());

		// 4. Create info regarding the involved microservices addresses
		String productAddress = product.getServiceAddress();
		String reviewAddress = (reviews != null && reviews.size() > 0) ? reviews.get(0).getServiceAddress() : "";
		String recommendationAddress = (recommendations != null && recommendations.size() > 0) ? recommendations.get(0).getServiceAddress() : "";
		ServiceAddresses serviceAddresses = new ServiceAddresses(serviceAddress, productAddress, reviewAddress, recommendationAddress);

		return new ProductAggregate(productId, name, weight, recommendationSummaries, reviewSummaries, serviceAddresses);
	}
}

