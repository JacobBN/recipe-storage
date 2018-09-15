package application;
	
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class Main extends Application {
	
	public static void main(String[] args) 
	{
		launch(args);
	}
	
	private int ingredientsAdded;
	private boolean defaultListMessageAdded;
	private Label ingredientsWarningMessage, recipeMessage;
	private String defaultIngredientListText;
	private String host, database, userName, password;
	
	@Override
	public void start(Stage primaryStage) 
	{
		int screenWidth = 900, screenHeight = 575;
		int defaultPadding = 20;
		int minButtonWidth = 100;
		int offset = 90;
		int instructionsInputHeight = 250;
		int recipeContainerWidth = (screenWidth / 3) * 2 - (defaultPadding * 2);
	    int ingredientsContainerWidth = screenWidth / 3 - (defaultPadding * 2) + offset;
	    int ingredientInputWidth = 200;
		int maxListWidth = 200 + offset, maxListHeight = 175;
		
		String defaultFont = "Helvetica";
		defaultIngredientListText = "name, amount, part of";

//      primaryStage.getIcons().add(new Image()); // TODO: Add an icon.
		primaryStage.setTitle("Recipe Storage");
		BorderPane root = new BorderPane();
		
		// Creating the header.
		HBox header = new HBox();
		header.setId("header");
		header.setPadding(new Insets(defaultPadding));
		
		Text headerText = new Text("Recipe Storage");
		headerText.setFont(Font.font(defaultFont, FontWeight.BOLD, 28));
		header.getChildren().add(headerText);
		header.setAlignment(Pos.CENTER);
		
		// Creating the recipe information section.
		VBox recipeContainer = new VBox();
		recipeContainer.setMinWidth(recipeContainerWidth);
		recipeContainer.setMaxWidth(recipeContainerWidth);
		recipeContainer.setPadding(new Insets(defaultPadding));
		
		Label recipeNameLabel = new Label("Recipe Name:");
		recipeNameLabel.setFont(Font.font(defaultFont, FontWeight.BOLD, 16));
		TextField recipeNameInput = new TextField();
		
		Label recipeInstructionsLabel = new Label("Recipe Instructions:");
		recipeInstructionsLabel.setFont(Font.font(defaultFont, FontWeight.BOLD, 16));
		TextArea recipeInstructionsInput = new TextArea();
		recipeInstructionsInput.setMinHeight(instructionsInputHeight);
		
		HBox recipeAddButtonWrapper = new HBox();
		recipeAddButtonWrapper.setAlignment(Pos.CENTER_RIGHT);
		recipeAddButtonWrapper.setPadding(new Insets(10, 0, 10, 0));
		Button recipeSubmit = new Button("Submit");
		recipeSubmit.setFont(Font.font(defaultFont, FontWeight.BOLD, 16));
		recipeSubmit.setMinWidth(minButtonWidth);
		
		recipeMessage = new Label("");
		recipeMessage.setFont(Font.font(defaultFont, FontWeight.BOLD, 16));
		recipeMessage.setPadding(new Insets(0, 15, 0, 0));

		recipeAddButtonWrapper.getChildren().add(recipeMessage);
		recipeAddButtonWrapper.getChildren().add(recipeSubmit);
		
		recipeContainer.getChildren().add(recipeNameLabel);
		recipeContainer.getChildren().add(recipeNameInput);
		recipeContainer.getChildren().add(recipeInstructionsLabel);
		recipeContainer.getChildren().add(recipeInstructionsInput);
		recipeContainer.getChildren().add(recipeAddButtonWrapper);
		
		// Creating the list of ingredients.
		VBox ingredientsContainer = new VBox();
		ingredientsContainer.setMinWidth(ingredientsContainerWidth);
		ingredientsContainer.setPadding(new Insets(defaultPadding));
		
		Text ingredientTitle = new Text("Add Ingredients");
		ingredientTitle.setFont(Font.font(defaultFont, FontWeight.BOLD, 16));
		
		GridPane ingredientContainer = new GridPane();
		ingredientContainer.setPadding(new Insets(10, 0, 10, 0));
		ingredientContainer.setHgap(10);
		ingredientContainer.setVgap(10);
		
		Label ingredientInputNameLabel = new Label("Name: *");
		ingredientInputNameLabel.setFont(Font.font(defaultFont, FontWeight.BOLD, 16));
		
		Label ingredientInputAmountLabel = new Label("Amount:");
		ingredientInputAmountLabel.setFont(Font.font(defaultFont, FontWeight.BOLD, 16));
		
		Label ingredientInputPartOfLabel = new Label("Part of: ");
		ingredientInputPartOfLabel.setFont(Font.font(defaultFont, FontWeight.BOLD, 16));
		
		TextField ingredientNameInput = new TextField();
		TextField ingredientAmountInput = new TextField();
		TextField ingredientPartOfInput = new TextField();
		ingredientNameInput.setMinWidth(ingredientInputWidth);
		ingredientAmountInput.setMinWidth(ingredientInputWidth);
		ingredientPartOfInput.setMinWidth(ingredientInputWidth);
		
		Button ingredientRemoveButton = new Button("Remove");
		ingredientRemoveButton.setFont(Font.font(defaultFont, FontWeight.BOLD, 16));
		ingredientRemoveButton.setMinWidth(minButtonWidth);

		Button ingredientAddButton = new Button("Add");
		ingredientAddButton.setFont(Font.font(defaultFont, FontWeight.BOLD, 16));
		ingredientAddButton.setMinWidth(minButtonWidth);
		
		ingredientContainer.add(ingredientInputNameLabel, 0, 0);
		ingredientContainer.add(ingredientInputAmountLabel, 0, 1);
		ingredientContainer.add(ingredientInputPartOfLabel, 0, 2);
		ingredientContainer.add(ingredientNameInput, 1, 0, 2, 1);
		ingredientContainer.add(ingredientAmountInput, 1, 1, 2, 1);
		ingredientContainer.add(ingredientPartOfInput, 1, 2, 2, 1);
		ingredientContainer.add(ingredientRemoveButton, 1, 3);
		ingredientContainer.add(ingredientAddButton, 2, 3);
		
		ObservableList<String> ingredients = FXCollections.observableArrayList(defaultIngredientListText);
		ListView<String> ingredientList = new ListView<>(ingredients);
        ingredientList.setCellFactory(TextFieldListCell.forListView());
        ingredientList.setMaxWidth(maxListWidth);
        ingredientList.setMaxHeight(maxListHeight);
		defaultListMessageAdded = true;
		ingredientsAdded = 0;
		
		ingredientList.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() 
		{
            @Override
            public void handle(MouseEvent event)
            {
                event.consume(); // This is to disable selection of items on list.
            }
        });
		
		ingredientsWarningMessage = new Label("");
		ingredientsWarningMessage.setFont(Font.font(defaultFont, FontWeight.BOLD, 16));
		ingredientsWarningMessage.setTextFill(Color.web("#cc0000"));
		
		ingredientsContainer.getChildren().add(ingredientTitle);
		ingredientsContainer.getChildren().add(ingredientContainer);
		ingredientsContainer.getChildren().add(ingredientList);
		ingredientsContainer.getChildren().add(ingredientsWarningMessage);
		
		// Footer
		HBox statementContainer = new HBox();
		statementContainer.setPadding(new Insets(15, defaultPadding, 15, defaultPadding));
		
		Text statement = new Text("Made by Jacob B. Noergaard, 2018");
		statement.setFont(Font.font(defaultFont, FontWeight.NORMAL, 16));
		
		statementContainer.getChildren().add(statement);
		
		HBox linkContainer = new HBox();
		linkContainer.setPadding(new Insets(15, defaultPadding, 15, defaultPadding));
		linkContainer.setAlignment(Pos.CENTER_RIGHT);
		
		Hyperlink link = new Hyperlink();
		String url = "http://www.jacobbn.com";
		link.setText(url);
		link.setFont(Font.font(defaultFont, FontWeight.NORMAL, 18));
		link.setPadding(new Insets(0,0,0,0));
		link.setOnAction(new EventHandler<ActionEvent>() 
		{
			public void handle(ActionEvent e)
			{
				getHostServices().showDocument(url);
			}
		});
		
		linkContainer.getChildren().add(link);
		
		// BorderPane to keep the footer text in the left side.
		BorderPane left = new BorderPane();
		left.setId("left");
		left.setCenter(recipeContainer);
		left.setBottom(statementContainer);
		
		BorderPane right = new BorderPane();
		right.setId("right");
		right.setTop(ingredientsContainer);
		right.setBottom(linkContainer);
		
		// ActionListeners
		ingredientRemoveButton.setOnAction(new EventHandler<ActionEvent>()
		{
			public void handle(ActionEvent e)
			{
				removeIngredientFromList(ingredientNameInput.getText().trim(), 
										 ingredientPartOfInput.getText().trim(),
										 ingredientList);
			}
		});
		
		ingredientAddButton.setOnAction(new EventHandler<ActionEvent>()
		{
			public void handle(ActionEvent e)
			{
				addIngredientToList(ingredientNameInput.getText().trim(),
									ingredientAmountInput.getText().trim(),
									ingredientPartOfInput.getText().trim(),
									ingredientList);
			}
		});
		
		recipeSubmit.setOnAction(new EventHandler<ActionEvent>()
		{
			public void handle(ActionEvent e)
			{
				String recipeName = recipeNameInput.getText().trim();
				if (recipeName.equals(""))
				{
					recipeMessage.setTextFill(Color.web("#cc0000"));
					recipeMessage.setText("ERROR: Please provide a recipe name!");
					return;
				}
				
				String recipeInstructions = recipeInstructionsInput.getText().trim();
				
				if (ingredientList.getItems().size() == 1 && 
						ingredientList.getItems().contains(defaultIngredientListText))
				{
					recipeMessage.setTextFill(Color.web("#cc0000"));
					recipeMessage.setText("ERROR: Please add ingredients!");
					return;
				}
				
				HashSet<Ingredient> ingredients = new HashSet<>();
				for (String nameAndAmount : ingredientList.getItems())
				{
					Ingredient ingredient = parseIngredient(nameAndAmount);
					ingredients.add(ingredient);
				}
				
				Recipe recipe = new Recipe(recipeName, recipeInstructions, ingredients);
				
				RecipeCloud recipeCloud = new RecipeCloud(host, database, userName, password);
				recipeCloud.add(recipe);
				clearFields();
				recipeMessage.setTextFill(Color.web("#3cb500"));
				recipeMessage.setText("Succesfully Submitted Recipe!");
			}

			private Ingredient parseIngredient(String ingredientString)
			{
				String[] subParts = ingredientString.split(", ");
				
				if (subParts.length == 2) // Has name and amount.
				{
					return new Ingredient(subParts[0], subParts[1]);
				}
				else if (subParts.length > 3) // Has name, amount, and part of.
				{
					return new Ingredient(subParts[0], subParts[1], subParts[2]);
				}
				
				return new Ingredient(subParts[0]);
			}
			
			private void clearFields() 
			{
				recipeNameInput.setText("");
				recipeInstructionsInput.setText("");
				ingredientNameInput.setText("");
				ingredientAmountInput.setText("");
				ingredientPartOfInput.setText("");
				ingredientList.getItems().clear();
				ingredientList.getItems().add(defaultIngredientListText);
			}
		});
		
		// Wrap it up.
		root.setTop(header);
		root.setLeft(left);
		root.setRight(right);
		
		Scene scene = new Scene(root, screenWidth, screenHeight);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setResizable(false);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	private void removeIngredientFromList(String ingredient, String partOf, ListView<String> ingredientList) 
	{
		if (!ingredient.trim().equals(""))
		{
			List<String> ingredientsToRemove = new ArrayList<>();
			for (String item : ingredientList.getItems())
			{
				if (ingredient.equals(item.split(", ")[0]))
				{
					ingredientsToRemove.add(item);
				}
			}
			
			if (ingredientsToRemove.size() > 1)
			{
				ingredientsWarningMessage.setText("SPECIFY: Part of the recipe");
			}
			else
			{
				ingredientList.getItems().removeAll(ingredientsToRemove);
				
				ingredientsAdded = ingredientsAdded == 0 ? ingredientsAdded : --ingredientsAdded;
				
				if (ingredientsAdded == 0 && !defaultListMessageAdded)
				{
					defaultListMessageAdded = true;
					ingredientList.getItems().add(defaultIngredientListText);
				}
				else if (ingredientsAdded == 0 && defaultListMessageAdded)
				{
					ingredientsWarningMessage.setText("NOTE: There is nothing to remove.");
				}
				else
				{
					ingredientsWarningMessage.setText("");
				}
			}
		}
		else
		{
			ingredientsWarningMessage.setText("ERROR: Provide ingredient details!");
		}
	}
	
	private void addIngredientToList(String name, String amount, String partOf, ListView<String> ingredientList) 
	{
		if (!name.equals("") && !(amount.equals("") && !partOf.equals("")))
		{
			if (ingredientsAdded == 0 && defaultListMessageAdded)
			{
				defaultListMessageAdded = false;
				ingredientList.getItems().clear();
			}
			
			if (amount.equals("") && partOf.equals(""))
			{
				ingredientList.getItems().add(ingredientsAdded++, name);
			}
			else if (partOf.equals(""))
			{
				ingredientList.getItems().add(ingredientsAdded++, name + ", " + amount);
			}
			else
			{
				ingredientList.getItems().add
						(ingredientsAdded++, name + ", " + amount + ", " + partOf);
			}
			
			ingredientsWarningMessage.setText("");
		}
		else
		{
			if (!name.equals("") && !(amount.equals("") && !partOf.equals("")))
			{
				ingredientsWarningMessage.setText("ERROR: Please specify an amount.");
			}
			else
			{
				ingredientsWarningMessage.setText("ERROR: Provide ingredient details!");
			}
		}
	}
}
