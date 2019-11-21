package game;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

public class SoundPlayer {

    public static void play(String filename)
    {
        try
        {
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(new File(filename)));
//            clip.loop(Clip.LOOP_CONTINUOUSLY);
            clip.start();
//            Thread.sleep(1000000);
        }
        catch (Exception exc)
        {
            exc.printStackTrace(System.out);
        }
    }



}