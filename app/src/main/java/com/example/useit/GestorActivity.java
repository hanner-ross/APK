package com.example.useit;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.useit.R;
import com.example.useit.response.IdeasResponse;
import com.example.useit.response.PostIdeasResponse;
import com.example.useit.response.TablerosResponse;
import com.example.useit.response.UsuariosResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GestorActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, AdapterView.OnItemSelectedListener {

    public int user_id=0;
    Spinner Tableros;
    int spipos=0;
    List<String> listableros;
    ArrayAdapter<String> comboAdapter;
    String nombretable;
    List<TablerosResponse> tables;
    public Button add;
    public EditText newidea;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Bundle extras = getIntent().getExtras();
        user_id= extras.getInt("user_id");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gestor);
        Call<List<TablerosResponse>> call = ApiAdapter.getApiService().getTableros();
        call.enqueue(new TablerosCallback());
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        add = (Button) findViewById(R.id.badd);
        newidea = (EditText) findViewById(R.id.newidea);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
           public void onClick(View v) {
               v.clearFocus();
               if (v != null) {
                   InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                   imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
               }
               nombretable = listableros.get(spipos);
               for (TablerosResponse t : tables) {
                   if (t.getTable_name().equals(nombretable)) {
                       Call<PostIdeasResponse> call = ApiAdapter.getApiService().postIdea(user_id, t.getId(), newidea.getText().toString());
                       call.enqueue(new Callback<PostIdeasResponse>() {
                           @Override
                           public void onResponse(Call<PostIdeasResponse> call, Response<PostIdeasResponse> response) {

                               if (response.isSuccessful()) {
                                   newidea.setText("");
                                   Toast.makeText(getBaseContext(), "Idea Añadida", Toast.LENGTH_SHORT).show();
                               }
                           }

                           @Override
                           public void onFailure(Call<PostIdeasResponse> call, Throwable t) {
                               Toast.makeText(getBaseContext(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                           }
                       });
                   }
               }
           }
        });
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    class TablerosCallback implements Callback<List<TablerosResponse>> {

        @Override
        public void onResponse(Call<List<TablerosResponse>> call, Response<List<TablerosResponse>> response) {
            if (response.isSuccessful()){
                loadDataList(response.body());
            }
            else{
                Toast.makeText(getBaseContext(), "Error en el formato de respuesta", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onFailure(Call<List<TablerosResponse>> call, Throwable t) {
            Toast.makeText(getBaseContext(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void loadDataList(List<TablerosResponse> tableros) {
        int pass=0;
        tables=tableros;
        listableros = new ArrayList<>();
        for (TablerosResponse t : tableros){
            if(t.getTable_privacy()==0 || t.getTable_owner()==user_id){
                pass=1;
                listableros.add(t.getTable_name());
            }
        }
        if(pass==1){
            Tableros = (Spinner) findViewById(R.id.spitable);
            Tableros.setOnItemSelectedListener(this);
            comboAdapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item, listableros);
            Tableros.setAdapter(comboAdapter);

        }

        }

    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()){
            case R.id.spitable:
                spipos=position;
                nombretable = listableros.get(position);

                //Toast.makeText(this, "Nombre tablero: " + nombretable, Toast.LENGTH_SHORT).show();

                break;
        }
    }

    public void onNothingSelected(AdapterView<?> parent) {}


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        Intent i = new Intent(GestorActivity.this, MainActivity.class);
        startActivity(i);

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_table) {
            Bundle bundle = new Bundle();
            Intent intent = new Intent(GestorActivity.this, TableActivity.class);
            //Intent intent = new Intent(MainActivity.this, GestorActivity.class);
            bundle.putInt("user_id",user_id);
            intent.putExtras(bundle);
            startActivity(intent);
            // Handle the camera action
        } /*else if (id == R.id.nav_idea) {

        }*/
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}