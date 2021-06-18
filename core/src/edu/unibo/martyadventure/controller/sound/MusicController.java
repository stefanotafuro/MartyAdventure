package edu.unibo.martyadventure.controller.sound;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;

public class MusicController {
    
    private MusicController() {
        
    }
    
    private static Music music = Gdx.audio.newMusic(new FileHandle("music/theme.ogg"));
    
    public static void startMusic() {
        music.setLooping(true);
        music.play();
    }
    
    public static void stopMusic() {
        music.stop();
    }
    
    public static void setMusicVolume(float volume) {
        music.setVolume(volume);
    }

}
