public class Land {
    private String plotName;
    private double length;
    private double width;

    // Constructor to initialize land details
    public Land(String plotName, double length, double width) {
        this.plotName = plotName;
        this.length = length;
        this.width = width;
    }

    // Method to calculate the area of the land
    public double calculateArea() {
        return length * width;
    }

    // Method to display land details
    public void displayLandDetails() {
        System.out.println("Plot Name: " + plotName);
        System.out.println("Length: " + length + " meters");
        System.out.println("Width: " + width + " meters");
        System.out.println("Area: " + calculateArea() + " square meters");
        System.out.println();
    }
}