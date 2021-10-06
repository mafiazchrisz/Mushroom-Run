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
import org.mini2Dx.core.graphics.Animation;
import org.mini2Dx.core.graphics.SpriteSheet;
import org.mini2Dx.core.graphics.Texture;

public class PlayerTexture {

    private static final String PLAYER_SPRITE_SHEET_LOCATION = "Player/mushroom.png";

    private float frameDuration = 0.025f;

    Texture spriteTexture = Mdx.graphics.newTexture(Mdx.files.internal(PLAYER_SPRITE_SHEET_LOCATION));
    SpriteSheet playerSpriteSheet = new SpriteSheet(spriteTexture,88,73);
    Animation playerAnimation = new Animation();


    public PlayerTexture(int color){
        int red[] = {1, 9, 12};
        int yellow[] = {0, 2, 10};
        int green[] = {3, 5, 7};
        int blue[] = {4, 6, 8};
        int stored[] = {0, 0, 0};
        for(int i=0;i<3;i++) {
            if (color == 1) stored[i] = red[i];
            else if (color == 2) stored[i] = yellow[i];
            else if (color == 3) stored[i] = blue[i];
            else if (color == 4) stored[i] = green[i];
        }
        playerAnimation.addFrame(playerSpriteSheet.getSprite(stored[0]), frameDuration);
        playerAnimation.addFrame(playerSpriteSheet.getSprite(stored[1]), frameDuration);
        playerAnimation.addFrame(playerSpriteSheet.getSprite(stored[2]), frameDuration);
        playerAnimation.addFrame(playerSpriteSheet.getSprite(stored[1]), frameDuration);
    }

}
