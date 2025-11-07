import java.util.*;

public class DataTable {
    private HashMap<String, BirdInfo> table;

    public DataTable() {
        table = new HashMap<>();

        // 1. Acorn Woodpecker
        HashSet<String> h1 = new HashSet<String>(Set.of("forest"));
        PowerBehavior behavior = new GainFoodBehavior(1, "wheat", true);
        addBird(new BirdInfo(
            "Acorn Woodpecker",
            h1,
            new String[][]{{"wheat"}, {"wheat"}, {"wheat"}},
            5,
            "cavity",
            4,
            46,
            "brown",
            behavior
        ));

        // 2. American Avocet
        HashSet<String> h2 = new HashSet<String>(Set.of("wetland"));
        PowerBehavior behavior = new LayEggBehavior(1, false, "ground");
        addBird(new BirdInfo(
            "American Avocet",
            h2,
            new String[][]{{"invertebrate"}, {"invertebrate"}, {"wheat"}},
            6,
            "ground",
            2,
            107,
            "pink",
            behavior
        ));

        // 3. American Bittern
        HashSet<String> h3 = new HashSet<String>(Set.of("wetland"));
        PowerBehavior behavior = new DrawCardBehavior(1, 0);
        addBird(new BirdInfo(
            "American Bittern",
            h3,
            new String[][]{{"invertebrate"}, {"fish"}, {"rodent"}},
            7,
            "platform",
            2,
            107,
            "brown",
            behavior
        ));

        // 4. American Coot
        HashSet<String> h4 = new HashSet<String>(Set.of("wetland"));
        PowerBehavior behavior = new TuckCardBehavior(1, true, DrawCardBehavior(1, 0));
        addBird(new BirdInfo(
            "American Coot",
            h4,
            new String[][]{{"wheat"}, {"choice"}},
            3,
            "platform",
            5,
            51,
            "brown",
            behavior
        ));

        // 5. American Crow
        HashSet<String> h5 = new HashSet<String>(Set.of("wetland", "forest", "grassland"));
        PowerBehavior behavior = new EggForFood(1, 1, false);
        addBird(new BirdInfo(
            "American Crow",
            h5,
            new String[][]{{"choice"}},
            4,
            "platform",
            2,
            99,
            "brown",
            behavior
        ));

        // 6. American Goldfinch
        HashSet<String> h6 = new HashSet<String>(Set.of("grassland"));
        PowerBehavior behavior = new GainFoodBehavior(3, "wheat");
        addBird(new BirdInfo(
            "American Goldfinch",
            h6,
            new String[][]{{"wheat"}, {"wheat"}},
            3,
            "bowl",
            3,
            23,
            "white",
            behavior
        ));

        // 7. American Kestrel
        HashSet<String> h7 = new HashSet<String>(Set.of("grassland"));
        PowerBehavior behavior = new
        addBird(new BirdInfo(
            "American Kestrel",
            h7,
            new String[][]{{"invertebrate"}, {"rodent"}},
            5,
            "cavity",
            3,
            56,
            "brown",
            behavior
        ));

        // 8. American Oystercatcher
        HashSet<String> h8 = new HashSet<String>(Set.of("wetland"));
        PowerBehavior behavior = new
        addBird(new BirdInfo(
            "American Oystercatcher",
            h8,
            new String[][]{{"invertebrate"}, {"invertebrate"}},
            5,
            "ground",
            2,
            81,
            "white",
            behavior
        ));

        // 9. American Redstart
        HashSet<String> h9 = new HashSet<String>(Set.of("forest"));
        PowerBehavior behavior = new
        addBird(new BirdInfo(
            "American Redstart",
            h9,
            new String[][]{{"invertebrate"}, {"berry"}},
            4,
            "bowl",
            2,
            20,
            "brown",
            behavior
        ));

        // 10. American Robin
        HashSet<String> h10 = new HashSet<String>(Set.of("forest", "grassland"));
        PowerBehavior behavior = new
        addBird(new BirdInfo(
            "American Robin",
            h10,
            new String[][]{{"invertebrate", "berry"}},
            1,
            "bowl",
            4,
            43,
            "brown",
            behavior
        ));

        // 11. American White Pelican
        HashSet<String> h11 = new HashSet<String>(Set.of("wetland"));
        addBird(new BirdInfo(
            "American White Pelican",
            h11,
            new String[][]{{"fish"}, {"fish"}},
            5,
            "ground",
            1,
            274,
            "brown",
            behavior
        ));

        // 12. American Woodcock
        HashSet<String> h12 = new HashSet<String>(Set.of("forest", "grassland"));
        addBird(new BirdInfo(
            "American Woodcock",
            h12,
            new String[][]{{"invertebrate"}, {"invertebrate"}, {"wheat"}},
            9,
            "ground",
            2,
            46,
            "white",
            behavior
        ));
        
        // 13. Anhinga
        HashSet<String> h13 = new HashSet<String>(Set.of("wetland"));
        addBird(new BirdInfo(
            "Anhinga",
            h13,
            new String[][]{{"fish"}, {"fish"}},
            6,
            "platform",
            2,
            114,
            "brown",
            behavior
        ));
        
        // 14. Anna's Hummingbird
        HashSet<String> h14 = new HashSet<String>(Set.of("wetland", "forest", "grassland"));
        addBird(new BirdInfo(
            "Anna's Hummingbird",
            h14,
            new String[][]{{"choice"}},
            4,
            "bowl",
            2,
            13,
            "brown",
            behavior
        ));
        
        // 15. Ash-Throated Flycatcher
        HashSet<String> h15 = new HashSet<String>(Set.of("grassland"));
        addBird(new BirdInfo(
            "Ash-Throated Flycatcher",
            h15,
            new String[][]{{"invertebrate"}, {"invertebrate"}, {"berry"}},
            4,
            "cavity",
            4,
            30,
            "white",
            behavior
        ));
        
        // 16. Atlantic Puffin
        HashSet<String> h16 = new HashSet<String>(Set.of("wetland"));
        addBird(new BirdInfo(
            "Atlantic Puffin",
            h16,
            new String[][]{{"fish"}, {"fish"}, {"fish"}},
            8,
            "star",
            1,
            53,
            "white",
            behavior
        ));
        
        // 17. Baird's Sparrow
        HashSet<String> h17 = new HashSet<String>(Set.of("grassland"));
        addBird(new BirdInfo(
            "Baird's Sparrow",
            h17,
            new String[][]{{"invertebrate"}, {"wheat"}},
            3,
            "ground",
            2,
            23,
            "brown",
            behavior
        ));
        
        // 18. Bald Eagle
        HashSet<String> h18 = new HashSet<String>(Set.of("wetland"));
        addBird(new BirdInfo(
            "Bald Eagle",
            h18,
            new String[][]{{"fish"}, {"fish"}, {"rodent"}},
            9,
            "platform",
            1,
            203,
            "white",
            behavior
        ));
        
        // 19. Baltimore Oriole
        HashSet<String> h19 = new HashSet<String>(Set.of("forest"));
        addBird(new BirdInfo(
            "Baltimore Oriole",
            h19,
            new String[][]{{"invertebrate"}, {"berry"}, {"berry"}},
            9,
            "star",
            2,
            30,
            "brown",
            behavior
        ));
        
        // 20. Barn Owl
        HashSet<String> h20 = new HashSet<String>(Set.of("forest", "wetland", "grassland"));
        addBird(new BirdInfo(
            "Barn Owl",
            h20,
            new String[][]{{"rodent"}, {"rodent"}},
            5,
            "cavity",
            4,
            107,
            "brown",
            behavior
        ));
        
        // 21. Barn Swallow
        HashSet<String> h21 = new HashSet<String>(Set.of("wetland", "grassland"));
        addBird(new BirdInfo(
            "Barn Swallow",
            h21,
            new String[][]{{"invertebrate"}},
            1,
            "star",
            3,
            38,
            "brown",
            behavior
        ));
        
        // 22. Barred Owl
        HashSet<String> h22 = new HashSet<String>(Set.of("forest"));
        addBird(new BirdInfo(
            "Barred Owl",
            h22,
            new String[][]{{"rodent"}},
            3,
            "cavity",
            2,
            107,
            "brown",
            behavior
        ));
        
        // 23. Barrow's Goldeneye
        HashSet<String> h23 = new HashSet<String>(Set.of("wetland"));
        addBird(new BirdInfo(
            "Barrow's Goldeneye",
            h23,
            new String[][]{{"invertebrate"}, {"wheat"}, {"fish"}},
            5,
            "cavity",
            4,
            71,
            "pink",
            behavior
        ));
        
        // 24. Bell's Vired
        HashSet<String> h24 = new HashSet<String>(Set.of("grassland", "forest"));
        addBird(new BirdInfo(
            "Bell's Vired",
            h24,
            new String[][]{{"invertebrate"}, {"invertebrate"}},
            4,
            "star",
            2,
            18,
            "white",
            behavior
        ));
        
        // 25. Belted Kingfisher
        HashSet<String> h25 = new HashSet<String>(Set.of("wetland"));
        addBird(new BirdInfo(
            "Belted Kingfisher",
            h25,
            new String[][]{{"fish"}, {"choice"}},
            4,
            "star",
            4,
            53,
            "pink",
            behavior
        ));

        // 26. Bewick's Wren
        HashSet<String> h26 = new HashSet<String>(Set.of("grassland", "forest", "wetland"));
        addBird(new BirdInfo(
            "Bewick's Wren",
            h26,
            new String[][]{{"invertebrate"}, {"invertebrate"}, {"wheat"}},
            4,
            "cavity",
            3,
            18,
            "brown",
            behavior
        ));
        
        // 27. Black Skimmer
        HashSet<String> h27 = new HashSet<String>(Set.of("wetland"));
        addBird(new BirdInfo(
            "Black Skimmer",
            h27,
            new String[][]{{"fish"}, {"fish"}},
            6,
            "ground",
            2,
            112,
            "brown",
            behavior
        ));
        
        // 28. Black Tern
        HashSet<String> h28 = new HashSet<String>(Set.of("wetland"));
        addBird(new BirdInfo(
            "Black Tern",
            h28,
            new String[][]{{"invertebrate", "fish"}},
            4,
            "star",
            2,
            61,
            "brown",
            behavior
        ));
        
        // 29. Black Vulture
        HashSet<String> h29 = new HashSet<String>(Set.of("forest"));
        addBird(new BirdInfo(
            "Black Vulture",
            h29,
            new String[][]{{""}},
            2,
            "cavity",
            1,
            150,
            "pink",
            behavior
        ));
        
        // 30. Black-Bellied Whistling-Duck
        HashSet<String> h30 = new HashSet<String>(Set.of("wetland"));
        addBird(new BirdInfo(
            "Black-Bellied Whistling-Duck",
            h30,
            new String[][]{{"wheat"}, {"wheat"}},
            2,
            "cavity",
            5,
            76,
            "brown",
            behavior
        ));
        
        // 31. Black-Billed Magpie
        HashSet<String> h31 = new HashSet<String>(Set.of("grassland"));
        addBird(new BirdInfo(
            "Black-Billed Magpie",
            h31,
            new String[][]{{"choice"}, {"choice"}},
            3,
            "star",
            3,
            64,
            "pink",
            behavior
        ));
        
        // 32. Black-Chinned Hummingbird
        HashSet<String> h32 = new HashSet<String>(Set.of("grassland", "forest", "wetland"));
        addBird(new BirdInfo(
            "Black-Chinned Hummingbird",
            h32,
            new String[][]{{"choice"}},
            4,
            "bowl",
            2,
            8,
            "brown",
            behavior
        ));
    }

    public void addBird(BirdInfo bird) {
        table.put(bird.getName(), bird);
    }

    public BirdInfo getBird(String name) {
        return table.get(name);
    }

    public int size() {
        return table.size();
    }
}
