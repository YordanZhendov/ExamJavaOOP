package aquarium.entities.fish;

public class FreshwaterFish extends BaseFish{
    private static final int SIZE_FRESH_FISH = 3;
    private static final int INCREASE_FRESH_SIZE = 3;

    public FreshwaterFish(String name, String species, double price) {
        super(name, species, price);
        setSize(SIZE_FRESH_FISH);
    }

    @Override
    public void eat() {
        setSize(INCREASE_FRESH_SIZE);
    }
}
