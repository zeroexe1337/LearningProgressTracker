package tracker;

public class StudentsID {
    private  int id;
    private  int Java;
    private  int DataStructuresAndAlgorithms;
    private  int databases;
    private  int Spring;


    public int getId() {
        return id;
    }

    public int getJava() {
        return Java;
    }

    public int getDataStructuresAndAlgorithms() {
        return DataStructuresAndAlgorithms;
    }

    public int getDatabases() {
        return databases;
    }

    public int getSpring() {
        return Spring;
    }

    public StudentsID(int id, int java, int dataStructuresAndAlgorithms, int databases, int spring) {
        this.id = id;
        this.Java = java;
        this.DataStructuresAndAlgorithms = dataStructuresAndAlgorithms;
        this.databases = databases;
        this.Spring = spring;

    }
    public boolean isInt() {
        if (Java == (int)Java && Java >= 0) {
            return true;
        }
        return false;
    }
    public boolean isInt2() {
        if (DataStructuresAndAlgorithms == (int)DataStructuresAndAlgorithms && DataStructuresAndAlgorithms >= 0) {
            return true;
        }
        return false;
    }
    public boolean isInt3() {
        if (databases == (int)databases && databases >= 0) {
            return true;
        }
        return false;
    }
    public boolean isInt4() {
        if (Spring == (int)Spring && databases >= 0) {
            return true;
        }
        return false;
    }



    public boolean containsID(int x) {
        return id == x;

    }

        public int updateC1(int x) {
           return this.Java = Java + x;
        }
    public int updateC2(int x) {
        return this.DataStructuresAndAlgorithms = DataStructuresAndAlgorithms + x;
    }
    public int updateC3(int x) {
        return this.databases = databases + x;
    }
    public int updateC4(int x) {
        return this.Spring = Spring + x;
    }

    public String toString() {
        return String.valueOf(getJava()) +" " + String.valueOf(getDataStructuresAndAlgorithms())
                + " " + String.valueOf(getDatabases()) + " " + String.valueOf(getSpring());
    }
    public boolean isJavaFinished() {
        return Java >= 600;
    }
    public boolean isDsaFinished() {
        return DataStructuresAndAlgorithms >= 400;
    }
    public boolean isDatabaseFinished() {
        return databases >= 480;
    }

    public boolean isSpringFinished() {
        return Spring >= 550;
    }

}




