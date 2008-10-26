package Util;

import java.io.*;
import java.util.*;

import Types.*;
public class ObjParse {
	private int vecIdx = 0;
	private String file;
	private String curLine;
	private LinkedList<Types.Vector> tempVec;
	private LinkedList<Normal> tempNor;
	private LinkedList<TexCoor> tempTex;
	private LinkedList<Face> tempFac;

	public ObjParse(String file) {
		this.file = file;
		this.tempVec = new LinkedList<Types.Vector>();
		this.tempNor = new LinkedList<Normal>();
		this.tempTex = new LinkedList<TexCoor>();
		this.tempFac = new LinkedList<Face>();
		parse();
	}
	
	public Types.Vector[] getVec() {
		return (Types.Vector[])this.tempVec.toArray();
	}
	public Normal[] getNor() {
		return (Normal[])this.tempNor.toArray();
	}
	
	public TexCoor[] getTex() {
		return (TexCoor[])this.tempTex.toArray();
	}

	public Face[] getFace() {
		return (Face[])this.tempFac.toArray();
	}
	
	private void parse() {
		FileInputStream input;
                DataInputStream data;
		BufferedReader reader;
		try {
                        System.out.println(new File(".").getAbsolutePath());
			input = new FileInputStream(this.file);
                        data = new DataInputStream(input);
			reader = new BufferedReader(new InputStreamReader(data));

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
				} else if(curLine.charAt(0) == 'f') {
					addFace();
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
		byte fIdx = 0;
		for(int i = 2; i < curLine.length(); i++) {
			if(curLine.charAt(i) == ' ')
				fIdx++;
			else
				buffer[fIdx].append(curLine.charAt(i));
		}
		this.tempVec.add(new Types.Vector(new Float(buffer[0].toString()).floatValue(), new Float(buffer[1].toString()).floatValue(), new Float(buffer[2].toString()).floatValue()));
	}

	private void addVertexN() {
		StringBuffer[] buffer = new StringBuffer[3];
		buffer[0] = new StringBuffer();
		buffer[1] = new StringBuffer();
		buffer[2] = new StringBuffer();
		byte fIdx = 0;
		for(int i = 3; i < curLine.length(); i++) {
			if(curLine.charAt(i) == ' ')
				fIdx++;
			else
				buffer[fIdx].append(curLine.charAt(i));
		}
		this.tempNor.add(new Normal(new Float(buffer[0].toString()).floatValue(), new Float(buffer[1].toString()).floatValue(), new Float(buffer[2].toString()).floatValue()));
		this.vecIdx++;
	}

	private void addVertexTex() {
		StringBuffer[] buffer = new StringBuffer[3];
		buffer[0] = new StringBuffer();
		buffer[1] = new StringBuffer();
		buffer[2] = new StringBuffer();
		byte fIdx = 0;
		for(int i = 3; i < curLine.length(); i++) {
			if(curLine.charAt(i) == ' ')
				fIdx++;
			else
				buffer[fIdx].append(curLine.charAt(i));
		}
		this.tempTex.add(new TexCoor(new Float(buffer[0].toString()).floatValue(), new Float(buffer[1].toString()).floatValue(), new Float(buffer[2].toString()).floatValue()));
	}

	private void addFace() {
		StringBuffer[] buffer = new StringBuffer[9];
		buffer[0] = new StringBuffer();
		buffer[1] = new StringBuffer();
		buffer[2] = new StringBuffer();
		buffer[3] = new StringBuffer();
		buffer[4] = new StringBuffer();
		buffer[5] = new StringBuffer();
		buffer[6] = new StringBuffer();
		buffer[7] = new StringBuffer();
		buffer[8] = new StringBuffer();
		int fIdx = 0;
		for(int i = 2; i < curLine.length(); i++) {
			if(curLine.charAt(i) == '/') 
				fIdx++;
			else 
				buffer[fIdx].append(curLine.charAt(i));
		}
		this.tempFac.add(new Face(new Integer(buffer[0].toString()).intValue(), new Integer(buffer[1].toString()).intValue(), new Integer(buffer[2].toString()).intValue(), new Integer(buffer[3].toString()).intValue(), new Integer(buffer[4].toString()).intValue(), new Integer(buffer[5].toString()).intValue(), new Integer(buffer[6].toString()).intValue(), new Integer(buffer[7].toString()).intValue(), new Integer(buffer[8].toString()).intValue()));
	}
}
