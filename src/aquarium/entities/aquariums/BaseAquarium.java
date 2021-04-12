package aquarium.entities.aquariums;

import aquarium.common.ExceptionMessages;
import aquarium.entities.decorations.Decoration;
import aquarium.entities.fish.Fish;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public abstract class BaseAquarium implements Aquarium{
    private String name;
    private int capacity;
    private Collection<Decoration> decorations;
    private Collection<Fish> fish;

    protected BaseAquarium(String name, int capacity) {
        setName(name);
        setCapacity(capacity);
        fish=new ArrayList<>();
        decorations=new ArrayList<>();
    }

    @Override
    public String getName() {
        return name;
    }

    private void setName(String name) {
        if(name==null || name.trim().isEmpty()){
            throw new NullPointerException(ExceptionMessages.AQUARIUM_NAME_NULL_OR_EMPTY);
        }
        this.name = name;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    @Override
    public Collection<Decoration> getDecorations() {
        return decorations;
    }

    public void setDecorations(Collection<Decoration> decorations) {
        this.decorations = decorations;
    }

    @Override
    public Collection<Fish> getFish() {
        return fish;
    }

    public void setFish(Collection<Fish> fish) {
        this.fish = fish;
    }

    @Override
    public int calculateComfort() {
        int totalCom=0;
        for (Decoration decoration : decorations) {
            totalCom+=decoration.getComfort();
        }
        return totalCom;
    }

    @Override
    public void addFish(Fish fish) {
        this.fish.add(fish);
    }

    @Override
    public void removeFish(Fish fish) {
        this.fish.remove(fish);
    }

    @Override
    public void addDecoration(Decoration decoration) {
        this.decorations.add(decoration);
    }

    @Override
    public void feed() {

    }

    @Override
    public String getInfo() {
        StringBuilder sb=new StringBuilder();
        sb.append(String.format("%s (%s):",this.getName(),this.getClass().getSimpleName()))
                .append(System.lineSeparator());

        if(this.fish.size()==0){
            sb.append("none")
                .append(System.lineSeparator());
        }else {
            sb.append("Fish: ");
            List<String> names=new ArrayList<>();
            for (Fish fish1 : fish) {
                names.add(fish1.getName());
            }

            sb.append(String.join(" ",names));

            sb.append(System.lineSeparator());
        }
        sb.append(String.format("Decorations: %d",decorations.size()))
                .append(System.lineSeparator());
        sb.append(String.format("Comfort: %d",calculateComfort()))
                .append(System.lineSeparator());

        return sb.toString().trim();
    }
}
