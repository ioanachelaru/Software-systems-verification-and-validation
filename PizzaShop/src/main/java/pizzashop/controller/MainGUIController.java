package pizzashop.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import pizzashop.gui.OrdersGUI;
import pizzashop.service.PizzaService;

public class MainGUIController {
    @FXML
    private Button table1;
    @FXML
    private Button table2;
    @FXML
    private Button table3;
    @FXML
    private Button table4;
    @FXML
    private Button table5;
    @FXML
    private Button table6;
    @FXML
    private Button table7;
    @FXML
    private Button table8;
    @FXML
    private MenuItem help;

    OrdersGUI table1Orders;
    OrdersGUI table2Orders;
    OrdersGUI table3Orders;
    OrdersGUI table4Orders;
    OrdersGUI table5Orders;
    OrdersGUI table6Orders;
    OrdersGUI table7Orders;
    OrdersGUI table8Orders;

    PizzaService pizzaService;

    public void setService(PizzaService service) {
        this.pizzaService = service;
        tableOrdersInit();
        tableHandlers();
    }

    private void tableOrdersInit() {
        table1Orders = new OrdersGUI(table1);
        table2Orders = new OrdersGUI(table2);
        table3Orders = new OrdersGUI(table3);
        table4Orders = new OrdersGUI(table4);
        table5Orders = new OrdersGUI(table5);
        table6Orders = new OrdersGUI(table6);
        table7Orders = new OrdersGUI(table7);
        table8Orders = new OrdersGUI(table8);
    }

    private void tableHandlers() {
        table1.setOnAction(event -> {
            table1Orders.setTableNumber(1);
            table1Orders.displayOrdersForm(pizzaService);
        });
        table2.setOnAction(event -> {
            table2Orders.setTableNumber(2);
            table2Orders.displayOrdersForm(pizzaService);
        });
        table3.setOnAction(event -> {
            table3Orders.setTableNumber(3);
            table3Orders.displayOrdersForm(pizzaService);
        });
        table4.setOnAction(event -> {
            table4Orders.setTableNumber(4);
            table4Orders.displayOrdersForm(pizzaService);
        });
        table5.setOnAction(event -> {
            table5Orders.setTableNumber(5);
            table5Orders.displayOrdersForm(pizzaService);
        });
        table6.setOnAction(event -> {
            table6Orders.setTableNumber(6);
            table6Orders.displayOrdersForm(pizzaService);
        });
        table7.setOnAction(event -> {
            table7Orders.setTableNumber(7);
            table7Orders.displayOrdersForm(pizzaService);
        });
        table8.setOnAction(event -> {
            table8Orders.setTableNumber(8);
            table8Orders.displayOrdersForm(pizzaService);
        });

    }


    public void initialize() {

        help.setOnAction((ActionEvent event) -> {
            Stage stage = new Stage();

            stage.setTitle("How to use..");
            final Group rootGroup = new Group();
            final Scene scene = new Scene(rootGroup, 600, 250);
            final Text text1 = new Text(
                    25, 25,
                    "1. Click on a Table button - a table order form will pop up. " + System.lineSeparator()
                            + System.lineSeparator() +
                            "2.Choose a Menu item from the menu list, choose Quantity from drop down list, " + System.lineSeparator()
                            + "press Add to order button and Click on the Menu list (Repeat)" + System.lineSeparator()
                            + System.lineSeparator() +
                            "3.Press Place order button, the order will appear in the Kitchen window" + System.lineSeparator()
                            + System.lineSeparator() +
                            "4.On the Kitchen window Click on the order in the Orders List and Press the Cook button, " + System.lineSeparator()
                            + "then after Click on the order in the Orders list and then on the Ready button" + System.lineSeparator()
                            + System.lineSeparator() +
                            "5.On the Table order form press the Order served button, at the end press" + System.lineSeparator()
                            + "the Pay order button " + System.lineSeparator()
            );

            text1.setFont(Font.font(java.awt.Font.SERIF, 15));
            rootGroup.getChildren().add(text1);

            stage.setScene(scene);
            stage.setResizable(false);
            stage.showAndWait();
        });
    }
}
