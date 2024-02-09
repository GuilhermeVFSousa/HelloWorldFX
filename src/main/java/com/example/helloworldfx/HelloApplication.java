package com.example.helloworldfx;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        // Criando uma label para mostrar o caminho do arquivo
        Label label = new Label("Arraste e solte um arquivo aqui");
        Label label2 = new Label("Processo:");

        // Configurando o evento de drag and drop na label
        label.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                if (event.getGestureSource() != label && event.getDragboard().hasFiles()) {
                    event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                }
                event.consume();
            }
        });

        label.setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                Dragboard db = event.getDragboard();
                boolean success = false;
                if (db.hasFiles()) {
                    String filePath = db.getFiles().get(0).getAbsolutePath();
                    label.setText("Arquivo soltado: " + filePath);
                    success = true;
                }
                event.setDropCompleted(success);
                event.consume();
            }
        });

        TextField textField = new TextField();
        Button button = new Button("Clique aqui");
        Button button2 = new Button("COMANDO WINDOWS");

        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Texto digitado: " + textField.getText());
            }
        });

        button2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ExecutorService executor = Executors.newFixedThreadPool(3);

                executor.execute(() -> {
                    WinOsCommandHandler.executeCommand(label2, "cd C:\\", "ls");
                });
            }
        });

        VBox vbox = new VBox(10); // Espaçamento de 10 pixels entre os elementos
        vbox.setPadding(new Insets(30));
        vbox.getChildren().addAll(label, textField, button, button2, label2);
        vbox.setAlignment(Pos.CENTER);

        Scene scene = new Scene(new StackPane(vbox), 600, 600);

        stage.setTitle("Drag and Drop Example");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}