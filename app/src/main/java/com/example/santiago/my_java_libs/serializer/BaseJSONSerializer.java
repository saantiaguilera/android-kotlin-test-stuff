package com.example.santiago.my_java_libs.serializer;

import com.example.santiago.my_java_libs.entity.BaseEntity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by santiago on 10/04/16.
 */
public abstract class BaseJSONSerializer<T extends BaseEntity> {

    /**
     * Create an instance of the entity T already inflated
     * with the data that holds the JSONObject
     *
     * @param jobj JSONObject that holds the information of the entity T
     * @return instance of T
     */
    public abstract T hidrate(JSONObject jobj);

    /**
     * By default it considers a JSONArray that holds all the T objects
     * in its root. Override if JSONArray has a different flow.
     *
     * @param jarr JSONArray that holds the information of a list of entities T
     * @return A list of instances of T
     */
    public List<T> hidrate(JSONArray jarr) {
        List<T> list = new ArrayList<>();

        try {
            for (int i = 0 ; i < jarr.length() ; ++i)
                list.add(hidrate(jarr.getJSONObject(i)));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return list;
    }

    /**
     * Serializes the information that T has
     *
     * @param t entity to be serialized to JSON
     * @return a JSONObject that holds the information of T
     */
    public abstract JSONObject serialize(T t);

    /**
     * Creates a JSONArray from a list of entities T.
     * By default it creates a JSONArray that holds all the entities
     * in its root. Override if JSONArray needs to have a different
     * structure.
     *
     * @param tList list that holds all the entities to be serialized
     * @return JSONArray with the list serialized
     */
    public JSONArray serialize(List<T> tList) {
        JSONArray jarr = new JSONArray();

        for (T t : tList)
            jarr.put(serialize(t));

        return jarr;
    }

}
