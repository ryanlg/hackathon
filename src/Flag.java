import processing.core.PApplet;

public class Flag extends Element {

    public Flag(PApplet processing) {

        super(processing);
    }

    @Override
    public void draw() {

        this.processing.image(this.getImage(), this.getPosition().x, this.getPosition().y,
                              Constants.FLAG_WIDTH, Constants.FLAG_HEIGHT);
    }

    @Override
    public void setup() {

        this.setImage(this.processing.loadImage("assets/flag.png"));
    }
}
