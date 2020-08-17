package ru.appline.logic;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Model implements Serializable {
    private static final Model instance = new Model();

    private final Map<Integer, User> model;

    public static Model getInstance(){
        return instance;
    }

    private Model(){
        model = new HashMap<>();

        model.put(1, new User("Nikita", "Petrov", 50000));
        model.put(2, new User("Anton", "Osipov", 45000));
        model.put(3, new User("Zoya", "Shevchenko", 40000));
        model.put(4, new User("Daya", "Lama", 35000));
        model.put(5, new User("Ati", "Bati", 30000));
    }

    public void add(User user, int id){
        model.put(id, user);
    }

    public Map<Integer, User> getFromList(){
        return model;
    }
}
