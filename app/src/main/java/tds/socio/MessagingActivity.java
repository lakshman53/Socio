package tds.socio;

/**
 * Created by laks on 29-01-2015.
 */

import android.content.res.TypedArray;
import android.os.Bundle;

public class MessagingActivity extends BaseActivity {
    private String[] navMenuTitles;
    private TypedArray navMenuIcons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messaging);
        navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);

        navMenuIcons = getResources()
                .obtainTypedArray(R.array.nav_drawer_icons);

        set(navMenuTitles, navMenuIcons);
//        SELECT       offer.offerid, sender, subject, message, sent
//        FROM            Offer INNER JOIN
//        OfferGroups ON Offer.OfferId = OfferGroups.OfferId INNER JOIN
//        Employee INNER JOIN
//        EmpGroups ON Employee.EmpId = EmpGroups.EmpId INNER JOIN
//        [Group] ON EmpGroups.GroupId = [Group].GroupId ON OfferGroups.GroupId = [Group].GroupId
//        WHERE Offer.OfferId > 0 and Employee.EmpId = 3



    }

}