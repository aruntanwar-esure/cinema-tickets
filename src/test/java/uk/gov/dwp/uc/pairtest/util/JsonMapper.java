package uk.gov.dwp.uc.pairtest.util;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonMapper {
    private static ObjectMapper objectMapper = new ObjectMapper();
    public static String getObjectAsJson(Object obj){
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
