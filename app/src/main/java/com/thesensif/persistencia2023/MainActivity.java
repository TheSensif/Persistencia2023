package com.thesensif.persistencia2023;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.nio.CharBuffer;
import java.nio.charset.StandardCharsets;

public class MainActivity extends AppCompatActivity {
    ObjectOutputStream oos;
    EditText mEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mEditText = findViewById(R.id.edit_text);
        FileInputStream fis = null;
        try {
            fis = openFileInput("dades.txt");
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String text;

            while ((text = br.readLine()) != null) {
                sb.append(text).append("\n");
            }

            mEditText.setText(sb.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = mEditText.getText().toString();

                if (!text.isEmpty()) {
                    text += "\n"; // Agregar un salto de línea solo si hay texto

                    FileOutputStream fos = null;
                    String message = "Saved to " + getFilesDir() + "/dades.txt";

                    try {
                        /// Ruta de guardado: data/data/com.thesensif.persistencia2023/files/dades.txt
                        fos = openFileOutput("dades.txt", MODE_APPEND);
                        fos.write(text.getBytes(StandardCharsets.UTF_8));
                        mEditText.getText().clear();
                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                    } catch (FileNotFoundException e) {
                        System.out.println("Error");
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        if (fos != null) {
                            try {
                                fos.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        });
    }
}