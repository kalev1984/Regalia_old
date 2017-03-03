import java.io.PrintWriter;
import java.io.IOException;
import java.util.Scanner;

public class Regalia {
	public static void main(String[] args) throws IOException{
		String failiNimi=Meetodid.failiNimi();
		Meetodid.pdf(failiNimi);
		String[]tellimus=Meetodid.tellimus();
		String kliendiKood=Meetodid.kliendikood();
		String[]tellimusteRead=Meetodid.korrastus(tellimus);
		String[]lõpp=Meetodid.lõpptulemused(tellimusteRead, 8);
		int unikaalne=Meetodid.unikaalne();
		if(tellimus.length<1){
			System.out.println("Tellimus on tühi");
			System.exit(0);
		}
		String[]tooteNimetus=new String[tellimusteRead.length];
		int[]kogusTellimusel=new int[tellimusteRead.length];
		int q=0;
		int a;
		Scanner sisend=new Scanner(System.in);
		for(int i=0;i<tellimusteRead.length;i++){
			String[]lõppTulemused=Meetodid.lõpptulemused(tellimusteRead, i);
			int kogus=Meetodid.kogus(tellimusteRead, i);
			if(lõppTulemused.length>1){
  				System.out.println("Mis number vastab tootele: "+tellimusteRead[i]+"?");
  				for(int j=0;j<lõppTulemused.length;j++){
					System.out.println(j+") "+lõppTulemused[j]);
				}
  				do{
					while(!sisend.hasNextInt()){
						System.out.println("Ole tähelepanelik ja proovi uuesti!");
						sisend.next();
					}
					a=sisend.nextInt();
					if(a>lõppTulemused.length-1){
						System.out.println("Number on liiga suur!");
					}
				} while(a>lõppTulemused.length-1);
				tooteNimetus[q]=lõppTulemused[a];
				kogusTellimusel[q]=Meetodid.kogus(tellimusteRead,i);
			}
			if(lõppTulemused.length==0){
				System.out.println("Ei leidnud midagi! Otsitav toode on: "+tellimusteRead[i]+". Palun sisesta tootekood.");
				String vastus=sisend.next();
				while(vastus.length()<8){
					System.out.println("Tootekood on liiga lühike. Palun sisesta uuesti!");
					vastus=sisend.nextLine();
				}
				tooteNimetus[q]=vastus;
				kogusTellimusel[q]=kogus;
			}
			if(lõppTulemused.length==1){
				String tootekood=lõppTulemused[0];
				tooteNimetus[q]=tootekood;
				kogusTellimusel[q]=kogus;
			}
  			q++;
  		}
		System.out.println();
		String[]topelt=Meetodid.topeltRead(tooteNimetus);
		if(topelt.length>1){
			for(int i=0;i<topelt.length;i++){
				System.out.println("Topeltread: "+topelt[i]);
			}
		}
		System.out.println();
		System.out.println("Kontrolli, kas tooted vastavad sellele, mis on tellimuses!");
		for (int i=0;i<tooteNimetus.length;i++){
			String lause1="";
			if (tooteNimetus[i].length() > 10) {
				lause1 = tooteNimetus[i].substring(9);
			} else {
				lause1=tooteNimetus[i];
			}
  			String lause2=tellimusteRead[i];
  			System.out.println(i+1+") Klient tellis: "+lause2);
			System.out.println(i+1+") Leitud: 	   "+lause1);
		}
		Meetodid.parandatudVersioon(tooteNimetus);
		String[] uued = Meetodid.uuedTooted();
		System.out.println();
		System.out.println("Tellimuse number on "+unikaalne);
  		PrintWriter writer = new PrintWriter("KILUMETS."+unikaalne+".txt", "UTF-8");
  		for(int i=0;i<tooteNimetus.length;i++){
			String tootekood=tooteNimetus[i].substring(0,8);
  			writer.println(kliendiKood+";"+tootekood+";"+kogusTellimusel[i]);
  		}
  		for (int i = 0; i < uued.length; i++) {
  			writer.println(kliendiKood + ";" + uued[i]);
  		}
  		writer.close();
		System.exit(0);
	}
}