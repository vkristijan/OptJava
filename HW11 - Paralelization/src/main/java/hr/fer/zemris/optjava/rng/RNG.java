package hr.fer.zemris.optjava.rng;

import jdk.internal.util.xml.impl.Input;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author Kristijan Vulinovic
 * @version 1.0.0
 */
public class RNG {
    private static IRNGProvider rngProvider;

    static {
        Properties properties = new Properties();
        ClassLoader loader = RNG.class.getClassLoader();
        try {
            InputStream is = loader.getResourceAsStream("rng-config.properties");
            properties.load(loader.getResourceAsStream("rng-config.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        String className = properties.getProperty("rng-provider");

        try {
            rngProvider = (IRNGProvider)loader.loadClass(className).newInstance();
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static IRNG getRNG(){
        return rngProvider.getRNG();
    }
}
