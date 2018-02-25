import processing.core.PApplet;

public class Guy extends Element {

    public Guy(PApplet processing) {

        super(processing);
    }

    @Override
    public void setup() {

        this.setImage(this.processing.loadImage("assets/upskier.png"));
    }
}
