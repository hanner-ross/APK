package com.example.useit;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.useit.response.IdeasResponse;
import com.example.useit.response.TablerosResponse;
import com.example.useit.response.UsuariosResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TableActivity extends AppCompatActivity {
    public int user_id=0;
    public RecyclerView rv;

      class thinks {
        String table_name;
        String think;
        String person_name;

        thinks(String table_name, String think, String person_name) {
            this.table_name = table_name;
            this.think = think;
            this.person_name = person_name;
        }
    }

    private List<thinks> pensamientos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Bundle extras = getIntent().getExtras();
        user_id= extras.getInt("user_id");
        Call<List<UsuariosResponse>> call = ApiAdapter.getApiService().getUsuarios();
        call.enqueue(new TableActivity.UsuariosCalback());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recyclerview);
        rv = (RecyclerView)findViewById(R.id.rv);
        rv.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getBaseContext());
        rv.setLayoutManager(llm);


    }

    class UsuariosCalback implements Callback<List<UsuariosResponse>> {

        @Override
        public void onResponse(Call<List<UsuariosResponse>> call, Response<List<UsuariosResponse>> response) {
            if (response.isSuccessful()){
                List<UsuariosResponse> usuarios = response.body();
                Call<List<TablerosResponse>> call1 = ApiAdapter.getApiService().getTableros();
                call1.enqueue(new Callback<List<TablerosResponse>>() {
                    @Override
                    public void onResponse(Call<List<TablerosResponse>> call1, Response<List<TablerosResponse>> response) {
                        if (response.isSuccessful()){
                            List<TablerosResponse> tableros = response.body();
                            Call<List<IdeasResponse>> call2 = ApiAdapter.getApiService().getIdeas();
                            call2.enqueue(new Callback<List<IdeasResponse>>() {
                                @Override
                                public void onResponse(Call<List<IdeasResponse>> call2, Response<List<IdeasResponse>> response) {
                                    if (response.isSuccessful()){
                                        loadDataList(response.body(),tableros,usuarios);
                                    }
                                    else{
                                        Toast.makeText(getBaseContext(), "Error en el formato de respuesta", Toast.LENGTH_SHORT).show();
                                    }
                                }
                                @Override
                                public void onFailure(Call<List<IdeasResponse>> call2, Throwable t2) {
                                    Toast.makeText(getBaseContext(), t2.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                        else{
                            Toast.makeText(getBaseContext(), "Error en el formato de respuesta", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<List<TablerosResponse>> call1, Throwable throwable) {
                        Toast.makeText(getBaseContext(), throwable.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
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

    private void loadDataList(List<IdeasResponse> ideas, List<TablerosResponse> tablerosList, List<UsuariosResponse> usuariosList){
        int pass=0;
        pensamientos = new ArrayList<>();
        for (TablerosResponse t : tablerosList){
            if(t.getTable_privacy()==0 || t.getTable_owner()==user_id){
                for (IdeasResponse it : ideas){
                    if(t.getId()==it.getTable_name()){
                        for(UsuariosResponse u: usuariosList){
                            if(it.getUser_name()==u.getId()){
                                String user_name=u.getName()+" "+u.getLast_name();
                                pensamientos.add(new thinks(t.getTable_name(), it.getThing(), user_name));
                                //String mostrar= t.getTable_name()+" "+it.getThing()+" "+user_name;
                                //Toast.makeText(getBaseContext(), mostrar, Toast.LENGTH_SHORT).show();
                                pass=1;
                            }
                        }
                    }
                }
            }
        }
        if(pass==0){
            Toast.makeText(getBaseContext(), "El usuario no tiene tableros", Toast.LENGTH_SHORT).show();
        }
        if(pass==1){
            RVAdapter adapter = new RVAdapter(pensamientos);
            rv.setAdapter(adapter);
            }

        }

    }


