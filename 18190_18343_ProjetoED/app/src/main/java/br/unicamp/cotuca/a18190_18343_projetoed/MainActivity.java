package br.unicamp.cotuca.a18190_18343_projetoed;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;

public class MainActivity extends AppCompatActivity {
    Button btnBuscar, btnNovaCidade, btnNovoCaminho;
    final String arq = "Cidades.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnBuscar = (Button)findViewById(R.id.btnBuscar);
        btnNovaCidade = (Button)findViewById(R.id.btnNovaCidade);
        btnNovoCaminho = (Button)findViewById(R.id.btnNovoCaminho);
    }

    //errado
    public void leArq(){
        try {
            File f = getFileStreamPath(arq);
            Log.i("MSG", "Lendo arquivo " + f.getAbsolutePath());
            if(f.exists()){
                FileInputStream in = openFileInput(arq);
                int tamanho = in.available();
                byte bytes[] = new byte[tamanho];
                in.read(bytes);
                String s = new String(bytes);
                Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
                in.close();
            }
        }
        catch (Exception e){
            Log.e("ERRO", e.getMessage());
        }
    }
}
