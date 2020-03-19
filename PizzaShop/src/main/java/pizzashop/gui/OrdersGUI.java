package pizzashop.gui;

import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import pizzashop.controller.OrdersGUIController;
import pizzashop.service.PizzaService;
import java.io.IOException;
import java.util.logging.Logger;


public class OrdersGUI {

    private int tableNumber;
    private int getTableNumber() {
        return tableNumber;
    }
    public void setTableNumber(int tableNumber) { this.tableNumber = tableNumber; }
    private PizzaService service;

    public void displayOrdersForm(PizzaService service){
     VBox vBoxOrders = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/OrdersGUIFXML.fxml"));

            vBoxOrders = loader.load();
            OrdersGUIController ordersCtrl= loader.getController();
            ordersCtrl.setService(service, tableNumber);

        } catch (IOException e) {
            Logger.getLogger(e.getMessage());
        }

     Stage stage = new Stage();
     stage.setTitle("Table"+getTableNumber()+" order form");
     stage.setResizable(false);

     // disable X on the window
        // consume event
        stage.setOnCloseRequest(Event::consume);
     stage.setScene(new Scene(vBoxOrders));
     stage.show();
    }
}
