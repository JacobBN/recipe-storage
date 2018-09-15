package application;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;

import org.junit.jupiter.api.Test;

class TestCases {
	
	private String host, database, userName, password;

	// ----- TESTING CLASS: Ingredient -----

	@Test
	public void ingredientNullNameConstructor1()
	{
		assertThrows(IllegalArgumentException.class, () -> new Ingredient(null));
	}

	@Test
	public void ingredientNullNameConstructor2()
	{
		assertThrows(IllegalArgumentException.class, () -> new Ingredient(null, "Amount"));
	}

	@Test
	public void ingredientNullNameConstructor3()
	{
		assertThrows(IllegalArgumentException.class, () -> new Ingredient(null, "Amount", "Used In"));
	}

	@Test
	public void ingredientNullAmountConstructor2()
	{
		assertThrows(IllegalArgumentException.class, () -> new Ingredient("Name", null));
	}

	@Test
	public void ingredientNullAmountConstructor3()
	{
		assertThrows(IllegalArgumentException.class, () -> new Ingredient("Name", null, "Used In"));
	}

	@Test
	public void ingredientNullUsedInConstructor3()
	{
		assertThrows(IllegalArgumentException.class, () -> new Ingredient("Name", "Amount", null));
	}

	@Test
	public void ingredientEmptyNameConstructor1()
	{
		assertThrows(IllegalArgumentException.class, () -> new Ingredient("       "));
	}

	@Test
	public void ingredientEmptyNameConstructor2()
	{
		assertThrows(IllegalArgumentException.class, () -> new Ingredient("       ", "Amount"));
	}

	@Test
	public void ingredientEmptyNameConstructor3()
	{
		assertThrows(IllegalArgumentException.class, () -> new Ingredient("       ", "Amount", "Used In"));
	}

	@Test
	public void ingredientSetNullName()
	{
		Ingredient ing = new Ingredient("Name");
		ing.setName(null);
		assertNotNull(ing.getName());
	}

	@Test
	public void ingredientSetNullAmount()
	{
		Ingredient ing = new Ingredient("Name");
		ing.setAmount(null);
		assertNotNull(ing.getAmount());
	}

	@Test
	public void ingredientSetNullUsedIn()
	{
		Ingredient ing = new Ingredient("Name");
		ing.setUsedIn(null);
		assertNotNull(ing.getUsedIn());
	}

	@Test
	public void ingredientEquals()
	{
		Ingredient ing = new Ingredient("Name");
		assertEquals(ing, new Ingredient("Name"));
		assertEquals(ing, new Ingredient("name"));
		assertNotEquals(ing, new Ingredient("NO"));

		ing = new Ingredient("Name", "Amount");
		assertEquals(ing, new Ingredient("Name", "Amount"));
		assertEquals(ing, new Ingredient("name", "amount"));
		assertNotEquals(ing, new Ingredient("Name", "NO"));

		ing = new Ingredient("Name", "Amount", "Used In");
		assertEquals(ing, new Ingredient("Name", "Amount", "Used In"));
		assertNotEquals(ing, new Ingredient("Name", "Amount", "NO"));

		assertNotEquals(ing, new Ingredient("Name"));
		assertNotEquals(ing, new Ingredient("Name", "Amount"));
	}

	// ----- TESTING CLASS: Recipe -----

	@Test
	public void recipeNullNameConstructor1()
	{
		assertThrows(IllegalArgumentException.class, () -> new Recipe(null));
	}

	@Test
	public void recipeNullNameConstructor2()
	{
		assertThrows(IllegalArgumentException.class, () -> new Recipe(null, new HashSet<Ingredient>()));
	}

	@Test
	public void recipeNullNameConstructor3()
	{
		assertThrows(IllegalArgumentException.class, () -> new Recipe(null, "Instructions", new HashSet<Ingredient>()));
	}

	@Test
	public void recipeNullInstructionsConstructor3()
	{
		assertThrows(IllegalArgumentException.class, () -> new Recipe("Name", null, new HashSet<Ingredient>()));
	}

