import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.*;

public class Katse {

	public static void main(String[] args) throws IOException{
		String klient=Meetodid.klient();
		System.out.println(klient);
		String failiNimi=Meetodid.failiNimi();
		Meetodid.pdf(failiNimi);
		int x=84;
		String[]hinnakiri=Meetodid.hinnakiri();
		String[]tellimus=Meetodid.tellimus();
		String[]tellimusteRead=Meetodid.korrastus(tellimus);
		System.out.println("Toode on:");
		System.out.println(tellimusteRead[x]);
		String[]esi=Meetodid.esialgsed_tulemused(tellimusteRead, x);
		System.out.println("Esialgsed tulemused:");
		//List<String> 
		for(int i=0;i<esi.length;i++){
			System.out.println(esi[i]);
		}
		System.out.println("Otsingusõnad on:");
		String[] otsingus = Meetodid.otsingusõnad(tellimusteRead, x);
		for(int i=0;i<otsingus.length;i++){
			System.out.println(otsingus[i]);
		}
		System.out.println("Optimaalne:");
		int opt=Meetodid.optimaalne(otsingus, hinnakiri, x);
		System.out.println(opt);
		//System.out.println(Meetodid.unikaalne());
		System.out.println("Lõpptulemused:");
		String[]end=Meetodid.lõpptulemused(tellimusteRead, x);
		for(int i=0;i<end.length;i++){
			System.out.println(end[i]);
		}
		
	}
}
