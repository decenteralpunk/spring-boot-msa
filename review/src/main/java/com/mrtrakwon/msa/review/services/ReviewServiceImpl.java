package com.mrtrakwon.msa.review.services;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import com.mrtrakwon.msa.api.core.review.Review;
import com.mrtrakwon.msa.api.core.review.ReviewService;
import com.mrtrakwon.msa.util.exceptions.InvalidInputException;
import com.mrtrakwon.msa.util.http.ServiceUtil;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class ReviewServiceImpl implements ReviewService {

	private final static Logger logger = LoggerFactory.getLogger(ReviewServiceImpl.class);
	private final ServiceUtil serviceUtil;

	@Override
	public List<Review> getReviews(int productId) {
		if (productId < 1) throw new InvalidInputException("Invalid productId: " + productId);

		if (productId == 213) {
			logger.debug("No reviews found for productId: {}", productId);
			return  new ArrayList<>();
		}

		List<Review> list = new ArrayList<>();
		list.add(new Review(productId, 1, "Author 1", "Subject 1", "Content 1", serviceUtil.getServiceAddress()));
		list.add(new Review(productId, 2, "Author 2", "Subject 2", "Content 2", serviceUtil.getServiceAddress()));
		list.add(new Review(productId, 3, "Author 3", "Subject 3", "Content 3", serviceUtil.getServiceAddress()));

		logger.debug("/reviews response size: {}", list.size());

		return list;
	}
}
