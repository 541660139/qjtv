package com.lwd.qjtv.mvp.model.entity;

import com.google.gson.Gson;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/8/16.
 */

public class VideoParse {

	/**
	 * code : 0
	 * cost : 35.343
	 * data : {"duration":2679.95,"streams":[{"quality":"SD","segs":[{"duration":377.6,"size":13729590},{"duration":402.267,"size":13548474},{"duration":365.666,"size":14767976},{"duration":362.6,"size":9673508},{"duration":414.2,"size":16036431},{"duration":354.8,"size":12196084},{"duration":402.734,"size":16254135}],"type":"FLV"},{"quality":"SD","segs":[{"duration":2679.867,"size":96206198}],"subType":"FLV","type":"M3U8"},{"quality":"HD","segs":[{"duration":406.72,"size":27314744},{"duration":406.16,"size":27154226},{"duration":367.16,"size":30124045},{"duration":387.12,"size":18869686},{"duration":405.28,"size":30359357},{"duration":337.2,"size":21729352},{"duration":370.12,"size":29002206}],"type":"MP4"},{"quality":"HD","segs":[{"duration":2679.76,"size":184553616}],"subType":"MP4","type":"M3U8"},{"quality":"SD","segs":[{"duration":391.4,"size":11345803},{"duration":208.6,"size":10895939}],"type":"MP4"},{"quality":"SD","segs":[{"duration":2680.065,"size":78427612}],"subType":"MP4","type":"M3U8"}],"title":"我可能不会爱你 TV版"}
	 * message : success
	 * type : vod
	 */

	private int code;
	private double cost;
	private DataBean data;
	private String message;
	private String type;

	public static VideoParse objectFromData(String str) {

		return new Gson().fromJson(str, VideoParse.class);
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	public DataBean getData() {
		return data;
	}

	public void setData(DataBean data) {
		this.data = data;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public static class DataBean {
		/**
		 * duration : 2679.95
		 * streams : [{"quality":"SD","segs":[{"duration":377.6,"size":13729590},{"duration":402.267,"size":13548474},{"duration":365.666,"size":14767976},{"duration":362.6,"size":9673508},{"duration":414.2,"size":16036431},{"duration":354.8,"size":12196084},{"duration":402.734,"size":16254135}],"type":"FLV"},{"quality":"SD","segs":[{"duration":2679.867,"size":96206198}],"subType":"FLV","type":"M3U8"},{"quality":"HD","segs":[{"duration":406.72,"size":27314744},{"duration":406.16,"size":27154226},{"duration":367.16,"size":30124045},{"duration":387.12,"size":18869686},{"duration":405.28,"size":30359357},{"duration":337.2,"size":21729352},{"duration":370.12,"size":29002206}],"type":"MP4"},{"quality":"HD","segs":[{"duration":2679.76,"size":184553616}],"subType":"MP4","type":"M3U8"},{"quality":"SD","segs":[{"duration":391.4,"size":11345803},{"duration":208.6,"size":10895939}],"type":"MP4"},{"quality":"SD","segs":[{"duration":2680.065,"size":78427612}],"subType":"MP4","type":"M3U8"}]
		 * title : 我可能不会爱你 TV版
		 */

		private double duration;
		private String title;
		private List<StreamsBean> streams;

		public static DataBean objectFromData(String str) {

			return new Gson().fromJson(str, DataBean.class);
		}

		public double getDuration() {
			return duration;
		}

		public void setDuration(double duration) {
			this.duration = duration;
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public List<StreamsBean> getStreams() {
			return streams;
		}

		public void setStreams(List<StreamsBean> streams) {
			this.streams = streams;
		}

		public static class StreamsBean implements Serializable{
			/**
			 * platform : "MOBILE_APP"
			 * quality : SD
			 * segs : [{"duration":377.6,"size":13729590},{"duration":402.267,"size":13548474},{"duration":365.666,"size":14767976},{"duration":362.6,"size":9673508},{"duration":414.2,"size":16036431},{"duration":354.8,"size":12196084},{"duration":402.734,"size":16254135}]
			 * type : FLV
			 * subType : FLV
			 * audioCodec 音频
			 * headers  List<String></String>头部信息
			 */

			private String quality;
			private String type;
			private String subType;
			private List<SegsBean> segs;
			private String audioCodec;
			private ArrayList<String> headers;
			private String platform;

			public String getPlatform() {
				return platform;
			}

			public void setPlatform(String platform) {
				this.platform = platform;
			}

			public String getAudioCodec() {
				return audioCodec;
			}

			public void setAudioCodec(String audioCodec) {
				this.audioCodec = audioCodec;
			}

			public ArrayList<String> getHeaders() {
				return headers;
			}

			public void setHeaders(ArrayList<String> headers) {
				this.headers = headers;
			}

			public static StreamsBean objectFromData(String str) {

				return new Gson().fromJson(str, StreamsBean.class);
			}

			public String getQuality() {
				return quality;
			}

			public void setQuality(String quality) {
				this.quality = quality;
			}

			public String getType() {
				return type;
			}

			public void setType(String type) {
				this.type = type;
			}

			public String getSubType() {
				return subType;
			}

			public void setSubType(String subType) {
				this.subType = subType;
			}

			public List<SegsBean> getSegs() {
				return segs;
			}

			public void setSegs(List<SegsBean> segs) {
				this.segs = segs;
			}

			public static class SegsBean implements Serializable{
				/**
				 * duration : 377.6
				 * size : 13729590
				 * url
				 * backupUrl
				 */

				private double duration;
				private int size;
				private String backupUrl;
				private String url;

				public String getBackupUrl() {
					return backupUrl;
				}

				public void setBackupUrl(String backupUrl) {
					this.backupUrl = backupUrl;
				}

				public String getUrl() {
					return url;
				}

				public void setUrl(String url) {
					this.url = url;
				}

				public static SegsBean objectFromData(String str) {

					return new Gson().fromJson(str, SegsBean.class);
				}

				public double getDuration() {
					return duration;
				}

				public void setDuration(double duration) {
					this.duration = duration;
				}

				public int getSize() {
					return size;
				}

				public void setSize(int size) {
					this.size = size;
				}
			}
		}
	}
}
