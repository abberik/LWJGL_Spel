package karta;

public class Bricka {

	public static Bricka[] brickor = new Bricka[16]; 
	public static byte antal_brickor = 0;
	
	public static final Bricka sten = new Bricka("sten");							//0 
	public static final Bricka stenblock = new Bricka("stenblock");					//1
	public static final Bricka mossigsten = new Bricka("mossigsten");				//2
	public static final Bricka gras = new Bricka("gras");							//3
	public static final Bricka vatten = new Bricka("vatten");						//4
	public static final Bricka blodandeogon = new Bricka("blodandeogon");			//5
	
	private byte id;
	private String texturen;
	
	public Bricka(String texturen) {
		this.id = antal_brickor;
		antal_brickor++;
		this.texturen = texturen;
		if(brickor[id] != null){
			throw new IllegalStateException("Brickan på " + id + " används redan." );
		}
		brickor[id] = this;
	}

	public static Bricka[] getBrickor() {
		return brickor;
	}

	public static void setBrickor(Bricka[] brickor) {
		Bricka.brickor = brickor;
	}

	public byte getId() {
		return id;
	}

	public void setId(byte id) {
		this.id = id;
	}

	public String getTexturen() {
		return texturen;
	}

	public void setTexturen(String texturen) {
		this.texturen = texturen;
	}

	public static Bricka getSten() {
		return sten;
	}

	public static Bricka getStenblock() {
		return stenblock;
	}

	public static Bricka getMossigsten() {
		return mossigsten;
	}

	public static Bricka getGras() {
		return gras;
	}

	public static Bricka getVatten() {
		return vatten;
	}

	
}
