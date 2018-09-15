/**
 * @author Jacob B.N.
 * @version 07/24/2018
 */
package application;

/**
 * <p>Represents an Ingredient in the RecipeCloud application.</p>
 * 
 * <p>An Ingredient contains a name, an amount required in the recipe, and 
 * and the part of the recipe's instructions it is used in (usedIn). 
 * Of these, the name is required - amount and usedIn are optional.</p>
 */
public class Ingredient {
	
	// Fields
	private String name, amount, usedIn;
	
	/**
	 * <p>Requires name to be non-null, and non-empty after being trimmed.</p>
	 * 
	 * <p>The ingredient string variables are set to lower case.</p>
	 * 
	 * @param name
	 * @throws IllegalArgumentException
	 */
	public Ingredient(String name)
	{
		if (name == null)
		{
			throw new IllegalArgumentException("Null values are illegal!");
		}
		
		if (name.trim().equals(""))
		{
			throw new IllegalArgumentException("Fill out the name!");
		}
		
		this.name = name.toLowerCase();
		amount = "";
		usedIn = "";
	}
	
	/**
	 * <p>Requires name to be non-null, and non-empty after being trimmed.<br>
	 * 
	 * Requires amount to be non-null.</p>
	 * 
	 * <p>The ingredient string variables are set to lower case.</p>
	 * 
	 * @param name, amount
	 * @throws IllegalArgumentException
	 */
	public Ingredient(String name, String amount)
	{
		if (name == null || amount == null)
		{
			throw new IllegalArgumentException("Null values are illegal!");
		}
		
		if (name.trim().equals(""))
		{
			throw new IllegalArgumentException("Fill out the name!");
		}
		
		this.name = name.trim().toLowerCase();
		this.amount = amount.trim().toLowerCase();
		usedIn = "";
	}
	
	/**
	 * <p>Requires name to be non-null, and non-empty after being trimmed.<br>
	 * 
	 * Requires amount and usedIn to be non-null.</p>
	 * 
	 * <p>The ingredient string variables are set to lower case.</p>
	 * 
	 * @param name, amount, usedIn
	 * @throws IllegalArgumentException
	 */
	public Ingredient(String name, String amount, String usedIn)
	{
		if (name == null || amount == null || usedIn == null)
		{
			throw new IllegalArgumentException("Null values are illegal!");
		}
		
		if (name.trim().equals(""))
		{
			throw new IllegalArgumentException("Fill out the name!");
		}
		
		this.name = name.trim().toLowerCase();
		this.amount = amount.trim().toLowerCase();
		this.usedIn = usedIn.trim().toLowerCase();
	}
	
	/**
	 * @return
	 */
	public String getName()
	{
		return name;
	}
	
	/**
	 * <p>Returns the ingredient's amount.</p>
	 * 
	 * @return
	 */
	public String getAmount()
	{
		return amount;
	}
	
	/**
	 * <p>Returns the ingredient's 'usedIn'.</p>
	 * 
	 * @return
	 */
	public String getUsedIn()
	{
		return usedIn;
	}
	
	/**
	 * <p>Sets the ingredient's name to the given variable.</p>
	 */
	public void setName(String name)
	{
		if (name != null)
		{
			this.name = name;
		}
	}
	
	/**
	 * <p>Sets the ingredient's amount to the given variable.</p>
	 */
	public void setAmount(String amount)
	{
		if (amount != null)
		{
			this.amount = amount;
		}
	}
	
	/**
	 * <p>Sets the ingredient's 'usedIn' to the given variable.<p>
	 */
	public void setUsedIn(String usedIn)
	{
		if (usedIn != null)
		{
			this.usedIn = usedIn;
		}
	}
	
	/**
	 * <p>Two ingredients are considered equal if their name, amount, and 'usedIn' 
	 * field variables are equal as per the String equals method.</p>
	 * 
	 * <p>Note that when an Ingredient object is created, it is both trimmed and 
	 * set to lower case.</p>
	 */
	@Override
	public boolean equals(Object obj)
	{
		if (obj == null || !(obj instanceof Ingredient))
		{
			return false;
		}

		Ingredient ing = (Ingredient) obj;
		boolean result = true;
		
		if (!this.amount.equals(ing.amount) ||
			!this.name.equals(ing.name)		||
			!this.usedIn.equals(ing.usedIn)	)
		{
			result = false;
		}
		
		return result;
	}
}
