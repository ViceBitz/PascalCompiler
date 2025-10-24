/**
 * Environment keeps track of all variable values through the use of a HashMap.
 * It has getter and setter functions for retrieving/changing or setting variable values.
 * Each object also has an associated parent as an implementation of global and local scope.
 * Global environments have the 'parent' instance variable set to null while local environments
 * have the instance variable pointing to the global environment.
 * 
 * @author Victor Gong
 * @version 10/24/2023
 */

package environment;

import java.util.*;

import ast.ProcedureDeclaration;
import scanner.ScanErrorException;
public class Environment {
    private HashMap<String, Integer> varMap;
    private HashMap<String, ProcedureDeclaration> procedureMap;
    private Environment parent;
    public Environment()
    {
        varMap = new HashMap<>();
        procedureMap = new HashMap<>();
        parent = null;
    }
    public Environment(Environment parent)
    {
        varMap = new HashMap<>();
        procedureMap = new HashMap<>();
        this.parent = parent;
    }

    /**
     * Checks the variable exists in this environment's variable map
     * 
     * @param var The string identifier representing the variable
     * @return True if exists, false otherwise
     */
    private boolean checkVariableExistence(String var)
    {
        return varMap.containsKey(var);
    }

    /**
     * Retrieves the global environment, which is either this object (parent is null) or
     * the parent environment of this object (because all sub-environments branch off the global one)
     * 
     * @return The current global environment
     */
    public Environment getGlobalEnvironment()
    {
        if (this.parent == null)
        {
            return this;
        }
        else
        {
            return this.parent;
        }
    }
    /**
     * Sets the variable by putting the variable's identifer into the map with the value
     * in the current (local) environment
     * 
     * @postcondition Variable in local environment mapped to value
     * @param var The string identifier representing the variable
     * @param val The value of the variable
     */
    public void declareVariable(String var, int val)
    {
        varMap.put(var, val);
    }

    /**
     * Sets the variable by checking if the variable already exists in the current (local)
     * environment, and if it does, then the method updates the value. Otherwise, it looks
     * in the global environment (parent) to check for the variable's existence and updates
     * the value there if it does exists. Otherwise, if it's in neither places, the method
     * creates the variable with the value in the current environment, putting the variable's
     * identifer into the map with the value.
     * 
     * @param var The string identifier representing the variable
     * @param val The value of the variable
     */
    public void setVariable(String var, int val)
    {
        if (varMap.containsKey(var) || parent == null || !parent.checkVariableExistence(var))
        {
            varMap.put(var, val);
        }
        else
        {
            parent.setVariable(var, val);
        }
    }

    /**
     * Retrieves the value of a variable given by an string id (if in local environment),
     * or retrieves the value from the global environment (the parent)
     * 
     * @precondition Variable declared/inside the HashMap of the local or global
     * environment
     * @param var The string identifier representing the variable
     * @return The value of the variable
     */
    public int getVariable(String var) throws ScanErrorException
    {
        if (checkVariableExistence(var))
        {
            return varMap.get(var);
        }
        else if (parent != null)
        {
            return parent.getVariable(var);
        }
        else
        {
            throw new ScanErrorException("Variable " + "\'" + var + "\' not defined.");
        }
    }

    /**
     * Sets a procedure by putting the procedure's identifer into the map with the corresponding
     * ProcedureDeclaration object (if global environment), or calls the setVariable of the
     * global environment
     * 
     * @postcondition Procedure with accompanying identifier set in the HashMap of the 
     * global environment
     * @param var The string identifier representing the procedure
     * @param val The accompanying ProcedureDeclaration object
     */
    public void setProcedure(String prod, ProcedureDeclaration dec)
    {
        if (parent == null)
        {
            procedureMap.put(prod, dec);
        }
        else
        {
            parent.setProcedure(prod, dec);
        }
    }

    /**
     * Retrieves the ProcedureDeclaration of a procedure given by a string id (if global
     * environment), or calls the getProcedure method of the global environment
     * 
     * @precondition Procedure declared/inside the HashMap of the global environment
     * @param var The string identifier representing the procedure
     * @return The value of the procedure
     */
    public ProcedureDeclaration getProcedure(String prod)
    {
        if (parent == null)
        {
            return procedureMap.get(prod);
        }
        else
        {
            return parent.getProcedure(prod);
        }
    }
}
