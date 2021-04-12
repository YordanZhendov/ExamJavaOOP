package aquarium.entities.fish;

public class SaltwaterFish extends BaseFish{
    private static final int SIZE_WATER_FISH = 5;
    private static final int INCREASE_SIZE = 2;

    public SaltwaterFish(String name, String species, double price) {
        super(name, species, price);
        setSize(SIZE_WATER_FISH);
    }

    @Override
    public void eat() {
        setSize(INCREASE_SIZE);
    }
}
