package tds.socio;

/**
 * Created by laks on 28-03-2015.
 */

import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.ViewGroup;
import android.widget.ListView;

import com.nhaarman.listviewanimations.appearance.simple.SwingBottomInAnimationAdapter;
import com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.OnDismissCallback;
import com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.SwipeDismissAdapter;

import java.util.List;

public class CardsActivity extends BaseActivity implements OnDismissCallback {

    private static final int INITIAL_DELAY_MILLIS = 300;
    private String[] navMenuTitles;
    private TypedArray navMenuIcons;

    private CardsAdapter mCardsAdapter;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cards);

        navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);
        navMenuIcons = getResources().obtainTypedArray(R.array.nav_drawer_icons);
        set(navMenuTitles, navMenuIcons);

        ListView listView = (ListView) findViewById(R.id.activity_googlecards_listview);

        mCardsAdapter = new CardsAdapter(this);
        SwingBottomInAnimationAdapter swingBottomInAnimationAdapter = new SwingBottomInAnimationAdapter(new SwipeDismissAdapter(mCardsAdapter, this));
        swingBottomInAnimationAdapter.setAbsListView(listView);

        assert swingBottomInAnimationAdapter.getViewAnimator() != null;
        swingBottomInAnimationAdapter.getViewAnimator().setInitialDelayMillis(INITIAL_DELAY_MILLIS);

        listView.setAdapter(swingBottomInAnimationAdapter);


        List<Offers> offers;
        offers = Offers.listAll(Offers.class);
       // Collections.reverse(offers);


        for (int i=0; i<offers.size(); i=i+1) {
            mCardsAdapter.add(i);

        }
        
    }

    @Override
    public void onDismiss(@NonNull final ViewGroup listView, @NonNull final int[] reverseSortedPositions) {
        for (int position : reverseSortedPositions) {
            mCardsAdapter.remove(position);
        }
    }
}
