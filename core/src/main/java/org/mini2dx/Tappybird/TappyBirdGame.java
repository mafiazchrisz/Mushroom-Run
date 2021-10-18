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
import org.mini2Dx.core.collision.CollisionBox;
import org.mini2Dx.core.game.BasicGame;
import org.mini2Dx.core.graphics.viewport.FitViewport;
import org.mini2Dx.core.graphics.viewport.Viewport;
import java.util.Random;

import static org.mini2dx.Tappybird.Sounds.*;

public class TappyBirdGame extends BasicGame {
    public static final String GAME_IDENTIFIER = "org.mini2Dx.TappyBird";
    public static float GRAVITY;
    public static float FLYING_SPEED;
    public static final float GAME_WIDTH = 800;
    public static final float GAME_HEIGHT = 500;
    public static int giftMove, giftPosition, separateGift = 10;
    private static boolean isGiftOccur = false, isImmortal = false;

    //These variables change how it feels to play the game.
    private static float GAME_GRAVITY = 0.6f;
    private float GAME_FLYING_SPEED = 8f;
    private static boolean IS_ROTATING = false;
    private static boolean IS_TESTING = false;
    private static int MAX_PILLARS = 8;
    public static int PILLAR_TIMING = 100;  //Lower will increase the frequency of pillars.
    private static float PILLAR_Y_MIN = -180f;
    private static float PILLAR_Y_MAX = -75f;
    private static float PILLAR_GAP_MIN = 175f;
    private static float PILLAR_GAP_MAX = 250f;

    //Game states and player score
    public static boolean inGame, isDead, isSkillStart = false;
    private static int playerScore, highScore;
    private int countDead, Collisionfield, choice, skillTime, numField = 9;

    Viewport fitViewport;
    InputHandler inputHandler;
    Player player;
    PlayerTexture playerTexture;
    TopBottomEdge ground1, ground2, upperRight;
    TopBottomEdgeTexture topBottomEdgeTexture;
    Background background1, background2;
    BackgroundTexture backgroundTexture;
    Pillars[] pillars;
    PillarTexture pillarTexture;
    UserInterface userInterface;
    UserInterfaceTexture userInterfaceTexture;
    PlayerData playerData;
    Sounds gameSounds;

    CollisionBox[] collisionRectanglesBottom, collisionRectanglesTop;

    int pillarIndexHead, pillarIndexTail;
    int pillarTiming;

    @Override
    public void initialise() {
        GRAVITY = 0.0f;
        FLYING_SPEED = GAME_FLYING_SPEED;
        inGame = false;
        isDead = false;
        playerScore = 0;

        pillars = new Pillars[MAX_PILLARS];
        collisionRectanglesBottom = new CollisionBox[MAX_PILLARS];
        collisionRectanglesTop = new CollisionBox[MAX_PILLARS];

        playerTexture = new PlayerTexture(4);  // input 1 for red
        // input 2 for yellow
        // input 3 for blue
        // input 4 for green
        backgroundTexture = new BackgroundTexture();
        pillarTexture = new PillarTexture();
        topBottomEdgeTexture = new TopBottomEdgeTexture();
        userInterfaceTexture = new UserInterfaceTexture();

        gameSounds = new Sounds();
        //gameSounds.loopBackgroundMusic();

        inputHandler = new InputHandler();
        player = new Player(playerTexture, IS_ROTATING, IS_TESTING);
        ground1 = new TopBottomEdge(topBottomEdgeTexture);
        ground2 = new TopBottomEdge(topBottomEdgeTexture);
        upperRight = new TopBottomEdge(topBottomEdgeTexture);
        ground2.generateHazardAtPos(GAME_WIDTH, GAME_HEIGHT - ground1.getGroundTextureHeight());
        background1 = new Background(backgroundTexture);
        background2 = new Background(backgroundTexture);
        background2.generateHazardAtPos(GAME_WIDTH, 0.0f);
        fitViewport = new FitViewport(GAME_WIDTH, GAME_HEIGHT);
        userInterface = new UserInterface(userInterfaceTexture);

        pillarTiming = 60;

        playerData = new PlayerData();
        playerData.loadPlayerData();
        highScore = playerData.getHighScore();
    }

