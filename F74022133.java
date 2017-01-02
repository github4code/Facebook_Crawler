import java.io.*; 
import java.net.URL;
import java.net.MalformedURLException;

import org.voltdb.client.Client;
import org.voltdb.client.ClientFactory;

public class F74022133{

	public static Client myApp;

	public static void DoQuery(String sql){
	  
		  try{
			  myApp.callProcedure("@AdHoc", sql);
			  return ;
		  } catch (Exception e) {
			  e.printStackTrace();
			  return;
		  }
     }

     public static void main(String []args){

		myApp = ClientFactory.createClient();
		try{
			myApp.createConnection("127.0.0.1");
		}catch(Exception e){
			return;
		}

		DoQuery("CREATE TABLE F74022133 (GID VARCHAR, UTALK BIGINT, ULIKE BIGINT)");

        try{

			String originUrl = "https://www.facebook.com/pg/";
			FileReader fr = new FileReader("IdList.txt");
			BufferedReader br = new BufferedReader(fr);
			BufferedReader buffer;
		    
			String pageId, inputLine, htmlText, fb_talks, fb_likes;
			String _substr, sub;
			URL url;
			int head, tail;
			int count=0;
            
			while (br.ready()) {

				System.out.println("---------"+ ++count + "---------");
				
				pageId = br.readLine();

				url = new URL( originUrl + pageId + "/likes/?ref=page_internal");
				System.out.println(url);
		        url.openConnection();

				try{
		        	buffer = new BufferedReader(new InputStreamReader(url.openStream()));
				}catch (MalformedURLException e) {
				    continue;
				} catch (IOException e) {
				    continue;
				}

		        htmlText = "";
		        while ((inputLine = buffer.readLine()) != null) {
					
		            htmlText = htmlText + inputLine;
		        }
		        buffer.close();

				if ( htmlText.contains("renderFollowsData") == false ) // invalid-fanpage
					continue;
				/* Cut temp substring */
				head = htmlText.indexOf("renderFollowsData");
				tail = htmlText.indexOf("\"ScriptPath\"");
				_substr = htmlText.substring(head, tail);

				/* Cut fb talks */
				tail = _substr.length()-7;
				head = tail-30;
 				sub = _substr.substring(head, tail);
				head = sub.lastIndexOf(",");
				fb_talks = sub.substring(head+1);

				/* Cut fb likes */
				head = _substr.indexOf("\"__m\":\"__elem_a588f507_0_0\"");
				tail = _substr.indexOf("PagesLikesTab");
				sub = _substr.substring(0, tail-8);
				head = sub.lastIndexOf(",");
				fb_likes = sub.substring(head+1);

				DoQuery("INSERT INTO F74022133 (GID, UTALK, ULIKE) VALUES ('" + pageId + "'," + fb_talks + "," + fb_likes +");");
				System.out.println(fb_talks);
				System.out.println(fb_likes);
			 }
			 fr.close();
        } 
        catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
     }
}