package com.example.android.justjava;

/**
 * IMPORTANT: Add your package below. Package name can be found in the project's AndroidManifest.xml file.
 * This is the package name our example uses:
 *
 * package com.example.android.justjava;
 *
 */


import java.text.NumberFormat;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import junit.framework.Assert;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    int quantity = 0;
    EditText clientsName;
    CheckBox whippedCream;
    public boolean isWhippedCream;
    CheckBox chocolate;
    public boolean isChocolate;

    /**
     * This method is called when the plus button is clicked.
     */
    public void increment(View view) {
        if (quantity < 10) {
            quantity++;
        }
        display(quantity);
    }

    /**
     * This method is called when the minus button is clicked.
     */
    public void decrement(View view) {
        if (quantity > 1) {
        quantity--;
        }
        display(quantity);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        chocolate = (CheckBox) findViewById(R.id.chocolate);
        isChocolate = chocolate.isChecked();
        clientsName = (EditText)findViewById(R.id.clients_name);
        int price = calculatePrice(isChocolate, isWhippedCream);
        String orderMessage = createOrderSummary(price, isWhippedCream, isChocolate);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mail to:"));
        intent.putExtra(Intent.EXTRA_TEXT, orderMessage);
        intent.putExtra(Intent.EXTRA_SUBJECT, "Order a coffee for: " + clientsName);
      //  intent.setData(Uri.parse("geo: 47.6, -122.3"));
        if (intent.resolveActivity(getPackageManager()) != null)
        {
            startActivity(intent);
        }

        displayMessage(orderMessage);
    }

    private int calculatePrice(boolean isChocolate, boolean isWhippedCream) {
        int basePrice = 5;
        if (isChocolate)
        {
            basePrice += 2;
        }

        if(isWhippedCream)
        {
            basePrice += 1;
        }

        Log.v("JustJava", "The price is: " + basePrice);
        return basePrice * quantity;
    }

    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * Create summary of the order.
     *
     * @param price           of the order
     * @param isWhippedCream is whether or not to add whipped cream to the coffee
     * @param isChocolate    is whether or not to add chocolate to the coffee
     * @return text summary
     */
    private String createOrderSummary(int price, boolean isWhippedCream, boolean isChocolate){
        String priceMessage = "Name: " + clientsName.getText().toString();
        priceMessage += "\nAdd whipped cream? " + isWhippedCream();
        priceMessage += "\nAdd chocolate? " + isChocolate;
        priceMessage += "\nQuantity: " + quantity;
        priceMessage += "\nTotal: $" + price;
        priceMessage += "\n" + getString(R.string.thank_you);
        Log.v("JustJava", "CalculatePrice to " + price);
        return priceMessage;
    }

    /**
     * This method displays the given price on the screen.
     */
    private void displayMessage(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setBackgroundColor(2);
        orderSummaryTextView.setText(message);
    }

    public boolean isWhippedCream() {
        whippedCream = (CheckBox) findViewById(R.id.whippedCream);
        return isWhippedCream = whippedCream.isChecked();
    }
}
