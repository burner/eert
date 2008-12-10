/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Util.Prelude;

import Types.Geometrie.Face;
import Types.Geometrie.TexCoor;
import Types.Geometrie.Vector;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 *
 * @author burner
 */
public class JObjParse {

    public ArrayList<Vector> v = new ArrayList<Vector>();
    public ArrayList<Vector> vn = new ArrayList<Vector>();
    public ArrayList<TexCoor> vt = new ArrayList<TexCoor>();
    public ArrayList<Face> f = new ArrayList<Face>();

    public JObjParse(String file) {
        readFile(file);
    }

    public Vector[] getVector() {
        Vector[] vector = this.v.toArray(new Vector[this.v.size()]);
        return vector;
    }

    public Vector[] getNormal() {
        Vector[] vector = this.vn.toArray(new Vector[this.vn.size()]);
        return vector;
    }

    public TexCoor[] getTex() {
        TexCoor[] vector = this.vt.toArray(new TexCoor[this.vt.size()]);
        return vector;
    }

    public Face[] getFace() {
        Face[] vector = this.f.toArray(new Face[this.f.size()]);
        return vector;
    }


    void readFile(String file) {
        System.out.println(file);
        DataInputStream dataInputStream;
        DataInputStream dataInputStream2;
        BufferedReader bufferedReader;
        BufferedReader bufferedReader2;
        try {
            dataInputStream = new DataInputStream(new FileInputStream(file));
            dataInputStream2 = new DataInputStream(new FileInputStream(file));
            bufferedReader = new BufferedReader(new InputStreamReader(dataInputStream));
            bufferedReader2 = new BufferedReader(new InputStreamReader(dataInputStream2));

            String line = bufferedReader.readLine();

            //Vertices
            while (line != null) {
                if (line == null) {
                    break;
                }
                if (line.charAt(0) == 'v') {
                    if (line.charAt(1) == ' ') {
                        readVertexLine(line, 2);
                    }
                    if (line.charAt(1) == 'n') {
                        readVertexNormalLine(line, 3);
                    }
                    if (line.charAt(1) == 't') {
                        readVertexTexCoordLine(line, 3);
                    }
                }
                line = bufferedReader.readLine();
            }
            line = bufferedReader2.readLine();

            //Faces
            while (line != null) {

                if (line == null) {
                    break;
                }
                if (line.charAt(0) == 'f') {
                    readFaceLine(line, 2);
                }
                line = bufferedReader2.readLine();
            }







            bufferedReader.close();
        } catch (Exception e) {
            System.out.println("e: " + e.getMessage());
            e.printStackTrace();
        }
    }

    void readVertexLine(String line, int offset) {
        StringBuffer[] s = new StringBuffer[3];
        int j = 0;
        s[j] = new StringBuffer();
        for (int i = offset; i < line.length() && j < 3; i++) {
            while (line.charAt(i) == ' ') { //more than one Whitespace
                if (line.charAt(i + 1) != ' ') {//End of Whitespaces
                    {
                        j++;
                        s[j] = new StringBuffer();
                    }
                }
                i++;
            }
            s[j].append(line.charAt(i));
        }

        //Der ArrayList die aktuelle Vertex hinzufuegen
        float tmpX = (new Double(new String(s[0]))).floatValue();
        float tmpY = (new Double(new String(s[1]))).floatValue();
        float tmpZ = (new Double(new String(s[2]))).floatValue();
        Vector tmpVec = new Vector(tmpX, tmpY, tmpZ);
        this.v.add(this.v.size(), tmpVec);

    }

    void readVertexNormalLine(String line, int offset) {
        StringBuffer[] s = new StringBuffer[3];
        int j = 0;
        s[j] = new StringBuffer();
        for (int i = offset; i < line.length() && j < 3; i++) {
            while (line.charAt(i) == ' ') { //more than one Whitespace
                if (line.charAt(i + 1) != ' ') {//End of Whitespaces
                    {
                        j++;
                        s[j] = new StringBuffer();
                    }
                }
                i++;
            }
            s[j].append(line.charAt(i));
        }

        //Der ArrayList die aktuelle Vertex hinzufuegen
        float tmpX = (new Double(new String(s[0]))).floatValue();
        float tmpY = (new Double(new String(s[1]))).floatValue();
        float tmpZ = (new Double(new String(s[2]))).floatValue();
        Vector tmpVec = new Vector(tmpX, tmpY, tmpZ);
        this.vn.add(this.vn.size(), tmpVec);

    }

    void readVertexTexCoordLine(String line, int offset) {
        StringBuffer[] s = new StringBuffer[2];
        int j = 0;
        s[0] = new StringBuffer();
        s[1] = new StringBuffer();
        for (int i = offset; i < line.length(); i++) {
            if (line.charAt(i) == ' ') {
                j = 1;
            } else {
                s[j].append(line.charAt(i));
            }
        }

        //Der ArrayList die aktuelle Vertex hinzufuegen
        float tmpX = (new Double(s[0].toString())).floatValue();
        float tmpY = (new Double(s[1].toString())).floatValue();
        TexCoor tmpVec = new TexCoor(tmpX, tmpY);
        this.vt.add(this.vt.size(), tmpVec);

    }

    void readFaceLine(String line, int offset) {
        StringBuffer[] s = new StringBuffer[9];
        int j = 0;
        s[j] = new StringBuffer();
        for (int i = offset; i < line.length() && j < 9; i++) {
            while (line.charAt(i) == ' ') { //more than one Whitespace
                if (line.charAt(i + 1) != ' ') {//End of Whitespaces
                    {
                        j++;
                        s[j] = new StringBuffer();
                    }
                }
                i++;
            }
            while (line.charAt(i) == '/') { //more than one Whitespace
                if (line.charAt(i + 1) != '/') {//End of Whitespaces
                    {
                        j++;
                        s[j] = new StringBuffer();
                    }
                }
                i++;
            }

            s[j].append(line.charAt(i));
        }

        //Der ArrayList die aktuelle Vertex hinzufuegen
        int index0 = (new Integer(new String(s[0]))).intValue() - 1;
        int index1 = (new Integer(new String(s[1]))).intValue() - 1;
        int index2 = (new Integer(new String(s[2]))).intValue() - 1;
        int index3 = (new Integer(new String(s[3]))).intValue() - 1;
        int index4 = (new Integer(new String(s[4]))).intValue() - 1;
        int index5 = (new Integer(new String(s[5]))).intValue() - 1;
        int index6 = 0, index7 = 0, index8 = 0;
        if (s[6] != null) {
            index6 = (new Integer(new String(s[6]))).intValue() - 1;
        }
        if (s[7] != null) {
            index7 = (new Integer(new String(s[7]))).intValue() - 1;
        }
        if (s[8] != null) {
            index8 = (new Integer(new String(s[8]))).intValue() - 1;
        }


        Face tmpFace = new Face(this.v.get(index0), this.v.get(index3), this.v.get(index6), this.vn.get(index2), this.vn.get(index5), this.vn.get(index8), this.vt.get(index1), this.vt.get(index4), this.vt.get(index7));
        this.f.add(tmpFace);


    }
}