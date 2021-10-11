package com.mrtrakwon.msa.composite.product.services;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mrtrakwon.msa.api.core.product.Product;
import com.mrtrakwon.msa.api.core.product.ProductService;
import com.mrtrakwon.msa.api.core.recommendation.Recommendation;
import com.mrtrakwon.msa.api.core.recommendation.RecommendationService;
import com.mrtrakwon.msa.api.core.review.Review;
import com.mrtrakwon.msa.api.core.review.ReviewService;
import com.mrtrakwon.msa.util.exceptions.InvalidInputException;
import com.mrtrakwon.msa.util.exceptions.NotFoundException;
import com.mrtrakwon.msa.util.http.HttpErrorInfo;

@Component
public class ProductCompositeIntegration implements ProductService, ReviewService, RecommendationService {

	private final static Logger logger = LoggerFactory.getLogger(ProductCompositeIntegration.class);

	private final String SERVICE_URL_TEMPLATE = "http://%s:%d/%s";
	private final RestTemplate restTemplate;
	private final ObjectMapper objectMapper;

	private final String productServiceUrl;
	private final String recommendationServiceUrl;
	private final String reviewServiceUrl;

	@Autowired
	public ProductCompositeIntegration(
		RestTemplate restTemplate,
		ObjectMapper objectMapper,

		@Value("${app.product.host}") String productServiceHost,
		@Value("${app.product.port}") int productServicePort,

		@Value("${app.recommendation.host}") String recommendationServiceHost,
		@Value("${app.recommendation.port}") int recommendationServicePort,

		@Value("${app.review.host}") String reviewServiceHost,
		@Value("${app.review.port}") int reviewServicePort
		) {

		this.restTemplate = restTemplate;
		this.objectMapper = objectMapper;

		this.productServiceUrl 			= String.format(this.SERVICE_URL_TEMPLATE, productServiceHost, productServicePort, "product/");
		this.recommendationServiceUrl 	= String.format(this.SERVICE_URL_TEMPLATE, recommendationServiceHost, recommendationServicePort, "/recommendation?productId=");
		this.reviewServiceUrl 			= String.format(this.SERVICE_URL_TEMPLATE, reviewServiceHost, reviewServicePort, "/review?productId=");
	}

	@Override
	public Product getProduct(int productId) {
		try {
			final String url = productServiceUrl + productId;
			final Product product = restTemplate.getForObject(url, Product.class);
			return product;
		} catch (HttpClientErrorException e) {
			switch (e.getStatusCode()) {
				case NOT_FOUND:
					throw new NotFoundException(getErrorMessage(e));

				case UNPROCESSABLE_ENTITY:
					throw new InvalidInputException(getErrorMessage(e));

				default:
					logger.warn("Got a unexpected HTTP error: {}, will rethrow it", e.getStatusCode());
					logger.warn("Error body: {}", e.getResponseBodyAsString());
					throw e;
			}
		}
	}

	private String getErrorMessage(HttpClientErrorException ex) {
		try {
			return this.objectMapper.readValue(ex.getResponseBodyAsString(), HttpErrorInfo.class).getMessage();
		} catch (IOException ioex) {
			return ex.getMessage();
		}
	}

	@Override
	public List<Recommendation> getRecommendations(int productId) {
		try {
			final String url = recommendationServiceUrl + productId;
			final List<Recommendation> recommendations
				= restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<Recommendation>>(){}).getBody();

			return recommendations;
		} catch (Exception e) {
			return Collections.emptyList();
		}
	}

	@Override
	public List<Review> getReviews(int productId) {
		try {
			final String url = reviewServiceUrl + productId;
			final List<Review> reviews
				= restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<Review>>(){}).getBody();

			return reviews;
		} catch (Exception e) {
			return Collections.emptyList();
		}

	}
}
