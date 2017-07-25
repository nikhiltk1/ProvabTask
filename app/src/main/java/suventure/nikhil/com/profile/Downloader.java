package suventure.nikhil.com.profile;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * Created by suventure on 25/7/17.
 */
public class Downloader
{

	public static final String TAG = "DOWNLOADER";


	/**
	 * This function makes a api call and returns the Json Object.
	 * Only POST, PUT api call's should be made
	 * @param apiName
	 * @param httpMethod
	 * @param token
	 * @param data
	 * @return JSONObject
	 * @throws Exception
	 */
	public static JSONObject downloadData(String apiName, String httpMethod, String token, String data) throws Exception
	{
		HttpURLConnection connection = null;
		
		try
		{
			URL url = new URL( apiName);

			Log.d(TAG, "Token : " + token);
			Log.d(TAG, "Link : " + url.toString());
			Log.d(TAG, "Data : " + data.toString());


			connection = (HttpURLConnection) url.openConnection();

	        if(connection != null)
	        {
	            connection.setReadTimeout(8000);
				connection.setConnectTimeout(8000);
				connection.setRequestMethod(httpMethod);
				connection.setDoInput(true);
				connection.setDoOutput(true);
				connection.setFixedLengthStreamingMode(data.toString().getBytes().length);

				connection.setRequestProperty("Content-length", String.valueOf(data.toString().getBytes().length));
				connection.setRequestProperty("Content-Type", "application/json");	// ;charset=utf-8

				if (token != null)
				{
					connection.setRequestProperty("token",token);
					//connection.setRequestProperty("token", "Bearer " + token);
				}

					// http://stackoverflow.com/questions/11968033/android-post-request-400-response-code-throws-exception
				if (connection != null)
				{
					Log.d(TAG, "Input : " + data.toString());

					connection.connect();

					OutputStream os = new BufferedOutputStream(connection.getOutputStream());
					os.write(data.toString().getBytes());

					os.flush();
					if (connection.getResponseCode() == HttpURLConnection.HTTP_OK)
					{
						data = convertInputStream_ToString(connection.getInputStream());

						Log.d(TAG, "200, Output : " + data);
					}
					else if (connection.getResponseCode() == HttpURLConnection.HTTP_CREATED)
					{
						data = convertInputStream_ToString(connection.getInputStream());

						Log.d(TAG, "200, Output : " + data);
					}
					else if(connection.getResponseCode() == HttpURLConnection.HTTP_BAD_REQUEST)
					{
						data = convertInputStream_ToString(connection.getErrorStream());

						Log.e(TAG, "400, Output : " + data);
					}
					else if(connection.getResponseCode() == HttpURLConnection.HTTP_UNAUTHORIZED)
					{
						data = convertInputStream_ToString(connection.getErrorStream());

						Log.e(TAG, "401, Output : " + data);


					}
					else if(connection.getResponseCode() == HttpURLConnection.HTTP_FORBIDDEN)
					{
						data = convertInputStream_ToString(connection.getErrorStream());

						Log.e(TAG, "403, Output : " + data);
					}
					else
					{
						data = convertInputStream_ToString(connection.getErrorStream());

						Log.e(TAG, String.valueOf(connection.getResponseCode()) + ", Output : " + data);
					}

					if (android.text.TextUtils.isEmpty(data))
						return null;
					else
						return new JSONObject(data);
				}
	        }			
		}catch (EOFException e){
			e.printStackTrace();
			downloadData(apiName,httpMethod,token,data);
		}
		catch(Exception ex)
		{
			throw ex;
		}
		finally
		{
			if(connection!=null)
				connection.disconnect();
		}
		
		return null;
	}



	/**
	 * This function makes a api call and returns the Json Object.
	 * Only GET, DELETE api call's should be made
	 * @param apiName
	 * @param httpMethod
	 * @return JSONObject
	 * @throws Exception
	 */
	public static String downloadData_GET(String apiName, String httpMethod) throws Exception
	{
		HttpURLConnection connection = null;

		try
		{
			URL url = new URL( apiName);

			Log.d(TAG, "Link : " + url.toString());

			connection = (HttpURLConnection) url.openConnection();
			if(connection !=null)
			{
				connection.setReadTimeout(8000);
				connection.setConnectTimeout(8000);
				connection.setRequestMethod(httpMethod);
				connection.setUseCaches(false);
				connection.setDoInput(true);
				connection.setDoOutput(false);
				connection.setRequestProperty("Content-Type", "application/json");




				if(connection != null)
				{
					Log.d(TAG, "Response Code : " + String.valueOf(connection.getResponseCode()));

					String data = null;

					if (connection.getResponseCode() == HttpURLConnection.HTTP_OK)
					{
						data = convertInputStream_ToString(connection.getInputStream());

						Log.d(TAG, "200, Output : " + data);
					}
					else if(connection.getResponseCode() == HttpURLConnection.HTTP_BAD_REQUEST)
					{
						data = convertInputStream_ToString(connection.getErrorStream());

						Log.e(TAG, "400, Output : " + data);
					}
					else if(connection.getResponseCode() == HttpURLConnection.HTTP_UNAUTHORIZED)
					{
						data = convertInputStream_ToString(connection.getErrorStream());

						Log.e(TAG, "401, Output : " + data);
		// Make it to null since user is not logged in or session is expired

					}
					else
					{
						data = convertInputStream_ToString(connection.getErrorStream());

						Log.e(TAG, String.valueOf(connection.getResponseCode()) + ", Output : " + data);
					}

					if (android.text.TextUtils.isEmpty(data))
						return null;
					else

					return data;

				}

			}
		}catch (EOFException e){
			e.printStackTrace();
		}
		catch(Exception ex)
		{
			throw ex;
		}
		finally
		{
			if(connection!=null)
				connection.disconnect();
		}

		return null;
	}




	/**
	 * This function converts InputStream to String
	 * @param inputStream
	 * @return String
	 * @throws IOException
	 */
	public static String convertInputStream_ToString(InputStream inputStream) throws IOException
	{
		InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

		/*
		  To convert the InputStream to String we use the BufferedReader.readLine() method.
		  We iterate until the BufferedReader return null which means there's no more data to read.
		  Each line will appended to a StringBuilder and returned as String.
		 */

		BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
		String line = null;
		StringBuilder builder = new StringBuilder();

		while ((line = bufferedReader.readLine()) != null)
		{
			builder.append(line);
		}

		bufferedReader.close();

		if (builder.length() == 0)
		{
			return null;
		}
		else
		{
			return builder.toString();
		}
	}








}
