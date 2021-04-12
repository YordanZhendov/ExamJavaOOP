package aquarium.entities.fish;

import aquarium.common.ExceptionMessages;

public abstract class BaseFish implements Fish{
    private String name;
    private String species;
    private int size;
    private double price;

    protected BaseFish(String name, String species, double price) {
        setName(name);
        setSpecies(species);
        setPrice(price);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        if(name == null || name.trim().isEmpty()){
            throw new NullPointerException(ExceptionMessages.FISH_NAME_NULL_OR_EMPTY);
        }
        this.name = name;
    }

    public String getSpecies() {
        return species;
    }

    private void setSpecies(String species) {
        if(species==null || species.trim().isEmpty()){
            throw new NullPointerException(ExceptionMessages.SPECIES_NAME_NULL_OR_EMPTY);
        }
        this.species = species;
    }

    @Override
    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = this.size+size;
    }

    @Override
    public double getPrice() {
        return price;
    }

    private void setPrice(double price) {
        if(price<=0){
            throw new IllegalArgumentException(ExceptionMessages.FISH_PRICE_BELOW_OR_EQUAL_ZERO);
        }
        this.price = price;
    }

}
