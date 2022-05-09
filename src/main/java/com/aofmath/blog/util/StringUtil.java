package com.aofmath.blog.util;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoField;
import java.util.Arrays;
import java.util.stream.Stream;

public class StringUtil {
	public static void main(String[] args) {
		
		System.out.println("================== 1.람다 표현식(Lambda Expression) ======================");
		/* 
		 * 1.람다 표현식(Lambda Expression) 
		 * 
		 * */
		
		new Thread(new Runnable() {
		    public void run() {
		        System.out.println("전통적인 방식의 일회용 스레드 생성");
		    }
		}).start();

		new Thread(()->{
		    System.out.println("람다 표현식을 사용한 일회용 스레드 생성");
		}).start();
		
		System.out.println("================== 2.스트림 API(Stream API) ======================");
		
		/* 
		 * 2.스트림 API(Stream API) 
		 * 
		 * */
		
		String[] arr = new String[]{"넷", "둘", "셋", "하나"};
		
		
		// 배열에서 스트림 생성
		Stream<String> stream1 = Arrays.stream(arr);
		stream1.forEach(e -> System.out.print(e + " "));
		System.out.println();
		
		
		// 배열의 특정 부분만을 이용한 스트림 생성
		Stream<String> stream2 = Arrays.stream(arr, 1, 3);
		stream2.forEach(e -> System.out.print(e + " "));
		
		
		System.out.println("================== 3.java.time 패키지 ======================");
		
		/* 
		 * 3.java.time 패키지 
		 * 
		 * */
		
		LocalDate today = LocalDate.now();
		System.out.println("올해는 " + today.getYear() + "년입니다.");
		System.out.println("이번달은 " + today.getMonthValue() + "월입니다.");
		System.out.println("오늘은 " + today.getDayOfWeek() + "입니다.");
		System.out.println("오늘은 1년 중 " + today.get(ChronoField.DAY_OF_YEAR) + "일째 날입니다.");
		
		LocalTime present = LocalTime.now();
		System.out.println("현재 시각은 " + present.getHour() + "시 " + present.getMinute() + "분입니다.");
	}
}
