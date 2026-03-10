package com.info.hesapmakinesi;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class MainActivity extends AppCompatActivity {

    private TextView txtDisplay;
    private TextView txtHistory;
    private String historyText = "";

    private BigDecimal left = null;
    private String pendingOp = null;
    private boolean newInput = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtDisplay = findViewById(R.id.txtDisplay);
        txtHistory = findViewById(R.id.txtHistory);

        int[] digitIds = {
                R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4,
                R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9
        };

        View.OnClickListener digitListener = v -> {
            if (newInput && left == null && pendingOp == null) {
                txtHistory.setText("");
                historyText = "";
            }

            String d = ((Button) v).getText().toString();
            if (newInput || txtDisplay.getText().toString().equals("0")) {
                txtDisplay.setText(d);
                newInput = false;
            } else {
                txtDisplay.setText(txtDisplay.getText().toString() + d);
            }
        };

        for (int id : digitIds)
            findViewById(id).setOnClickListener(digitListener);

        findViewById(R.id.btnPlus).setOnClickListener(v -> onBinaryOp("+"));
        findViewById(R.id.btnMinus).setOnClickListener(v -> onBinaryOp("-"));
        findViewById(R.id.btnMul).setOnClickListener(v -> onBinaryOp("*"));
        findViewById(R.id.btnDiv).setOnClickListener(v -> onBinaryOp("/"));

        findViewById(R.id.btnPow).setOnClickListener(v -> onBinaryOp("pow"));

        findViewById(R.id.btnEq).setOnClickListener(v -> onEquals());

        findViewById(R.id.btnC).setOnClickListener(v -> clearAll());

        findViewById(R.id.btnSqrt).setOnClickListener(v -> onUnaryOp("sqrt"));
        findViewById(R.id.btnInv).setOnClickListener(v -> onUnaryOp("inv"));
        findViewById(R.id.btnPercent).setOnClickListener(v -> onPercent());
    }

    private BigDecimal current() {
        try {
            return new BigDecimal(txtDisplay.getText().toString());
        } catch (Exception e) {
            return BigDecimal.ZERO;
        }
    }

    private void setDisplay(BigDecimal value) {
        value = value.stripTrailingZeros();
        String s = value.toPlainString();
        txtDisplay.setText(s.equals("-0") ? "0" : s);
    }

    private void clearAll() {
        left = null;
        pendingOp = null;
        newInput = true;
        txtDisplay.setText("0");

        historyText = "";
        txtHistory.setText("");
    }

    private void onBinaryOp(String op) {
        BigDecimal cur = current();

        String opSymbol = op.equals("pow") ? "^" : op;

        if (left == null) {
            left = cur;
        } else if (!newInput && pendingOp != null) {
            left = apply(left, cur, pendingOp);
            setDisplay(left);
        }

        pendingOp = op;
        newInput = true;

        historyText = left.stripTrailingZeros().toPlainString() + " " + opSymbol + " ";
        txtHistory.setText(historyText);
    }

    private void onEquals() {
        if (pendingOp == null || left == null) return;

        BigDecimal cur = current();
        String curStr = cur.stripTrailingZeros().toPlainString();
        BigDecimal res = apply(left, cur, pendingOp);

        txtHistory.setText(historyText + curStr + " =");
        setDisplay(res);

        left = null;
        pendingOp = null;
        newInput = true;
        historyText = "";
    }

    private void onUnaryOp(String type) {
        BigDecimal cur = current();

        switch (type) {
            case "sqrt":
                if (cur.compareTo(BigDecimal.ZERO) < 0) {
                    txtDisplay.setText("Hata");
                    newInput = true;
                    return;
                }
                double d = Math.sqrt(cur.doubleValue());
                setDisplay(BigDecimal.valueOf(d));
                break;

            case "inv":
                if (cur.compareTo(BigDecimal.ZERO) == 0) {
                    txtDisplay.setText("Hata");
                    newInput = true;
                    return;
                }
                setDisplay(BigDecimal.ONE.divide(cur, 12, RoundingMode.HALF_UP));
                break;
        }

        newInput = true;
    }

    private void onPercent() {
        BigDecimal cur = current();

        if (left != null && pendingOp != null) {
            BigDecimal p = left.multiply(cur)
                    .divide(new BigDecimal("100"), 12, RoundingMode.HALF_UP);
            setDisplay(p);
        } else {
            BigDecimal p = cur
                    .divide(new BigDecimal("100"), 12, RoundingMode.HALF_UP);
            setDisplay(p);
        }
        newInput = true;
    }

    private BigDecimal apply(BigDecimal a, BigDecimal b, String op) {
        switch (op) {
            case "+": return a.add(b);
            case "-": return a.subtract(b);
            case "*": return a.multiply(b);
            case "/":
                if (b.compareTo(BigDecimal.ZERO) == 0) {
                    txtDisplay.setText("Hata");
                    newInput = true;
                    return BigDecimal.ZERO;
                }
                return a.divide(b, 12, RoundingMode.HALF_UP);

            case "pow":
                double res = Math.pow(a.doubleValue(), b.doubleValue());
                return BigDecimal.valueOf(res);

            default:
                return b;
        }
    }
}