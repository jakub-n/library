package cz.muni.fi.pv243.library.entity;


public enum Role {
	
    READER ("READER"),
    LIBRARIAN ("LIBRARIAN"),
    MANAGER ("MANAGER");

    private String string;

    private Role(String string) {
        this.string = string;
    }

    @Override
    public String toString() {
        return string;
    }
}
