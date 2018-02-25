import processing.core.*;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

public class Main extends PApplet {

    private ArrayList<Element> elements;

    private Random random;

    private Guy guy;
    private ArrayList<Flag> flags;

    private int[] lane; // middle x of lane

    private int loopCount;
    private boolean running;
    private int guyLane;

    public static void main(String[] args) {

        PApplet.main("Main");
    }

    public Main() {

        this.elements = new ArrayList<>(10);
        this.flags = new ArrayList<>(10);
        this.random = new Random();
        this.lane = new int[Constants.LANE_TOTAL];
        this.running = true;
        this.guyLane = 1;
    }

    public void setup() {

        background(255);

        int width = (this.width - Constants.MARGIN * 2) / Constants.LANE_TOTAL;
        for (int i = 0; i < lane.length; i++) {

            lane[i] = Constants.MARGIN + width / 2 + width * i;
        }

        this.guy = new Guy(this);
        guy.getPosition().move(400, 450);
        elements.add(guy);

        for (int i = 0; i < Constants.FLAG_TOTAL; i++) {

            Flag flag = new Flag(this);
            flag.getPosition().setLocation(rerandomizeFlagPosition());
            flags.add(flag);
            elements.add(flag);
        }

        for (Element element : elements) {

            element.setup();
        }
    }

    public void settings() {

        size(Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
    }

    public void draw() {

        background(255);

        for (Element element : elements) {

            element.draw();
        }

        if (running) {

            this.moveFlagsDown();
            this.checkFlags();
            if (this.checkCollision()) {

                running = false;
                fill(0);
                textSize(100);
                text("git gud", 350, 300);
            }
        }else {

            fill(0);
            textSize(100);
            text("git gud", 350, 300);
        }

        if (loopCount == Constants.SPEED_INCREMENT) {

            Constants.SPEED += 1;
            loopCount = 0;
        }
        loopCount += 1;
    }

    public void keyPressed(){

        switch (this.key) {

            case 'a':
            case 'A':{

                if (guyLane != 0) {
                    guyLane -= 1;
                }

                guy.getPosition().x = lane[guyLane];
                break;
            }

            case 'd':
            case 'D':{

                if (guyLane != 2) {
                    guyLane += 1;
                }

                guy.getPosition().x = lane[guyLane];
                break;
            }

        }
    }

    private int randomizeFlagLane() {

        return lane[random.nextInt(Constants.LANE_TOTAL)];
    }

    private Point rerandomizeFlagPosition() {

        Point point = new Point(randomizeFlagLane(), -100);
        return point;
    }

    private void moveFlagsDown() {

        for (Flag flag : flags) {

            flag.getPosition().y += Constants.SPEED;
        }
    }

    private void checkFlags() {

        for (Flag flag : flags) {

            if (flag.getPosition().y > this.height + flag.getImage().height / 2) {

                flag.getPosition().setLocation(rerandomizeFlagPosition());
            }
        }
    }

    private boolean checkCollision() {

        int guySizeXHalf = this.guy.getImage().width / 2;
        int guySizeYHalf = this.guy.getImage().height / 2;
        int guyLeft = this.guy.getPosition().x - guySizeXHalf;
        int guyRight = this.guy.getPosition().x + guySizeXHalf;
        int guyTop = this.guy.getPosition().y - guySizeYHalf;
        int guyBottom = this.guy.getPosition().y + guySizeYHalf;
        int flagSizeXHalf = this.flags.get(0).getImage().width / 2;
        int flagSizeYHalf = this.flags.get(0).getImage().height / 2;

        int flagLeft, flagRight, flagTop, flagBottom;
        for (Flag flag : flags) {
            flagLeft = flag.getPosition().x - flagSizeXHalf;
            flagRight = flag.getPosition().x + flagSizeXHalf;
            flagTop = flag.getPosition().y - flagSizeYHalf;
            flagBottom = flag.getPosition().y + flagSizeYHalf;

            // check guy left to flag right // check guy right to flag left
            if ((guyLeft < flagRight && guyLeft > flagLeft) ||
                    (guyRight > flagLeft && guyRight < flagRight)) {
                if ((guyTop > flagBottom && guyTop < flagTop) ||
                        (guyBottom < flagTop && guyBottom > flagBottom)) {

                    return true;
                }
            }
        }

//        for (Flag flag : flags) {
//
//            int flagX = this.guy.getPosition().x;
//            int guyX = this.guy.getPosition().x;
//
//            int guySizeYHalf = this.guy.getImage().height / 2;
//            int guyTop = this.guy.getPosition().y - guySizeYHalf;
//            int guyBottom = this.guy.getPosition().y + guySizeYHalf;
//            int flagSizeYHalf = this.flags.get(0).getImage().height / 2;
//            int flagTop = flag.getPosition().y - flagSizeYHalf;
//            int flagBottom = flag.getPosition().y + flagSizeYHalf;
//
//            if (flagX == guyX) {
//
//                if((guyTop < flagBottom && guyBottom > flagBottom) ||
//                        (guyTop > flagTop && guyBottom < flagTop)){
//
//                    return true;
//                }
//            }
//        }

        return false;
    }
}
