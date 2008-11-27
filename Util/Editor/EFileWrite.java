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
 
package Util.Editor;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 *
 * @author burner
 */
public class EFileWrite {

    private String[] toWrite;

    public EFileWrite(String[] toRight) {
        this.toWrite = toRight;
        writeToFile();        
    }

    private void writeToFile() {
        FileOutputStream fos;
        DataOutputStream dos;
        try {
            File file = new File(this.toWrite[0]);
            fos = new FileOutputStream(file);
            dos = new DataOutputStream(fos);
            for(int i = 1; i < this.toWrite.length; i++) {
                dos.writeChars(this.toWrite[i]);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
