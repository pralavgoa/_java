package pralav.test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class JSoupWebPageParser {

	public static void main(String[] args) throws IOException{
		//Document doc = Jsoup.connect("http://www.vaneck.com/market-vectors/equity-etfs/gdxj/holdings/").get();
		
		File input = new File("testFiles/gdxj.test");
		Document doc = Jsoup.parse(input,"UTF-8");
		Elements holdings = doc.select(".holding");
		
		Element holdingTable = holdings.first();
		
		Elements rows = holdingTable.getElementsByTag("tr");
		
		rows.remove(0);
		rows.remove(rows.size()-1);
		rows.remove(rows.size()-1);
		
		List<Holding> holdingsList = new ArrayList<>();
		
		for(Element row : rows){
			Elements columns = row.getElementsByTag("td");
			columns.remove(0);
			holdingsList.add(new Holding(columns.first().text(),columns.get(1).text(),columns.get(6).text()));
		}
		
		System.out.println(holdingsList);
	}
	
	public static class Holding{
		private final String holdingName;
		private final String holdingSymbol;
		private final String percentage;
		public Holding(String name, String symbol, String percentage){
			this.holdingName = name;
			this.holdingSymbol = symbol;
			this.percentage = percentage;
		}
		
		public String toString(){
			return holdingName+"|"+holdingSymbol+"|"+percentage+"\n";
		}
	}
	
}
