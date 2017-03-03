import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;
import java.util.HashSet;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;

public class Meetodid {
 	static String failiNimi() throws IOException {
		System.out.print("Failinimi: ");
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		String input = reader.readLine();
		return input;
	}
 	static void pdf(String failinimi) throws IOException {
 		PDFParser parser = null;
 		COSDocument cosDoc = null;
 		PDDocument pdDoc = null;
 		PDFTextStripper pdfStripper;
 		failinimi = failinimi + ".pdf";
 		try {
 			parser = new PDFParser(new FileInputStream(failinimi));
 		} catch (FileNotFoundException e) {
 			System.out.println("Fail puudub!");
 			System.exit(0);
 		}
 		parser.parse();
 		cosDoc = parser.getDocument();
 		pdfStripper = new PDFTextStripper();
 		pdDoc = new PDDocument(cosDoc);
 		pdfStripper.setStartPage(0);
 		pdfStripper.setEndPage(2);
 		String parsedText = pdfStripper.getText(pdDoc);
 		pdDoc.close();
 		PrintWriter writer = new PrintWriter("tellimus.txt", "UTF-8");
  		writer.println(parsedText);
  		writer.close();
 	}
	static String[] tellimus() throws IOException {
		List<String> nimekiri = new ArrayList<String>();
		BufferedReader br = null;
		String failinimi = "tellimus.txt";
		try {
			String sCurrentLine;
			br = new BufferedReader(new FileReader(failinimi));
			while ((sCurrentLine = br.readLine()) != null) {
				nimekiri.add(sCurrentLine);
			}
		} catch (IOException e) {
			System.out.println("faili " + failinimi + " ei leitud!");
			System.exit(0);
		}
		String[] tellimus = nimekiri.toArray(new String[0]);
		return tellimus;
	}
	static String[] hinnakiri() throws IOException {
		List<String> nimekiri = new ArrayList<String>();
		Scanner input = new Scanner(System.in);
		File file = new File("Hinnakiri.txt");
		try {
			input = new Scanner(file);
			while (input.hasNextLine()) {
				nimekiri.add(input.nextLine());
				}
			input.close();
		} catch (FileNotFoundException e) {
			System.out.println("Faili ei leitud!");
			System.exit(0);
		}
		String[] hinnakiri = nimekiri.toArray(new String[0]);
		return hinnakiri;
	}
	static String kliendikood() {
		System.out.println("Mis on kliendikood?");
		Scanner input = new Scanner(System.in);
		String kliendikood = "";
		if (input.hasNextLine()) {
			kliendikood = input.nextLine();
		}
		kliendikood = kliendikood.toUpperCase();
		return kliendikood;
	}
	static boolean onNumber(String lause) {  
		try {  
			int d = Integer.parseInt(lause);  
		} catch (NumberFormatException nfe) {  
			return false;  
		}  
		return true;  
	}
	static String[] korrastus(String[]tellimus) throws IOException {
		List<String>nimekiri = new ArrayList<String>();
		int pikkus = tellimus.length;
		for (int i = 1; i < 8; i++) {
			tellimus[pikkus - i] = "";
		}
		String esimene = "Regalia Kaubandus";
		for (int i = 0; i < tellimus.length; i++) {
			String mida_otsin = esimene;
			String kust_otsin = tellimus[i];
			boolean leitud = kust_otsin.toLowerCase().contains(mida_otsin.toLowerCase());
			if (leitud) {
				tellimus[i] = "";
			}
		}
		String teine = "Kood Kauba nimetus";
		for (int i = 0; i < tellimus.length; i++) {
			String mida_otsin = teine;
			String kust_otsin = tellimus[i];
			boolean leitud = kust_otsin.toLowerCase().contains(mida_otsin.toLowerCase());
			if (leitud) {
				tellimus[i] = "";
			}
		}
		String kolmas = "vahesumma";
		for (int i = 0; i < tellimus.length; i++) {
			String mida_otsin = kolmas;
			String kust_otsin = tellimus[i];
			boolean leitud = kust_otsin.toLowerCase().contains(mida_otsin.toLowerCase());
			if (leitud) {
				tellimus[i] = "";
			}
		}
		for (int i = 0; i < tellimus.length; i++) {
			if (tellimus[i].length() < 10) {
				tellimus[i] = "";
			}
		}
		for (int i = 0; i < tellimus.length; i++) {
			int protsent = tellimus[i].indexOf("%");
			int s = 3;
			for (int j = -1; (j = tellimus[i].indexOf(",", j + 1)) != -1;) {
				if (j > protsent) {
					tellimus[i] = new StringBuilder(tellimus[i]).insert(j + s, " ").toString();
				}
			}
		}
		for (int i = 0; i < tellimus.length; i++) {
			int pdl = tellimus[i].indexOf("pdl");
			if (pdl > 0) {
				tellimus[i] = new StringBuilder(tellimus[i]).insert(pdl + 3, " ").toString();
			}
		}
		for (int i = 0; i < tellimus.length; i++) {
			int tk = tellimus[i].indexOf("tk");
			if (tk > 0) {
				tellimus[i] = new StringBuilder(tellimus[i]).insert(tk + 2, " ").toString();
			}
		}
		for (int i = 0; i < tellimus.length; i++) {
			int pk = tellimus[i].indexOf("pk");
			if (pk > 0) {
				tellimus[i] = new StringBuilder(tellimus[i]).insert(pk + 2, " ").toString();
			}
		}
		for (int i = 0; i < tellimus.length; i++) {
			if (tellimus[i].length() > 5) {
				nimekiri.add(tellimus[i]);
			}
		}
		tellimus = nimekiri.toArray(new String[0]);
		for (int i = 0; i < tellimus.length; i++) {
			String[] tokens = tellimus[i].split("\\s+");
			tokens[tokens.length - 1] = "";
			tokens[tokens.length - 2] = "";
			tokens[tokens.length - 4] = "";
			tokens[tokens.length - 5] = "";
			tellimus[i] = Meetodid.join(tokens, " ");
		}
		for (int i = 0; i < tellimus.length; i++) {
			tellimus[i] = tellimus[i].replaceAll("\\.", " ");
		}
		return tellimus;
	}
	static int kogus(String[]korrastus, int rida) throws IOException {
		String esimene = korrastus[rida]; 
		String[] tokens = esimene.split("\\s+"); 
		String kogus;
		kogus = tokens[tokens.length - 1];
		kogus = kogus.replaceAll("\\,00", "");
		int quantity = 0;
		try {
			quantity = Integer.valueOf(kogus);
		} catch (NumberFormatException e) {
			System.out.println("Viga koguse arvestuses!");
		}
		return quantity;
	}
	static String[] otsingusınad(String[]korrastus, int rida) throws IOException{
		int size = 0, protsent = 0;
		String esimene = korrastus[rida];
		List<String>ajutine = new ArrayList<String>(); 
		String[] tokens = esimene.split("\\s+"); 
		for (int i = 0; i < tokens.length; i++) {
			if (tokens[i].equals("Ciqoqne")) {
				tokens[i - 2] = ("Cigogne");
			}
		}
		for (int i = 0; i < tokens.length; i++) {
			if (tokens[i].equals("D¬¥Argent")) {
				tokens[i - 2] = ("D¥Argent");
			}
		}
		for (int i = 0; i < tokens.length; i++) {
			if (tokens[i].equals("Cel")) {
				tokens[i] = ("Sel");
			}
		}
		for (int i = 0; i < tokens.length; i++) {
			if (tokens[i].equals("N√ºwang")) {
				tokens[i] = ("N¸wang");
			}
		}
		for (int i = 0; i < tokens.length; i++) {
			if (tokens[i].equals("viin")) {
				tokens[i] = ("");
			}
		}
		for (int i = 0; i < tokens.length; i++) {
			if (tokens[i].equals("Vein")) {
				tokens[i] = ("");
			}
		}
		tokens[tokens.length-1] = "";
		for (int i = 0; i < tokens.length; i++) {
			String mida_otsin = "cl";
			String kust_otsin = tokens[i];
			boolean leitud = kust_otsin.toLowerCase().contains(mida_otsin.toLowerCase());
			if (leitud) {
				size = i;
			}
		}
		tokens[size] = ""; 
		for (int i = 0; i < tokens.length; i++) {
			String mida_otsin = "%";
			String kust_otsin = tokens[i];
			boolean leitud = kust_otsin.toLowerCase().contains(mida_otsin.toLowerCase());
			if (leitud) {
				protsent = i;
			}
		}
		tokens[protsent] = "";
		for (int i = 0; i < tokens.length; i++) {
			if (tokens[i].length() > 2) {
				String text = tokens[i];
				ajutine.add(text);
			}			
		}
		tokens = ajutine.toArray(new String[0]); 
		return tokens;
	}
	static int optimaalne(String[]otsingusınad, String[]hinnakiri, int rida) throws IOException {
		List<String>ajutine = new ArrayList<String>(); 
		int[] matches = new int[otsingusınad.length]; 
		boolean leidub = false;
		int indeks = 0;
		int word = 0; 
		int x = 0;
		while (x < otsingusınad.length) { 
			for (int i = 0; i < hinnakiri.length; i++) {
				String mida_otsin = otsingusınad[x];
				String kust_otsin = hinnakiri[i];
				boolean leitud = kust_otsin.toLowerCase().contains(mida_otsin.toLowerCase());
				if (leitud) {
					ajutine.add(kust_otsin);
				}
			}
			int listi_suurus = ajutine.size();
			matches[x] = listi_suurus;
			ajutine.clear();
			x++;
		}
		ajutine.clear();
		for (int i = 0; i < matches.length; i++) {
			if (matches[i] == 1) {
				leidub = true;
				indeks = i;
			}
		}
		if (leidub) {
			word = indeks;
		} else {
			int smallest = Integer.MAX_VALUE;
			for (int i = 0; i < matches.length; i++) {
				if (matches[i] > 0 && matches[i] < smallest) {
					smallest = matches[i];
					indeks = i;
				}
			}
			word = indeks;
		}
		return word;
	}
	static String[] esialgsed_tulemused(String[] korrastus, int rida) throws IOException {
		String[] otsing = otsingusınad(korrastus, rida);
		String[] products = hinnakiri();
		int optimaalne = optimaalne(otsing, products, rida);
		String esimene = korrastus[rida];
		String[] tokens = esimene.split("\\s+");
		String size = "";
		String protsent = "";
		String percentage = "";
		String[] tulemused = hinnakiri();
		List<String>ajutine = new ArrayList<String>(); 
		for (int i = 0; i < products.length; i++) {
			String mida_otsin = otsing[optimaalne];
			String kust_otsin = products[i];
			boolean leitud = kust_otsin.toLowerCase().contains(mida_otsin.toLowerCase());
			if (leitud) {
				ajutine.add(kust_otsin);
			}
		}
		tulemused = ajutine.toArray(new String[0]);
		ajutine.clear();
		for (int i = 0; i < tokens.length; i++) {
			String mida_otsin = "cl";
			String kust_otsin = tokens[i];
			boolean leitud = kust_otsin.toLowerCase().contains(mida_otsin.toLowerCase());
			if (leitud) {
				size = tokens[i];
			}
		}
		for (int i = 0; i < tulemused.length; i++) {
			String mida_otsin = size;
			String kust_otsin = tulemused[i];
			boolean leitud = kust_otsin.toLowerCase().contains(mida_otsin.toLowerCase());
			if (leitud) {
				ajutine.add(kust_otsin);
			}
		}
		tulemused = ajutine.toArray(new String[0]);
		ajutine.clear();
		for (int i = 0; i < tokens.length; i++) {
			String mida_otsin = "%";
			String kust_otsin = tokens[i];
			boolean leitud = kust_otsin.toLowerCase().contains(mida_otsin.toLowerCase());
			if (leitud) {
				protsent = tokens[i];
			}
		}
		int protsendi_koht_stringis = protsent.indexOf("%"); 
		if (protsendi_koht_stringis > 1) {
			percentage = protsent.substring(0, protsendi_koht_stringis); 
			String per = percentage.replaceAll("\\,", ".");
			double pr = Double.parseDouble(per);
			if (tulemused.length > 1 && pr > 15) {
				for (int i = 0; i < tulemused.length; i++) {
					String mida_otsin = percentage;
					String kust_otsin = tulemused[i];
					String[] hk = kust_otsin.split("\\s+");
					String otsitav = hk[hk.length - 1];
					otsitav = otsitav.replaceAll("\\,00", "");
					boolean leitud = otsitav.contains(mida_otsin);
					if (leitud) {
						ajutine.add(kust_otsin);
					}
				}
				while (!(ajutine.isEmpty())) {
					tulemused = ajutine.toArray(new String[0]);
					ajutine.clear();
				}
			}
		}
		return tulemused;
	}
	static String[] lıpptulemused(String[]korrastus, int rida) throws IOException {
		String[] esialgsed_tulemused = esialgsed_tulemused(korrastus, rida);
		String[] tulemused = new String[esialgsed_tulemused.length];
		List<String> ajutine = new ArrayList<String>();
		if (esialgsed_tulemused.length > 1) {
			String lause = korrastus[rida];
			String[] tokens = lause.split("\\s+");
			int pikkus = tokens.length;
			tokens[0] = "";
			tokens[pikkus - 1] = "";
			tokens[pikkus - 2] = "";
			tokens[pikkus - 3] = "";
			if (tokens[pikkus - 4].contains("%") == true) {
				tokens[pikkus - 4] = "";
			}
			int otsitav_indeks = 0;
			for (int i = 0; i < tokens.length; i++) {
				if (tokens[i].length() > 3) {
					otsitav_indeks = i;
					break;
				}
			}
			for (int i = otsitav_indeks; i < tokens.length; i++) {
				if (tokens[i].equals("Bollino")) {
					if (tokens[i + 1].equals("v")) {
						tokens[i + 1] = "bianco";
					}
				if (tokens[i + 1].equals("p")) {
						tokens[i + 1] = "rosso";
					}
				if (tokens[i + 1].equals("r")) {
						tokens[i + 1] = "rose";
					}
				}
			}
			for (int i = otsitav_indeks; i < tokens.length; i++) {
				if (tokens[i].equals("p")) {
					tokens[i] = "red";
					break;
				}
			}
			for (int i = otsitav_indeks; i < tokens.length; i++) {
				if (tokens[i].equals("v")) {
					tokens[i] = "white";
					break;
				}
			}
			for (int i = otsitav_indeks; i < tokens.length; i++) {
				if (tokens[i].equals("r")) {
					tokens[i] = "rose";
					break;
				}
			}
			for (int i = otsitav_indeks; i < tokens.length; i++) {
				if (tokens[i].equals("C/S")) {
					tokens[i] = "cabernet";
					break;
				}
			}
			for (int i = otsitav_indeks; i < tokens.length; i++) {
				if (tokens[i].equals("p/m")) {
					tokens[i] = "medium sweet";
					break;
				}
			}
			for (int i = otsitav_indeks; i < tokens.length; i++) {
				if (tokens[i].equals("Johvika")) {
					tokens[i] = "jıhvika";
					break;
				}
			}
			for (int i = otsitav_indeks; i < tokens.length; i++) {
				if (tokens[i].equals("S/Bl")) {
					tokens[i] = "sauvignon blanc";
					break;
				}
			}
			for (int i = otsitav_indeks; i < tokens.length; i++) {
				if (tokens[i].equals("pun")) {
					tokens[i] = "red";
					break;
				}
			}
			for (int i = otsitav_indeks; i < tokens.length; i++) {
				if (tokens[i].equals("Cr")) {
					tokens[i] = "crianza";
					break;
				}
			}
			for (int i = otsitav_indeks; i < tokens.length; i++)  {
				if (tokens[i].equals("3Y")) {
					tokens[i] = "3 years";
					break;
				}
			}
			for (int i = otsitav_indeks; i < tokens.length; i++) {
				if (tokens[i].equals("5Y")) {
					tokens[i] = "5 years";
					break;
				}
			}
			lause = String.join(" ", tokens);
			lause = lause.replaceAll("\\/", " ");
			tokens = lause.split("\\s+");
			for (int i = 0; i < tokens.length; i++) {
				if (tokens[i].length() < 3) {
					tokens[i] = "";
				}
			}
			int optimaalne = optimaalne(tokens, esialgsed_tulemused, rida);
			for (int i = 0; i < esialgsed_tulemused.length; i++) {
				String mida_otsin = tokens[optimaalne];
				String kust_otsin = esialgsed_tulemused[i];
				boolean leitud = kust_otsin.toLowerCase().contains(mida_otsin.toLowerCase());
				if (leitud) {
					ajutine.add(kust_otsin);
				}
			}
			tulemused = ajutine.toArray(new String[0]);
			ajutine.clear();
		} else {
			tulemused = esialgsed_tulemused;
		}
		return tulemused;
	}
	static int unikaalne() {
		Random suvaline = new Random();
		int vajalik = 9999998;
		int value = suvaline.nextInt(vajalik) + 1;
		return value;
	}
	static String[] topeltRead(String[] strArray) {
		HashSet<String> hash = new HashSet<String>();
		List<String> set = new ArrayList<String>();
        for (String arrayElement : strArray) {
            if (!hash.add(arrayElement)) {
                set.add(arrayElement);
            }
        }
        String[] topeltRead = set.toArray(new String[0]);
        return topeltRead;
	}
	static String[] parandatudVersioon(String[] esialgneTellimus) {
		String otsus, parandatudTootekood;
		boolean jahEi = true;
		Scanner input = new Scanner(System.in);
		int tooteNumber;
		while (jahEi) {
			System.out.println("Kas soovid parandada mında rida?");
			System.out.println("Kas 'jah' vıi 'ei'");
			otsus = input.next();
		    switch (otsus) {
		        case "jah":
		            System.out.println("Millist tootekoodi soovid muuta?");
		            for (int i = 0; i < esialgneTellimus.length; i++) {
		            	System.out.println(i + ") " + esialgneTellimus[i]);
		            }
		            do {
						while (!input.hasNextInt()) {
							System.out.println("Ole t‰helepanelik ja proovi uuesti!");
							input.next();
						}
						tooteNumber = input.nextInt();
						if (tooteNumber > esialgneTellimus.length - 1) {
							System.out.println("Number on liiga suur!");
						}
					} while (tooteNumber > esialgneTellimus.length - 1);
		            System.out.println("Milline on ıige tootekood?");
		            parandatudTootekood = input.next();
		            esialgneTellimus[tooteNumber] = parandatudTootekood;
		            jahEi = true;
		            break;
		        case "ei":
		        	jahEi = false;
		            break;
		        default:
		            System.out.println("Vastus saab olla kas 'jah' vıi 'ei'!");
		            boolean kordus = true;
		            while (kordus) {
		            	otsus = input.nextLine();
		            	System.out.println("Kas soovid parandada mında rida?");
		    			System.out.println("Kas 'jah' vıi 'ei'");
		                switch (otsus) {
		                    case "jah":
		                    	kordus = true;
		                        System.out.println("Millist nime soovid muuta?");
		    		            for (int i = 0; i < esialgneTellimus.length; i++) {
		    		            	System.out.println(i + ") " + esialgneTellimus[i]);
		    		            }
		    		            do {
		    						while (!input.hasNextInt()) {
		    							System.out.println("Proovi uuesti!");
		    							input.next();
		    						}
		    						tooteNumber = input.nextInt();
		    						if (tooteNumber > esialgneTellimus.length - 1) {
		    							System.out.println("Number on liiga suur!");
		    						}
		    					} while (tooteNumber > esialgneTellimus.length - 1);
		    		            System.out.println("Milline on uus v‰‰rtus?");
		    		            parandatudTootekood = input.next();
		    		            esialgneTellimus[tooteNumber] = parandatudTootekood;
		                        break;
		                    case "ei":
		                    	kordus = false;
		                        break;
		                    default:
		    		            System.out.println("Vastus saab olla kas 'jah' vıi 'ei'!");
		    		            kordus = true;
		                }
		            }
		            break;
		    }
		}
		return esialgneTellimus;
	}
	public static String join(String[] strings, String glue) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < strings.length; i++) {
            sb.append(strings[i]);
            if (i < strings.length - 1) {
                sb.append(glue);
            }
        }
        return sb.toString();
    }
	public static boolean onToode (String tootekood) throws IOException {
		String[] tooted = hinnakiri();
		boolean onOlemas = false;
		for(int i = 0; i < tooted.length; i++) {
			onOlemas = tooted[i].toLowerCase().contains(tootekood.toLowerCase());
		}
		return onOlemas;
	}
	static String[] lisaTellimus() throws IOException {
		List<String> nimekiri = new ArrayList<String>();
		Scanner input = new Scanner(System.in);
		File file = new File("Lisatellimus.txt");
		try {
			input = new Scanner(file);
			while (input.hasNextLine()) {
				nimekiri.add(input.nextLine());
				}
			input.close();
		} catch (FileNotFoundException e) {
			System.out.println("Faili ei leitud!");
			System.exit(0);
		}
		String[] tellimus = nimekiri.toArray(new String[0]);
		return tellimus;
	}
	static String[] uuedTooted() throws IOException {
		List<String> nimekiri = new ArrayList<String>();
		Scanner input = new Scanner(System.in);
		File file = new File("uus.txt");
		try {
			input = new Scanner(file);
			while (input.hasNextLine()) {
				nimekiri.add(input.nextLine());
				}
			input.close();
		} catch (FileNotFoundException e) {
			System.out.println("Faili ei leitud!");
			System.exit(0);
		}
		String[] hinnakiri = nimekiri.toArray(new String[0]);
		return hinnakiri;
	}
	static String klient() throws IOException {
		List<String> kliendid = new ArrayList<String>();
		int a = 0;
		String kliendikood = "";
		Scanner input = new Scanner(System.in);
		File file = new File("kliendid.txt");
		try {
			input = new Scanner(file);
			while (input.hasNextLine()) {
				kliendid.add(input.nextLine());
				}
			input.close();
		} catch (FileNotFoundException e) {
			System.out.println("Faili ei leitud!");
			System.exit(0);
		}
		for (int i = 0; i < kliendid.size(); i++) {
			System.out.println(kliendid.get(i));
		}
		do{
			while(!input.hasNextInt()){
				System.out.println("Ole t‰helepanelik ja proovi uuesti!");
				input.next();
			}
			a=input.nextInt();
			if(a>kliendid.size()-1){
				System.out.println("Number on liiga suur!");
			}
		} while(a>kliendid.size()-1);
		kliendikood.equals(kliendid.get(a));
		//<>
		return kliendikood;
	}
}