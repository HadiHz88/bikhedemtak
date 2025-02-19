package lb.edu.ul.bikhedemtak.SquareCategories;

public class SquareItem {
    private int categoryId;
    private int iconResId;
    private String categoryName;

    public SquareItem(int categoryId, int iconResId, String categoryName) {
        this.categoryId = categoryId;
        this.iconResId = iconResId;
        this.categoryName = categoryName;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public int getIconResId() {
        return iconResId;
    }

    public String getCategoryName() {
        return categoryName;
    }
}