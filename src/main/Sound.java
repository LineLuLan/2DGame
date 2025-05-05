package main;

import java.io.File;
import java.net.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sound {
    Clip clip;
    URL[] soundURL = new URL[30];

    public Sound() {
        try{
            soundURL[0] = new File("res/sound/2DGame.wav").toURI().toURL();
            soundURL[1] = new File("res/sound/coin.wav").toURI().toURL();
            soundURL[2] = new File("res/sound/powerup.wav").toURI().toURL();
            soundURL[3] = new File("res/sound/unlock.wav").toURI().toURL();
            soundURL[4] = new File("res/sound/fanfare.wav").toURI().toURL(); 
            soundURL[5] = new File("res/sound/hitmonster.wav").toURI().toURL();
            soundURL[6] = new File("res/sound/receivedamage.wav").toURI().toURL();
            soundURL[7] = new File("res/sound/swingweapon.wav").toURI().toURL();
            soundURL[8] = new File("res/sound/levelup.wav").toURI().toURL();

    
        } catch (Exception e) {
            e.printStackTrace();
            
        }
    }

    public void setFile(int i) {
        try{
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);
        } catch(Exception e) {

        }
    }

    public void play() {
        clip.start();
    }

    public void loop() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stop() {
        clip.stop();
    }

}
