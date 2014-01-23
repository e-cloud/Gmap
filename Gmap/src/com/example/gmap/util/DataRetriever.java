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
		// ����Դ��ʶ��תΪ�ַ���
		String url = activity.getResources().getString(R.string.bling);
		// �洢��õ�Blingcoord�����ڽ����ݷ���
		List<Blingcoord> blingArrayList = new ArrayList<Blingcoord>();
		// ָ����������URL
		HttpGet httpGet = new HttpGet(url);

		HttpClient httpClient = new DefaultHttpClient();

		try {
			HttpResponse httpResponse = httpClient.execute(httpGet);
			// ��ȡ��Ӧʵ��
			HttpEntity httpEntity = httpResponse.getEntity();
			// ����Ӧʵ��ת��Ϊ�ַ���
			String jsonString = EntityUtils.toString(httpEntity);

			// ���ַ���תΪJSONArray
			JSONArray jsonArray = new JSONArray(jsonString);
			Blingcoord blingcoord;

			for (int i = 0; i < jsonArray.length(); i++) {
				/**
				 * ���������ص�������JSONArray����JSONArray�����в�ͬ��JSONObject��
				 * ��JSONObject�С�fields�����ƺ����ֵ��JSONObject�� ������Ҫ�ľ������JSONObject
				 */
				JSONObject jsonObj = jsonArray.getJSONObject(i).getJSONObject(
						"fields");
				// ��ʼ��Blingcoord
				blingcoord = new Blingcoord();

				// ���jsonObj�е�"name"���ƺ����ֵ����������blingcoord�е�name����
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

				// ��blingcoord�ŵ�blingArrayList��ȥ
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
