package com.synectiks.asset.aws;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.StringTokenizer;

public class Utils {

	public static void main(String[] a) throws IOException {
//		System.out.println("helo");
		String str = readFileWithBufferedReader();
		byte bary[] = str.getBytes();
		String nStr = new String(bary).replaceAll("\\\\", "");
//		System.out.println("New string : "+nStr);
		String ary[] = nStr.split("}");
		for(int i=0;i <ary.length;i++) {
//			System.out.println(ary[i]);
			String x = ary[i];
			if(i == 0 ) {
				x = x.replace("\"[", "").concat("}");
			}else if(i == ary.length - 1) {
				x = x.replace("]\"", "");
			}else {
				x = x.replaceFirst(",", "").concat("}");
			}
			if(x.trim().length()>0) {
				System.out.println(i +" -> "+ x);
			}
		}
	}
	
	public static void readStringWithStream() throws IOException {
		String text = readFileWithBufferedReader();
		
		InputStream inputStream = new ByteArrayInputStream(text.getBytes(Charset.forName("UTF-8")));
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String output = bufferedReader.readLine();
        while (output != null) {
            System.out.println(output);
            output = bufferedReader.readLine();
        }
	}
	
	public static String readFileWithBufferedReader() throws IOException {
		String filePath = "C:\\Users\\admin\\Desktop\\honda_defect.txt";
//		File file = new File(filePath);
		BufferedReader reader = new BufferedReader(new FileReader(filePath));
		StringBuilder stringBuilder = new StringBuilder();
		char[] buffer = new char[10];
		while (reader.read(buffer) != -1) {
			stringBuilder.append(new String(buffer));
			buffer = new char[10];
		}
		reader.close();

		String content = stringBuilder.toString();
		System.out.println("File content : "+content);
		
//		IntStream ins = "1234_abcd".chars();
//		ins.forEach(p -> System.out.println(p));
		return content;
	}
}
