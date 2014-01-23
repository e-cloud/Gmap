package com.example.gmap.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;

import com.example.gmap.R;
import com.example.gmap.model.Blingcoord;

public class DataRetriever {
	public static List<Blingcoord> retrieveAllBlingcoords(Activity activity) {
		// 将资源标识符转为字符串
		String url = activity.getResources().getString(R.string.bling);
		// 存储获得得Blingcoord，用于将数据返回
		List<Blingcoord> blingArrayList = new ArrayList<Blingcoord>();
		// 指定服务器端URL
		HttpGet httpGet = new HttpGet(url);

		HttpClient httpClient = new DefaultHttpClient();

		try {
			HttpResponse httpResponse = httpClient.execute(httpGet);
			// 获取响应实体
			HttpEntity httpEntity = httpResponse.getEntity();
			// 将响应实体转换为字符串
			String jsonString = EntityUtils.toString(httpEntity);

			// 将字符串转为JSONArray
			JSONArray jsonArray = new JSONArray(jsonString);
			Blingcoord blingcoord;

			for (int i = 0; i < jsonArray.length(); i++) {
				/**
				 * 服务器返回的数据是JSONArray，在JSONArray里面有不同的JSONObject，
				 * 在JSONObject中“fields“名称后面得值是JSONObject， 我们需要的就是这个JSONObject
				 */
				JSONObject jsonObj = jsonArray.getJSONObject(i).getJSONObject(
						"fields");
				// 初始化Blingcoord
				blingcoord = new Blingcoord();

				// 获得jsonObj中的"name"名称后面得值，并保存在blingcoord中的name域中
				blingcoord.setCity(jsonObj.getString("city"));
				blingcoord.setName(jsonObj.getString("name"));
				blingcoord.setZipCode(jsonObj.getString("zip"));
				blingcoord.setLongitude(Double.parseDouble(jsonObj
						.getString("longitude")));
				blingcoord.setState(jsonObj.getString("state"));
				blingcoord.setAddress(jsonObj.getString("address"));
				blingcoord.setLatitude(Double.parseDouble(jsonObj
						.getString("latitude")));
				blingcoord.setBling_id(jsonObj.getString("bling_id"));

				// 将blingcoord放到blingArrayList中去
				blingArrayList.add(blingcoord);

			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return blingArrayList;
	}
}
