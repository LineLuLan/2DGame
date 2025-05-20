package main;

import java.io.File;
import java.net.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class Sound {
    Clip clip;
    URL[] soundURL = new URL[30];

    FloatControl fc;
    int volumeScale = 3;
    float volume;


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
            soundURL[9] = new File("res/sound/cursor.wav").toURI().toURL();
            soundURL[10] = new File("res/sound/burning.wav").toURI().toURL();
            soundURL[11] = new File("res/sound/cuttree.wav").toURI().toURL();
            soundURL[12] = new File("res/sound/gameover.wav").toURI().toURL();

    
        } catch (Exception e) {
            e.printStackTrace();
            
        }
    }

    public void setFile(int i) {
        try{
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);
            fc = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
            checkVolume();
            
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

    public void checkVolume(){
        switch (volumeScale) {
            case 0:
                volume = -80.0f;
                break;
            case 1:
                volume = -20.0f;
                break;
            case 2:
                volume = -12.0f;
                break;
            case 3:
                volume = -5.0f;
                break;
            case 4:
                volume = 1f;
                break;
            case 5:
                volume = 6f;
                break;
        }
        fc.setValue(volume);
    }

}
