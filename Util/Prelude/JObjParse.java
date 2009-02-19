/*
 *EERT = EERT enhanced rendering technology
 *
 *Copyright (C) [2008] [Robert "BuRnEr" Schadek]

 *This program is free software; you can redistribute it and/or modify it under
 *the terms of the GNU General Public License as published by the Free Software
 *Foundation; either version 3 of the License,
 *or (at your option) any later version.

 *This program is distributed in the hope that it will be useful, but WITHOUT
 *ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 *FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.

 *You should have received a copy of the GNU General Public License along with
 *this program; if not, see <http://www.gnu.org/licenses/>.
 */
package Util.Prelude;

import Content.Objects.Objects;
import Types.Geometrie.Face;
import Types.Geometrie.TexCoor;
import Types.Geometrie.Vector;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class JObjParse {

    public ArrayList<Vector> vectors = new ArrayList<Vector>();
    public ArrayList<Vector> vn = new ArrayList<Vector>();
    public ArrayList<TexCoor> vt = new ArrayList<TexCoor>();
    public ArrayList<Face> faces = new ArrayList<Face>();
    private Vector middle;
    public float boudingRadius;

    public JObjParse(String file) {
        readFile(file);
        makeFriends();
        makeMiddle();
        makeBoundingSphere();
        checkFacesEdges();
    }

    public Vector[] getVector() {
        Vector[] vector = this.vectors.toArray(new Vector[this.vectors.size()]);
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
        Face[] vector = this.faces.toArray(new Face[this.faces.size()]);
        return vector;
    }

    private void checkFacesEdges() {
        for(Face toTest : this.faces) {
            if(toTest.fr1 == null)
                System.out.println("Friend1 Missing");
            if(toTest.fr2 == null)
                System.out.println("Friend2 Missing");
            if(toTest.fr3 == null)
                System.out.println("Friend3 Missing");

            if(toTest.ed1 == null)
                System.out.println("Edge1 Missing");
            if(toTest.ed1 == null)
                System.out.println("Edge1 Missing");
            if(toTest.ed1 == null)
                System.out.println("Edge1 Missing");
        }
    }

    private void makeFriends() {
        //this one is expensive
        for (int i = 0; i < this.faces.size(); i++) {
            for (int j = 0; j < this.faces.size(); j++) {
                //not a good idea to check against one self
                if(i == j)
                    continue;
                
                int countV1 = 0;
                int countV2 = 0;
                int countV3 = 0;
                Face faceToTest = this.faces.get(i);
                Face faceForTest = this.faces.get(j);

                //test all vertices against all

                //this is the first vertex
                if(faceToTest.v1 == faceForTest.v1) {
                    countV1++;
                }
                if(faceToTest.v1 == faceForTest.v2) {
                    countV1++;
                }
                if(faceToTest.v1 == faceForTest.v3) {
                    countV1++;
                }

                //this is the second vertex
                if(faceToTest.v2 == faceForTest.v1) {
                    countV2++;
                }
                if(faceToTest.v2 == faceForTest.v2) {
                    countV2++;
                }
                if(faceToTest.v2 == faceForTest.v3) {
                    countV2++;
                }

                //this is the thrid vertex
                if(faceToTest.v3 == faceForTest.v1) {
                    countV3++;
                }
                if(faceToTest.v3 == faceForTest.v2) {
                    countV3++;
                }
                if(faceToTest.v3 == faceForTest.v3) {
                    countV3++;
                }

                //assign friends
                if(countV1 > 0 && countV2 > 0) {
                    faceToTest.fr1 = faceForTest;
                }

                if(countV1 > 0 && countV3 > 0) {
                    faceToTest.fr3 = faceForTest;
                }

                if(countV2 > 0 && countV3 > 0) {
                    faceToTest.fr2 = faceForTest;
                }
            }
        }
    }

    void readFile(String file) {
        System.out.println(file);
        BufferedReader bufferedReader;
        BufferedReader bufferedReader2;
        InputStream input;
        try {
            input = Objects.class.getResourceAsStream(file);
            bufferedReader = new BufferedReader(new InputStreamReader(input));
            bufferedReader2 = new BufferedReader(new InputStreamReader(input));

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
                if (line.charAt(0) == 'f') {
                    readFaceLine(line, 2);
                }
                line = bufferedReader.readLine();
            }
          /*  line = bufferedReader2.readLine();

            //Faces
            while (line != null) {
                System.out.println(line);
                if (line == null) {
                    break;
                }
                if (line.charAt(0) == 'f') {
                    readFaceLine(line, 2);
                }
                line = bufferedReader2.readLine();
            }*/
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
        this.vectors.add(this.vectors.size(), tmpVec);

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


        Face tmpFace = new Face(this.vectors.get(index0), this.vectors.get(index3), this.vectors.get(index6), this.vn.get(index2), this.vn.get(index5), this.vn.get(index8), this.vt.get(index1), this.vt.get(index4), this.vt.get(index7));
        this.faces.add(tmpFace);
    }

    private void makeMiddle() {
        Vector middleM = new Vector();
        int number = this.vectors.size();
        for (Vector forMiddle : this.vectors) {
            middleM.x += forMiddle.x / number;
            middleM.y += forMiddle.y / number;
            middleM.z += forMiddle.z / number;
        }
        this.middle = middleM;
    }

    public void makeBoundingSphere() {
        float dis = 0f;
        for (Vector toTest : this.vectors) {
            float newDis = (float) Math.sqrt(Math.pow(toTest.x - this.middle.x, 2) +
                    Math.pow(toTest.y - this.middle.y, 2) +
                    Math.pow(toTest.z - this.middle.z, 2));

            if (newDis > dis) {
                dis = newDis;
            }
        }

        this.boudingRadius = dis;
    }
}
