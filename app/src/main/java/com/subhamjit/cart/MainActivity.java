package com.subhamjit.cart;

import android.app.Activity;
import android.app.*;
import android.os.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;
import android.content.*;
import android.content.res.*;
import android.graphics.*;
import android.graphics.drawable.*;
import android.media.*;
import android.net.*;
import android.text.*;
import android.text.style.*;
import android.util.*;
import android.webkit.*;
import android.animation.*;
import android.view.animation.*;
import java.util.*;
import java.util.regex.*;
import java.text.*;
import org.json.*;
import java.util.HashMap;
import java.util.ArrayList;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import com.google.gson.Gson;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.DialogFragment;


public class MainActivity extends  Activity {


	private double n1 = 0;
	private double selectedTotal = 0;
	private double n2 = 0;
	private HashMap<String, Object> cacheMap = new HashMap<>();

	private ArrayList<HashMap<String, Object>> itemList = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> resultData = new ArrayList<>();

	private LinearLayout linear1;
	private ListView listview1;
	private Button button1;

	private Intent intent = new Intent();


	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.main);
		initialize(_savedInstanceState);
		initializeLogic();
	}

	private void initialize(Bundle _savedInstanceState) {

		linear1 = (LinearLayout) findViewById(R.id.linear1);
		listview1 = (ListView) findViewById(R.id.listview1);
		button1 = (Button) findViewById(R.id.button1);

		button1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				n1 = 0;
				resultData.clear();
				for(int _repeat14 = 0; _repeat14 < (int)(itemList.size()); _repeat14++) {
					if (itemList.get((int)n1).get("select").toString().equals("1")) {
						cacheMap = itemList.get((int)n1);
						resultData.add(cacheMap);
					}
					n1++;


				}


				//NEW ACTIVITY

				intent.setClass(getApplicationContext(), ResultActivity.class);
				intent.putExtra("data", new Gson().toJson(resultData));
				startActivity(intent);


			}
		});
	}

	private void initializeLogic() {
		getWindow().setStatusBarColor(Color.parseColor("#212121"));

		listview1.setVerticalScrollBarEnabled(false);
		selectedTotal = 0;
		n1 = 1;
		listview1.setAdapter(new Listview1Adapter(itemList));
		for(int _repeat11 = 0; _repeat11 < (int)(15); _repeat11++) {
			_addItem("Product ".concat(String.valueOf((long)(n1))), "5");
			n1++;
		}
		((BaseAdapter)listview1.getAdapter()).notifyDataSetChanged();
		_refreshSelected();
	}



	public void _addItem (final String _item_title, final String _item_subtitle) {
		{
			HashMap<String, Object> _item = new HashMap<>();
			_item.put("title", _item_title);
			itemList.add(_item);
		}

		itemList.get((int)itemList.size() - 1).put("subtitle", _item_subtitle);
		itemList.get((int)itemList.size() - 1).put("total", "0");
		itemList.get((int)itemList.size() - 1).put("select", "0");
	}


	public void _refreshSelected () {
		if (selectedTotal > 0) {
			//Toast.makeText(this, calculate_total_price()+"", Toast.LENGTH_SHORT).show();
			button1.setEnabled(true);
			button1.setText(String.valueOf((long)(selectedTotal)).concat(" ITEMS SELECTED, PRICE ₹"+calculate_total_price()+" TAP TO VIEW"));
		}
		else {
			button1.setEnabled(false);
			button1.setText("SELECT ITEMS TO CONTINUE");
		}
	}


	public int calculate_total_price ()
	{
		int total_p = 0;
		for(int x=0; x<itemList.size(); x++)
		{

			if (itemList.get(x).get("select").toString().equals("1")) {

				int price_of_position = Integer.parseInt(itemList.get(x).get("subtitle").toString());
				int qty_of_postiton =  Integer.parseInt(itemList.get(x).get("total").toString());

				// total is qty of product
				//subtitle is price

				total_p = total_p + (price_of_position*qty_of_postiton);

			}



		}

		return total_p;
	}



	public void _selectItem (final double _pos) {
		if (itemList.get((int)_pos).get("select").toString().equals("1")) {
			itemList.get((int)_pos).put("select", "0");
			selectedTotal = selectedTotal - Double.parseDouble(itemList.get((int)_pos).get("total").toString());
		}
		else {
			itemList.get((int)_pos).put("select", "1");
			if (itemList.get((int)_pos).get("total").toString().equals("0")) {
				itemList.get((int)_pos).put("total", "1");
			}
			selectedTotal = selectedTotal + Double.parseDouble(itemList.get((int)_pos).get("total").toString());
		}
		((BaseAdapter)listview1.getAdapter()).notifyDataSetChanged();
		_refreshSelected();
	}


	public void _increaseTotal (final double _position, final double _value) {
		n2 = Double.parseDouble(itemList.get((int)_position).get("total").toString()) + _value;
		if (n2 > 0) {
			itemList.get((int)_position).put("total", String.valueOf((long)(n2)));
		}
		((BaseAdapter)listview1.getAdapter()).notifyDataSetChanged();
		selectedTotal = selectedTotal + _value;
		_refreshSelected();
	}



	public class Listview1Adapter extends BaseAdapter {
		ArrayList<HashMap<String, Object>> _data;
		public Listview1Adapter(ArrayList<HashMap<String, Object>> _arr) {
			_data = _arr;
		}

		@Override
		public int getCount() {
			return _data.size();
		}

		@Override
		public HashMap<String, Object> getItem(int _index) {
			return _data.get(_index);
		}

		@Override
		public long getItemId(int _index) {
			return _index;
		}
		@Override
		public View getView(final int _position, View _v, ViewGroup _container) {
			LayoutInflater _inflater = (LayoutInflater)getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View _view = _v;
			if (_view == null) {
				_view = _inflater.inflate(R.layout.item, null);
			}

			final LinearLayout linear1 = (LinearLayout) _view.findViewById(R.id.linear1);
			final LinearLayout linear2 = (LinearLayout) _view.findViewById(R.id.linear2);
			final LinearLayout linear3 = (LinearLayout) _view.findViewById(R.id.linear3);
			final ImageView imageview1 = (ImageView) _view.findViewById(R.id.imageview1);
			final TextView textview1 = (TextView) _view.findViewById(R.id.textview1);
			final TextView textview2 = (TextView) _view.findViewById(R.id.textview2);
			final ImageView imageview2 = (ImageView) _view.findViewById(R.id.imageview2);
			final TextView textview3 = (TextView) _view.findViewById(R.id.textview3);
			final ImageView imageview3 = (ImageView) _view.findViewById(R.id.imageview3);


			int price_of_position = Integer.parseInt(itemList.get(_position).get("subtitle").toString());
			int qty_of_postiton =  Integer.parseInt(itemList.get(_position).get("total").toString());

			// total is qty of product
			//subtitle is price



			textview1.setText(itemList.get((int)_position).get("title").toString());
			textview2.setText(price_of_position*qty_of_postiton+"");

			if (itemList.get((int)_position).get("select").toString().equals("1")) {
				imageview1.setImageResource(R.drawable.ic_brightness_1_white);
				imageview1.setColorFilter(0xFF00BCD4, PorterDuff.Mode.MULTIPLY);
				linear3.setVisibility(View.VISIBLE);
				textview3.setText(itemList.get((int)_position).get("total").toString());
			}
			else {
				imageview1.setImageResource(R.drawable.ic_panorama_fisheye_white);
				imageview1.setColorFilter(0xFFBDBDBD, PorterDuff.Mode.MULTIPLY);
				linear3.setVisibility(View.GONE);
			}

			imageview1.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View _view) {
					_selectItem(_position);
					}
			});
			imageview2.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View _view) {
					_increaseTotal(_position, -1);
				}
			});
			imageview3.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View _view) {
					_increaseTotal(_position, 1);
				}
			});

			return _view;
		}
	}

	@Deprecated
	public void showMessage(String _s) {
		Toast.makeText(getApplicationContext(), _s, Toast.LENGTH_SHORT).show();
	}


}