    @Override
    public void update(float delta) {

        if (isDead) {
            if (inputHandler.spacePressed()) {
                if (playerScore>highScore){
                    playerData.savePlayerData(playerScore);
                }
                gameSounds.disposeBackgroundMusic();
                initialise();
            }
        } else {
            pillarTiming -= new Random().nextInt(2) + 1;

            if (inputHandler.spacePressed()) {
                GRAVITY = GAME_GRAVITY;
                inGame = true;
            }

            if (inGame && pillarTiming < 0) {
                pillarTiming = PILLAR_TIMING;
                pillars[pillarIndexTail] = new Pillars(pillarTexture, IS_ROTATING);
                pillars[pillarIndexTail].generateHazardAtPos(GAME_WIDTH + 100f,
                        randomFloatMinMax(PILLAR_Y_MIN, PILLAR_Y_MAX), randomFloatMinMax(PILLAR_GAP_MIN, PILLAR_GAP_MAX));
                collisionRectanglesBottom[pillarIndexTail] = pillars[pillarIndexTail].collisionRectBottom;
                collisionRectanglesTop[pillarIndexTail] = pillars[pillarIndexTail].collisionRectTop;
                pillarIndexTail = (pillarIndexTail + 1) % MAX_PILLARS;
            }

            for (int i = 0; i < MAX_PILLARS; i++) {
                if (pillars[i] != null) {
                    pillars[i].update(FLYING_SPEED);
                    if (pillars[pillarIndexHead] != null && pillars[pillarIndexHead].getHazardXPos() < -100) {
                        pillars[pillarIndexHead] = null;
                        collisionRectanglesBottom[pillarIndexHead] = null;
                        collisionRectanglesTop[pillarIndexHead] = null;
                        pillarIndexHead = (pillarIndexHead + 1) % MAX_PILLARS;
                    }
                }
            }

            player.update(inputHandler.spacePressed(), delta);
            ground1.update();
            ground2.update();
            background1.update();
            background2.update();

            if (player.getPlayerY() < 0 || player.getPlayerY() > GAME_HEIGHT - player.getPlayerTextureHeight()) {
                setDead();
            }
            if (getScore()==0){ // Set heart in game start
                choice = 0;
            }

            checkCollisions();
            increaseSpeed();
            giftOccur();
            skill();
        }
    }

    @Override
    public void render(Graphics g) {

        fitViewport.apply(g);
        background1.render(g);
        background2.render(g);
        player.render(g);

        for (int i = 0; i < MAX_PILLARS; i++) {
            if (pillars[i] != null) {
                pillars[i].render(g,IS_TESTING);
            }
        }

        ground1.render(g);
        ground2.render(g);
        upperRight.setWhichHeart(choice, g);
        userInterface.displayScore(g, playerScore);

        if (!inGame) {
            userInterface.displayGetReadyMessage(g);
            userInterface.displayHighscore(g,highScore);
        }

        if (isDead) {
            userInterface.displayGameOverMessage(g);
        }
    }

    float randomFloatMinMax(float min, float max) {
        float leftLimit = min;
        float rightLimit = max;

        if(new Random().nextBoolean()) return leftLimit;
        else return rightLimit;
        //return leftLimit + new Random().nextFloat() * (rightLimit - leftLimit);
    }

    void checkCollisions() {
        for (int i = 0; i < MAX_PILLARS; i++) {
            if (collisionRectanglesBottom[i] != null && !isImmortal) {
                if (player.playerCollisionBox.intersects(collisionRectanglesBottom[i])) {
                    setHeart();
                }
            }
        }
    }

    public static void setScore(int score) {
        playerScore = score;
        if(getScore() % 100 == 0){
            scoreup();
        }
    }

    public static int getScore() {
        return playerScore;
    }

    public static void increaseSpeed(){
        if(getScore() % 100 == 0 && getScore() != 0 && getScore()<500){ // Speed increase every 100 score of entire game
            FLYING_SPEED += 0.25f;
        }
    }
    public static void giftOccur(){
        if(isGiftOccur) giftMove++;
        if(getScore() == separateGift){
            separateGift = getScore();
            isGiftOccur = true;
            switch (new Random().nextInt() % 3) { // Random gift position height
                case 0:
                    giftPosition = 320;
                    break;
                case 1:
                    giftPosition = 200;
                    break;
                case 2:
                    giftPosition = 120;
                    break;
            }
            if(separateGift==10) giftPosition = 320;
            separateGift += whenGiftOccur(); // When gift occur again in soon
            giftMove = 0; // Stop move gift
        }
        if(giftMove*6==850){
            giftMove = 0;
            isGiftOccur = false;
        }
    }
    private static int whenGiftOccur() {
        return new Random().nextInt() % 10 + 60;
    }
    void setHeart(){
        if(getScore() % 100 == 0 && getScore() != 0 && getScore()<500) numField--;

        if(Collisionfield==numField) {
            countDead++;
            Collisionfield = 0;
            choice++;
        }else if(countDead == 3){
            setDead();
            countDead = 0;
        }
        Collisionfield++;
    }

    void setDead() {
        isDead = true;
        FLYING_SPEED = 0f;
        GRAVITY = 0f;
        deadSound();

    }
    public void skill(){
        if(giftMove==125 && (Player.getPlayerY()+40>giftPosition && Player.getPlayerY()-40<giftPosition) ) {
            FLYING_SPEED += 8f;
            isImmortal = true;
            isSkillStart = true;
        }else if(skillTime==200) {
            FLYING_SPEED -= 8f;
            isImmortal = false;
            skillTime = 0;
            isSkillStart = false;
        }
        if(isSkillStart) skillTime++;
    }
}
