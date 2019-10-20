package com.example.thepranami.donation;


import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class DonorListFragment extends Fragment {
RecyclerView donorListRecycler;
ProgressDialog progressDialog;

    public DonorListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View donorView = inflater.inflate(R.layout.fragment_donor_list, container, false);
        donorListRecycler = (RecyclerView)donorView.findViewById(R.id.donorList_recyclerView_id);
         //execute
        new FetchDonorListTask().execute();
        return donorView;
    }

    //   fetch all data
    class FetchDonorListTask extends AsyncTask<String, Void, String>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setTitle("Loading");
            progressDialog.setMessage("Please wait...");
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            String RES = null;
            try {
                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(AppUrlLink.BASE_URL+"fetch_donor_list.php");

                HttpResponse httpResponse = httpClient.execute(httpPost);
                HttpEntity httpEntity = httpResponse.getEntity();
                RES = EntityUtils.toString(httpEntity);

            }
            catch (Exception e) {
                RES = e.toString();
            }
            return RES;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.dismiss();

            ArrayList<DonorViewModel> modelArrayList = new ArrayList<>();
            JSONArray jsonArray = null;
            try {
                jsonArray = new JSONArray(s);
                for (int i=0; i<jsonArray.length(); i++){
                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    String ID = jsonObject.getString("id");
                    String AMOUNT = jsonObject.getString("amount");
                    String NAME = jsonObject.getString("name");
                    String OTHER = jsonObject.getString("other");
                    String ADDRESS = jsonObject.getString("address");
                    String MOBILE = jsonObject.getString("mobile");

                    modelArrayList.add(new DonorViewModel(ID, AMOUNT, NAME, OTHER, ADDRESS, MOBILE));
                }
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                donorListRecycler.setLayoutManager(layoutManager);
                CustomView customView = new CustomView(modelArrayList, getActivity());
                donorListRecycler.setAdapter(customView);

            } catch (JSONException e) {
                e.printStackTrace();
            }
           // Toast.makeText(getActivity(), s, Toast.LENGTH_LONG).show();
        }
    }
}
