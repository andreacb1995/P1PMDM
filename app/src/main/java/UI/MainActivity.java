package UI;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.andreacarballidop1pmdm.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

import core.Entrenamiento;

public class MainActivity extends AppCompatActivity {

    EditText fechaentrenamiento;
    ArrayList listaentrenamientos = new ArrayList();
    ArrayList lista = new ArrayList();
    Calendar calendar= Calendar.getInstance();
    int year = calendar.get(Calendar.YEAR);
    int month = calendar.get(Calendar.MONTH);
    int day = calendar.get(Calendar.DAY_OF_MONTH);
//    EditText fechaentrenamiento = new EditText(this );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton btAdd = findViewById(R.id.btAdd);
        ListView lvItems = (ListView) this.findViewById( R.id.lvItems );

        this.registerForContextMenu( lvItems );


        lvItems.setLongClickable( true );
        this.itemsAdapter = new ArrayAdapter<String>(
                this.getApplicationContext(),
                android.R.layout.simple_selectable_list_item,
                lista

        );

        lvItems.setAdapter( this.itemsAdapter );

        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int pos, long l){
                final TextView textview = new TextView(MainActivity.this);
                textview.setText(MainActivity.this.listaentrenamientos.get(pos).toString());

                if (pos>=0){
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("Entrenamiento: ");
                    builder.setView(textview);
                    builder.setNegativeButton("Volver",null);
                    builder.create().show();
                }
            }});


        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.this.onAdd();
            }
        });

    }


    private void onAdd() {

        final AlertDialog.Builder builder = new AlertDialog.Builder( this );


        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);

        fechaentrenamiento= new EditText(this);
        layout.addView(fechaentrenamiento);
        fechaentrenamiento.setHint("Fecha");

        fechaentrenamiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(0);

            }
        });

        final EditText tiempoh = new EditText( this );
//        tiempoh.requestFocus();
        layout.addView(tiempoh);
        tiempoh.setHint("Horas");

        final EditText tiempom = new EditText( this );
//        tiempom.requestFocus();
        layout.addView(tiempom);
        tiempom.setHint("Minutos");

        final EditText tiempos = new EditText( this );
//        tiempos.requestFocus();
        layout.addView(tiempos);
        tiempos.setHint("Segundos");

        final EditText distancia = new EditText( this );
//        tiempos.requestFocus();

        layout.addView(distancia);
        distancia.setHint("Metros");
        distancia.setImeActionLabel("Custom text", KeyEvent.KEYCODE_ENTER);

        builder.setTitle("Nuevo entrenamiento");
        builder.setMessage( "Introduce el tiempo y la distancia" );
        builder.setView(layout );

