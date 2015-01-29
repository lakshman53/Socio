package tds.socio;

/**
 * Created by laks on 29-01-2015.
 */

import android.content.res.TypedArray;
import android.os.Bundle;

public class AttendanceActivity extends BaseActivity {
    private String[] navMenuTitles;
    private TypedArray navMenuIcons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);
        navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);

        navMenuIcons = getResources()
                .obtainTypedArray(R.array.nav_drawer_icons);

        set(navMenuTitles, navMenuIcons);
    }
}

