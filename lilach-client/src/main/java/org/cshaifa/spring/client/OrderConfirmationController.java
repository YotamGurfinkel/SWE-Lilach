package org.cshaifa.spring.client;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;

import org.cshaifa.spring.entities.CatalogItem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class OrderConfirmationController {

    @FXML
    private Label addressLabel;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Label cardNumberLabel;

    @FXML
    private Label deliveryTimeLabel;

    @FXML
    private Label expDateLabel;

    @FXML
    private Label firstNameDeliveryLabel;

    @FXML
    private Label firstNamePaymentLabel;

    @FXML
    private VBox itemsVbox;

    @FXML
    private Label lastNameDeliveryLabel;

    @FXML
    private Label lastNamePaymentLabel;

    @FXML
    private Label messageLabel;

    @FXML
    private Label phoneNumberLabel;

    @FXML
    private Label storeNameLabel;

    @FXML
    private Label storeAddressLabel;

    @FXML
    private VBox summaryVbox;

    @FXML
    private AnchorPane deliveryAnchorPane;

    @FXML
    private AnchorPane pickupAnchorPane;

    private HBox getItemHBox(CatalogItem item, int quantity) {
        HBox hBox = new HBox();
        ImageView iv = null;

        if (item.getImage() != null) {
            try {
                iv = new ImageView(App.getImageFromByteArray(item.getImage()));
                iv.setFitWidth(50);
                iv.setFitHeight(50);
            } catch (IOException e1) {
                // TODO: maybe log the exception somewhere
                e1.printStackTrace();
            }
        }

        Label itemName = new Label(item.getName() + "\t\t");
        double price = item.getPrice();
        if (item.isOnSale()) {
            price = new BigDecimal((price * 0.01 * (100 - item.getDiscount()))).setScale(2, RoundingMode.HALF_UP)
                    .doubleValue();
        }
        double finalPrice = price * quantity;
        Label itemPrice = new Label(String.format("%.2f", price) + "\t");
        Label itemFinalPrice = new Label(String.format("%.2f", finalPrice) + "\t");
        Label itemQuantity = new Label(Integer.toString(quantity));
        HBox.setMargin(itemName, new Insets(0, 0, 0, 0));
        HBox.setMargin(itemPrice, new Insets(0, 60, 0, 60));
        HBox.setMargin(itemQuantity, new Insets(0, 0, 0, 0));
        HBox.setMargin(itemFinalPrice, new Insets(0, 0, 0, 0));
        hBox.getChildren().addAll(iv, itemName, itemPrice, itemQuantity, itemFinalPrice);
        hBox.setSpacing(5);
        hBox.getStyleClass().add("item");
        hBox.setAlignment(Pos.CENTER_LEFT);
        return hBox;
    }

    @FXML
    void initialize() {
        App.getCart().forEach((item, quantity) -> itemsVbox.getChildren().add(getItemHBox(item, quantity)));

        addressLabel.setText(App.getRecipientAddress());
        cardNumberLabel.setText(App.getCardNumber().substring(App.getCardNumber().length() - 4, App.getCardNumber().length()));
        expDateLabel.setText(App.getCardExpDate());
        // TODO: change to actual first name
        firstNamePaymentLabel.setText("Yaakov");
        lastNamePaymentLabel.setText("Levi");

        if (!App.isOrderDelivery()) {
            // TODO: change to actual store name
            storeNameLabel.setText("Temp Store");
            storeAddressLabel.setText("Tel Aviv");
            return;
        } else {
            pickupAnchorPane.setVisible(false);
            deliveryAnchorPane.setVisible(true);
        }

        firstNameDeliveryLabel.setText(App.getRecipientFirstName());
        lastNameDeliveryLabel.setText(App.getRecipientLastName());
        messageLabel.setText(App.getMessage());
        phoneNumberLabel.setText(App.getCustomerPhoneNumber());
        deliveryTimeLabel.setText(new SimpleDateFormat("dd/MM/yyyy HH:mm").format(App.getSupplyDate()));
    }

    @FXML
    private void goBack(ActionEvent event) {
        try {
            App.setContent("paymentDetails");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void placeOrder(ActionEvent event) {
        // TODO: place the order...
    }
}