class ID implements Comparable<ID> {
    private int id;

    public ID(int id) {
        this.id = id;
    }

    public int getID() {
        return id;
    }

    @Override
    public int compareTo(ID o) {
        return id - o.id;
    }

    public String toString() {
        return ("ID = " + id);
    }
}
