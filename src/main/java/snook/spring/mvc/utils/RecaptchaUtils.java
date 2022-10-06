package snook.spring.mvc.utils;

import java.io.IOException;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("grcp")
public class RecaptchaUtils {
		
	public boolean checkCaptcha(String grcp) throws IOException, ParseException {
		
		String VERIFY_URL = "https://www.google.com/recaptcha/api/siteverify";				
		String params = "?secret=6Lcz81oiAAAAAEbZjbMrMsdgTitrDqL6Is3x7jaI" + "&response=" + grcp;		
		String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/105.0.0.0 Safari/537.36";
		
		Logger log = LoggerFactory.getLogger(getClass());
		
		
		// httpclient 객체 생성
		CloseableHttpClient req = HttpClients.createDefault();
		
		
		// 요청할 URL을 Get 메서드로 
		HttpGet get = new HttpGet(VERIFY_URL + params);
		

		// 요청 header 정의
		get.addHeader("User-Agent", USER_AGENT);
		get.addHeader("Content-type", "application/json");
		
		// httpclient 객체 생성
		CloseableHttpResponse res = req.execute(get);
		
		// 실행 여부 확인 (응답코드: 200-정상실행, 404-505-실행실패)
		// log.info(res.getCode());
		
		// 응답 결과 확인
		String result = EntityUtils.toString(res.getEntity());
		// log.info(result);
		
		// 결과 문자열을 JSON 객체로 변환하고 
		// success 키의 값을 알아냄 : json객체명.getXxx(키)
			JSONObject json = new JSONObject(result);
			Boolean success = json.getBoolean("success");
			System.out.println(success);	
			
			//httpclient 객체 닫기
		req.close();
		
		return success;
	}
	
}
