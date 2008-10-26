import Engine.*;
import Util.*;
import Types.*;
public class Eert {
	private static Thread erun;
	public static void main(String[] args) {
		erun = new Thread(new ERun());
		erun.start();
	}
}
