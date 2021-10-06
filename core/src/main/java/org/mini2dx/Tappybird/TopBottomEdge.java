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

import org.mini2Dx.core.Graphics;

import java.util.Random;

import static org.mini2dx.Tappybird.TappyBirdGame.GAME_HEIGHT;
import static org.mini2dx.Tappybird.TappyBirdGame.GAME_WIDTH;

public class TopBottomEdge extends Hazards {
    private static final String whichHeart = ". . .";

    TopBottomEdgeTexture topBottomEdgeTexture;
    private float groundTextureHeight;

    public TopBottomEdge(TopBottomEdgeTexture topBottomEdgeModel) {
        this.topBottomEdgeTexture = topBottomEdgeModel;
        groundTextureHeight = topBottomEdgeModel.groundTexture.getHeight();
    }

    @Override
    void update() {
        super.update();
        if (point.getX() < -GAME_WIDTH-5) {
            point.setX(GAME_WIDTH-5);
        }
    }

    void render(Graphics g) {
        g.drawTexture(topBottomEdgeTexture.groundTexture, point.getX(),
                GAME_HEIGHT - topBottomEdgeTexture.groundTexture.getHeight());
        TappyBirdGame tap = new TappyBirdGame();
        g.drawTexture(topBottomEdgeTexture.gift, 850- tap.giftMove*6, tap.giftPosition);
        //    g.drawTexture(topBottomEdgeTexture.ceilingTexture, point.getX(), 0f);

    }

    public void setWhichHeart(int choice, Graphics g){
        if(choice==0) g.drawTexture(topBottomEdgeTexture.heartF,670,10);
        else if(choice==1) g.drawTexture(topBottomEdgeTexture.heartH,670,10);
        else g.drawTexture(topBottomEdgeTexture.heartL,670,10);

    }


    float getGroundTextureHeight() {
        return groundTextureHeight;
    }
}