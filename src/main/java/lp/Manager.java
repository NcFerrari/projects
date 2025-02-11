package lp;

import lp.molekuly.App;
import lp.molekuly.Molecule;

import java.util.HashSet;
import java.util.Set;

public class Manager {

    private static Manager manager;

    private final Set<Molecule> molecules = new HashSet<>();

    public static Manager getInstance() {
        if (manager == null) {
            manager = new Manager();
        }
        return manager;
    }

    private Manager() {
    }

    public Set<Molecule> getMolecules() {
        return molecules;
    }

    public static void main(String[] args) {
        javafx.application.Application.launch(App.class);
    }
}
