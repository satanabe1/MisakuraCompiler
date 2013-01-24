package hoge;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Hoge implements Piyo {

	public static void saySay(int i) {
		System.out.println("HogeHOge");
		System.out.println(Fuga.class.getName());
		Fuga.say("FUGAAAAABUAAAAA");

		int i;

		Piyo.sayPiyo();
		Moge.sayMoge();
		Moge moge = new Moge();
		moge.sayMoge();

		i = Moge.hogeee;
		i = Moge.mogeee;
		
		return 0;
	}

	@Override
	public int moge() {
		return 1;
	}
}

abstract class Moge {
	static abstract void sayMoge();

	final void sayMogeMoge();

	final int moge() {
		return 0;
	}
	
	private int mogeee = 0;
}

interface Piyo {
	void sayPiyo();

	static void doPiyo();
}