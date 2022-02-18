package com.example.myapp2.async;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.viewbinding.ViewBinding;

import com.example.myapp2.BaseActivity;
import com.example.myapp2.R;
import com.example.myapp2.databinding.ActivityAsyncTaskLoaderBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AsyncTaskLoaderActivity extends BaseActivity implements LoaderManager.LoaderCallbacks<String> {

    @Override
    protected int currentId() {
        return R.id.nav_asyncTask;
    }

    private ActivityAsyncTaskLoaderBinding binding;

    @Override
    protected ViewBinding binding() {
        return binding;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityAsyncTaskLoaderBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);

        if (getSupportLoaderManager().getLoader(0) != null)
            getSupportLoaderManager().initLoader(0, null, this);
    }

    public void searchBooks(View view) {
        String query = binding.songInput.getText().toString();

        //to hide the keyboard when the button is pressed
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        if (inputMethodManager != null) {
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }

        //to check the status of the network connection
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = null;
        if (manager != null) networkInfo = manager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected() && query.length() != 0) {
            Bundle queryBundle = new Bundle();
            queryBundle.putString("query", query);
            getSupportLoaderManager().restartLoader(0, queryBundle, this);

            binding.authorText.setText("");
            binding.titleText.setText("loading");
        } else {
            if (query.length() == 0) {
                binding.authorText.setText("");
                binding.titleText.setText("Your query has no results");
            } else {
                binding.authorText.setText("");
                binding.titleText.setText("No network");
            }
        }

    }

    @NonNull
    @Override
    public Loader<String> onCreateLoader(int id, @Nullable Bundle args) {
        String query = "";

        if (args != null) query = args.getString("query");

        return new com.example.myapp2.async.Loader(this, query);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String data) {
        try {
            // Convert the response into a JSON object.
            JSONObject jsonObject = new JSONObject(data);
            // Get the JSONArray of book items.
            JSONArray itemsArray = jsonObject.getJSONArray("items");

            // Initialize iterator and results fields.
            int i = 0;
            String title = null;
            String authors = null;

            // Look for results in the items array, exiting when both the
            // title and author are found or when all items have been checked.
            while (i < itemsArray.length() &&
                    (authors == null && title == null)) {
                // Get the current item information.
                JSONObject book = itemsArray.getJSONObject(i);
                JSONObject volumeInfo = book.getJSONObject("volumeInfo");

                // Try to get the author and title from the current item,
                // catch if either field is empty and move on.
                try {
                    title = volumeInfo.getString("title");
                    authors = volumeInfo.getString("authors");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                // Move to the next item.
                i++;
            }

            // If both are found, display the result.
            if (title != null && authors != null) {
                binding.titleText.setText(title);
                binding.authorText.setText(authors);
                //mBookInput.setText("");
            } else {
                // If none are found, update the UI to show failed results.
                binding.titleText.setText(R.string.no_results);
                binding.authorText.setText("");
            }

        } catch (Exception e) {
            // If onPostExecute does not receive a proper JSON string,
            // update the UI to show failed results.
            binding.titleText.setText(R.string.no_results);
            binding.authorText.setText("");
            e.printStackTrace();
        }

    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {

    }
}