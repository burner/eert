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
package Engine;
// Import the JLayer classes
import javazoom.jl.player.AudioDevice;
import javazoom.jl.player.FactoryRegistry;
import javazoom.jl.player.advanced.AdvancedPlayer;
import javazoom.jl.player.advanced.PlaybackListener;
import javazoom.jl.player.advanced.PlaybackEvent;
import javazoom.jl.decoder.JavaLayerException;

import java.io.*;

public class EMusicPlayerMP3 implements Runnable {

    private AdvancedPlayer player;
    private AudioDevice device;
    private int position = 0;
    private Thread thread = null;
    private String name;

    public EMusicPlayerMP3(String name) {
        this.name = name;
    }

    public void play() {
        try {
            InputStream is = new BufferedInputStream(new FileInputStream(this.name));

            FactoryRegistry r = FactoryRegistry.systemRegistry();
            device = r.createAudioDevice();
            player = new AdvancedPlayer(is, device);
            player.setPlayBackListener(new PlaybackListener() {

                @Override
                public void playbackStarted(PlaybackEvent playbackEvent) {
                    //vmech().onMessage("Playback started..");
                    System.out.println("Playback started..");
                //		   thread.resume();
                }

                public void playbackFinished(PlaybackEvent playbackEvent) {
                    //vmech().onMessage("Playback finished..");
                    System.out.println("Playback finished..");
                }
            });

            thread = new Thread(this);
            thread.start();
        } catch (JavaLayerException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void run() {
        try {
            System.out.println("Play : " + position);
            player.play(position, Integer.MAX_VALUE);
        //System.out.println("Playback finished..");
        } catch (Exception exp) {
            exp.printStackTrace();
        }
    }
}
