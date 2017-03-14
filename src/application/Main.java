package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;


public class Main extends Application
{
	@Override
	public void start(Stage primaryStage)
	{
		try
		{
			BorderPane root = FXMLLoader.load(Main.class.getResource("view/GOLfxml.fxml"));
			Scene scene = new Scene(root,1300,900);
			scene.getStylesheets().add(getClass().getResource("view/application.css").toExternalForm());

			primaryStage.setFullScreen(true);
			scene.setOnMouseClicked(event ->
			{
				if(event.getButton().equals(MouseButton.PRIMARY))
				{
					if(event.getClickCount() == 2)
					{
						primaryStage.setFullScreen(!primaryStage.isFullScreen());
					}
				}
			});
			primaryStage.setTitle("Game of life");
			primaryStage.setMinHeight(1000);
			primaryStage.setMinWidth(1350);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public static void main(String[] args)
	{
		launch(args);
	}
}
