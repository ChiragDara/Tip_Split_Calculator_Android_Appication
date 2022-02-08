package com.example.tipsplit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    TextView tipAmountTextView, totalWithTip, overageTxt, totAmtPerPersonTxt;
    EditText totalBill, numOfPplTxt;
    RadioGroup rgTipPercent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        totalBill = findViewById(R.id.totalBill);
        rgTipPercent = findViewById(R.id.rgTipPercent);
        tipAmountTextView = findViewById(R.id.tipAmountTextView);
        totalWithTip = findViewById(R.id.totalWithTip);
        totAmtPerPersonTxt = findViewById(R.id.totAmtPerPersonTxt);
        numOfPplTxt = findViewById(R.id.numOfPplTxt);
        overageTxt = findViewById(R.id.overageT);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putString("tipAmountTextView",tipAmountTextView.getText().toString() );
        outState.putString("totalWithTip", totalWithTip.getText().toString());
        outState.putString("totAmtPerPersonTxt", totAmtPerPersonTxt.getText().toString());
        outState.putString("overageTxt", overageTxt.getText().toString());
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        //Retrieving Values from saved Instance
        tipAmountTextView.setText(savedInstanceState.getString("tipAmountTextView"));
        totalWithTip.setText(savedInstanceState.getString("totalWithTip"));
        totAmtPerPersonTxt.setText(savedInstanceState.getString("totAmtPerPersonTxt"));
        overageTxt.setText(savedInstanceState.getString("overageTxt"));
    }

    // Function for calculating Tip Amount
    public void calculateTipAmount(View v) {
        // Getting text from edit text with id - totalBill
        String billTotal = totalBill.getText().toString();

        //  Checking null values
        if (billTotal.isEmpty() || billTotal == "") {
            rgTipPercent.clearCheck();
            return;
        }

        Double calculatedTip = 0.0;

        //Make a new instance df that has 2 decimal places pattern.
        DecimalFormat formattingDecimal = new DecimalFormat("#.##");

        // Need to Calculate tip on the basis of Radio Button
        if (v.getId() == R.id.rb_12) {
            // converting billTotal form double to string.
            String formatedTip = formattingDecimal.format((Double.parseDouble(billTotal)) * 0.12);
            calculatedTip = Double.parseDouble(formatedTip);
        } else if (v.getId() == R.id.rb_15) {
            String formatedTip = formattingDecimal.format((Double.parseDouble(billTotal)) * 0.15);
            calculatedTip = Double.parseDouble(formatedTip);
        } else if (v.getId() == R.id.rb_18) {
            String formatedTip = formattingDecimal.format((Double.parseDouble(billTotal)) * 0.18);
            calculatedTip = Double.parseDouble(formatedTip);
        } else if (v.getId() == R.id.rb_20) {
            String formatedTip = formattingDecimal.format((Double.parseDouble(billTotal)) * 0.20);
            calculatedTip = Double.parseDouble(formatedTip);
        }

        tipAmountTextView.setText(NumberFormat.getCurrencyInstance(Locale.US).format(calculatedTip));
        calculatedTip = Double.parseDouble(billTotal) + calculatedTip;
        totalWithTip.setText(NumberFormat.getCurrencyInstance(Locale.US).format(calculatedTip));
    }

    public void btnGO(View v) {
        System.out.print("In Go Button Click ");
        String billTotal = totalBill.getText().toString();
        // Checking null values
        if (billTotal.isEmpty() || billTotal.equals("0"))  {
            return;
        }
        String no = numOfPplTxt.getText().toString();
        if(no.isEmpty() || no.equals("0")) return;
        int numOfPeople = Integer.parseInt(no);

        // getting total amount without first character - $
        double totalAmountWithTip = Double.parseDouble(totalWithTip.getText().toString().substring(1)) * 100;
        double upperValue = Math.ceil(totalAmountWithTip / numOfPeople);
        double totalAmount = totalAmountWithTip / numOfPeople;
        double overageValue = Math.abs(totalAmount - upperValue) * numOfPeople;

        totAmtPerPersonTxt.setText(NumberFormat.getCurrencyInstance().format(upperValue/100));
        overageTxt.setText(NumberFormat.getCurrencyInstance().format(overageValue/100));
    }

    public void clearAll(View v) {
        tipAmountTextView.setText("");
        totalWithTip.setText("");
        overageTxt.setText("");
        totAmtPerPersonTxt.setText("");
        totalBill.setText("");
        numOfPplTxt.setText("");
        rgTipPercent.clearCheck();

    }
}