package com.example.memoryleaksapp.singleton;

public class SingletonJ {
    private static volatile SingletonJ instance;
    public String value;

    private SingletonJ(String value) {
        try {
            // Имитация долгой инициализации
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        this.value = value;
    }

    public static SingletonJ getInstance(String value) {
        if (instance == null) {
            synchronized (SingletonJ.class) {
                if (instance == null) {
                    instance = new SingletonJ(value);
                }
            }
        }

        return instance;
    }
}
