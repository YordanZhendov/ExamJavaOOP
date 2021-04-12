package aquarium.entities.decorations;

public class Ornament extends BaseDecoration {
    private static final double PRICE_ORNAMENT = 5;
    private static final int COMFORT_ORNAMENT = 1;

    public Ornament() {
        super(COMFORT_ORNAMENT, PRICE_ORNAMENT);
    }
    
}
