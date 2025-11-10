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

        // 33. Black-Crowned Night-Heron
        HashSet<String> h33 = new HashSet<String>(Set.of("wetland"));
        addBird(new BirdInfo(
            "Black-Crowned Night-Heron",
            h33,
            new String[][]{{"invertebrate"}, {"fish"}, {"rodent"}},
            9,
            "platform",
            2,
            112,
            "brown",
            behavior
        ));

        // 34. Black-Necked Stilt
        HashSet<String> h34 = new HashSet<String>(Set.of("wetland"));
        addBird(new BirdInfo(
            "Black-Necked Stilt",
            h34,
            new String[][]{{"invertebrate"}, {"invertebrate"}},
            4,
            "ground",
            2,
            74,
            "white",
            behavior
        ));

        // 35. Blue Grosbeak
        HashSet<String> h35 = new HashSet<String>(Set.of("wetland", "forest", "grassland"));
        addBird(new BirdInfo(
            "Blue Grosbeak",
            h35,
            new String[][]{{"wheat"}, {"wheat"}, {"invertebrate"}},
            4,
            "bowl",
            3,
            28,
            "brown",
            behavior
        ));

        // 36. Blue Jay
        HashSet<String> h36 = new HashSet<String>(Set.of("forest"));
        addBird(new BirdInfo(
            "Blue Jay",
            h36,
            new String[][]{{"wheat"}, {"choice"}},
            3,
            "bowl",
            2,
            41,
            "brown",
            behavior
        ));

        // 37. Blue-Gray Gnatcatcher
        HashSet<String> h37 = new HashSet<String>(Set.of("forest"));
        addBird(new BirdInfo(
            "Blue-Gray Gnatcatcher",
            h37,
            new String[][]{{"invertebrate"}},
            1,
            "bowl",
            3,
            15,
            "brown",
            behavior
        ));

        // 38. Blue-Winged Warbler
        HashSet<String> h38 = new HashSet<String>(Set.of("forest", "grassland"));
        addBird(new BirdInfo(
            "Blue-Winged Warbler",
            h38,
            new String[][]{{"invertebrate"}, {"invertebrate"}},
            8,
            "bowl",
            2,
            20,
            "white",
            behavior
        ));

        // 39. Bobolink
        HashSet<String> h39 = new HashSet<String>(Set.of("grassland"));
        addBird(new BirdInfo(
            "Bobolink",
            h39,
            new String[][]{{"wheat"}, {"wheat"}, {"invertebrate"}},
            4,
            "ground",
            3,
            30,
            "white",
            behavior
        ));

        // 40. Brant
        HashSet<String> h40 = new HashSet<String>(Set.of("wetland"));
        addBird(new BirdInfo(
            "Brant",
            h40,
            new String[][]{{"wheat"}, {"choice"}},
            3,
            "ground",
            2,
            114,
            "white",
            behavior
        ));

        // 41. Brewer's Blackbird
        HashSet<String> h41 = new HashSet<String>(Set.of("grassland"));
        addBird(new BirdInfo(
            "Brewer's Blackbird",
            h41,
            new String[][]{{"wheat"}, {"choice"}},
            3,
            "bowl",
            3,
            41,
            "brown",
            behavior
        ));

        // 42. Broad-Winged Hawk
        HashSet<String> h42 = new HashSet<String>(Set.of("forest"));
        addBird(new BirdInfo(
            "Broad-Winged Hawk",
            h42,
            new String[][]{{"rodent"}},
            4,
            "platform",
            2,
            85,
            "brown",
            behavior
        ));

        // 43. Bronzed Cowbird
        HashSet<String> h43 = new HashSet<String>(Set.of("grassland");
        addBird(new BirdInfo(
            "Bronzed Cowbird",
            h43,
            new String[][]{{"wheat"}, {"invertebrate"}},
            5,
            "",
            0,
            36,
            "pink",
            behavior
        ));

        // 44. Brown Pelican
        HashSet<String> h44 = new HashSet<String>(Set.of("wetland"));
        addBird(new BirdInfo(
            "Brown Pelican",
            h44,
            new String[][]{{"fish"}, {"fish"}},
            4,
            "platform",
            2,
            201,
            "white",
            behavior
        ));

        // 45. Brown-Headed Cowbird
        HashSet<String> h45 = new HashSet<String>(Set.of("grassland"));
        addBird(new BirdInfo(
            "Brown-Headed Cowbird",
            h45,
            new String[][]{{"wheat"}},
            3,
            "",
            0,
            30,
            "pink",
            behavior
        ));

        // 46. Burrowing Owl
        HashSet<String> h46 = new HashSet<String>(Set.of("grassland"));
        addBird(new BirdInfo(
            "Burrowing Owl",
            h46,
            new String[][]{{"rodent"}, {"invertebrate"}},
            5,
            "star",
            4,
            53
            "brown",
            behavior
        ));

        // 47. Bushtit
        HashSet<String> h47 = new HashSet<String>(Set.of("forest", "wetland", "grassland"));
        addBird(new BirdInfo(
            "Bushtit",
            h47,
            new String[][]{{"invertebrate"}, {"wheat"}},
            2,
            "star",
            5,
            15,
            "brown",
            behavior
        ));

        // 48. California Condor
        HashSet<String> h48 = new HashSet<String>(Set.of("grassland","wetland","forest"));
        addBird(new BirdInfo(
            "California Condor",
            h48,
            new String[][]{{""}},
            1,
            "ground",
            1,
            277,
            "white",
            behavior
        ));

        // 49. California Quail
        HashSet<String> h49 = new HashSet<String>(Set.of("grassland","forest"));
        addBird(new BirdInfo(
            "California Quail",
            h49,
            new String[][]{{"wheat"}, {"wheat"}, {"invertebrate"}},
            3,
            "ground",
            6,
            36,
            "brown",
            behavior
        ));

        // 50. Canada Goose
        HashSet<String> h50 = new HashSet<String>(Set.of("wetland", "grassland"));
        addBird(new BirdInfo(
            "Canada Goose",
            h50,
            new String[][]{{"wheat"}, {"wheat"}},
            3,
            "ground",
            3,
            132,
            "brown",
            behavior
        ));

        // 51. Canvasback
        HashSet<String> h51 = new HashSet<String>(Set.of("wetland"));
        addBird(new BirdInfo(
            "Canvasback",
            h51,
            new String[][]{{"wheat"}, {"choice"}},
            4,
            "star",
            4,
            82,
            "brown",
            behavior
        ));

        // 52. Carolina Chickadee
        HashSet<String> h52 = new HashSet<String>(Set.of("forest"));
        addBird(new BirdInfo(
            "Carolina Chickadee",
            h52,
            new String[][]{{"invertebrate", "wheat"}},
            2,
            "cavity",
            3,
            20,
            "brown",
            behavior
        ));

        // 53. Carolina Wren
        HashSet<String> h53 = new HashSet<String>(Set.of("forest"));
        addBird(new BirdInfo(
            "Carolina Wren",
            h53,
            new String[][]{{"invertebrate", "berry"}},
            1,
            "cavity",
            5,
            20,
            "white",
            behavior
        ));

        // 54. Cassin's Finch
        HashSet<String> h54 = new HashSet<String>(Set.of("forest"));
        addBird(new BirdInfo(
            "Cassin's Finch",
            h54,
            new String[][]{{"wheat"}, {"berry"}},
            4,
            "bowl",
            3,
            30,
            "brown",
            behavior
        ));

        // 55. Cassin's Sparrow
        HashSet<String> h55 = new HashSet<String>(Set.of("grassland"));
        addBird(new BirdInfo(
            "Cassin's Sparrow",
            h55,
            new String[][]{{"invertebrate"}, {"wheat"}},
            3,
            "ground",
            2,
            20,
            "brown",
            behavior
        ));

        // 56. Cedar Waxwing
        HashSet<String> h56 = new HashSet<String>(Set.of("forest", "grassland"));
        addBird(new BirdInfo(
            "Cedar Waxwing",
            h56,
            new String[][]{{"berry"}, {"berry"}},
            3,
            "bowl",
            3,
            25,
            "brown",
            behavior
        ));

        // 57. Cerulean Warbler
        HashSet<String> h57 = new HashSet<String>(Set.of("forest"));
        addBird(new BirdInfo(
            "Cerulean Warbler",
            h57,
            new String[][]{{"invertebrate"}, {"wheat"}},
            4,
            "bowl",
            2,
            20,
            "white",
            behavior
        ));

        // 58. Chestnut-Collared Longspur
        HashSet<String> h58 = new HashSet<String>(Set.of("grassland"));
        addBird(new BirdInfo(
            "Chestnut-Collared Longspur",
            h58,
            new String[][]{{"wheat"}, {"wheat"}, {"invertebrate"}},
            5,
            "ground",
            4,
            25,
            "white",
            behavior
        ));

        // 59. Chihuahuan Raven
        HashSet<String> h59 = new HashSet<String>(Set.of("grassland"));
        addBird(new BirdInfo(
            "Chihuahuan Raven",
            h59,
            new String[][]{{"rodent"}, {"choice"}, {"choice"}},
            4,
            "platform",
            3,
            112,
            "brown",
            behavior
        ));

        // 60. Chimney Swift
        HashSet<String> h60 = new HashSet<String>(Set.of("forest","grassland", "wetland"));
        addBird(new BirdInfo(
            "Chimney Swift",
            h60,
            new String[][]{{"invertebrate"}, {"invertebrate"}},
            3,
            "star",
            2,
            36,
            "brown",
            behavior
        ));

        // 61. Chipping Sparrow
        HashSet<String> h61 = new HashSet<String>(Set.of("forest","grassland"));
        addBird(new BirdInfo(
            "Chipping Sparrow",
            h61,
            new String[][]{{"wheat", "invertebrate"}},
            1,
            "bowl",
            3,
            23,
            "brown",
            behavior
        ));

        // 62. Clark's Grebe
        HashSet<String> h62 = new HashSet<String>(Set.of("wetland"));
        addBird(new BirdInfo(
            "Clark's Grebe",
            h62,
            new String[][]{{"fish"}},
            5,
            "star",
            2,
            61,
            "brown",
            behavior
        ));

        // 63. Clark's Nutcracker
        HashSet<String> h63 = new HashSet<String>(Set.of("forest"));
        addBird(new BirdInfo(
            "Clark's Nutcracker",
            h63,
            new String[][]{{"wheat"}, {"wheat"}, {"choice"}},
            5,
            "platform",
            2,
            61,
            "brown",
            behavior
        ));

        // 64. Common Grackle
        HashSet<String> h64 = new HashSet<String>(Set.of("forest","grassland","wetland"));
        addBird(new BirdInfo(
            "Common Grackle",
            h64,
            new String[][]{{"wheat"}, {"choice"}},
            3,
            "bowl",
            3,
            43,
            "brown",
            behavior
        ));

        // 65. Common Loon
        HashSet<String> h65 = new HashSet<String>(Set.of("wetland"));
        addBird(new BirdInfo(
            "Common Loon",
            h65,
            new String[][]{{"fish"}, {"choice"}},
            6,
            "ground",
            1,
            117,
            "brown",
            behavior
        ));

        // 66. Common Merganser
        HashSet<String> h66 = new HashSet<String>(Set.of("wetland"));
        addBird(new BirdInfo(
            "Common Merganser",
            h66,
            new String[][]{{"fish"}, {"choice"}},
            5,
            "cavity",
            4,
            86,
            "brown",
            behavior
        ));

        // 67. Common Nighthawk
        HashSet<String> h67 = new HashSet<String>(Set.of("grassland","forest", "wetland"));
        addBird(new BirdInfo(
            "Common Nighthawk",
            h67,
            new String[][]{{"invertebrate"}, {"invertebrate"}},
            3,
            "ground",
            2,
            56,
            "brown",
            behavior
        ));

        // 68. Common Raven
        HashSet<String> h68 = new HashSet<String>(Set.of("forest","grassland","wetland"));
        addBird(new BirdInfo(
            "Common Raven",
            h68,
            new String[][]{{"rodent"}, {"choice"}, {"choice"}},
            5,
            "platform",
            2,
            135,
            "brown",
            behavior
        ));

        // 69. Common Yellowthroat
        HashSet<String> h69 = new HashSet<String>(Set.of("wetland"));
        addBird(new BirdInfo(
            "Common Yellowthroat",
            h69,
            new String[][]{{"invertebrate"}},
            1,
            "bowl",
            4,
            18,
            "brown",
            behavior
        ));

        // 70. Cooper's Hawk
        HashSet<String> h70 = new HashSet<String>(Set.of("forest"));
        addBird(new BirdInfo(
            "Cooper's Hawk",
            h70,
            new String[][]{{"rodent"}},
            3,
            "platform",
            2,
            79,
            "brown",
            behavior
        ));

        // 71. Dark-Eyed Junco
        HashSet<String> h71 = new HashSet<String>(Set.of("forest","grassland"));
        addBird(new BirdInfo(
            "Dark-Eyed Junco",
            h71,
            new String[][]{{"wheat"}, {"invertebrate"}},
            3,
            "ground",
            3,
            23,
            "brown",
            behavior
        ));

        // 72. Dickcissel
        HashSet<String> h72 = new HashSet<String>(Set.of("grassland"));
        addBird(new BirdInfo(
            "Dickcissel",
            h72,
            new String[][]{{"invertebrate"}, {"wheat"}, {"wheat"}},
            4,
            "ground",
            3,
            25,
            "brown",
            behavior
        ));

        // 73. Double-Crested Cormorant
        HashSet<String> h73 = new HashSet<String>(Set.of("wetland"));
        addBird(new BirdInfo(
            "Double-Crested Cormorant",
            h73,
            new String[][]{{"fish"}, {"choice"}},
            3,
            "platform",
            3,
            132,
            "brown",
            behavior
        ));

        // 74. Downy Woodpecker
        HashSet<String> h74 = new HashSet<String>(Set.of("forest"));
        addBird(new BirdInfo(
            "Downy Woodpecker",
            h74,
            new String[][]{{"invertebrate", "wheat", "berry"}},
            3,
            "cavity",
            2,
            30,
            "white",
            behavior
        ));

        // 75. Eastern Bluebird
        HashSet<String> h75 = new HashSet<String>(Set.of("grassland"));
        addBird(new BirdInfo(
            "Eastern Bluebird",
            h75,
            new String[][]{{"invertebrate"}, {"berry"}},
            4,
            "cavity",
            5,
            33,
            "white",
            behavior
        ));

        // 76. Eastern Kingbird
        HashSet<String> h76 = new HashSet<String>(Set.of("forest","grassland", "wetland"));
        addBird(new BirdInfo(
            "Eastern Kingbird",
            h76,
            new String[][]{{"invertebrate", "berry"}},
            2,
            "bowl",
            2,
            38,
            "pink",
            behavior
        ));

        // 77. Eastern Phoebe
        HashSet<String> h77 = new HashSet<String>(Set.of("forest","grassland","wetland"));
        addBird(new BirdInfo(
            "Eastern Phoebe",
            h77,
            new String[][]{{"invertebrate", "berry"}},
            3,
            "star",
            4,
            28,
            "brown",
            behavior
        ));

        // 78. Eastern Screech-Owl
        HashSet<String> h78 = new HashSet<String>(Set.of("forest"}));
        addBird(new BirdInfo(
            "Eastern Screech-Owl",
            h78,
            new String[][]{{"rodent", "invertebrate"}},
            4,
            "cavity",
            2,
            51,
            "brown",
            behavior
        ));

        // 79. Ferruginous Hawk
        HashSet<String> h79 = new HashSet<String>(Set.of("grassland"));
        addBird(new BirdInfo(
            "Ferruginous Hawk",
            h79,
            new String[][]{{"rodent"}, {"rodent"}},
            6,
            "platform",
            2,
            142,
            "brown",
            behavior
        ));

        // 80. Fish Crow
        HashSet<String> h80 = new HashSet<String>(Set.of("wetland","forest","grassland"));
        addBird(new BirdInfo(
            "Fish Crow",
            h80,
            new String[][]{{"fish"}, {"choice"}},
            6,
            "platform",
            2,
            91,
            "brown",
            behavior
        ));

        // 81. Forster's Tern
        HashSet<String> h81 = new HashSet<String>(Set.of("wetland"));
        addBird(new BirdInfo(
            "Forster's Tern",
            h81,
            new String[][]{{"fish", "invertebrate"}},
            4,
            "star",
            2,
            79,
            "brown",
            behavior
        ));

        // 82. Franklin's Gull
        HashSet<String> h82 = new HashSet<String>(Set.of("wetland","grassland"));
        addBird(new BirdInfo(
            "Franklin's Gull",
            h82,
            new String[][]{{"fish"}, {"choice"}},
            3,
            "star",
            2,
            91,
            "brown",
            behavior
        ));

        // 83. Golden Eagle
        HashSet<String> h83 = new HashSet<String>(Set.of("grassland","wetland"));
        addBird(new BirdInfo(
            "Golden Eagle",
            h83,
            new String[][]{{"rodent"}, {"rodent"}, {"rodent"}},
            8,
            "platform",
            1,
            201,
            "brown",
            behavior
        ));

        // 84. Grasshopper Sparrow
        HashSet<String> h84 = new HashSet<String>(Set.of("grassland"));
        addBird(new BirdInfo(
            "Grasshopper Sparrow",
            h84,
            new String[][]{{"invertebrate", "wheat"}},
            2,
            "ground",
            2,
            20,
            "brown",
            behavior
        ));

        // 85. Gray Catbird
        HashSet<String> h85 = new HashSet<String>(Set.of("forest","wetland","grassland"));
        addBird(new BirdInfo(
            "Gray Catbird",
            h85,
            new String[][]{{"invertebrate"}, {"berry"}, {"berry"}},
            5,
            "bowl",
            3,
            28,
            "brown",
            behavior
        ));

        // 86. Great Blue Heron
        HashSet<String> h86 = new HashSet<String>(Set.of("wetland"));
        addBird(new BirdInfo(
            "Great Blue Heron",
            h86,
            new String[][]{{"fish"}, {"invertebrate"}},
            5,
            "platform",
            2,
            183,
            "white",
            behavior
        ));

        // 87. Great Crested Flycatcher
        HashSet<String> h87 = new HashSet<String>(Set.of("forest"));
        addBird(new BirdInfo(
            "Great Crested Flycatcher",
            h87,
            new String[][]{{"invertebrate"}, {"berry"}},
            5,
            "cavity",
            3,
            33,
            "brown",
            behavior
        ));

        // 88. Great Egret
        HashSet<String> h88 = new HashSet<String>(Set.of("wetland"));
        addBird(new BirdInfo(
            "Great Egret",
            h88,
            new String[][]{{"fish"}, {"fish"}, {"rodent"}},
            7,
            "platform",
            3,
            130,
            "white",
            behavior
        ));

        // 89. Great Horned Owl
        HashSet<String> h89 = new HashSet<String>(Set.of("forest"));
        addBird(new BirdInfo(
            "Great Horned Owl",
            h89,
            new String[][]{{"rodent"}, {"rodent"}, {"rodent"}},
            8,
            "platform",
            2,
            112,
            "brown",
            behavior
        ));

        // 90. Greater Prairie-Chicken
        HashSet<String> h90 = new HashSet<String>(Set.of("grassland"));
        addBird(new BirdInfo(
            "Greater Prairie-Chicken",
            h90,
            new String[][]{{"wheat"}, {"wheat"}, {"invertebrate"}},
            5,
            "ground",
            4,
            71,
            "white",
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
