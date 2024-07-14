package com.example.comic_store.commons;

import org.springframework.stereotype.Component;

@Component
public class Common {

    public static String convertTypeName(String typeNameObject) {
        String[] typeName =  typeNameObject.split(",");
        StringBuilder typeNameStandard = new StringBuilder();
        for (int i = 0 ; i < typeName.length ; i++) {
            if (!typeName[i].equals(" ")) {
                typeNameStandard.append(typeName[i]);
            }
            if (i < typeName.length - 1 && ! typeName[i + 1].equals(" ")) {
                typeNameStandard.append(", ");
            }
        }
        return typeNameStandard.toString();
    }
}
