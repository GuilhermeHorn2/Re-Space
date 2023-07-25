package misc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;


public class main_misc {
	
	
	public static final int MAX = 1_000_000;
	
	public static HashMap<String,Integer> dictionary = new HashMap<>();
	
	public static String doc = "";
	
	public static void main(String[] args) {
		
		
	doc = "jesslookedjustliketimherbrother";
	
	dictionary.put("looked",0);
	dictionary.put("just",1);
	dictionary.put("like",2);
	dictionary.put("her",3);
	dictionary.put("brother",4);
	
	System.out.println(format_doc());
		
	}
	private static List<String> words_in_order(){
		
		/*return a list with words in order from biggest length to smallest
		 */
		
		List<String> words = new ArrayList<>();
		
		
		List<Integer> lens = new ArrayList<>();
		HashMap<Integer,List<String>> lens_map = new HashMap<>();
		for(Map.Entry<String,Integer> entry : dictionary.entrySet()){
			String w = entry.getKey();
			lens.add(w.length());
			if(lens_map.containsKey(w.length())){
				lens_map.get(w.length()).add(w);
			}
			else {
				List<String> tmp = new ArrayList<>(Arrays.asList(w));
				lens_map.put(w.length(),tmp);
			}
		}
		
		Collections.sort(lens);
		
		int past_len = 0;
		for(int i = 0;i < lens.size();i++){
			
			int len = lens.get(i);
			
			if(past_len == len){
				continue;
			}
			
			words.addAll(lens_map.get(len));
			
			past_len = len;
		}
		
		
		return words;
		
	}
	
	private static boolean in_list(String word,List<String> list){
		
		for(int i = 0;i < list.size();i++){
			
			if(word.equals(list.get(i))) {
				return true;
			}
			
		}
		return false;
	}
	
	private static void remove_dup(List<Integer> arr){
		
		HashMap<Integer,Boolean> map = new HashMap<>();
		
		int len = arr.size();
		arr.add(null);
		
		int c = 0;
		while(arr.get(c) != null){
			
			if(map.containsKey(arr.get(c))){
				arr.remove(c);
			}
			else {
				map.put(arr.get(c),true);
				c++;
			}
			
		}
		arr.remove(c);	
	}
	
	private static void set_intervals(List<Integer> offsets){
		
		
		//the even indexes are a start and the odd ones are a end
		
		offsets.add(null);
		offsets.add(null);
		
		int c = 1;
		while(offsets.get(c) != null) {
			
			
			if(c % 2 != 0){
				
				int x = offsets.get(c);
				int i = c+2;
				while(offsets.get(i) != null){
					
					if(offsets.get(i) == x){
						//System.out.println(x);
						offsets.remove(i);
						offsets.remove(i-1);
					}
					else {
						i+=2;
					}
					
				}
				
			}
			c+=2;
			
		}
		offsets.remove(offsets.size()-1);
		offsets.remove(offsets.size()-1);
		
		
	}
	
	private static String format_doc(){
		
		//StringBuilder recog_words = new StringBuilder();
		
		List<String> words = words_in_order();
		
		//List<String> recog_words = new ArrayList<>();
		
		List<Integer> offsets = new ArrayList<>();
		
		int doc_len = doc.length();
		
		for(int i = words.size()-1;i >= 0;i--){
			
			int len = words.get(i).length();
			
			for(int j = 0;j < doc_len;j++){
				
				if(j+len <= doc_len){
					String word = doc.substring(j,j+len);
					
					if(dictionary.containsKey(word)){
						offsets.add(j-1);
						offsets.add(j+len-1);
						
					}
				}
				
			}
			
		}
		
		//now i have to make the string from recog_words and doc
		
		StringBuilder formated = new StringBuilder();
		
		//System.out.println(offsets);
		
		set_intervals(offsets);
		
		//System.out.println(offsets);
		remove_dup(offsets);
		
		Collections.sort(offsets);
		
		//System.out.println(offsets);
		
		int c = 0;
		for(int i = 0;i < doc.length();i++){
			
			formated.append(doc.substring(i,i+1));
			
			if(c < offsets.size() && i == offsets.get(c)){
				formated.append(" ");
				c++;
			}
			
		}
		
		
		return formated.toString();
	}
	
		
}
