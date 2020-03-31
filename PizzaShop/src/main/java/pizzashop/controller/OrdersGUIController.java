package pizzashop.controller;

import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import pizzashop.model.MenuDataModel;
import pizzashop.gui.PaymentAlert;
import pizzashop.service.PizzaService;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class OrdersGUIController {

    @FXML
    private ComboBox<Integer> orderQuantity;
    @FXML
    private TableView orderTable;
    @FXML
    private TableColumn tableQuantity;
    @FXML
    protected TableColumn tableMenuItem;
    @FXML
    private TableColumn tablePrice;
    @FXML
    private Label pizzaTypeLabel;
    @FXML
    private Button addToOrder;
    @FXML
    private Label orderStatus;
    @FXML
    private Button placeOrder;
    @FXML
    private Button orderServed;
    @FXML
    private Button payOrder;
    @FXML
    private Button newOrder;

    private List<String> orderList = FXCollections.observableArrayList();
    private List<Double> orderPaymentList = FXCollections.observableArrayList();

    private static double getTotalAmount() {
        return totalAmount;
    }

    private static void setTotalAmount(double totalAmount) {
        OrdersGUIController.totalAmount = totalAmount;
    }

    private PizzaService pizzaService;
    private int tableNumber;
    private Button tableButton;

    private TableView<MenuDataModel> table = new TableView<>();
    private Calendar now = Calendar.getInstance();
    private static double totalAmount;

    public void setService(PizzaService service, int tableNumber, Button tableButton) {
        this.pizzaService = service;
        this.tableNumber = tableNumber;
        this.tableButton = tableButton;

        initData();

    }

    private void initData() {
        ObservableList<MenuDataModel> menuData;

        this.tableButton.setStyle("-fx-background-color:ORANGE");

        menuData = FXCollections.observableArrayList(pizzaService.getMenuData());
        menuData.setAll(pizzaService.getMenuData());
        orderTable.setItems(menuData);

        //Controller for Place Order Button
        placeOrder.setOnAction(event -> {
            orderList = menuData.stream()
                    .filter(x -> x.getQuantity() > 0)
                    .map(menuDataModel -> menuDataModel.getQuantity() + " " + menuDataModel.getMenuItem())
                    .collect(Collectors.toList());
            KitchenGUIController.order.add("Table" + tableNumber + " " + orderList.toString());
            orderStatus.setText("Order placed at: " + now.get(Calendar.HOUR) + ":" + now.get(Calendar.MINUTE));
        });

        //Controller for Order Served Button
        orderServed.setOnAction(event -> orderStatus.setText("Served at: " + now.get(Calendar.HOUR) + ":" + now.get(Calendar.MINUTE)));

        //Controller for Pay Order Button
        payOrder.setOnAction(event -> {
            orderPaymentList = menuData.stream()
                    .filter(x -> x.getQuantity() > 0)
                    .map(menuDataModel -> menuDataModel.getQuantity() * menuDataModel.getPrice())
                    .collect(Collectors.toList());
            setTotalAmount(orderPaymentList.stream().mapToDouble(e -> e).sum());
            orderStatus.setText("Total amount: " + getTotalAmount());
            System.out.println("--------------------------");
            System.out.println("Table: " + tableNumber);
            System.out.println("Total: " + getTotalAmount());
            System.out.println("--------------------------");
            PaymentAlert pay = new PaymentAlert(pizzaService);

            pay.showPaymentAlert(tableNumber, getTotalAmount());
            for (MenuDataModel item : menuData) {
                item.setQuantity(0);
            }
        });
    }

    public void initialize() {

        //populate table view with menuData from OrderGUI
        table.setEditable(true);
        tableMenuItem.setCellValueFactory(
                new PropertyValueFactory<MenuDataModel, String>("menuItem"));
        tablePrice.setCellValueFactory(
                new PropertyValueFactory<MenuDataModel, Double>("price"));
        tableQuantity.setCellValueFactory(
                new PropertyValueFactory<MenuDataModel, Integer>("quantity"));

        //bind pizzaTypeLabel and quantity combo box with the selection on the table view
        orderTable.getSelectionModel().selectedItemProperty().addListener((ChangeListener<MenuDataModel>) (observable, oldValue, newValue) -> pizzaTypeLabel.textProperty().bind(newValue.menuItemProperty()));

        //Populate Combo box for Quantity
        ObservableList<Integer> quantityValues = FXCollections.observableArrayList(0, 1, 2, 3, 4, 5);
        orderQuantity.getItems().addAll(quantityValues);
        orderQuantity.setPromptText("Quantity");
        //Check if a quantity is selected
        orderQuantity.setOnAction(event -> addToOrder.setDisable(false));

        //Controller for Add to order Button
        addToOrder.setDisable(true);
        addToOrder.setOnAction(event -> {
            MenuDataModel orderItem = (MenuDataModel) orderTable.getSelectionModel().getSelectedItem();
            if (orderQuantity.getValue() != 0) {
                orderItem.setQuantity(orderQuantity.getValue() + orderItem.getQuantity());
            } else {
                orderItem.setQuantity(0);
            }

        });

        //Controller for Exit table Button
        newOrder.setOnAction(event -> {
            Alert exitAlert = new Alert(Alert.AlertType.CONFIRMATION, "Exit table?", ButtonType.YES, ButtonType.NO);
            Optional<ButtonType> result = exitAlert.showAndWait();
            if (result.get() == ButtonType.YES) {
                this.tableButton.setStyle(null);
                Stage stage = (Stage) newOrder.getScene().getWindow();
                stage.close();
            }
        });
    }
}
