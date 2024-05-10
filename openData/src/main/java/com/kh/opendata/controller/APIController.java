package com.kh.opendata.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class APIController {
	public static final String SERVICE_KEY = "2fYCnjyLnJ8kSIxyupXYgCH%2BtWNXAFeWKxkfQfbtcWc3ZdW7MvWN4lqHdbBcLnSQ3QRR%2BAOr6Hzf5DLku1x1Fw%3D%3D";
	
	@ResponseBody
	@RequestMapping(value="air", produces="application/json; charset=UTF-8")
	public String getAirInfo(String location) throws IOException {
		String url = "http://apis.data.go.kr/B552584/ArpltnInforInqireSvc/getCtprvnRltmMesureDnsty";
		
		url += "?serviceKey=" + SERVICE_KEY;
		url += "&returnType=json";
		url += "&sidoName=" + URLEncoder.encode(location, "UTF-8"); //요청시 전달값에 한글이 있으면 인코딩 작업을 해줘야함!
		
//		System.out.println(url);
		
		// 자바코드로 서버에서 서버로 요청을 보내야함
		// ** HttpURLConnection 객체를 이용해서 요청을 보내자
		
		// 1. 요청하고자하는 url을 전달하면서 java.net.URL객체 생성
		URL requestURL = new URL(url);
			
		// 2. 만들어진 URL객체를 가지고 HttpURLConnection 객체 생성
		HttpURLConnection urlConnection = (HttpURLConnection)requestURL.openConnection();
			
		// 3. 요청에 필요한 Header 설정
		urlConnection.setRequestMethod("GET");
		
		// 4. 해당 api서버로 요청 보낸후 입력데이터 읽어오기
		BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
		String responseText = "";
		String line;
		while((line = br.readLine()) != null ) {
			responseText += line; 
		}
		
		br.close();
		urlConnection.disconnect();
		
		return responseText;
		

	}

}
