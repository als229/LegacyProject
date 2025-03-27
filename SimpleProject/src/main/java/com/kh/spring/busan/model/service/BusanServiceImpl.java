package com.kh.spring.busan.model.service;

import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BusanServiceImpl implements BusanService {

	private String SERVICE_KEY = "scPtfDcubHnmICsvSbBfelpKNaDtoF3yo4h3TDa%2BPkjUtDr4KfjvhaHoZhNKCBNDr%2F3Ht8eRT7HVBewcCRHCIA%3D%3D";
	
	@Override
	public String requestGetBusan(int pageNo) {
		
		if(pageNo < 1) { pageNo = 1; }
		
		StringBuilder sb = new StringBuilder();
		sb.append("http://apis.data.go.kr/6260000/FoodService/getFoodKr");
		sb.append("?serviceKey=" + SERVICE_KEY);
		sb.append("&pageNo=" + pageNo);
		sb.append("&numOfRows=9");
		sb.append("&resultType=json");
		
		URI uri = null;
		
		try {
			uri = new URI(sb.toString());
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		
		String apiResponseData = new RestTemplate().getForObject(uri, String.class);
		
//		log.info("Api 확인 {}", apiResponseData); 잘 나온다.
		
		return apiResponseData;
	}

	@Override
	public String requestGetBusanDetail(int pk) {
		return null;
	}

}
