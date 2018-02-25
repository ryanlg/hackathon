import processing.core.PApplet;
import processing.core.PImage;

import java.awt.Point;

public abstract class Element implements Drawable {

    public PApplet processing;
    private Point position;
    private PImage image;

    public Element(PApplet processing) {

        this.processing = processing;
        this.position = new Point();
    }

    public abstract void setup();

    public void draw(){

        processing.imageMode(processing.CENTER);
        processing.image(this.getImage(), this.getPosition().x, this.getPosition().y);
    }

    public Point getPosition() {

        return position;
    }

    public PImage getImage() {

        return image;
    }

    public void setImage(PImage image) {

        this.image = image;
    }
}
