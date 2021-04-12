package aquarium.core;

import aquarium.common.ConstantMessages;
import aquarium.common.ExceptionMessages;
import aquarium.entities.aquariums.Aquarium;
import aquarium.entities.aquariums.BaseAquarium;
import aquarium.entities.aquariums.FreshwaterAquarium;
import aquarium.entities.aquariums.SaltwaterAquarium;
import aquarium.entities.decorations.Decoration;
import aquarium.entities.decorations.Ornament;
import aquarium.entities.decorations.Plant;
import aquarium.entities.fish.BaseFish;
import aquarium.entities.fish.Fish;
import aquarium.entities.fish.FreshwaterFish;
import aquarium.entities.fish.SaltwaterFish;
import aquarium.repositories.DecorationRepository;

import java.util.ArrayList;
import java.util.Collection;

public class ControllerImpl implements Controller{

    private DecorationRepository decorationRepository;
    private Collection<BaseAquarium> aquariums;

    public ControllerImpl() {
        decorationRepository=new DecorationRepository();
        aquariums=new ArrayList<>();
    }

    @Override
    public String addAquarium(String aquariumType, String aquariumName) {
        BaseAquarium aquarium;
        if(aquariumType.equals("FreshwaterAquarium")){
            aquarium=new FreshwaterAquarium(aquariumName);
        }else if(aquariumType.equals("SaltwaterAquarium")){
            aquarium=new SaltwaterAquarium(aquariumName);
        }else {
            throw new NullPointerException(ExceptionMessages.INVALID_AQUARIUM_TYPE);
        }
        this.aquariums.add(aquarium);
        return String.format(ConstantMessages.SUCCESSFULLY_ADDED_AQUARIUM_TYPE,aquariumType);
    }

    @Override
    public String addDecoration(String type) {
        Decoration decoration;
        if(type.equals("Ornament")){
            decoration=new Ornament();
        }else if(type.equals("Plant")){
            decoration=new Plant();
        }else {
            throw new IllegalArgumentException(ExceptionMessages.INVALID_DECORATION_TYPE);
        }
        decorationRepository.add(decoration);
        return String.format(ConstantMessages.SUCCESSFULLY_ADDED_DECORATION_TYPE,type);
    }

    @Override
    public String insertDecoration(String aquariumName, String decorationType) {
        Decoration decoration=this.decorationRepository.findByType(decorationType);
        if(decoration==null){
            throw new IllegalArgumentException(String.format(ExceptionMessages.NO_DECORATION_FOUND,decorationType));
        }

        for (BaseAquarium aquarium : aquariums) {
            if(aquarium.getName().equals(aquariumName)){
                aquarium.addDecoration(decoration);
                decorationRepository.remove(decoration);
                return String.format(ConstantMessages.SUCCESSFULLY_ADDED_DECORATION_IN_AQUARIUM,decorationType,aquariumName);
            }
        }
        return null;
    }

    @Override
    public String addFish(String aquariumName, String fishType, String fishName, String fishSpecies, double price) {
        BaseFish baseFish;
        if(fishType.equals("FreshwaterFish")){
            baseFish=new FreshwaterFish(fishName,fishSpecies,price);
        }else if(fishType.equals("SaltwaterFish")){
            baseFish=new SaltwaterFish(fishName,fishSpecies,price);
        }else {
            throw new IllegalArgumentException(ExceptionMessages.INVALID_FISH_TYPE);
        }

        BaseAquarium baseAquarium = this.aquariums.stream().filter(p -> p.getName().equals(aquariumName)).findFirst().orElse(null);
        if(baseAquarium.getClass().getSimpleName().equals("FreshwaterAquarium") && fishType.equals("SaltwaterFish")){
            return ConstantMessages.WATER_NOT_SUITABLE;
        }else if(baseAquarium.getClass().getSimpleName().equals("SaltwaterAquarium") && fishType.equals("FreshwaterFish")){
            return ConstantMessages.WATER_NOT_SUITABLE;
        }

        int totalSum=baseAquarium.getCapacity()-baseFish.getSize();
        if(totalSum < 0){
            return ConstantMessages.NOT_ENOUGH_CAPACITY;
        }

        baseAquarium.addFish(baseFish);
        baseAquarium.setCapacity(totalSum);

        return String.format(ConstantMessages.SUCCESSFULLY_ADDED_FISH_IN_AQUARIUM, fishType,aquariumName);
    }

    @Override
    public String feedFish(String aquariumName) {
        BaseAquarium aquarium=this.aquariums.stream().filter(p->p.getName().equals(aquariumName)).findFirst().orElse(null);
        long count = aquarium.getFish().stream().count();
        return String.format(ConstantMessages.FISH_FED,count);
    }

    @Override
    public String calculateValue(String aquariumName) {
        BaseAquarium baseAquarium=aquariums.stream().filter(p->p.getName().equals(aquariumName)).findFirst().orElse(null);
        double priceFish=0.0;
        for (Fish fish : baseAquarium.getFish()) {
            priceFish+=fish.getPrice();
        }
        double priceDeco=0.0;
        for (Decoration decoration : baseAquarium.getDecorations()) {
            priceDeco+=decoration.getPrice();
        }
        double i=priceDeco+priceFish;
        return String.format(ConstantMessages.VALUE_AQUARIUM,aquariumName,i);
    }

    @Override
    public String report() {
        StringBuilder sb=new StringBuilder();
        for (BaseAquarium aquarium : aquariums) {
            sb.append(aquarium.getInfo());
            sb.append(System.lineSeparator());
        }

        return sb.toString().trim();
    }
}