	@Test
	public void recipeNullIngredientsConstructor2()
	{
		assertThrows(IllegalArgumentException.class, () -> new Recipe("Name", null));
	}

	@Test
	public void recipeNullIngredientsConstructor3()
	{
		assertThrows(IllegalArgumentException.class, () -> new Recipe("Name", "Instructions", null));
	}

	@Test
	public void recipeAddIngredients()
	{
		HashSet<Ingredient> ingredients = new HashSet<>();
		ingredients.add(new Ingredient("Carrot"));

		Recipe recipe = new Recipe("Carrot Cake");
		recipe.addIngredients(ingredients);

		assertEquals(ingredients, recipe.getIngredients());
	}

	@Test
	public void recipeModifyIngredientsFromOutsideObject()
	{
		HashSet<Ingredient> ingredients = new HashSet<>();
		ingredients.add(new Ingredient("Carrot"));

		Recipe recipe = new Recipe("Carrot Cake", ingredients);
		HashSet<Ingredient> recipeIngredients = recipe.getIngredients();
		recipeIngredients.remove(new Ingredient("Carrot"));

		assertEquals(ingredients, recipeIngredients);
		// TODO: Double check this.
	}

	// ----- TESTING CLASS: RecipeCloud -----

	@Test
	public void recipeCloudAddAndRemove()
	{
		RecipeCloud cloud = new RecipeCloud(host, database, userName, password);
		
		Ingredient carrot = new Ingredient("Carrot");
		Ingredient onion = new Ingredient("Onion");
		HashSet<Ingredient> ingredients = new HashSet<>();
		ingredients.add(carrot);
		ingredients.add(onion);

		Recipe recipe = new Recipe("UnitTestsss", "Instructions", ingredients);

		cloud.add(recipe);

		Recipe result = cloud.getRecipeByID(recipe.getId());

		System.out.println(result.getId());
		System.out.println(result.getName());
		System.out.println(result.getInstructions());
		for (Ingredient ingredient : result.getIngredients())
		{
			System.out.println(ingredient.getName());
		}

		assertEquals(recipe, result);

		cloud.remove(recipe);

		result = cloud.getRecipeByID(recipe.getId());

		assertNull(result);
	}

	@Test
	public void recipeCloudAddDuplicateEntry()
	{

	}

	@Test
	public void recipeCloudGetRecipeIDByNameNull()
	{
		RecipeCloud cloud = new RecipeCloud(host, database, userName, password);
		
		assertThrows(IllegalArgumentException.class, () -> cloud.getRecipeIDByName(null));
	}

	@Test
	public void recipeCloudGetRecipeByIDNegative()
	{
		RecipeCloud cloud = new RecipeCloud(host, database, userName, password);
		
		assertThrows(IllegalArgumentException.class, () -> cloud.getRecipeByID(-1));
	}

	@Test
	public void recipeCloudGetRecipeByNameNull()
	{
		RecipeCloud cloud = new RecipeCloud(host, database, userName, password);
		
		assertThrows(IllegalArgumentException.class, () -> cloud.getRecipeByName(null));
	}

	@Test
	public void recipeCloudAddNull()
	{
		RecipeCloud cloud = new RecipeCloud(host, database, userName, password);
		
		assertThrows(IllegalArgumentException.class, () -> cloud.add(null));
	}

	@Test
	public void recipeCloudRemoveNullRecipe()
	{
		RecipeCloud cloud = new RecipeCloud(host, database, userName, password);
		
		Recipe recipe = null;
		assertThrows(IllegalArgumentException.class, () -> cloud.remove(recipe));
	}

	@Test
	public void recipeCloudRemoveNullName()
	{
		RecipeCloud cloud = new RecipeCloud(host, database, userName, password);

		String string = null;
		assertThrows(IllegalArgumentException.class, () -> cloud.remove(string));
	}
}