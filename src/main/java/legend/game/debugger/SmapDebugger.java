package legend.game.debugger;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SmapDebugger extends Application {
  @Override
  public void start(final Stage stage) throws Exception {
    final Parent root = FXMLLoader.load(this.getClass().getResource("smap_debugger.fxml"));
    final Scene scene = new Scene(root);
    scene.getStylesheets().add(this.getClass().getResource("smap_debugger.css").toExternalForm());

    stage.setTitle("Submap Debugger");
    stage.setScene(scene);
    stage.show();
  }
}
