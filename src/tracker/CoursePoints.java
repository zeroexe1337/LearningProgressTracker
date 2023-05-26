package tracker;

public class CoursePoints {
    String name;
    int popularity;
    int activity;
    double difficultyLevel;

    public CoursePoints(String name, int popularity, int activity, double difficultyLevel) {
        this.name = name;
        this.popularity = popularity;
        this.activity = activity;
        this.difficultyLevel = difficultyLevel;
    }

    public String getName() {
        return name;
    }

    public int getPopularity() {
        return popularity;
    }

    public int getActivity() {
        return activity;
    }

    public double getDifficultyLevel() {
        return difficultyLevel;
    }

    @Override
    public String toString() {
        return name;
    }


}
