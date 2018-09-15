/**
 * @author Jacob B.N.
 * @version 07/24/2018
 */
package application;

import java.util.HashSet;

/**
 * <p>Represents a recipe in the RecipeCloud application.</p>
 * 
 * <p>A recipe contains an id, a name, instructions, and ingredients.</p>
 */
public class Recipe {
	
	// Fields
	private int id; // Defaults to -1.
	private String name, instructions;
	private HashSet<Ingredient> ingredients;
	
	/**
	 * <p>Constructs a Recipe object. Its id is defaulted to -1.</p>
	 * 
	 * @param name
	 * @param ingredients
	 * @throws IllegalArgumentException
	 */
	public Recipe(String name)
	{
		if (name == null)
		{
			throw new IllegalArgumentException("Null values are illegal!");
		}

		id = -1;
		this.name = name.toLowerCase().trim();
		instructions = "";
		ingredients = new HashSet<>();
	}
	
	/**
	 * <p>Constructs a Recipe object. Its id is defaulted to -1.</p>
	 * 
	 * @param name
	 * @param ingredients
	 * @throws IllegalArgumentException
	 */
	public Recipe(String name, HashSet<Ingredient> ingredients)
	{
		if (name == null || ingredients == null)
		{
			throw new IllegalArgumentException("Null values are illegal!");
		}

		id = -1;
		this.name = name.toLowerCase().trim();
		instructions = "";
		this.ingredients = new HashSet<>(ingredients);
	}
	
	/**
	 * <p>Constructs a Recipe object. Its id is defaulted to -1.</p>
	 * 
	 * @param name
	 * @param instructions
	 * @param ingredients
	 * @throws IllegalArgumentException
	 */
	public Recipe(String name, String instructions, HashSet<Ingredient> ingredients)
	{
		if (name == null || instructions == null || ingredients == null)
		{
			throw new IllegalArgumentException("Null values are illegal!");
		}

		id = -1;
		this.name = name.toLowerCase().trim();
		this.instructions = instructions.toLowerCase().trim();
		this.ingredients = new HashSet<>(ingredients);
	}
	
	/**
	 * <p>Adds the ingredients to the recipe. Null values are ignored.</p>
	 * 
	 * @param ingredients
	 */
	public void addIngredients(HashSet<Ingredient> ingredients)
	{
		if (ingredients != null)
		{
			for (Ingredient ingredient : ingredients)
			{
				addIngredient(ingredient);
			}
		}
	}
	
	/**
	 * <p>Adds an ingredient to the recipe. If the value is null, 
	 * it is ignored.</p>
	 * 
	 * @param ingredient
	 */
	public void addIngredient(Ingredient ingredient)
	{
		if (ingredient != null && !ingredients.contains(ingredient))
		{
			ingredients.add(ingredient);
		}
	}
	
	/**
	 * Updates the recipe's id to the given value.
	 * 
	 * @param id
	 */
	public void setId(int id)
	{
		this.id = id;
	}
	
	/**
	 * Updates the recipe's name to the given value.
	 * 
	 * @param name
	 */
	public void setName(String name)
	{
		this.name = name;
	}	
	
	/**
	 * Updates the recipe's instructions to the given value.
	 * 
	 * @param instructions
	 */
	public void setInstructions(String instructions)
	{
		this.instructions = instructions;
	}
	
	/**
	 * Removes the given ingredient from the recipe.
	 * 
	 * @param ingredient
	 */
	public void removeIngredient(Ingredient ingredient)
	{
		if (ingredients.contains(ingredient))
		{
			ingredients.remove(ingredient);
		}
	}
	
	/**
	 * Returns the recipe's id.
	 * 
	 * @return
	 */
	public int getId()
	{
		return id;
	}
	
	/**
	 * Returns the recipe's name.
	 * 
	 * @return
	 */
	public String getName()
	{
		return name;
	}
	
	/**
	 * Returns the recipe's instructions.
	 * 
	 * @return
	 */
	public String getInstructions()
	{
		return instructions;
	}
	
	/**
	 * Returns a HashSet with the recipe's ingredients.
	 * 
	 * @return
	 */
	public HashSet<Ingredient> getIngredients()
	{
		return new HashSet<>(ingredients);
	}
	
	/**
	 * <p>Two recipes are considered equal if their 
	 * id, name, instructions, and ingredients are equal.</p>
	 */
	@Override
	public boolean equals(Object obj)
	{
		if (obj == null || !(obj instanceof Recipe))
		{
			return false;
		}

		Recipe recipe = (Recipe) obj;
		
		if (this.ingredients.size() != recipe.ingredients.size() || 
			this.id != recipe.id || 
			!this.name.equals(recipe.name) || 
			!this.instructions.equals(recipe.instructions))
		{
			return false;
		}
		
		// Ingredients are the same?
		
		return true;
	}
}
