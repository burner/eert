import java.io.*;
import java.util.*;
public class ObjParse {
    private int vecIdx = 0;
    private String file;
    private String curLine;
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

	    curLine = reader.readLine();

	    while(curLine != null) {
		curLine = reader.readLine();
		if(curLine.charAt(0) == 'v') { 
		    if(curLine.charAt(1) == ' ') {
			addVertex();
		    } else if(curLine.charAt(1) == 'n') {
			addVertexN();
		    } else if(curLine.charAt(1) == 't') {
			addVertexTex();
		    }
		}
	    }
	    reader.close();
	} catch(ArrayIndexOutOfBoundsException e) {
	    System.out.println("File " + this.file + " does not exist!");
	} catch(IOException e) {
	    System.out.println("Unknown Error");
	    e.printStackTrace();
	}
    }

    private void addVertex() {
	StringBuffer[] buffer = new StringBuffer[3];
	buffer[0] = new StringBuffer();
	buffer[1] = new StringBuffer();
	buffer[2] = new StringBuffer();
	short idx = 2;
	byte fIdx = 0;
	for(int i = 2; i < curLine.length(); i++) {
	    if(curLine.charAt(i) == ' ')
		fIdx++;
	    else
		buffer[fIdx].append(curLine.charAt(i));
	}
	this.tempVec.add(new Vector(new Float(buffer[0].toString()).floatValue(), new Float(buffer[1].toString()).floatValue(), new Float(buffer[2].toString()).floatValue()));
    }

    private void addVertexN() {
	StringBuffer[] buffer = new StringBuffer[3];
	buffer[0] = new StringBuffer();
	buffer[1] = new StringBuffer();
	buffer[2] = new StringBuffer();
	short idx = 2;
	byte fIdx = 0;
	for(int i = 2; i < curLine.length(); i++) {
	    if(curLine.charAt(i) == ' ')
		fIdx++;
	    else
		buffer[fIdx].append(curLine.charAt(i));
	}
	this.tempVec.add(new Vector(new Float(buffer[0].toString()).floatValue(), new Float(buffer[1].toString()).floatValue(), new Float(buffer[2].toString()).floatValue()));
    }

    private void addVertexTex() {
    }
}
