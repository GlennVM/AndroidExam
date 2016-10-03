package com.example;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class CriminalGreenDaoGenerator {
    public static void main(String args[]) {

        try {
            Schema schema = new Schema(1, "com.example.glenn.criminal.model");

            Entity crime = schema.addEntity("Crime");
            crime.addIdProperty();
            crime.addStringProperty("title");
            crime.addDateProperty("date");
            crime.addBooleanProperty("solved");
            crime.addStringProperty("suspect");

            new DaoGenerator().generateAll(schema, "../app/src/main/java");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
