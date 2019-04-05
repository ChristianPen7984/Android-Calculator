package com.example.lab6;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class Calculator extends Fragment {

    ResultCommunicator resultCommunicator;
    EquationParser equationParser;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View calculator = inflater.inflate(R.layout.calculator, null);
        equationParser = new EquationParser();
        computeCalcInput(calculator);
        return calculator;
    }

    private void computeCalcInput(View view) {

        final int[] buttonsIds = {
                R.id.one, R.id.two, R.id.three, R.id.four, R.id.five, R.id.six,
                R.id.seven, R.id.eight, R.id.nine, R.id.zero, R.id.decimal, R.id.clear,
                R.id.modulus, R.id.divide, R.id.multiply, R.id.subtract, R.id.add, R.id.equals,
        };
        final Button[] button = new Button[buttonsIds.length];
        final StringBuilder infixEquation = new StringBuilder();
        int index;
        for (index = 0; index < buttonsIds.length; index++) {
            button[index] = (Button)view.findViewById(buttonsIds[index]);
            final int currentIndex = index;
            button[index].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    infixEquation.append(button[currentIndex].getText().toString());
                    resultCommunicator.setResult(infixEquation.toString());
                    String result = infixEquation.toString();
                        switch (button[currentIndex].getId()) {
                            case R.id.equals:
                                resultCommunicator.setResult(String.valueOf(equationParser.convert(result)));
                                infixEquation.setLength(0);
                                infixEquation.append(String.valueOf(equationParser.convert(result)));
                                break;
                            case R.id.clear:
                                infixEquation.setLength(0);
                                resultCommunicator.setResult("");
                                break;
                        }
                }
            });
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        resultCommunicator = (ResultCommunicator) context;

    }

}
