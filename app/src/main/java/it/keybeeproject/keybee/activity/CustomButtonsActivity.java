package it.keybeeproject.keybee.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;

import it.keybeeproject.keybee.R;
import it.keybeeproject.keybee.adapter.PopUpAdapter;
import it.keybeeproject.keybee.utility.PrefData;
import it.keybeeproject.keybee.utility.TypefaceSpan;

public class CustomButtonsActivity extends AppCompatActivity {//implements IabBroadcastReceiver.IabBroadcastListener {

    String[] array, array1, a, c, d, e, g, h, i, j, k, l, o, n, r, s, t, u, w, y, z;
    int currentKeyboardLayout;
    Spinner Spin_A0;
    TextView Text_A0, reset, subscribe, main_txt;
    //private IabHelper iabHelper;
    ArrayList<String> list;


    ArrayAdapter A0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        setContentView(R.layout.activity_custom_buttons);
        initGloble();
        Onclick();
        setData();

        subscribe.setText(R.string.use);

    }


    private void setData() {
        Spin_A0.setSelection(A0.getPosition(PrefData.getStringPrefs(CustomButtonsActivity.this, PrefData.AO_EN, array[0]).toUpperCase()));
    }

    private void Onclick() {

        Text_A0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentKeyboardLayout == PrefData.VAL_LAYOUT_ITALIAN) {
                    if (PrefData.getArrayListPref(CustomButtonsActivity.this, PrefData.k) != null && PrefData.getArrayListPref(CustomButtonsActivity.this, PrefData.k).size() != 0) {
                        list = PrefData.getArrayListPref(CustomButtonsActivity.this, PrefData.k);
                    } else {
                        list = new ArrayList<String>(Arrays.asList(k));
                    }
                    openPopupDialog(list, PrefData.k);
                }
                if (currentKeyboardLayout == PrefData.VAL_LAYOUT_SPANISH) {
                    if (PrefData.getArrayListPref(CustomButtonsActivity.this, PrefData.g) != null && PrefData.getArrayListPref(CustomButtonsActivity.this, PrefData.g).size() != 0) {
                        list = PrefData.getArrayListPref(CustomButtonsActivity.this, PrefData.g);
                    } else {
                        list = new ArrayList<String>(Arrays.asList(g));
                    }
                    openPopupDialog(list, PrefData.g);
                }

            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PrefData.remove(CustomButtonsActivity.this, PrefData.TOP_BUTTON_1);
                PrefData.remove(CustomButtonsActivity.this, PrefData.TOP_BUTTON_2);
                PrefData.remove(CustomButtonsActivity.this, PrefData.TOP_BUTTON_3);
                PrefData.remove(CustomButtonsActivity.this, PrefData.TOP_BUTTON_4);
                PrefData.remove(CustomButtonsActivity.this, PrefData.BOTTOM_BUTTON_1);
                PrefData.remove(CustomButtonsActivity.this, PrefData.BOTTOM_BUTTON_2);
                PrefData.remove(CustomButtonsActivity.this, PrefData.BOTTOM_BUTTON_3);
                PrefData.remove(CustomButtonsActivity.this, PrefData.BOTTOM_BUTTON_4);
                setData();
                finishmethod();
            }

        });
        subscribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if (PrefData.getBooleanPrefs(CustomLayoutActivity.this, Constant.SKU)) { //need to remove not
                String A0 = Spin_A0.getSelectedItem().toString();
                if (currentKeyboardLayout == PrefData.VAL_LAYOUT_ENGLISH) {
                    PrefData.setStringPrefs(CustomButtonsActivity.this, PrefData.AO_EN, A0.toLowerCase());
                } else if (currentKeyboardLayout == PrefData.VAL_LAYOUT_ITALIAN) {
                    PrefData.setStringPrefs(CustomButtonsActivity.this, PrefData.AO_IT, A0.toLowerCase());
                } else if (currentKeyboardLayout == PrefData.VAL_LAYOUT_SPANISH) {
                    PrefData.setStringPrefs(CustomButtonsActivity.this, PrefData.AO_SP, A0.toLowerCase());
                } else if (currentKeyboardLayout == PrefData.VAL_LAYOUT_GERMAN) {
                    PrefData.setStringPrefs(CustomButtonsActivity.this, PrefData.AO_DE, A0.toLowerCase());
                }

                finishmethod();

                //} else { showFreeSubscriptionAlert();}
            }
        });

    }

    private void openPopupDialog(final ArrayList<String> list, final String charcter) {
        final Dialog dialog = new Dialog(CustomButtonsActivity.this);
        dialog.setContentView(R.layout.popup_dialog);
        RecyclerView rv = dialog.findViewById(R.id.popup_recycler);
        rv.setLayoutManager(new LinearLayoutManager(CustomButtonsActivity.this));
        final PopUpAdapter adapter = new PopUpAdapter(CustomButtonsActivity.this, list);
        rv.setAdapter(adapter);
        TextView ok = dialog.findViewById(R.id.ok);
        TextView reset = dialog.findViewById(R.id.reset);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (charcter) {
                    case PrefData.a:
                        ArrayList<String> a = adapter.getList();
                        PrefData.setArrayListPref(CustomButtonsActivity.this, PrefData.a, a);
                        break;
                }

                popupupdate();
                dialog.dismiss();
            }
        });
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch (charcter) {
                    case PrefData.a:
                        ArrayList<String> a1 = new ArrayList<String>(Arrays.asList(a));
                        adapter.setList(a1);
                        adapter.notifyDataSetChanged();
                        break;
                }
                //popupupdate();
            }
        });
        dialog.show();
    }

    private void popupupdate() {
        PrefData.setBooleanPrefs(CustomButtonsActivity.this, PrefData.KEY_KEYBOARD_LAYOUT_CUSTOMPOPUP, !PrefData.getBooleanPrefs(CustomButtonsActivity.this, PrefData.KEY_KEYBOARD_LAYOUT_CUSTOMPOPUP));

    }


    private void finishmethod() {
        PrefData.setBooleanPrefs(CustomButtonsActivity.this, PrefData.KEY_KEYBOARD_LAYOUT_CUSTOMLAYOUT, !PrefData.getBooleanPrefs(CustomButtonsActivity.this, PrefData.KEY_KEYBOARD_LAYOUT_CUSTOMLAYOUT));
        finish();
    }

    private void initGloble() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        SpannableString spannableString = new SpannableString(actionBar.getTitle());
        spannableString.setSpan(new TypefaceSpan(this, getString(R.string.font_dosis_regular)), 0, spannableString.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        actionBar.setTitle(spannableString);
        main_txt = findViewById(R.id.main_txt);
        currentKeyboardLayout = PrefData.getIntPrefs(this, PrefData.KEY_KEYBOARD_LAYOUT_I,
                PrefData.VAL_LAYOUT_ENGLISH);
        //e,u,i,o,a,s,c,n,r,t,w,y,z,d,g,h,j,k,l

        //a,c,d,e,g,h,i,j,k,l,o,r,s,t,u,w,y,z

        a = new String[]{"æ", "à", "ã", "å", "á", "â", "ä", "ª", "ā", "ă", "ą"};
        c = new String[]{"ć", "č", "ç", "ċ", "ĉ"};
        d = new String[]{"ð", "đ", "ď"};
        e = new String[]{"ē", "è", "ê", "ë", "é", "ę", "ė", "ě"};
        g = new String[]{"ġ", "ğ", "ĝ", "ģ"};
        h = new String[]{"ħ", "ĥ"};
        i = new String[]{"ï", "ì", "ī", "î", "í", "į", "ĩ"};
        //j = new String[]{"ĵ"};
        k = new String[]{"ĸ", "ķ"};
        l = new String[]{"ļ", "ĺ", "ľ", "ŀ", "ł"};
        n = new String[]{"ń", "ñ", "ņ", "ŋ", "ň", "ŉ"};
        o = new String[]{"õ", "ò", "ó", "ø", "ō", "ö", "ô", "œ", "º", "ő"};
        r = new String[]{"ŕ", "ř", "ŗ"};
        s = new String[]{"ŝ", "ß", "š", "ş", "ś", "ș"};
        t = new String[]{"ŧ", "ť", "ț", "ţ"};
        u = new String[]{"ü", "ù", "ú", "ů", "û", "ū", "ŭ", "ũ", "ų", "ű", "µ"};
        // w = new String[]{"ŵ"};
        y = new String[]{"ý", "ŷ", "ÿ"};
        z = new String[]{"ź", "ż", "ž"};


        if (currentKeyboardLayout == PrefData.VAL_LAYOUT_ITALIAN) {
            main_txt.setText(R.string.italian);
            array1 = new String[]{"<u>K</u>", "V", "B", "X", "Q", "<u>D</u>", "<u>R</u>", "P", "<u>U</u>", "<u>E</u>", "<u>A</u>", "<u>S</u>", "<u>H</u>", "<u>I</u>", "<u>T</u>", "<u>C</u>", "<u>L</u>", "<u>O</u>", "<u>N</u>", "W", "F", "<u>G</u>", "M", "J", "<u>Y</u>", "<u>Z</u>"};
            array = new String[]{"K", "V", "B", "X", "Q", "D", "R", "P", "U", "E", "A", "S", "H", "I", "T", "C", "L", "O", "N", "W", "F", "G", "M", "J", "Y", "Z"};
        } else if (currentKeyboardLayout == PrefData.VAL_LAYOUT_SPANISH) {
            main_txt.setText(R.string.spanish);
            array1 = new String[]{"<u>G</u>", "B", "F", "X", "Q", "<u>R</u>", "<u>L</u>", "P", "<u>U</u>", "<u>O</u>", "<u>A</u>", "M", "<u>H</u>", "<u>I</u>", "<u>S</u>", "<u>C</u>", "<u>Y</u>", "<u>E</u>", "<u>T</u>", "W", "V", "<u>D</u>", "<u>N</u>", "<u>K</u>", "J", "<u>Z</u>"};
            array = new String[]{"G", "B", "F", "X", "Q", "R", "L", "P", "U", "O", "A", "M", "H", "I", "S", "C", "Y", "E", "T", "W", "V", "D", "N", "K", "J", "Z"};
        } else if (currentKeyboardLayout == PrefData.VAL_LAYOUT_GERMAN) {
            main_txt.setText(R.string.german);
            array1 = new String[]{"Q", "<u>Y</u>", "P", "X", "<u>Z</u>", "<u>G</u>", "<u>R</u>", "B", "<u>U</u>", "<u>N</u>", "<u>E</u>", "M", "<u>H</u>", "<u>I</u>", "<u>D</u>", "<u>C</u>", "<u>S</u>", "<u>A</u>", "W", "<u>K</u>", "<u>L</u>", "<u>T</u>", "<u>O</u>", "J", "F", "V"};
            array = new String[]{"Q", "Y", "P", "X", "Z", "G", "R", "B", "U", "N", "E", "M", "H", "I", "D", "C", "S", "A", "W", "K", "L", "T", "O", "J", "F", "V"};
        } else {
            main_txt.setText(R.string.english);
            array1 = new String[]{"J", "<u>Z</u>", "B", "X", "Q", "F", "<u>R</u>", "P", "<u>U</u>", "<u>O</u>", "<u>E</u>", "<u>L</u>", "W", "<u>H</u>", "<u>A</u>", "<u>I</u>", "<u>T</u>", "<u>S</u>", "<u>D</u>", "<u>C</u>", "<u>N</u>", "<u>Y</u>", "M", "<u>K</u>", "<u>G</u>", "V"};
            array = new String[]{"J", "Z", "B", "X", "Q", "F", "R", "P", "U", "O", "E", "L", "W", "H", "A", "I", "T", "S", "D", "C", "N", "Y", "M", "K", "G", "V"};
        }

        reset = findViewById(R.id.reset);
        subscribe = findViewById(R.id.subscribe);


        Text_A0 = findViewById(R.id.Text_A0);
        Text_A0.setText(Html.fromHtml(array1[0]));

        Spin_A0 = findViewById(R.id.Spin_A0);

        A0 = ArrayAdapter.createFromResource(this, R.array.alphabet, R.layout.text_spinner);
        Spin_A0.setAdapter(A0);

        setData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.theme_market_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_info:
                showLayoutInfoDialog();
                break;
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showLayoutInfoDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.DialogTheme);
        SpannableString spannableString = new SpannableString(getString(R.string.layout_info));
        spannableString.setSpan(new TypefaceSpan(this, getString(R.string.font_dosis_regular)), 0, spannableString.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder.setMessage(spannableString)
                .setPositiveButton(R.string.cancel, null)
                .create().show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

}
