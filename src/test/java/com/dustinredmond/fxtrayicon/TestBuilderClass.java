package com.dustinredmond.fxtrayicon;

/*
 * Copyright (c) 2021 Dustin K. Redmond & contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;

/**
 * A test of the FXTrayIcon functionality in the form
 * of a runnable JavaFX application. Compile and run this
 * class to test the features of FXTrayIcon.
 */
public class TestBuilderClass extends Application {

	private FXTrayIcon trayIcon = null;

	@Override
	public void start(Stage stage) {


		BorderPane root = new BorderPane();
		stage.setScene(new Scene(root));

		// By default, our FXTrayIcon will have an entry with our Application's title in bold font,
		// when clicked, this MenuItem will call stage.show()
		//
		// This can be disabled by simply removing the MenuItem after instantiating the FXTrayIcon
		// though, by convention, most applications implement this functionality.
		stage.setTitle("FXTrayIcon Test Builder Class!");

		// We first create the MenuItems that we want to add into FXTrayIcon
		MenuItem menuItemTest = new MenuItem("Create some JavaFX component!");
		menuItemTest.setOnAction(e -> new Alert(Alert.AlertType.INFORMATION, "We just ran some JavaFX code from an AWT MenuItem!").showAndWait());
		MenuItem menuExit = new MenuItem("Exit Application");
		menuExit.setOnAction(e->System.exit(0));


		// We can also nest menus, below is an Options menu with sub-items
		Menu menuOptions = new Menu("Options");
		MenuItem miOn = new MenuItem("On");
		miOn.setOnAction(e -> System.out.println("Options -> On clicked"));
		MenuItem miOff = new MenuItem("Off");
		miOff.setOnAction(e -> System.out.println("Options -> Off clicked"));
		menuOptions.getItems().addAll(miOn, miOff);

		VBox vBox = new VBox(5);
		vBox.getChildren().add(new Label("You should see a tray icon!\nTry closing this window " +
										 "and double-clicking the icon.\n" +
										 "Try single-clicking it."));
		Button buttonRemoveTrayIcon = new Button("Remove TrayIcon");
		vBox.getChildren().add(buttonRemoveTrayIcon);


		// Removing the FXTrayIcon, this will also cause the JVM to terminate
		// after the last JavaFX Stage is hidden
		buttonRemoveTrayIcon.setOnAction(e -> trayIcon.hide());

		Button buttonDefaultMsg = new Button("Show a \"Default\" message");
		// showDefaultMessage uses the FXTrayIcon image in the notification
		buttonDefaultMsg.setOnAction(e -> trayIcon.showMessage("A caption text", "Some content text."));

		Button buttonInfoMsg = new Button("Show a \"Info\" message");
		// other showXXX methods use an icon appropriate for the message type
		buttonInfoMsg.setOnAction(e -> trayIcon.showInfoMessage("A caption text", "Some content text"));

		Button buttonWarnMsg = new Button("Show a \"Warn\" message");
		buttonWarnMsg.setOnAction(e -> trayIcon.showWarningMessage("A caption text", "Some content text"));

		Button buttonErrorMsg = new Button("Show a \"Error\" message");
		buttonErrorMsg.setOnAction(e -> trayIcon.showErrorMessage("A caption text", "Some content text"));

		HBox hBox = new HBox(5, buttonDefaultMsg, buttonInfoMsg, buttonWarnMsg, buttonErrorMsg);
		vBox.getChildren().add(hBox);

		root.setCenter(vBox);
		stage.sizeToScene();
		stage.show();

		// Once we have built our Stage and Scene, we invoke FXTrayIcons Builder class
		// providing it with the parent Stage and a path to an image file.
		// The Builder class brings everything together in a single sentence, including
		// all of our menus and separators.
		System.out.println(System.getProperty("os.name"));
		int width = (System.getProperty("os.name").contains("Windows")) ? 16 : 26; //Windows likes 16 x 16 icons
		int height = (System.getProperty("os.name").contains("Windows")) ? 16 : 26;

		trayIcon = new FXTrayIcon.Builder(stage,getClass().getResource("FXIconRedYellow.png"),width,height)
				.toolTip("An alternative tooltip!")
				.menuItem(menuItemTest)
				.menuItem(menuOptions)
				.applicationTitle("FXTrayIcon Builder Text")
				.separator()
				.menuItem(menuExit)
				.build();

		trayIcon.show();
	}

	/**
	 * Test icon used for FXTrayIcon runnable tests
	 * @return URL to an example icon PNG
	 */
	public URL getIcon() {
		return getClass().getResource("FXIconRedYellow.png");
	}

}