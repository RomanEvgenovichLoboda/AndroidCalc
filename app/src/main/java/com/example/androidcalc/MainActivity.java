package com.example.androidcalc;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText editText;
    TextView editTextHist;
    String oper="";
    String oldNumb="0";
    Boolean isNull=true;
    Boolean isMinus=false;
    Boolean isDot=false;
    //int Id=1;
    SQLiteDatabase db;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText=findViewById(R.id.editText);
        editTextHist=findViewById(R.id.editTextHist);


        db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS users ( name TEXT)");
        //query.close();
        db.close();
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
        if(!oper.equals("")) oldNumb=getResult();
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
        ContentValues values=new ContentValues();
        String newNumb=editText.getText().toString();
        Double res=0.0;
        switch (oper){
            case "-":{
                res=Double.parseDouble(oldNumb)-Double.parseDouble(newNumb);

                //db.execSQL("INSERT OR IGNORE INTO users VALUES (oldNumb+'-'+newNumb+'='+ resStr);");
                String resStr = res.toString();
                values.put("name",oldNumb+'-'+newNumb+'='+ resStr);
                db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);

                db.insert("users",null,values);
                db.close();
                //Id++;
                break;
            }
            case "+":{
                res=Double.parseDouble(oldNumb)+Double.parseDouble(newNumb);
                //db.execSQL("INSERT OR IGNORE INTO users VALUES (Id, oldNumb+'+'+newNumb+'='+ resStr);");
                String resStr = res.toString();
                values.put("name",oldNumb+'+'+newNumb+'='+ resStr);
                db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
                db.insert("users",null,values);
                db.close();
                //Id++;
                break;
            }
            case "/":{
                res=Double.parseDouble(oldNumb)/Double.parseDouble(newNumb);
                //db.execSQL("INSERT OR IGNORE INTO users VALUES (Id, oldNumb+'/'+newNumb+'='+ resStr);");
                String resStr = res.toString();
                values.put("name",oldNumb+'/'+newNumb+'='+ resStr);
                db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
                db.insert("users",null,values);
                db.close();
                //Id++;
                break;
            }
            case "*":{
                res=Double.parseDouble(oldNumb)*Double.parseDouble(newNumb);
                //db.execSQL("INSERT OR IGNORE INTO users VALUES (Id, oldNumb+'*'+newNumb+'='+ resStr);");
                String resStr = res.toString();
                values.put("name",oldNumb+'-'+newNumb+'='+ resStr);
                db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
                db.insert("users",null,values);
                db.close();
                //Id++;
                break;
            }
        }
        return res.toString();
    }

    public void clickHistory(View view) {
        db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
        Cursor query = db.rawQuery("SELECT * FROM users;", null);
        //TextView textView = findViewById(R.id.textView);
        //textView.setText("");
        String txt="";
        while(query.moveToNext()){
            txt+=query.getString(1)+"\n";
            //String name = query.getString(0);
            //int age = query.getInt(1);
            //textView.append("Name: " + name + " Age: " + age + "\n");
        }
        editTextHist.setText(txt);
        Toast.makeText(getApplicationContext(), txt, Toast.LENGTH_LONG);
        db.close();
    }
}
