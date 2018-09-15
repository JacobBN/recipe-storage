/**
 * @author Jacob B.N.
 * @version 08/04/2018
 */
package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;

/**
 * <p>Represents the recipe data. Connects to a database as necessary.</p>
 */
public class RecipeCloud {
	
	// Fields
	private String url;
	private String userName;
	private String password;
	
	/**
	 * <p>Creates a RecipeCloud object.</p>
	 * 
	 * @param hostName
	 * @param dbName
	 * @param userName
	 * @param password
	 */
	public RecipeCloud(String hostName, String dbName, String userName, String password)
	{
		this.url = "jdbc:mysql://" + hostName + "/" + dbName + "?useSSL=false&serverTimezone=UTC";
		this.userName = userName;
		this.password = password;
	}
	
	/**
	 * <p>Gets the recipe's id and returns it.</p>
	 * 
	 * @param name
	 * @return
	 * @throws IllegalArgumentException
	 */
	public int getRecipeIDByName(String name)
	{
		if (name == null)
		{
			throw new IllegalArgumentException();
		}
		
		int id = -1;
		try 
		{
			Connection connection = DriverManager.getConnection(url, userName, password);
			
			String query = "SELECT ID FROM Recipe WHERE Name = ?";
			
			PreparedStatement preStatement = connection.prepareStatement(query);
			preStatement.setString(1, name);
			ResultSet result = preStatement.executeQuery();
			
			while (result.next())
			{
				id = result.getInt(1);
			}
			
			connection.close();
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
		return id;
	}
	
	/**
	 * <p>Gets the recipe information and returns it as a Recipe object.</p>
	 * 
	 * <p>Returns null if unable to find recipe.</p>
	 * 
	 * @param name
	 * @return
	 * @throws IllegalArgumentException
	 */
	public Recipe getRecipeByID(int ID)
	{
		if (ID < 0)
		{
			throw new IllegalArgumentException();
		}
		
		Recipe recipe = null;
		
		try 
		{
			Connection connection = DriverManager.getConnection(url, userName, password);
			
			String query = "SELECT Name, Instructions FROM Recipe WHERE ID = ?";
			
			PreparedStatement preStatement = connection.prepareStatement(query);
			preStatement.setInt(1, ID);
			ResultSet result = preStatement.executeQuery();
			
			String name = "";
			String instructions = "";
			
			while (result.next())
			{
				name = result.getString(1);
				instructions = result.getString(2);
			}
			
			recipe = new Recipe(name);
			recipe.setId(ID);
			recipe.setInstructions(instructions);
			System.out.println(recipe.getId());
			System.out.println(recipe.getName());
			System.out.println(recipe.getInstructions());
			
			query = "SELECT Amount, Item, Part FROM Ingredient WHERE RecipeID = ?";
			preStatement = connection.prepareStatement(query);
			preStatement.setInt(1, ID);
			result = preStatement.executeQuery();
			
			while (result.next())
			{
				Ingredient ingredient = 
						new Ingredient(result.getString(2), result.getString(1), result.getString(3));
				recipe.addIngredient(ingredient);
				System.out.println(ingredient.getName());
			}
			
			connection.close();
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
		return recipe;
	}
	
	/**
	 * <p>Gets the recipe by its name and returns it.</p>
	 * 
	 * <p>Returns null if it can't be found.</p>
	 * 
	 * @param name
	 * @return
	 * @throws IllegalArgumentException
	 */
	public Recipe getRecipeByName(String name)
	{
		if (name == null)
		{
			throw new IllegalArgumentException();
		}
		
		Recipe recipe = null;
		try 
		{
			Connection connection = DriverManager.getConnection(url, userName, password);
			
			String query = "SELECT ID, Instructions FROM Recipe WHERE Name = ?";
			
			PreparedStatement preStatement = connection.prepareStatement(query);
			preStatement.setString(1, name);
			ResultSet result = preStatement.executeQuery();
			
			recipe = new Recipe(name);
			
			while (result.next())
			{
				recipe = new Recipe(name);
				recipe.setId(result.getInt(1));
				recipe.setInstructions(result.getString(2));
			}
			
			query = "SELECT Amount, Item, Part FROM Ingredient WHERE RecipeID = ?";
			preStatement = connection.prepareStatement(query);
			preStatement.setInt(1, recipe.getId());
			result = preStatement.executeQuery();

			while (result.next())
			{
				Ingredient ingredient = 
						new Ingredient(result.getString(2), result.getString(1), result.getString(3));
				recipe.addIngredient(ingredient);
			}
			
			connection.close();
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
		return recipe;
	}
	
	/**
	 * <p>Adds the recipe to the database.</p>
	 * 
	 * @param recipe
	 * @throws IllegalArgumentException
	 */
	public void add(Recipe recipe)
	{
		if (recipe == null)
		{
			throw new IllegalArgumentException();
		}
		
		String name = recipe.getName();
		String instructions = recipe.getInstructions();
		HashSet<Ingredient> ingredients = recipe.getIngredients();
		
		try
		{
			Connection connection = DriverManager.getConnection(url, userName, password);
			
			String query = "INSERT INTO Recipe (Name, Instructions) VALUES (?, ?)";
			
			PreparedStatement preStatement = connection.prepareStatement(query);
			preStatement.setString(1, name);
			preStatement.setString(2, instructions);
			int responseCode = preStatement.executeUpdate();
			
			if (responseCode == 0)
			{
				throw new SQLException("Insertion of recipe failed");
			}
			
			query = "SELECT Max(ID) FROM Recipe";
			preStatement = connection.prepareStatement(query);
			ResultSet result = preStatement.executeQuery();
			if (result.next())
			{
				recipe.setId(result.getInt(1));
			}
			int recipeId = recipe.getId();
			int amountOfIngredients = ingredients.size();
			
			query = "INSERT INTO Ingredient (RecipeID, Amount, Item, Part) VALUES ";
			
			int count = 1;
			for (@SuppressWarnings("unused") Ingredient ingredient : ingredients)
			{
				if (count != amountOfIngredients)
				{
					query += "(?, ?, ?, ?),";
				}
				else
				{
					query += "(?, ?, ?, ?)";
				}
				count++;
			}
			
			preStatement = connection.prepareStatement(query);
			
			count = 1;
			for (Ingredient ingredient : ingredients)
			{
				preStatement.setInt(count, recipeId);
				count++;
				
				preStatement.setString(count, ingredient.getAmount());
				count++;
				
				preStatement.setString(count, ingredient.getName());
				count++;
				
				preStatement.setString(count, ingredient.getUsedIn());
				count++;
			}
			
			responseCode = preStatement.executeUpdate();
			
			if (responseCode == 0)
			{
				throw new SQLException("Insertion of ingredients failed");
			}
			
			connection.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * <p>Removes the recipe from the database.</p>
	 * 
	 * @param recipe
	 * @throws IllegalArgumentException
	 */
	public void remove(Recipe recipe)
	{
		if (recipe == null)
		{
			throw new IllegalArgumentException();
		}
		
		try
		{
			Connection connection = DriverManager.getConnection(url, userName, password);
			
			String query = "SELECT ID FROM Recipe WHERE Name = ?";
			PreparedStatement preStatement = connection.prepareStatement(query);
			preStatement.setString(1, recipe.getName());
			ResultSet result = preStatement.executeQuery();
			if (result.next())
			{
				recipe.setId(result.getInt(1));
			}
			int recipeId = recipe.getId();
			
			query = "DELETE FROM Ingredient WHERE RecipeID = ?";
			preStatement = connection.prepareStatement(query);
			preStatement.setInt(1, recipeId);
			preStatement.executeUpdate();

			query = "DELETE FROM Recipe WHERE ID = ?";
			preStatement = connection.prepareStatement(query);
			preStatement.setInt(1, recipeId);
			preStatement.executeUpdate();
			
			connection.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * <p>Removes the recipe from the database.</p>
	 * 
	 * @param recipeName
	 * @throws IllegalArgumentException
	 */
	public void remove(String recipeName)
	{
		if (recipeName == null)
		{
			throw new IllegalArgumentException();
		}
		
		try
		{
			Connection connection = DriverManager.getConnection(url, userName, password);
			
			String query = "SELECT ID FROM Recipe WHERE Name = ?";
			PreparedStatement preStatement = connection.prepareStatement(query);
			preStatement.setString(1, recipeName);
			ResultSet result = preStatement.executeQuery();
			int recipeId = -1;
			if (result.next())
			{
				recipeId = result.getInt(1);
			}
			
			query = "DELETE FROM Ingredient WHERE RecipeID = ?";
			preStatement = connection.prepareStatement(query);
			preStatement.setInt(1, recipeId);
			preStatement.executeUpdate();

			query = "DELETE FROM Recipe WHERE ID = ?";
			preStatement = connection.prepareStatement(query);
			preStatement.setInt(1, recipeId);
			preStatement.executeUpdate();
			
			connection.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
}
