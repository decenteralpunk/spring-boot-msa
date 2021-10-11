package com.mrtrkwon.msa.recommendation.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import com.mrtrakwon.msa.api.core.recommendation.Recommendation;
import com.mrtrakwon.msa.api.core.recommendation.RecommendationService;
import com.mrtrakwon.msa.util.exceptions.InvalidInputException;
import com.mrtrakwon.msa.util.http.ServiceUtil;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class RecommendationServiceImpl implements RecommendationService {

	private final static Logger logger = LoggerFactory.getLogger(RecommendationServiceImpl.class);
	private final ServiceUtil serviceUtil;

	@Override
	public List<Recommendation> getRecommendations(int productId) {
		if( productId < 1)
			throw new InvalidInputException("invalid productId" + productId);

		if (productId == 113) {
			logger.debug("No Recommendations found for productId : {}", productId);
			return Collections.EMPTY_LIST;
		}

		List<Recommendation> list = new ArrayList<>();
		list.add(new Recommendation(productId, 1, "Author 1", 1, "Content 1", serviceUtil.getServiceAddress()));
		list.add(new Recommendation(productId, 2, "Author 2", 2, "Content 2", serviceUtil.getServiceAddress()));
		list.add(new Recommendation(productId, 3, "Author 3", 3, "Content 3", serviceUtil.getServiceAddress()));

		logger.debug("/recommendation response size: {}", list.size());

		return list;

	}
}
