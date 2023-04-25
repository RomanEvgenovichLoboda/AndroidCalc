package com.example.androidcalc;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText editText;
    String oper="";
    String oldNumb="0";
    Boolean isNull=true;
    Boolean isMinus=false;
    Boolean isDot=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText=findViewById(R.id.editText);
    }

    public void clickNumb(View view) {
        if(isNull&&view.getId()!=R.id.bDot&&view.getId()!=R.id.bPerc){
            editText.setText("");
            isNull=false;
            isMinus=false;
        }
        String numb = editText.getText().toString();
        switch (view.getId()){
            case R.id.b0:numb=numb+"0";break;
            case R.id.b1:numb=numb+"1";break;
            case R.id.b2:numb=numb+"2";break;
            case R.id.b3:numb=numb+"3";break;
            case R.id.b4:numb=numb+"4";break;
            case R.id.b5:numb=numb+"5";break;
            case R.id.b6:numb=numb+"6";break;
            case R.id.b7:numb=numb+"7";break;
            case R.id.b8:numb=numb+"8";break;
            case R.id.b9:numb=numb+"9";break;
            case R.id.bAC:{
                oldNumb="0";
                numb="0";
                oper="";
                isNull=true;
                isMinus=false;
                isDot=false;
                break;}
            case R.id.bPlMn:{
                if(!isMinus){
                numb="-"+numb;
                isMinus=true;}
                else{

                }
                break;
            }
            case R.id.bDot:{
                if(!isDot){
                    numb=numb+".";
                    isNull=false;
                    isDot=true;
                    break;
                }
            }
            case R.id.bPerc:{
                double perc = Double.parseDouble(numb)/100;
                numb=String.valueOf(perc);
                break;
            }
        }
        editText.setText(numb);
    }

    public void clickOperation(View view) {
        isNull=true;
        if(!oper.equals("")) getResult();
        else oldNumb=editText.getText().toString();
        switch (view.getId()){
            case R.id.bMin:oper="-";break;
            case R.id.bPl:oper="+";break;
            case R.id.bDiv:oper="/";break;
            case R.id.bMult:oper="*";break;
            case R.id.bRes:{
                editText.setText(oldNumb.toString());
                oper="";
                break;
            }
        }
    }

    public String getResult(){
        String newNumb=editText.getText().toString();
        Double res=0.0;
        switch (oper){
            case "-":res=Double.parseDouble(oldNumb)-Double.parseDouble(newNumb);break;
            case "+":res=Double.parseDouble(oldNumb)+Double.parseDouble(newNumb);break;
            case "/":res=Double.parseDouble(oldNumb)/Double.parseDouble(newNumb);break;
            case "*":res=Double.parseDouble(oldNumb)*Double.parseDouble(newNumb);break;
        }
        oldNumb=res.toString();
        return res.toString();
    }
}
