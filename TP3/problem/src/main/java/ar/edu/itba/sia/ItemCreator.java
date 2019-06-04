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

    private static String BOOTS_FILE        = "/botas.tsv";
    private static String BREAST_PLATE_FILE = "/pecheras.tsv";
    private static String GLOVES_FILE       = "/guantes.tsv";
    private static String HELMET_FILE       = "/cascos.tsv";
    private static String WEAPON_FILE       = "/armas.tsv";

    private static int ID_POSITION          = 0;
    private static int STRENGTH_POSITION    = 1;
    private static int AGILITY_POSITION     = 2;
    private static int EXPERTISE_POSITION   = 3;
    private static int RESILIENCE_POSITION  = 4;
    private static int VITALITY_POSITION    = 5;

    public static void createAllFromFiles(String path) {
        createBoots(path);
        createBreastPlates(path);
        createGloves(path);
        createHelmets(path);
        createWeapons(path);
    }

    private static void createBoots(String path) {
        Object[] objectBoots = createItemsFromTSVFile(path + BOOTS_FILE,
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

    private static void createBreastPlates(String path) {
        Object[] objectBreastPlates = createItemsFromTSVFile(path + BREAST_PLATE_FILE,
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

    private static void createGloves(String path) {
        Object[] objectGloves = createItemsFromTSVFile(path + GLOVES_FILE,
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

    private static void createHelmets(String path) {
        Object[] objectHelmets = createItemsFromTSVFile(path + HELMET_FILE,
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

    private static void createWeapons(String path) {
        Object[] objectWeapons = createItemsFromTSVFile(path + WEAPON_FILE,
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
        try (BufferedReader tsvReader = new BufferedReader(new FileReader(file))) {
            String row;
            LinkedList<Item> items = new LinkedList<>();
            tsvReader.readLine(); // ignore first line which just has column headers.
            while ((row = tsvReader.readLine()) != null) {
                String[] attributes = row.split("\t");
                items.add(itemCreator.apply(attributes));
            }
            return items;
        }
        catch (IOException e) {
            e.printStackTrace();
            return new LinkedList<>();
        }
    }
}
