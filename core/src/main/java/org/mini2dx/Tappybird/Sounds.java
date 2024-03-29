/*******************************************************************************
 * Copyright 2019 Viridian Software Limited
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/

package org.mini2dx.Tappybird;

import org.mini2Dx.core.Mdx;
import org.mini2Dx.core.audio.Music;
import org.mini2Dx.core.audio.Sound;

import java.io.IOException;
import java.util.Random;

public class Sounds{

    private static final String PILLAR_PASS_SOUND_LOCATION = "Sounds/coin1.ogg";
    private static final String EXPLOSION_1_SOUND_LOCATION = "Sounds/explosion1.ogg";
    private static final String EXPLOSION_2_SOUND_LOCATION = "Sounds/explosion2.ogg";
    private static final String EXPLOSION_3_SOUND_LOCATION = "Sounds/explosion3.ogg";
    private static final String EXPLOSION_4_SOUND_LOCATION = "Sounds/explosion4.ogg";
    private static final String EXPLOSION_5_SOUND_LOCATION = "Sounds/explosion5.ogg";
    private static final String BACKGROUND_MUSIC_LOCATION = "Sounds/Cheerful Annoyance_v2.wav";
    private static final String JUMP_SOUND = "Sounds/jump.wav";
    private static final String DEAD_SOUND = "Sounds/dead.wav";
    private static final String SCOREUP_SOUND = "Sounds/scoreup.wav";

    static long SCOREUP_ID;
    static Sound score;

    static long JUMP_ID;
    static Sound jump;

    static long DEAD_ID;
    static Sound dead;

    static{
        try{
            score = Mdx.audio.newSound(Mdx.files.internal(SCOREUP_SOUND));
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    static{
        try{
            dead = Mdx.audio.newSound(Mdx.files.internal(DEAD_SOUND));
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    static{ // Jump Sound
        try{
          jump = Mdx.audio.newSound(Mdx.files.internal(JUMP_SOUND));
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    Music backgroundMusic;

    {
        try { // BG Music
            backgroundMusic = Mdx.audio.newMusic(Mdx.files.internal(BACKGROUND_MUSIC_LOCATION));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    Sound[] explosionSounds;

    {
        try {
            explosionSounds = new Sound[]{Mdx.audio.newSound(Mdx.files.internal(EXPLOSION_1_SOUND_LOCATION)),
                        Mdx.audio.newSound(Mdx.files.internal(EXPLOSION_2_SOUND_LOCATION)),
                        Mdx.audio.newSound(Mdx.files.internal(EXPLOSION_3_SOUND_LOCATION)),
                        Mdx.audio.newSound(Mdx.files.internal(EXPLOSION_4_SOUND_LOCATION)),
                        Mdx.audio.newSound(Mdx.files.internal(EXPLOSION_5_SOUND_LOCATION))};
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static void scoreup() {SCOREUP_ID = score.play(3f);}

    static void deadSound() {DEAD_ID = dead.play(3f);} // Dead Sound

    static void jumpSound() {JUMP_ID = jump.play(3f); } // Jump Sound

    void disposeBackgroundMusic(){
        backgroundMusic.dispose();
    }

}
