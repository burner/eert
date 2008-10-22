import java.io.*;
import java.util.*;
public class ObjParse {
	private String file;
	private LinkedList<Vector> tempVec;
	private LinkedList<Triangle> tempTri;
	private LinkedList<TexCoor> tempTex;

	public ObjParse(String file) {
		this.file = file;
		this.tempVec = new LinkedList<Vector>();
		this.tempTri = new LinkedList<Triangle>();
		this.tempTex = new LinkedList<TexCoor>();
		parse();
	}

	private void parse() {
		FileReader input;
		BufferedReader reader;
		try {
			input = new FileReader(this.file);
			reader = new BufferedReader(input);

			String curLine = reader.readLine();

			while(curLine != null) {
				curLine = reader.readLine();

			}
			reader.close();
		} catch(ArrayIndexOutOfBoundsException e) {
			System.out.println("File " + this.file + " does not exist!");
		} catch(IOException e) {
			System.out.println("Unknown Error");
			e.printStackTrace();
		}
	}
}
