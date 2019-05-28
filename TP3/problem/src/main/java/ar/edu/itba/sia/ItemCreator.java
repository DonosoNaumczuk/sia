package ar.edu.itba.sia;

import ar.edu.itba.sia.equipment.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

public class ItemCreator {

    private static String BOOTS_FILE = "problem/src/main/java/ar/edu/itba/sia/testdata/botas.tsv";
    private static String BREAST_PLATE_FILE = "problem/src/main/java/ar/edu/itba/sia/testdata/pecheras.tsv";
    private static String GLOVES_FILE = "problem/src/main/java/ar/edu/itba/sia/testdata/guantes.tsv";
    private static String HELMET_FILE = "problem/src/main/java/ar/edu/itba/sia/testdata/cascos.tsv";
    private static String WEAPON_FILE = "problem/src/main/java/ar/edu/itba/sia/testdata/armas.tsv";

    private static int ID_POSITION = 0;
    private static int STRENGTH_POSITION = 1;
    private static int AGILITY_POSITION = 2;
    private static int EXPERTISE_POSITION = 3;
    private static int RESILIENCE_POSITION = 4;
    private static int VITALITY_POSITION = 5;

    public static void createAllFromFiles() {
        createBoots();
        createBreastPlates();
        createGloves();
        createHelmets();
        createWeapons();
    }

    private static void createBoots() {
        Object[] objectBoots = createItemsFromTSVFile(BOOTS_FILE,
                attr -> Item.newBoots( Integer.valueOf(attr[ID_POSITION]),
                                        Double.valueOf(attr[STRENGTH_POSITION]),
                                        Double.valueOf(attr[AGILITY_POSITION]),
                                        Double.valueOf(attr[EXPERTISE_POSITION]),
                                        Double.valueOf(attr[RESILIENCE_POSITION]),
                                        Double.valueOf(attr[VITALITY_POSITION])))
                .toArray();
        Boots[] boots = Arrays.copyOf(objectBoots, objectBoots.length, Boots[].class);
        ItemStorage.getInstance().setBoots(boots);
    }

    private static void createBreastPlates() {
        Object[] objectBreastPlates = createItemsFromTSVFile(BREAST_PLATE_FILE,
                attr -> Item.newBreastPlate( Integer.valueOf(attr[ID_POSITION]),
                                            Double.valueOf(attr[STRENGTH_POSITION]),
                                            Double.valueOf(attr[AGILITY_POSITION]),
                                            Double.valueOf(attr[EXPERTISE_POSITION]),
                                            Double.valueOf(attr[RESILIENCE_POSITION]),
                                            Double.valueOf(attr[VITALITY_POSITION])))
                .toArray();
        BreastPlate[] breastPlates = Arrays.copyOf(objectBreastPlates, objectBreastPlates.length, BreastPlate[].class);
        ItemStorage.getInstance().setBreastPlates(breastPlates);
    }

    private static void createGloves() {
        Object[] objectGloves = createItemsFromTSVFile(GLOVES_FILE,
                attr -> Item.newGloves( Integer.valueOf(attr[ID_POSITION]),
                                        Double.valueOf(attr[STRENGTH_POSITION]),
                                        Double.valueOf(attr[AGILITY_POSITION]),
                                        Double.valueOf(attr[EXPERTISE_POSITION]),
                                        Double.valueOf(attr[RESILIENCE_POSITION]),
                                        Double.valueOf(attr[VITALITY_POSITION])))
                .toArray();
        Gloves[] gloves = Arrays.copyOf(objectGloves, objectGloves.length, Gloves[].class);
        ItemStorage.getInstance().setGloves(gloves);
    }

    private static void createHelmets() {
        Object[] objectHelmets = createItemsFromTSVFile(HELMET_FILE,
                attr -> Item.newHelmet( Integer.valueOf(attr[ID_POSITION]),
                                    Double.valueOf(attr[STRENGTH_POSITION]),
                                    Double.valueOf(attr[AGILITY_POSITION]),
                                    Double.valueOf(attr[EXPERTISE_POSITION]),
                                    Double.valueOf(attr[RESILIENCE_POSITION]),
                                    Double.valueOf(attr[VITALITY_POSITION])))
                .toArray();
        Helmet[] helmets = Arrays.copyOf(objectHelmets, objectHelmets.length, Helmet[].class);
        ItemStorage.getInstance().setHelmets(helmets);
    }

    private static void createWeapons() {
        Object[] objectWeapons = createItemsFromTSVFile(WEAPON_FILE,
                attr -> Item.newWeapon( Integer.valueOf(attr[ID_POSITION]),
                                        Double.valueOf(attr[STRENGTH_POSITION]),
                                        Double.valueOf(attr[AGILITY_POSITION]),
                                        Double.valueOf(attr[EXPERTISE_POSITION]),
                                        Double.valueOf(attr[RESILIENCE_POSITION]),
                                        Double.valueOf(attr[VITALITY_POSITION])))
                .toArray();
        Weapon[] weapons = Arrays.copyOf(objectWeapons, objectWeapons.length, Weapon[].class);
        ItemStorage.getInstance().setWeapons(weapons);
    }

    private static List<Item> createItemsFromTSVFile(String file, Function<String[] , ? extends Item> itemCreator) {
        try (BufferedReader csvReader = new BufferedReader(new FileReader(file))) {
            String row;
            LinkedList<Item> items = new LinkedList<>();
            csvReader.readLine(); // ignore first line which just has column headers.
            while ((row = csvReader.readLine()) != null) {
                String[] attributes = row.split("\t");
                items.add(itemCreator.apply(attributes));
            }
            return items;
        }
        catch (IOException e) {
            return new LinkedList<>();
        }
    }
}
