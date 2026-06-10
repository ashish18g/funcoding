public class Ashish {
    protected class Stats {
        private final int weight;
        private final int height;
        private String[] knownLangs;

        public Stats(int weight, int height) {
            this.weight = weight;
            this.height = height;
        }

        public Stats(int weight, int height, String[] knownLangs) {
            this.weight = weight;
            this.height = height;
            this.knownLangs = knownLangs;
        }

        public int getWeight() {
            return weight;
        }

        public int getHeight() {
            return height;
        }

        public String[] getKnownLangs() {
            return knownLangs;
        }
    }
}

// Inheriting to Btech per year, having
class Btech extends Ashish {
    private final int year;

    public Btech(int year) {
        this.year = year;
        Stats yr;
        switch (this.year) {
            case 1:
                yr = new Stats(63, 165, new String[] {"Python", "Java"});
            case 2:
                yr = new Stats(65, 165, new String[] {"Python", "Java"});
            case 3:
                yr = new Stats(67, 165, new String[] {"Python", "Java"});
        }
    }

    public int getYear() {
        return year;
    }
}
