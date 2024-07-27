public class Dessert {
    public static int total;
    public int flavor, price;

    public Dessert(int f, int p) {
        total++;
        flavor = f;
        price = p;
    }

    public static int numDesserts() {
        return total;
    }

    public void printDessert() {
        System.out.println(flavor + " " + price + " " + total);
    }

    public static void main(String[] args) {
        System.out.println("I love dessert!");
    }
}