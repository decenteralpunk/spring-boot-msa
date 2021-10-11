package com.mrtrakwon.msa.product.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;

import com.mrtrakwon.msa.api.core.product.Product;
import com.mrtrakwon.msa.api.core.product.ProductService;
import com.mrtrakwon.msa.util.exceptions.InvalidInputException;
import com.mrtrakwon.msa.util.exceptions.NotFoundException;
import com.mrtrakwon.msa.util.http.ServiceUtil;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class ProductServiceImpl implements ProductService {

	private final static Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);
	private final ServiceUtil serviceUtil;

	@Override
	public Product getProduct(int productId) {

		if( productId < 1)
			throw new InvalidInputException("invalid input : " +productId);
		if (productId == 13)
			throw new NotFoundException("No product found");

		return new Product(productId, "name-"+productId, 123, serviceUtil.getServiceAddress());
	}

}
