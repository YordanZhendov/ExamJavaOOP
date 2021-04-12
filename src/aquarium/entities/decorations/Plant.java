package aquarium.entities.decorations;

public class Plant extends BaseDecoration {
    private static final double PRICE_PLANT = 10;
    private static final int COMFORT_PLANT = 5;

    public Plant() {
        super(COMFORT_PLANT, PRICE_PLANT);
    }
}
