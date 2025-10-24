package emitter2;

import java.io.*;
import java.util.HashMap;
import java.util.List;

import ast.ProcedureDeclaration;
import ast.Variable;

public class Emitter
{
	private PrintWriter out;
	private static HashMap<String, Integer> labelCounters = new HashMap<>();
	private ProcedureDeclaration procedureContext;
	private int excessStackHeight;

	//creates an emitter for writing to a new file with given name
	public Emitter(String outputFileName)
	{
		try
		{
			out = new PrintWriter(new FileWriter(outputFileName), true);
		}
		catch(IOException e)
		{
			throw new RuntimeException(e);
		}
	}

	//prints one line of code to file (with non-labels indented)
	public void emit(String code)
	{
		if (!code.endsWith(":"))
			code = "\t" + code;
		out.println(code);
	}

	/**
	 * Prints code to push a register onto the stack
	 * 
	 * @param reg The register to push
	 */
	public void emitPush(String reg)
	{
		emit("subu $sp $sp 4");
		emit("sw " + reg + " ($sp)");
		excessStackHeight += 4;
	}

	/**
	 * Prints code to pop the top of the stack and save it into a register
	 * 
	 * @param reg The register to push
	 */
	public void emitPop(String reg)
	{
		emit("lw " + reg + " ($sp)");
		emit("addu $sp $sp 4");
		excessStackHeight -= 4;
	}
	
	//closes the file.  should be called after all calls to emit.
	public void close()
	{
		out.close();
	}

	/**
     * Puts the title with a counter value of 0 if not in the counter Map. Increments the
	 * label counter for the title then generates a new label in the format
	 * [title..labelCounters.get(title)]
     * @postcondition "labelCounter" added by 1
	 * @param title The label title (prefix)
     */
    public static String generateLabelId(String title)
    {
        labelCounters.putIfAbsent(title, 0);
		labelCounters.put(title, labelCounters.get(title)+1);
        return title+labelCounters.get(title);
    }

	/**
	 * Sets procedureContext to the current ProcedureDeclaration being compiled
	 * 
	 * @postcondition procedureContext set to 'proc'
	 * @param proc The current ProcedureDeclaration being compiled
	 */
	public void setProcedureContext(ProcedureDeclaration proc)
	{
		excessStackHeight = 0;
		procedureContext = proc;
	}

	/**
	 * Clears procedureContext by setting it to null
	 * @postcondition procedureContext set to null
	 */
	public void clearProcedureContext()
	{
		procedureContext = null;
	}

	/**
	 * Checks if a variable is a local variable (e.g. parameter of a procedure, defined in procedure, return value) or global
	 * (defined in the .data section)
	 * 
	 * @param varName The variable id
	 * @return True if local, false otherwise
	 */
	public boolean isLocalVariable(String varName)
	{
		if (procedureContext != null)
		{
			//Parameter
			for (Variable param: procedureContext.getParams())
			{
				if (param.getId().equals(varName))
				{
					return true;
				}
			}
			//Local Variable
			for (Variable var: procedureContext.getLocalVariables())
			{
				if (var.getId().equals(varName))
				{
					return true;
				}
			}

			//Return Value
			if (procedureContext.getId().equals(varName))
			{
				return true;
			}
		}
		return false;
	}

	/**
	 * Gets the offset on the stack ($sp) of a param, local variable, or return value
	 * of the currently compiling procedure. The stack contains the values as follows:
	 * params, return value, $ra, local vars, (excess stack stuff).
	 * 
	 * @param localVarName The name of the item
	 * @return The offset of the item on the stack
	 */
	public int getOffset(String localVarName)
	{
		int offset = excessStackHeight; //Current offset is excessStackHeight (all stack space on top)

		//Check if localVarName is local variable
		List<Variable> localVars = procedureContext.getLocalVariables();
		for (int i=localVars.size()-1;i>=0;i--)
		{
			if (localVarName.equals(localVars.get(i).getId()))
			{
				return offset;
			}
			offset += 4;
		}

		//Next item in stack is $ra, just ignore
		offset += 4;

		//Check if localVarName is return value
		if (localVarName.equals(procedureContext.getId()))
		{
			return offset;
		}
		offset += 4;

		//Check if localVarName is a parameter
		List<Variable> params = procedureContext.getParams();
		for (int i=params.size()-1;i>=0;i--)
		{
			if (localVarName.equals(params.get(i).getId()))
			{
				return offset;
			}
			offset += 4;
		}
		
		return offset;
	}
}