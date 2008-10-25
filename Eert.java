import Engine.*;
import util.*;
import Types.*;
public class Eert {
	private Thread erun;
	public static void main(String[] args) {
		this.erun = new Thread(new ERun());
		erun.start();
	}
}
