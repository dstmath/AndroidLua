package cm.android.luademo;

import org.keplerproject.luajava.LuaState;
import org.keplerproject.luajava.LuaStateFactory;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.io.IOException;
import java.io.InputStream;

import cm.lua.moon.LuaResult;
import cm.lua.moon.LuaRuntime;

public class MainActivity extends AppCompatActivity {

    private LuaState state;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(cm.android.luademo.R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(cm.android.luademo.R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(cm.android.luademo.R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                LuaRuntime.exec("log(\"fuck...\")");

//                String showToast = "local toast = luajava.bindClass(\"android.widget.Toast\"):makeText(context(),\"I'm from lua script!\",1) toast:show()";
//                LuaRuntime.exec(showToast);

                String script = readAssets();
                Log.e("ggg", "ggg script = " + script);
                LuaResult result = LuaRuntime.exec(script);
                Log.e("ggg", "ggg result = " + result.result);
            }
        });

        LuaRuntime.setupContext(this);

        state = LuaStateFactory.newLuaState();
        state.openLibs();

        state.LdoString("ggg = 'from lua.'");
        state.getGlobal("ggg");
        Log.e("ggg", "ggg = " + state.toString(-1));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(cm.android.luademo.R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == cm.android.luademo.R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */

    private String readAssets() {
        InputStream inputStream = null;
        try {
            inputStream = getAssets().open("test.lua");
            byte[] bytes = new byte[inputStream.available()];
            int len = inputStream.read(bytes);
            return new String(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return "";
    }
}
