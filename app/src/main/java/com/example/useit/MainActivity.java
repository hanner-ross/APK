package com.example.useit;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import com.example.useit.response.UsuariosResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity{

    public Button blogin;
    public EditText tuser;
    public EditText tpass;

    class UsuariosCallback implements Callback<List<UsuariosResponse>> {

        @Override
        public void onResponse(Call<List<UsuariosResponse>> call, Response<List<UsuariosResponse>> response) {
            if (response.isSuccessful()){
                loadDataList(response.body());
            }
            else{
                Toast.makeText(getBaseContext(), "Error en el formato de respuesta", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onFailure(Call<List<UsuariosResponse>> call, Throwable t) {
            Toast.makeText(getBaseContext(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void loadDataList(List<UsuariosResponse> usersList) {
    String uservalue = tuser.getText().toString();
    String passvalue = tpass.getText().toString();
    int pass=0;
    int user_id=0;
    for (UsuariosResponse u : usersList){
        if(u.getUser().equals(uservalue) && u.getPassword().equals(passvalue)){
            Toast.makeText(getBaseContext(), "Bienvenido", Toast.LENGTH_SHORT).show();
            user_id =u.getId();
            pass=1;
        }
    }
    if(pass==0){
            Toast.makeText(getBaseContext(), "Usuario no encontrado", Toast.LENGTH_SHORT).show();
        }
    else{
        Bundle bundle = new Bundle();
        //Intent intent = new Intent(MainActivity.this, TableActivity.class);
        Intent intent = new Intent(MainActivity.this, GestorActivity.class);
        bundle.putInt("user_id",user_id);
        intent.putExtras(bundle);
        startActivity(intent);
    }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        blogin = (Button) findViewById(R.id.blogin);
        tuser = (EditText) findViewById(R.id.tuser);
        tpass = (EditText) findViewById(R.id.tpass);
        blogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.clearFocus();
                if (v != null) {
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
                Call<List<UsuariosResponse>> call = ApiAdapter.getApiService().getUsuarios();
                call.enqueue(new UsuariosCallback());
            }
        });
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

/*        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();*/
    }

}
