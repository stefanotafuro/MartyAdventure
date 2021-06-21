package edu.unibo.martyadventure.controller.sound;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public class MusicController {

    private static Music music = Gdx.audio.newMusic(Gdx.files.internal("music/theme.ogg"));

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

    private MusicController() {
    }
}
