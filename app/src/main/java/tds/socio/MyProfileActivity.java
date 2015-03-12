package tds.socio;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.widget.TextView;

import java.util.List;

/**
 * Created by laks on 29-01-2015.
 */

public class MyProfileActivity extends BaseActivity {
    private String[] navMenuTitles;
    private TypedArray navMenuIcons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);

        navMenuIcons = getResources()
                .obtainTypedArray(R.array.nav_drawer_icons);

        set(navMenuTitles, navMenuIcons);

        List<Employee> employees;
        employees = Employee.listAll(Employee.class);

        if (employees.size() > 0) {
            TextView TVEmpName = (TextView) findViewById(R.id.EmpName);
            String EmpName = "Name: " + employees.get(0).getFirstName() + " " + employees.get(0).getLastName();
            TVEmpName.setText(EmpName);

            TextView TVStoreName = (TextView) findViewById(R.id.EmpStore);
            String StoreName = "Store: " + employees.get(0).getStoreName();
            TVStoreName.setText(StoreName);
        }
    }
}