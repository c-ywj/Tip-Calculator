package com.ssd.a01084638.android_tipcalc;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.view.KeyEvent;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.text.DecimalFormat;
public class MainActivity extends AppCompatActivity {

    TextView percentResult;
    TextView tipResult;
    TextView totalResult;
    TextView perPersonResult;

    EditText billAmtTextBox;

    Button plusBtn;
    Button minusBtn;
    Button applyBtn;

    RadioButton roundingNoneBtn;
    RadioButton roundingTipBtn;
    RadioButton roundingTotalBtn;

    Spinner splitBillSpinner;

    Double inputAmt;
    Double tipAmt;
    Double totalAmt;
    Double percentAmt;
    Double splitAmt;
    Integer splitWay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        percentAmt = 10d;

        splitBillSpinner = (Spinner) findViewById(R.id.splitBillSpinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.split_array,
                android.R.layout.simple_spinner_item
        );

        adapter.setDropDownViewResource (
                android.R.layout.simple_spinner_dropdown_item
        );

        splitBillSpinner.setAdapter(adapter);


        percentResult       = (TextView) findViewById(R.id.percentResult);
        tipResult           = (TextView) findViewById(R.id.tipResult);
        totalResult         = (TextView) findViewById(R.id.totalResult);
        perPersonResult     = (TextView) findViewById(R.id.perPersonResult);

        billAmtTextBox      = (EditText) findViewById(R.id.billAmtTextBox);

        plusBtn             = (Button) findViewById(R.id.plusBtn);
        minusBtn            = (Button) findViewById(R.id.minusBtn);
        applyBtn            = (Button) findViewById(R.id.applyBtn);

        roundingNoneBtn     = (RadioButton) findViewById(R.id.roundingNoneRadio);
        roundingTipBtn      = (RadioButton) findViewById(R.id.roundingTipRadio);
        roundingTotalBtn    = (RadioButton) findViewById(R.id.roundingTotalRadio);


        billAmtTextBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String temp = String.valueOf(billAmtTextBox.getText());
                inputAmt = Double.valueOf(temp);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        percentResult.setText(percentAmt + "%");

        plusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                addPercent();
            }
        });

        minusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                minusPercent();
            }
        });

        applyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                calcPerPerson();
            }
        });


    }

    private void addPercent() {
        percentAmt++;
        percentResult.setText(percentAmt + "%");
    }

    private void minusPercent() {
        if(percentAmt <= 0d) {
            percentAmt = 0d;
            percentResult.setText(percentAmt + "%");
        } else {
            percentAmt--;
            percentResult.setText(percentAmt + "%");
        }
    }

    private void calcTip() {
        DecimalFormat df = new DecimalFormat("###,###.00");
        tipAmt = (percentAmt / 100) * inputAmt;
        tipAmt = Double.valueOf(df.format(tipAmt));
        roundOrNot();
        tipResult.setText("$" + tipAmt);
    }

    private void calcTotal() {
        totalAmt = tipAmt + inputAmt;
        roundOrNot();
        totalResult.setText("$" + totalAmt);
    }

    private void calcSplit() {
        splitWay = splitBillSpinner.getSelectedItemPosition();
        DecimalFormat df = new DecimalFormat("###,###.00");
        if(splitWay == 0) {
            splitAmt = totalAmt;
            splitAmt = Double.valueOf(df.format(splitAmt));
        } else if(splitWay == 1) {
            splitAmt = totalAmt / 2;
            splitAmt = Double.valueOf(df.format(splitAmt));
        } else if(splitWay == 2) {
            splitAmt = totalAmt / 3;
            splitAmt = Double.valueOf(df.format(splitAmt));
        } else if(splitWay == 3) {
            splitAmt = totalAmt / 4;
            splitAmt = Double.valueOf(df.format(splitAmt));
        }
    }

    private void calcPerPerson() {
        calcTip();
        calcTotal();
        calcSplit();
        perPersonResult.setText("$" + splitAmt);
    }

    private void roundOrNot() {
        DecimalFormat df = new DecimalFormat("###,###.00");
        if(roundingTipBtn.isChecked()) {
            tipAmt = Double.valueOf(Math.round(tipAmt));
            tipAmt = Double.valueOf(df.format(tipAmt));
        } else if(roundingTotalBtn.isChecked()) {
            totalAmt = Double.valueOf(Math.round(totalAmt));
            totalAmt = Double.valueOf(df.format(totalAmt));
        }
    }



}