//        builder.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
//        AlertDialog dialog = (AlertDialog) getDialog(); dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);

        builder.setPositiveButton("+", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
//                builder.dismiss();

                String h = tiempoh.getText().toString();
                String m = tiempom.getText().toString();
                String s = tiempos.getText().toString();
                String d = distancia.getText().toString();
                String f= fechaentrenamiento.getText().toString();

                /*AlertDialog dialog= builder.show();
                if (h.equals("") && m.equals("") && s.equals("") && d.equals("")) {
                    Button button = dialog.getButton(AlertDialog.BUTTON_NEUTRAL);
                    button.setEnabled(false);

                }*/


                if (h.equals("")) {
                    h = "0";
                }
                if (m.equals("")) {
                    m = "0";
                }
                if (s.equals("")) {
                    s = "0";
                }
                if (d.equals("")) {
                    d = "0";
                }

                //Pasamos las horas y los segundos a minutos y calculamos los minutos y segundos por km
                double horas = Double.parseDouble(h);
                double minutos = Double.parseDouble(m);
                double segundos = Double.parseDouble(s);
                double metros = Double.parseDouble(d);
                double km = metros / 1000;
                double horasenminutos = horas * 60;
                double segundosenminutos = minutos / 60;
                double tiempo = horasenminutos + minutos + segundosenminutos;
                double resultadominutosporkm = km / tiempo;

                resultadominutosporkm = Math.round(resultadominutosporkm * 100) / 100d;
                //Pasamos los minutos y los segundos a horas y calculamos la velocidad media

                double minutoahora = minutos / 60;
                double segundoahora = segundos / 3600;
                double tiempoenhoras = horas + minutoahora + segundoahora;
                double velocidad =km / tiempoenhoras;

                velocidad = Math.round(velocidad * 100) / 100d;

                Entrenamiento e = new Entrenamiento(f,horas, minutos, segundos, metros, resultadominutosporkm, velocidad);
                MainActivity.this.itemsAdapter.add("Fecha: " +f + "\n" +   h + " horas, " + m + " minutos, " + s + " segundos," + d + " metros");
                MainActivity.this.listaentrenamientos.add(e);

            }
        });

        builder.setNegativeButton("Cancel", null);
        builder.create().show();
    }

    private ArrayAdapter<String> itemsAdapter;

    @Override
    public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo cmi)
    {
      if ( view.getId() == R.id.lvItems)
        {
            this.getMenuInflater().inflate( R.menu.context_menu, contextMenu );
            contextMenu.setHeaderTitle( "Menú contextual" );
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem menuItem)
    {
        final AdapterView.AdapterContextMenuInfo info =
                (AdapterView.AdapterContextMenuInfo) menuItem.getMenuInfo();

        boolean toret = false;

        switch( menuItem.getItemId() ) {
            case R.id.mostrare:
                final TextView textview = new TextView(MainActivity.this);
                textview.setText(MainActivity.this.listaentrenamientos.get(info.position).toString());

                if (info.position>=0){
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("Entrenamiento: ");
                    builder.setView(textview);
                    builder.setNegativeButton("Volver",null);
                    builder.create().show();
                }

                toret = true;
                break;
            case R.id.modificare:

                final EditText editText = new EditText(MainActivity.this);
                editText.setText(MainActivity.this.lista.get(info.position).toString());

                if (info.position>=0){
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("Modificar");
                    builder.setView(editText);
                    builder.setNegativeButton("Cancelar",null);
                    builder.setPositiveButton("Modificar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            //cambiar los datos en el arraylist,al cambiar los datos hacer de nuevo los calculos de las estadisticas
//
                            MainActivity.this.listaentrenamientos.set(info.position,editText.getText().toString());

                            MainActivity.this.lista.set(info.position,editText.getText().toString());
                            MainActivity.this.itemsAdapter.notifyDataSetChanged();
                        }

                    });
                    builder.create().show();
                }
                toret = true;
                break;
            case R.id.eliminare:
                if (info.position >= 0 ) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("Borrar Elemento");
                    builder.setMessage("Seguro que quieres borrar el elemento: '" + MainActivity.this.lista.get(info.position).toString() +"'?");
                    builder.setPositiveButton("Borrar",new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            MainActivity.this.listaentrenamientos.remove( info.position );
                            MainActivity.this.itemsAdapter.remove(itemsAdapter.getItem(info.position));
                            MainActivity.this.itemsAdapter.notifyDataSetChanged();

                        }
                    });
                    builder.setNegativeButton("Cancelar", null);
                    builder.create().show();

                }
                toret = true;
                break;
        }

        return toret;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        super.onCreateOptionsMenu( menu );

        this.getMenuInflater().inflate( R.menu.actions_menu, menu );
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem)
    {
        boolean toret = false;

        switch( menuItem.getItemId() ) {
            case R.id.añadire:
                onAdd();
                toret = true;
                break;
            case R.id.estadisticase:
                final TextView textview = new TextView(MainActivity.this);

                textview.setText(estadisticas);

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Estadísticas generales: ");
                builder.setView(textview);
                builder.setNegativeButton("Volver",null);
                builder.create().show();
                toret = true;
                break;
        }


        return toret;
    }

    @Override
    @Deprecated
    protected Dialog onCreateDialog(int id) {
        return new DatePickerDialog(this, datePickerListener, year, month, day);

    }

    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
            day = selectedDay;
            month = selectedMonth;
            year = selectedYear;
          fechaentrenamiento.setText(day+"/"+(month+1)+"/"+year);

        }

    };

    double kmtotales;
    double mediaminutosporkm;
    String estadisticas= "Kilómetros totales: " + kmtotales + "\n" +  "Media de minutos por kilómetro: " + mediaminutosporkm;

    public void Estadisticasgenerales(){
         Iterator iterador = listaentrenamientos.listIterator();

                while( iterador.hasNext() ) {
                    Entrenamiento b = (Entrenamiento) iterador;
                    double conversion= b.getMetros()/1000;
                    kmtotales= kmtotales + conversion;
                    double media=b.getVelocidadmediakmporhora();
                    mediaminutosporkm= (mediaminutosporkm + media);

                }

    }

}

