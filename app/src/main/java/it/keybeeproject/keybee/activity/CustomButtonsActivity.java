package it.keybeeproject.keybee.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.Pair;
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
import java.util.List;

import it.keybeeproject.keybee.R;
import it.keybeeproject.keybee.adapter.PopUpAdapter;
import it.keybeeproject.keybee.model.ButtonAction;
import it.keybeeproject.keybee.model.CustomButtonSetting;
import it.keybeeproject.keybee.utility.PrefData;
import it.keybeeproject.keybee.utility.TypefaceSpan;

public class CustomButtonsActivity extends AppCompatActivity {//implements IabBroadcastReceiver.IabBroadcastListener {

    // List of default custom button settings. Short and long actions
    String[] defaultButtonActions = {
            ButtonAction.Disabled.name(), ButtonAction.Disabled.name(),
            ButtonAction.Disabled.name(), ButtonAction.Disabled.name(),
            ButtonAction.Disabled.name(), ButtonAction.Disabled.name(),
            ButtonAction.Disabled.name(), ButtonAction.Disabled.name(),
            ButtonAction.Disabled.name(), ButtonAction.Settings.name(),
            ButtonAction.Emoji.name(), ButtonAction.Disabled.name(),
            ButtonAction.Enter.name(), ButtonAction.Disabled.name(),
            ButtonAction.Layout.name(), ButtonAction.Disabled.name()};
    String[] array, array1, a, c, d, e, g, h, i, j, k, l, o, n, r, s, t, u, w, y, z;
    int currentKeyboardLayout;
    Spinner Spin_A0;
    TextView Text_A0, reset, done, main_txt;
    //private IabHelper iabHelper;
    ArrayList<String> list;

    int[] spinnerIds = {R.id.Spin_Top1Short, R.id.Spin_Top1Long, R.id.Spin_Top2Short, R.id.Spin_Top2Long, R.id.Spin_Top3Short, R.id.Spin_Top3Long, R.id.Spin_Top4Short, R.id.Spin_Top4Long, R.id.Spin_Bottom1Short, R.id.Spin_Bottom1Long, R.id.Spin_Bottom2Short, R.id.Spin_Bottom2Long, R.id.Spin_Bottom3Short, R.id.Spin_Bottom3Long, R.id.Spin_Bottom4Short, R.id.Spin_Bottom4Long};
    int[] textIds = {R.id.Text_Top1Short, R.id.Text_Top1Long, R.id.Text_Top2Short, R.id.Text_Top2Long, R.id.Text_Top3Short, R.id.Text_Top3Long, R.id.Text_Top4Short, R.id.Text_Top4Long, R.id.Text_Bottom1Short, R.id.Text_Bottom1Long, R.id.Text_Bottom2Short, R.id.Text_Bottom2Long, R.id.Text_Bottom3Short, R.id.Text_Bottom3Long, R.id.Text_Bottom4Short, R.id.Text_Bottom4Long};
    ArrayAdapter A0;
    CustomButtonSetting[] customButtonSettingList = new CustomButtonSetting[16];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        List<String> currentSettings = PrefData.getArrayListPref(CustomButtonsActivity.this, PrefData.CUSTOM_BUTTONS);
        if (currentSettings == null || currentSettings.size() != customButtonSettingList.length) {
            currentSettings = Arrays.asList(defaultButtonActions);
        }
        for (int i = 0; i < customButtonSettingList.length; i++) {
            customButtonSettingList[i] = new CustomButtonSetting(
                    i,
                    this,
                    findViewById(spinnerIds[i]),
                    findViewById(textIds[i]),
                    i%2==0,
                    ButtonAction.valueOf(currentSettings.get(i))
            );
        }

        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        setContentView(R.layout.activity_custom_buttons);
        initGloble();
        Onclick();
        setData();
    }


    private void setData() {
        Spin_A0.setSelection(A0.getPosition(PrefData.getStringPrefs(CustomButtonsActivity.this, PrefData.AO_EN, array[0]).toUpperCase()));
    }

    private void Onclick() {

        Text_A0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (currentKeyboardLayout == PrefData.VAL_LAYOUT_SPANISH) {
                if (PrefData.getArrayListPref(CustomButtonsActivity.this, PrefData.g) != null && PrefData.getArrayListPref(CustomButtonsActivity.this, PrefData.g).size() != 0) {
                    list = PrefData.getArrayListPref(CustomButtonsActivity.this, PrefData.g);
                } else {
                    list = new ArrayList<String>(Arrays.asList(g));
                }
                openPopupDialog(list, PrefData.g);
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
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if (PrefData.getBooleanPrefs(CustomLayoutActivity.this, Constant.SKU)) { //need to remove not
                String A0 = Spin_A0.getSelectedItem().toString();
                // PrefData.setStringPrefs(CustomButtonsActivity.this, PrefData.AO_EN, A0.toLowerCase());
                // CustomButton.valueOf("Enter")
                // enumValue.name();
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




//        if (currentKeyboardLayout == PrefData.VAL_LAYOUT_ITALIAN) {
//        main_txt.setText(R.string.italian);
        array1 = new String[]{"<u>K</u>", "V", "B", "X", "Q", "<u>D</u>", "<u>R</u>", "P", "<u>U</u>", "<u>E</u>", "<u>A</u>", "<u>S</u>", "<u>H</u>", "<u>I</u>", "<u>T</u>", "<u>C</u>", "<u>L</u>", "<u>O</u>", "<u>N</u>", "W", "F", "<u>G</u>", "M", "J", "<u>Y</u>", "<u>Z</u>"};
        array = new String[]{"K", "V", "B", "X", "Q", "D", "R", "P", "U", "E", "A", "S", "H", "I", "T", "C", "L", "O", "N", "W", "F", "G", "M", "J", "Y", "Z"};

        reset = findViewById(R.id.reset);
        done = findViewById(R.id.done);


        Text_A0 = findViewById(R.id.Text_A0);
        Text_A0.setText(Html.fromHtml(array1[0]));

        Spin_A0 = findViewById(R.id.Spin_A0);

        A0 = new ArrayAdapter<>(this, R.layout.text_spinner, ButtonAction.LABELS);
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
