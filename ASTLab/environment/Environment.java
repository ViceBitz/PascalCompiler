/**
 * Environment keeps track of all variable values through the use of a HashMap.
 * It has getter and setter functions for retrieving/changing or setting
 * variable values.
 * 
 * @author Victor Gong
 * @version 10/16/2023
 */

package environment;

import java.util.*;
public class Environment {
    private HashMap<String, Integer> varMap;
    public Environment()
    {
        varMap = new HashMap<>();
    }

    /**
     * Set the variable by putting the variable's identifer into the map with the value
     * @param var The string identifier representing the variable
     * @param val The value of the variable
     */
    public void setVariable(String var, int val)
    {
        varMap.put(var, val);
    }

    /**
     * Retrieves the value of a variable given by an string id
     * @precondition Variable declared/inside the HashMap
     * @param var The string identifier representing the variable
     * @return The value of the variable
     */
    public int getVariable(String var)
    {
        return varMap.get(var);
    }
}
