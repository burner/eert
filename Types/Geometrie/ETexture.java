/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Types.Geometrie;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.media.opengl.GL;
import javax.media.opengl.glu.GLU;

/**
 *
 * @author burner
 */
public class ETexture {

    private int width;
    private int height;
    ByteBuffer buffer;
    ByteBuffer mirroredBuffer;
    private FileInputStream fileInputStream;
    private BufferedImage bufferedImage;

    public ETexture(String filename) throws FileNotFoundException {
        loadImage(filename);
        convert();
    }

    void loadImage(String filename) throws FileNotFoundException {
        try {
            File file = new File("./Textures/" + filename);
            fileInputStream = new FileInputStream(file);
            bufferedImage = ImageIO.read(fileInputStream);
            width = bufferedImage.getWidth();
            height = bufferedImage.getHeight();
        } catch (IOException ex) {
            Logger.getLogger(Image.class.getName()).log(Level.SEVERE, null, ex);
        }
        convert();
    }

    private void convert() {
        int[] packedPixels = new int[width * height];
        bufferedImage.getRGB(0, 0, width, height, packedPixels, 0, width);
        int[] temp = new int[width * height];
        int[] temp2 = new int[width * height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                temp[x + (y * width)] = packedPixels[x + ((height -1 - y) * width)];
                temp2[x + (y * width)] = packedPixels[x + (y*height)];
            }
        }

        buffer = ByteBuffer.allocateDirect(temp.length * 4);
        buffer.order(ByteOrder.nativeOrder());
        buffer.put(convertToRGBA(temp));
        buffer.rewind();

        mirroredBuffer = ByteBuffer.allocateDirect(temp2.length * 4);
        mirroredBuffer.order(ByteOrder.nativeOrder());
        mirroredBuffer.put(convertToRGBA(temp2));
        mirroredBuffer.rewind();


    }

    public void makeRGBTexture(GL gl, GLU glu, int target, boolean mipmapped) {
        if (mipmapped) {
            glu.gluBuild2DMipmaps(target, GL.GL_RGB8, this.getWidth(),
                    this.getHeight(), GL.GL_RGB, GL.GL_UNSIGNED_BYTE, this.getBuffer());
        } else {
            gl.glTexImage2D(target, 0, GL.GL_RGB, this.getWidth(),
                    this.getHeight(), 0, GL.GL_RGB, GL.GL_UNSIGNED_BYTE, this.getBuffer());
        }
    }

    /**
     * convert argb to rgba
     * @param array
     * @return
     */
    public static byte[] convertToRGBA(int[] array) {
        byte[] data = new byte[array.length * 4];
        int count = 0;
        for (int x = 0; x < array.length; x++) {
            data[count++] = (byte) ((array[x] & 0x00ff0000) >> 16);
            data[count++] = (byte) ((array[x] & 0x0000ff00) >> 8);
            data[count++] = (byte) ((array[x] & 0x000000ff) >> 0);
            data[count++] = (byte) ((array[x] & 0xff000000) >> 24);
        }
        return data;
    }

    public ByteBuffer getBuffer() {
        return buffer;
    }

     public ByteBuffer getMirroredBuffer() {
        return mirroredBuffer;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

}
