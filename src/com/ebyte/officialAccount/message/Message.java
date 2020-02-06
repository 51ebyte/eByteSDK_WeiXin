package com.ebyte.officialAccount.message;

import java.util.ArrayList;
import java.util.Map;

import com.ebyte.officialAccount.Factory;
import com.ebyte.officialAccount.message.type.*;
/**
 * 消息基类
 * @author Administrator
 *
 */
public class Message extends Factory {

	public Text text = new Text();
	public Image image = new Image();
	public Voice voice = new Voice();
	public Video video = new Video();
	public Music music = new Music();
	public News news = new News();

	public String text(String text) throws Exception {
		return new Text().setContent(text);
	}

	public String setText(String text) throws Exception {
		return text(text);
	}

	public String image(String MediaId) throws Exception {
		return new Image().setMediaId(MediaId);
	}

	public String setImage(String mediaId) throws Exception {
		return image(mediaId);
	}

	public String voice(String MediaId) throws Exception {
		return new Voice().setMediaId(MediaId);
	}

	public String setVoice(String mediaId) throws Exception {
		return voice(mediaId);
	}

	public String video(String MediaId) throws Exception {
		return new Video().setMediaId(MediaId);
	}

	public String video(String MediaId, String Title) throws Exception {
		return new Video().setVideo(MediaId, Title);
	}

	public String video(String MediaId, String Title, String Description) throws Exception {
		return new Video().setVideo(MediaId, Title, Description);
	}

	public String setVideo(String mediaId) throws Exception {
		return video(mediaId);
	}

	public String setVideo(String mediaId, String title) throws Exception {
		return video(mediaId, title);
	}

	public String setVideo(String mediaId, String title, String description) throws Exception {
		return video(mediaId, title, description);
	}

	public String music(String ThumbMediaId) throws Exception {
		return new Music().setMediaId(ThumbMediaId);
	}

	public String music(String ThumbMediaId, String Title) throws Exception {
		return new Music().setMusic(ThumbMediaId, Title);
	}

	public String music(String ThumbMediaId, String Title, String Description) throws Exception {
		return new Music().setMusic(ThumbMediaId, Title, Description);
	}

	public String music(String ThumbMediaId, String Title, String Description, String MusicURL) throws Exception {
		return new Music().setMusic(ThumbMediaId, Title, Description, MusicURL);
	}

	public String music(String ThumbMediaId, String Title, String Description, String MusicURL, String HQMusicUrl)
			throws Exception {
		return new Music().setMusic(ThumbMediaId, Title, Description, MusicURL, HQMusicUrl);
	}

	public String setMusic(String ThumbMediaId) throws Exception {
		return music(ThumbMediaId);
	}

	public String setMusic(String ThumbMediaId, String Title) throws Exception {
		return music(ThumbMediaId, Title);
	}

	public String setMusic(String ThumbMediaId, String Title, String Description) throws Exception {
		return music(ThumbMediaId, Title, Description);
	}

	public String setMusic(String ThumbMediaId, String Title, String Description, String MusicURL) throws Exception {
		return music(ThumbMediaId, Title, Description, MusicURL);
	}

	public String setMusic(String ThumbMediaId, String Title, String Description, String MusicURL, String HQMusicUrl)
			throws Exception {
		return music(ThumbMediaId, Title, Description, MusicURL, HQMusicUrl);
	}

	public String news(ArrayList<Map<String, Object>> list) throws Exception {
		return new News().set(list);
	}

}
