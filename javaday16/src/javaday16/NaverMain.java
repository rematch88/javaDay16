package javaday16;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class NaverMain {

	public static void main(String[] args) {
		//필요한 HTML을 다운로드: https://www.naver.com에서 다운로드
		String html = null;
		try {
			//다운로드 받을 URL 실행
			//URL에 한글이 있드면 URLEncoder.encoder 이용해서 인코딩을 해야 합니다.
			String addr = "https://www.naver.com";
			URL url = new URL(addr);
			
			//연결
			HttpURLConnection con = (HttpURLConnection)url.openConnection();
			con.setConnectTimeout(30000);
			con.setUseCaches(false);
			
			//데이터를 다운로드
			StringBuilder sb = new StringBuilder();
			BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
			while(true) {
				String line = br.readLine();
				if(line == null) {
					break;
				}
				sb.append(line + "\n");
			}
			
			//사용한 스트림 정리
			br.close();
			con.disconnect();
			//데이터 확인
			html = sb.toString();
			//System.out.println(html);
			
			
		}catch(Exception e) {
			System.out.println("다운로드 예외" + e.getMessage());
			e.printStackTrace();
		}
		
		
		if(html == null) {
			System.out.println("다운로드 받은 문자열이 없습니다.");
			return;
		}
		
		//HTML 파싱을 해서 원라는 자료구조를 만들기
		try {
			//문자열을 메모리에 DOM(Document Object Model)으로 펼치기
			Document document = Jsoup.parse(html);
			
			
			//span 태그 가져오기 - 태그는 중복될 수 있으므로 여러 개 리턴
			/*
			Elements span = document.getElementsByTag("span");
			//가져온 데이터를 순회하면서 출력
			for(int i=0; i < span.size(); i=i+1) {
				Element element = span.get(i);
				System.out.println(element.text());
				
			}
			*/
			
			
			//클래스 이름을 이용해서 가져오기
			/*
			Elements span = document.getElementsByClass("an_txt");
			//가져온 데이터를 순회하면서 출력
			for(int i=0; i < span.size(); i=i+1) {
				Element element = span.get(i);
				System.out.println(element.text());
			}
			*/
			
			//선택자 이용
			Elements span = document.select("#PM_ID_serviceNavi > li:nth-child(9) > a > span.an_txt");
			//가져온 데이터를 순회하면서 출력
			for(int i=0; i < span.size(); i=i+1) {
				Element element = span.get(i);
				//text() 대신에 getAttr(String key)를 이용하면 속성의 값을 가져옴
				System.out.println(element.text());
			}
			
			
		}catch(Exception e) {
			System.out.println("파싱 예외" + e.getMessage());
			e.printStackTrace();
		}
		

	}

}
