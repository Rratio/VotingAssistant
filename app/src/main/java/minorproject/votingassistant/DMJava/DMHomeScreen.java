package minorproject.votingassistant.DMJava;

import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import minorproject.votingassistant.R;

import static android.content.Context.MODE_PRIVATE;


public class DMHomeScreen extends Fragment {

    private RecyclerView recyclerView;
    private SDMDataAdapter adapter;
    private List<SDMData> albumList;
    private MenuItem logout;
    String name,id;
    SharedPreferences sharedPreferences;


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        sharedPreferences = getActivity().getSharedPreferences("LoginData",MODE_PRIVATE);

        if(sharedPreferences!=null){
            name = sharedPreferences.getString("dm_name",null);
            id = sharedPreferences.getString("dm_id",null);

        }


        View view =  inflater.inflate(R.layout.fragment_sdmhome_screen, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view1);
        logout = (MenuItem) view.findViewById(R.id.logout);

        albumList = new ArrayList<>();
        adapter = new SDMDataAdapter(getActivity(), albumList);
        CardView card = (CardView) view.findViewById(R.id.card_view);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new DMHomeScreen.GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        prepareAlbums();
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            StateListAnimator stateListAnimator = AnimatorInflater
//                    .loadStateListAnimator(getContext(), R.anim.lift_on_touch);
//            card.setStateListAnimator(stateListAnimator);
//        }
//        card.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });

        return view;
    }
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("DMHomeScreen");
    }


    //            FOR MENU ICON OF LOGOUT




//    ON LOGOUT MENU SELECTION


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.logout) {
            Toast.makeText(getActivity(), "back to Login", Toast.LENGTH_LONG).show();
            return true;
        }

        return super.onOptionsItemSelected(item);


    }


    /**
     * Adding few albums for testing
     */
    private void prepareAlbums() {
        int[] covers = new int[]{
                R.mipmap.ic_add,
                R.mipmap.ic_update,
                R.mipmap.ic_check,
                R.mipmap.ic_list,
                R.mipmap.ic_list};

        SDMData a = new SDMData("Add SDM", covers[0]);
        albumList.add(a);

        a = new SDMData("Update SDM", covers[1]);
        albumList.add(a);

        a = new SDMData("Check Voting Status", covers[2]);
        albumList.add(a);

        a = new SDMData("SDM List", covers[3]);
        albumList.add(a);

        a = new SDMData("Zonal Officer List", covers[4]);
        albumList.add(a);

        adapter.notifyDataSetChanged();
    }

    /**
     * RecyclerView item decoration - give equal margin around grid item
     */
    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
}


