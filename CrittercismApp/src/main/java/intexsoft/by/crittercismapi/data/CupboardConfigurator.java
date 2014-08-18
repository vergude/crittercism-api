package intexsoft.by.crittercismapi.data;

import intexsoft.by.crittercismapi.data.bean.CrittercismApp;
import intexsoft.by.crittercismapi.data.bean.DailyStatisticsItem;
import nl.qbusict.cupboard.CupboardBuilder;
import nl.qbusict.cupboard.CupboardFactory;

import static nl.qbusict.cupboard.CupboardFactory.cupboard;


public final class CupboardConfigurator
{

    private CupboardConfigurator() {}

    private static Class<?>[] models = {DailyStatisticsItem.class, CrittercismApp.class};

    public static void configure() {
        setUseAnnotations();
        registerModels();
    }

    private static void setUseAnnotations() {
        CupboardFactory.setCupboard(new CupboardBuilder().useAnnotations().build());
    }

    private static void registerModels() {
        for (Class<?> clazz : models) {
            registerModel(clazz);
        }
    }

    private static void registerModel(Class<?> modelClazz) {
        cupboard().register(modelClazz);
    }

}
